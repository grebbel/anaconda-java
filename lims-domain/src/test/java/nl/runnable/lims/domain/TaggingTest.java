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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/jpa-integration-test-context.xml")
@Transactional
public class TaggingTest {

	@Inject
	private TagRepository tagRepository;

	@Inject
	private TargetRepository targetRepository;

	@Inject
	private AssayRepository assayRepository;

	@Before
	public void createFixture() {
		for (final String name : Arrays.asList("Influenzavirus A matrix", "Phocine distemper virus VIC",
				"Influenzavirus A H1")) {
			targetRepository.saveAndFlush(new Target(name));
		}
		tagRepository.save(new Tag("Respiratoir"));
		tagRepository.save(new Tag("Ardeno"));
		tagRepository.save(new Tag("MRSA"));
		createAssay();
	}

	private void createAssay() {
		final Assay assay = new Assay();
		for (final Target target : targetRepository.findByNameLike("%Influenza%")) {
			final AssayTarget assayTarget = new AssayTarget();
			assayTarget.setTarget(target);
			assayTarget.setTreshold(0.1);
			assay.addAssayTarget(assayTarget);
		}
		assay.setName("PCR H1N1 Mexicaanse griep");
		assayRepository.save(assay);
	}

	@Test
	public void testTargetTags() {
		assertEquals(3, tagRepository.count());
		final Tag tag = tagRepository.findByName("Respiratoir");
		for (final Target influenza : targetRepository.findByNameLike("%Influenza%")) {
			influenza.getTags().add(tag);
			targetRepository.saveAndFlush(influenza);
		}
		assertEquals(2, targetRepository.findByTagsNameIn(Arrays.asList(tag.getName())).size());
		final List<Assay> assays = assayRepository.findByTargetTags(Arrays.asList(tag.getName()));
		assertEquals(1, assays.size());
		System.out.println(assayRepository.findTags());
	}
}
