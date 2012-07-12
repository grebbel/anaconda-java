package nl.runnable.lims.webapp.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.runnable.lims.domain.Activity;
import nl.runnable.lims.domain.ActivityRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/activities")
public class ActivityController {

	/* Dependencies */

	@Inject
	private ActivityRepository activityRepository;

	@PersistenceContext
	private EntityManager entityManager;

	/* Operations */

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Result<ActivityItem> index(final Pageable pageable, @RequestParam(required = false) final Long from,
			@RequestParam(required = false) final Long to,
			@RequestParam(required = false, defaultValue = "0") final int importance) {
		final Date dateFrom = from != null ? new Date(from) : null;
		final Date dateTo = to != null ? new Date(to) : new Date();
		final Page<Activity> activities;
		if (dateFrom != null && dateTo != null) {
			activities = activityRepository.findByDateRange(pageable, dateFrom, dateTo, importance);
		} else {
			activities = activityRepository.findAll(pageable, importance);
		}
		final List<ActivityItem> items = new ArrayList<ActivityItem>(activities.getContent().size());
		for (final Activity activity : activities.getContent()) {
			items.add(new ActivityItem(activity, entityManager));
		}
		return Result.forPageAndItems(activities, items);
	}

}
