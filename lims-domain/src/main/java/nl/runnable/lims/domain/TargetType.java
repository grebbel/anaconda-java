package nl.runnable.lims.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

@Entity
@Table(name = "target_type")
public class TargetType extends AbstractType {

	@ElementCollection(fetch = FetchType.LAZY)
	@JoinTable(name = "target_type_code", joinColumns = @JoinColumn(name = "target_type_id"))
	@Column(name = "code", nullable = false, unique = true)
	private Set<String> codes = new HashSet<String>();

	public void setCodes(final Set<String> codes) {
		this.codes = codes;
	}

	public Set<String> getCodes() {
		return codes;
	}

	public TargetType(final String name, final String... codes) {
		setName(name);
		getCodes().addAll(Arrays.asList(codes));
	}

	public TargetType() {
	}

}
