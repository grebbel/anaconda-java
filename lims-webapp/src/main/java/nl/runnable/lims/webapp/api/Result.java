package nl.runnable.lims.webapp.api;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

class Result<T> {

	static <T> Result<T> fromPage(final Page<T> page) {
		return new Result<T>(Metadata.forPage(page), page.getContent());
	}

	static <T> Result<T> forItems(final Collection<T> items) {
		return new Result<T>(new Metadata(1, 1, items.size(), items.size()), items);
	}

	static <T> Result<T> forMetadataAndItems(final Metadata metadata, final Collection<T> items) {
		return new Result<T>(metadata, items);
	}

	static <T> Result<T> forPageAndItems(final Page<?> page, final Collection<T> items) {
		return new Result<T>(Metadata.forPage(page), items);
	}

	@SuppressWarnings("unchecked")
	static <T> Result<T> forPageAndItems(final Page<?> page) {
		return new Result<T>(Metadata.forPage(page), (List<T>) page.getContent());
	}

	private final Metadata metadata;

	private final Collection<T> items;

	Result(final Metadata metadata, final Collection<T> items) {
		this.metadata = metadata;
		this.items = items;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public Collection<T> getItems() {
		return items;
	}

}
