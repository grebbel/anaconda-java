package nl.runnable.lims.webapp.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import nl.runnable.lims.domain.Amplification;
import nl.runnable.lims.domain.Analysis;
import nl.runnable.lims.domain.AnalysisRepository;
import nl.runnable.lims.domain.Grouping;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Defines {@link Analysis} query and update operations.
 * 
 * @author Laurens Fridael
 * 
 */
@Controller
public class AnalysisController {

	private static final String REQUEST_IDS_PARAM = "requestIds[]";

	/* Dependencies */

	@Inject
	private AnalysisRepository analysisRepository;

	/* Show operations */

	/**
	 * Handles an index request for {@link Analysis} instances. This handler supports a request parameter that
	 * determines whether to include amplification data.
	 * 
	 * @param pageable
	 * @param tags
	 *            The tags to filter against.
	 * @param includeAmplifications
	 *            Indicates whether to include amplification data in the output.
	 * @return
	 */
	@RequestMapping(value = "/analyses")
	@ResponseBody
	public Result<Analysis> index(final Pageable pageable,
			@RequestParam(value = "tags[]", required = false) final Set<String> tags,
			@RequestParam(value = "amplifications", defaultValue = "false") final boolean includeAmplifications) {
		final Page<Analysis> page;
		if (CollectionUtils.isEmpty(tags)) {
			page = analysisRepository.findAll(pageable);
		} else {
			page = analysisRepository.findAllWithAnyTags(pageable, tags);
		}
		if (includeAmplifications == false) {
			for (final Analysis analysis : page.getContent()) {
				analysis.setAmplifications(Collections.<Amplification> emptyList());
			}
		}
		return Result.forPageAndItems(page);
	}

	/* Query operations */

	@RequestMapping("/analyses/query/without-amplifications")
	@ResponseBody
	public Result<Analysis> queryWithoutAmplifications() {
		return Result.forItems(analysisRepository.findAllWithoutAmplifications());
	}

	@RequestMapping("/analyses/query/by-request")
	@ResponseBody
	public List<Grouping<Analysis>> queryByRequest(@RequestParam(REQUEST_IDS_PARAM) final Collection<Long> requestIds) {
		final List<Analysis> analyses = analysisRepository.findByRequestIds(requestIds);
		Long currentRequestId = null;
		final List<Grouping<Analysis>> groups = new ArrayList<Grouping<Analysis>>();
		for (final Analysis analysis : analyses) {
			if (analysis.getRequestId().equals(currentRequestId) == false) {
				groups.add(new Grouping<Analysis>(analysis.getRequestDescription()));
				currentRequestId = analysis.getRequestId();
			}
			groups.get(groups.size() - 1).getItems().add(analysis);
		}
		return groups;
	}

	/* Update operations */

	@RequestMapping(value = "/analyses/status", method = { RequestMethod.POST, RequestMethod.PUT })
	@ResponseBody
	public void updateStatus(@Valid @RequestBody final AnalysisStatusUpdateRequest request,
			final BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Invalid analysis update request.");
		}
		for (final AnalysisStatusUpdate statusUpdate : request.getUpdates()) {
			final Analysis analysis = analysisRepository.findOne(statusUpdate.getId());
			// For now we ignore references to non-existing Analyses.
			if (analysis != null) {
				if (statusUpdate.getTreshold() != null) {
					analysis.setTreshold(statusUpdate.getTreshold());
					analysis.setCt(analysis.calculateCt());
				}
				if (statusUpdate.getStatus() != null) {
					analysis.setStatus(statusUpdate.getStatus());
				}
				analysisRepository.save(analysis);
			}
		}
	}

	@RequestMapping(value = "/analyses/{id}/amplifications", method = RequestMethod.PUT)
	public void importAmplifications(@PathVariable("id") final long id,
			@RequestBody final List<Map<String, Number>> amplifications, final HttpServletResponse response)
			throws IOException {
		final Analysis analysis = analysisRepository.findOne(id);
		if (analysis != null) {
			analysis.setAmplifications(createAmplifications(amplifications));
			analysisRepository.saveAndFlush(analysis);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public void badRequest(final IllegalArgumentException e, final HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
	}

	/* Utility operations */

	private List<Amplification> createAmplifications(final List<Map<String, Number>> items) {
		final List<Amplification> amplifications = new ArrayList<Amplification>(items.size());
		for (final Map<String, Number> item : items) {
			final int cycle = item.get("cycle").intValue();
			final double rn = item.get("rn").doubleValue();
			final double drn = item.get("drn").doubleValue();
			final Amplification amplification = Amplification.forCycleRnAndDrn(cycle, rn, drn);
			amplifications.add(amplification);
		}
		return amplifications;
	}

}
