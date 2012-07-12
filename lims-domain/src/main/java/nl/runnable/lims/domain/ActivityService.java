package nl.runnable.lims.domain;

public interface ActivityService {

	void registerRequestSubmitted(Request request);

	void registerRequestUpdated(Request request);

	void registerRequestRemoved(Request request);

	void registerTargetCreated(Target target);

	void registerTargetUpdated(Target target);

	void registerTargetRemoved(Target target);

	void registerTagCreated(Tag tag);

	void registerTagRemoved(Tag tag);

}
