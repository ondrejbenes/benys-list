package cz.beny.util.tree;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a node of a Tree data structure.
 * @author Ondrej Benes
 *
 * @param <T> node's data
 * @param <E> node's elements
 */
public class TreeNode<T, E> implements Iterable<TreeNode<T, E>>{
	
	private TreeNode<T, E> parent;

	private T data;
	private List<TreeNode<T, E>> children = new LinkedList<>();
	private List<E> elements = new LinkedList<>();
	
	/**
	 * Constructor useful for creating root nodes.
	 * @param data
	 */
	public TreeNode(T data) {
		this.data = data; 
	}
	
	/**
	 * Contructor useful for creating nodes with no initial elements
	 * @param parent
	 * @param data
	 */
	public TreeNode(TreeNode<T, E> parent, T data) {
		this.parent = parent;
		this.data = data; 
	}
	
	/**
	 * Constructor useful for creating nodes with initial elements
	 * @param parent
	 * @param data
	 * @param elements
	 */
	@SafeVarargs
	public TreeNode(TreeNode<T, E> parent, T data, E... elements) {
		this.parent = parent;
		this.data = data;
		this.elements = Arrays.asList(elements);
		parent.addChild(this);
	}

	public TreeNode<T, E> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T, E> parent) {
		this.parent = parent;
	}
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<E> getElements() {
		return elements;
	}

	@SuppressWarnings("unchecked")
	public void setElements(E... elements) {
		this.elements = Arrays.asList(elements);
	}
	
	/**
	 * Adds passed node to the list of this node's children.
	 * It's the child's node responsibility to set it's parent
	 * reference to this node.
	 * @param child
	 */
	public void addChild(TreeNode<T, E> child) {
		children.add(child);
	}

	public List<TreeNode<T, E>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode<T, E>> children) {
		this.children = children;
	}
	
	public Iterator<TreeNode<T, E>> getIterator() {
		return iterator();
	}

	@Override
	public Iterator<TreeNode<T, E>> iterator() {
		return new TreeIterator<T, E>().iterator(this);
	}
	
	/**
	 * Returns the depth level of this node 
	 * (the distance to the root).
	 * @return
	 */
	public int getDepth() {
		int depth = 0;
		TreeNode<T, E> parent = this.parent;
		while(parent != null) {
			parent = parent.parent;
			depth++;
		}
		return depth;
	}
}
