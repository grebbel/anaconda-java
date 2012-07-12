package nl.runnable.lims.webapp.api;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import nl.runnable.lims.domain.Tag;
import nl.runnable.lims.webapp.AbstractEntityController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles entity index requests.
 * 
 * @author Laurens Fridael
 * 
 */
@Controller
public class EntityIndexController extends AbstractEntityController {

	private static final String TAGS_PARAM = "tags[]";

	/* Operations */

	@RequestMapping(value = "/{entityName}", method = RequestMethod.GET)
	@ResponseBody
	public Result<?> index(@PathVariable final String entityName, final Pageable pageable,
			@RequestParam(value = TAGS_PARAM, required = false) final Set<String> tags,
			final HttpServletResponse response) throws IOException {
		Result<?> result = null;

		Page<?> items = null;
		if (CollectionUtils.isEmpty(tags)) {
			items = getEntityDao().findAll(entityName, pageable);
		} else {
			if (getEntityDao().supportsTagFiltering(entityName)) {
				items = getEntityDao().findAllWithAnyTags(entityName, pageable, tags);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			}
		}
		if (items != null) {
			result = Result.forPageAndItems(items);
		}
		return result;
	}

	@RequestMapping("/{entityName}/tags")
	@ResponseBody
	public List<Tag> tags(@PathVariable final String entityName, final HttpServletResponse response) throws IOException {
		List<Tag> tags = null;
		if (getEntityDao().supportsTagFiltering(entityName)) {
			tags = getEntityDao().findTags(entityName);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return tags;
	}

}
