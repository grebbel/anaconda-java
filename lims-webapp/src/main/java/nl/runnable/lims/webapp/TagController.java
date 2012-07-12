package nl.runnable.lims.webapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import nl.runnable.lims.domain.Tag;
import nl.runnable.lims.domain.TagRepository;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Provides reference data and an update endpoint for front-end widgets that need to show {@link Tag}s.
 * 
 * @author Laurens Fridael
 * 
 */
@Controller
public class TagController {

	public static enum Format {
		DEFAULT, AUTOCOMPLETE
	};

	/* Dependencies */

	@Inject
	private TagRepository tagRepository;

	/* Operations */

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	@ResponseBody
	public List<?> index(@RequestParam(value = "format", defaultValue = "DEFAULT") final Format format,
			@RequestParam(defaultValue = "false") final boolean valueOnly,
			@RequestParam(required = false) final String term) {
		final List<Tag> tags;
		if (StringUtils.hasText(term) == false) {
			tags = tagRepository.findAllSortByName();
		} else {
			tags = tagRepository.findByNameLike(term + "%");
		}
		List<?> result = tags;
		switch (format) {
		case AUTOCOMPLETE:
			result = convertToAutocompleteItems(tags, valueOnly);
			break;
		case DEFAULT:
			// Fall through
		default:
			result = tags;
			break;
		}
		return result;
	}

	@RequestMapping(value = "/tags", method = RequestMethod.POST)
	@ResponseBody
	public List<String> create(@RequestBody final List<String> names) {
		for (final Iterator<String> it = names.iterator(); it.hasNext();) {
			final String name = it.next();
			final Tag tag = tagRepository.findByName(name);
			if (tag == null) {
				tagRepository.save(new Tag(name));
			} else {
				it.remove();
			}
		}
		return names;
	}

	/* Utility operations */

	private List<Option> convertToAutocompleteItems(final List<Tag> tags, final boolean valueOnly) {
		final List<Option> items = new ArrayList<Option>(tags.size());
		for (final Tag tag : tags) {
			final Object id = valueOnly ? tag.getName() : tag.getId();
			items.add(new Option(id, tag.getName(), tag.getCategory()));
		}
		return items;
	}
}
