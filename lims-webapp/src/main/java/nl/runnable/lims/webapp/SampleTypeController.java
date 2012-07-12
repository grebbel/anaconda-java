package nl.runnable.lims.webapp;

import java.util.List;

import javax.inject.Inject;

import nl.runnable.lims.domain.SampleTypeRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleTypeController {

	@Inject
	private SampleTypeRepository sampleTypeRepository;

	@RequestMapping("/sample-types")
	@ResponseBody
	public List<Option> index() {
		return Option.createFromTypes(sampleTypeRepository.findAll());
	}
}
