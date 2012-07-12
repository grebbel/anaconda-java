package nl.runnable.lims.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query("SELECT activity FROM Activity activity WHERE importance >= ?1 ORDER BY date DESC")
	public Page<Activity> findAll(Pageable pageable, int minImportance);

	@Query("SELECT activity FROM Activity activity WHERE activity.date >= ?1 AND activity.date <= ?2 AND importance >= ?3 ORDER BY date DESC")
	public Page<Activity> findByDateRange(Pageable pageable, Date from, Date to, int minImportance);

	@Query("SELECT activity FROM Activity activity WHERE activity.resourceLink.resourceType = ?1 AND activity.resourceLink.resourceId = ?2")
	public List<Activity> findByResourceLink(String resourceType, Long resourceId);
}
