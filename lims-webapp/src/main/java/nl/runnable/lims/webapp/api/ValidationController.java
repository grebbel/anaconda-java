package nl.runnable.lims.webapp.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.runnable.lims.domain.AbstractEntity;
import nl.runnable.lims.domain.SampleTypeRepository;
import nl.runnable.lims.domain.Tag;
import nl.runnable.lims.domain.TagRepository;
import nl.runnable.lims.domain.TargetRepository;
import nl.runnable.lims.domain.TargetTypeRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ValidationController {

	@Inject
	private TargetRepository targetRepository;

	@Inject
	private TagRepository tagRepository;

	@Inject
	private SampleTypeRepository sampleTypeRepository;

	@Inject
	private TargetTypeRepository targetTypeRepository;

	@RequestMapping("/targets/validations/name-unique")
	@ResponseBody
	public boolean isTargetNameUnique(@RequestParam(required = false) final Long id, @RequestParam final String name) {
		return targetRepository.findDuplicateNames(id, Arrays.asList(name)).isEmpty();
	}

	@RequestMapping("/tags/validations/name-unique")
	@ResponseBody
	public boolean isTagNameUnique(@RequestParam(required = false) final Long id, @RequestParam final String name) {
		return tagRepository.findDuplicateNames(id, Arrays.asList(name)).isEmpty();
	}

	@RequestMapping("/tags/validations/non-existing-names")
	@ResponseBody
	public Collection<String> nonExistingTagNames(@RequestParam("names[]") final Set<String> names) {
		final List<Tag> existingTags = tagRepository.findDistinctByNameIn(names);
		for (final Tag tag : existingTags) {
			names.remove(tag.getName());
		}
		return names;
	}

	@RequestMapping("/sample-types/validations/name-unique")
	@ResponseBody
	public boolean isSampleTypeNameUnique(@RequestParam(required = false) final Long id, @RequestParam final String name) {
		return sampleTypeRepository.findDuplicateNames(id, Arrays.asList(name)).isEmpty();
	}

	@RequestMapping("/sample-types/validations/codes-unique")
	@ResponseBody
	public boolean areSampleTypeCodesUnique(@RequestParam(required = false) final Long id,
			@RequestParam("codes[]") final Set<String> codes) {
		return hasDuplicate(id, sampleTypeRepository.findByCodesIn(codes));
	}

	@RequestMapping("/target-types/validations/name-unique")
	@ResponseBody
	public boolean isTargetTypeNameUnique(@RequestParam(required = false) final Long id, @RequestParam final String name) {
		return targetTypeRepository.findDuplicateNames(id, Arrays.asList(name)).isEmpty();
	}

	@RequestMapping("/target-types/validations/codes-unique")
	@ResponseBody
	public boolean areTargetTypeCodesUnique(@RequestParam(required = false) final Long id,
			@RequestParam("codes[]") final Set<String> codes) {
		return hasDuplicate(id, targetTypeRepository.findByCodesIn(codes));
	}

	/* Utility operations */

	private boolean hasDuplicate(final Long id, final List<? extends AbstractEntity> entities) {
		if (id == null) {
			return entities.isEmpty();
		} else {
			boolean unique = true;
			for (final AbstractEntity entity : entities) {
				if (entity.getId().equals(id) == false) {
					unique = false;
				}
			}
			return unique;
		}
	}

}
