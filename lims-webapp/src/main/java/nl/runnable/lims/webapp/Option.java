package nl.runnable.lims.webapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.runnable.lims.domain.AbstractType;

import org.springframework.util.Assert;

class Option implements Comparable<Option> {

	public static List<Option> createFromTypes(final Collection<? extends AbstractType> types) {
		final List<Option> options = new ArrayList<Option>();
		for (final AbstractType type : types) {
			options.add(new Option(type.getId(), type.getName()));
		}
		Collections.sort(options);
		return options;
	}

	private final Object value;

	private final String label;

	private final String category;

	Option(final Object value, final String label, final String category) {
		Assert.notNull(value);
		Assert.hasText(label);
		this.value = value;
		this.label = label;
		this.category = category;
	}

	Option(final Object value, final String label) {
		this(value, label, null);
	}

	public Object getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public String getCategory() {
		return category;
	}

	@Override
	public int compareTo(final Option o) {
		return this.getLabel().compareToIgnoreCase(o.getLabel());
	}
}
