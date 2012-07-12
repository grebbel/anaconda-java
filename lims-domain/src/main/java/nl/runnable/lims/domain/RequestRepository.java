package nl.runnable.lims.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

	public Request findByWorkflowId(String workflowId);
}
