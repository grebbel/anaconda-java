package nl.runnable.lims.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.runnable.lims.domain.Target;
import nl.runnable.lims.domain.TargetRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Provides reference data and an update endpoint for front-end widgets that need to show {@link Target}s.
 * 
 * @author Laurens Fridael
 * 
 */
@Controller
public class TargetController {

	@Inject
	private TargetRepository targetRepository;

	@RequestMapping("/targets")
	@ResponseBody
	public List<Option> index() {
		final List<Target> targets = targetRepository.findAll();
		final List<Option> options = new ArrayList<Option>(targets.size());
		for (final Target target : targets) {
			options.add(new Option(target.getId(), target.getName()));
		}
		Collections.sort(options);
		return options;
	}

}
