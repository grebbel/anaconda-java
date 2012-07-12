package nl.runnable.lims.domain;

import javax.annotation.ManagedBean;

import org.springframework.util.StringUtils;

@ManagedBean
public class TagUpdateHandler implements UpdateHandler<TagUpdate, Tag> {

	@Override
	public void updateEntity(final TagUpdate update, final Tag entity) {
		entity.setName(update.getName());
		String category = update.getCategory();
		if (StringUtils.hasText(category) == false) {
			category = null;
		}
		entity.setCategory(category);
		entity.setDescription(update.getDescription());
	}

}
