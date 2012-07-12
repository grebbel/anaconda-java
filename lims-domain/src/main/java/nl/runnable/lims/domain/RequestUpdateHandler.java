package nl.runnable.lims.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

@ManagedBean
public class RequestUpdateHandler implements UpdateHandler<RequestUpdate, Request> {

	@Inject
	private TargetRepository targetRepository;

	@Inject
	private SampleTypeRepository sampleTypeRepository;

	@Override
	public void updateEntity(final RequestUpdate update, final Request request) {
		request.setExternalId(update.getExternalId());
		request.setDescription(update.getDescription());
		final int msInOneDay = 60 * 60 * 1000 * 24;
		request.setDate(new Date(update.getDate() - (update.getDate() % msInOneDay)));
		request.setSampleType(sampleTypeRepository.findOne(update.getSampleTypeId()));
		final List<Target> targets = targetRepository.findByIdIn(update.getTargetIds());
		request.setTargets(new HashSet<Target>(targets));
	}

}
