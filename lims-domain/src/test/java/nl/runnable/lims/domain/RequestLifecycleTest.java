package nl.runnable.lims.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test for verifying the lifecycle of a {@link Request}.
 * 
 * @author Laurens Fridael
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/jpa-integration-test-context.xml")
@Transactional
public class RequestLifecycleTest {

	@Inject
	private TargetRepository targetRepository;

	@Inject
	private AssayRepository assayRepository;

	@Inject
	private RequestRepository requestRepository;

	@Inject
	private AnalysisRepository analysisRepository;

	@Inject
	private AnalysisService analysisService;

	@Before
	public void createReferenceData() {
		createTargets();
		createAssay();
	}

	@Test
	public void testLifecycle() {
		assertEquals(0, analysisRepository.findAllWithoutAmplifications().size());
		final Request request = createRequest();
		requestRepository.saveAndFlush(request);
		assertEquals(3, analysisRepository.findAllWithoutAmplifications().size());
		requestRepository.deleteAll();
		analysisRepository.findByRequestIds(Arrays.asList(0l));
	}

	private Request createRequest() {
		final Request request = new Request();
		request.setExternalId("123");
		final List<Target> targets = targetRepository.findAll();
		final List<Analysis> analyses = analysisService.resolveAnalysesByTargets(targets);
		for (int i = 0; i < 3; i++) {
			final Analysis analysis = analyses.get(i);
			assertEquals(targets.get(i), analysis.getAssayTarget().getTarget());
			request.addAnalysis(analysis);
		}
		return request;

	}

	/* Utility operations */

	private void createTargets() {
		for (final String name : Arrays.asList("Influenzavirus A matrix", "Phocine distemper virus VIC",
				"Influenzavirus A H1")) {
			targetRepository.saveAndFlush(new Target(name));
		}

	}

	private void createAssay() {
		final Assay assay = new Assay();
		for (final Target target : targetRepository.findAll()) {
			final AssayTarget assayTarget = new AssayTarget();
			assayTarget.setTarget(target);
			assayTarget.setTreshold(0.1);
			assay.addAssayTarget(assayTarget);
		}
		assay.setName("PCR H1N1 Mexicaanse griep");
		assayRepository.saveAndFlush(assay);

	}

}
