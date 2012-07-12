package nl.runnable.lims.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.ManagedBean;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@ManagedBean
public class SampleTypeUpdateHandler implements UpdateHandler<SampleTypeUpdate, SampleType> {

	@Override
	public void updateEntity(final SampleTypeUpdate update, final SampleType sampleType) {
		sampleType.setName(update.getName());
		sampleType.setDescription(update.getDescription());
		sampleType.setCodes(getCodes(update.getCodes()));
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
