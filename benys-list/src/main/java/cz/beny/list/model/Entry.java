package cz.beny.list.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/*
 * Entity Representing an Entry.
 */
@Entity
public class Entry {
	
	@Id
	private Long id;

	private String hyperlink;
	private String note;
	
	@Index
	private int order = 0;
	
	@Index
	private Long categoryId;
	
	public Entry() {
		this.hyperlink = "";
		this.note = "";
	}
	
	public Entry(Long id) {
		this.id = id;
	}

	public Entry(String hyperlink, String note, Long categoryId, int order) {
		this.hyperlink = hyperlink;
		this.note = note;
		this.categoryId = categoryId;
		this.order = order;
	}
	
	public Entry(Long id, Long categoryId, int order, String hyperlink, String note) {
		super();
		this.id = id;
		this.hyperlink = hyperlink;
		this.note = note;
		this.order = order;
		this.categoryId = categoryId;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
