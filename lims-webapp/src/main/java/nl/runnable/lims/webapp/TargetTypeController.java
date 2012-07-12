package nl.runnable.lims.webapp;

import java.util.List;

import javax.inject.Inject;

import nl.runnable.lims.domain.TargetTypeRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TargetTypeController {

	@Inject
	private TargetTypeRepository targetTypeRepository;

	@RequestMapping("/target-types")
	@ResponseBody
	public List<Option> getTargetTypes() {
		return Option.createFromTypes(targetTypeRepository.findAll());

	}

}
