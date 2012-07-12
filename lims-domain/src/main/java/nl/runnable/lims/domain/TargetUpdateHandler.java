package nl.runnable.lims.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@ManagedBean
public class TargetUpdateHandler implements UpdateHandler<TargetUpdate, Target> {

	@Inject
	private TagRepository tagRepository;

	@Inject
	private TargetTypeRepository targetTypeRepository;

	@Override
	public void updateEntity(final TargetUpdate update, final Target target) {
		target.setName(update.getName());
		if (update.getTargetTypeId() != null) {
			target.setTargetType(targetTypeRepository.findOne(update.getTargetTypeId()));
		}
		target.setDescription(update.getDescription());
		target.setTags(getTags(update.getTags()));
		target.setCodes(getCodes(update.getCodes()));
	}

	private Set<Tag> getTags(final List<String> tagNames) {
		return CollectionUtils.isEmpty(tagNames) ? Collections.<Tag> emptySet() : new HashSet<Tag>(
				tagRepository.findDistinctByNameIn(tagNames));
	}

	private Set<String> getCodes(List<String> codes) {
		if (CollectionUtils.isEmpty(codes) == false) {
			codes = new ArrayList<String>(codes);
			for (final Iterator<String> it = codes.iterator(); it.hasNext();) {
				if (StringUtils.hasText(it.next()) == false) {
					it.remove();
				}
			}
		}
		return new HashSet<String>(codes);
	}

}
