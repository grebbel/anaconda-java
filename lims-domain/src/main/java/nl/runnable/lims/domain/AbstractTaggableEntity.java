package nl.runnable.lims.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

@MappedSuperclass
public class AbstractTaggableEntity extends AbstractEntity {

	@ManyToMany
	@JoinTable(name = "target_tag", joinColumns = @JoinColumn(name = "target_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	@OrderBy("name")
	private Set<Tag> tags = new HashSet<Tag>();

	@JsonIgnore
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(final Set<Tag> tags) {
		this.tags = tags;
	}

	@JsonProperty(value = "tags")
	public Collection<String> getTagNames() {
		final List<String> tagNames = new ArrayList<String>();
		for (final Tag tag : getTags()) {
			tagNames.add(tag.getName());
		}
		return tagNames;
	}

}
