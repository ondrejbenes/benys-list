package cz.beny.list.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import cz.beny.list.CommonConstants;

/*
 * Entity Representing a Category.
 */
@Entity
public class Category {
	@Id
	private Long id;
	
	private String name;	
	
	@Index
	private int order = 0;
	
	@Index
	private Long parentCategoryId;
	
	private static Category root;
	
	static {
		root = new Category();
		root.setId(CommonConstants.ROOT_CATEGORY_ID);
		root.setName("root");		
	}
		
	public static Category getRoot() {
		return root;
	}

	public Category() {

	}
	
	public Category(String name, Long parentCategoryId, int order) {
		this.name = name;
		this.order = order;
		this.setParentCategoryId(parentCategoryId);
	}

	public Category(Long id, Long parentCategoryId, int order, String name) {
		this.id = id;
		this.name = name;
		this.order = order;
		this.parentCategoryId = parentCategoryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
}
