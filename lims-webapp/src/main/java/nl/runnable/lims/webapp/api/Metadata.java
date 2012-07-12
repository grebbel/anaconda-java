package nl.runnable.lims.webapp.api;

import java.util.Collection;

import nl.runnable.lims.domain.Tag;

import org.springframework.data.domain.Page;

class Metadata {

	static Metadata forPage(final Page<?> page) {
		return new Metadata(page.getNumber() + 1, page.getTotalPages(), page.getNumberOfElements(),
				page.getTotalElements());
	}

	private final int page;

	private final int pageCount;

	private final int itemCount;

	private final long totalCount;

	private Collection<Tag> tags;

	Metadata(final int page, final int pageCount, final int itemCount, final long totalCount) {
		this.page = page;
		this.pageCount = pageCount;
		this.itemCount = itemCount;
		this.totalCount = totalCount;
	}

	public int getPage() {
		return page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getItemCount() {
		return itemCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

}
