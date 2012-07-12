package nl.runnable.lims.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssayTargetRepository extends JpaRepository<AssayTarget, Long> {

	public List<AssayTarget> findByTargetIn(Target target);

}
