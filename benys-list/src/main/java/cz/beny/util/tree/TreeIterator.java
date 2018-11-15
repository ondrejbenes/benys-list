package cz.beny.util.tree;

import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

/**
 * Used to Iterate over a Tree that is made of {@link TreeNode}s
 * 
 */
public class TreeIterator<T, E> {
	public Iterator<TreeNode<T, E>> iterator(final TreeNode<T, E> root) {
		return new Iterator<TreeNode<T,E>>() {
			private Stack<TreeNode<T, E>> stack = new Stack<>();
			private boolean rootIterated = false;

			@Override
			public boolean hasNext() {
				return !stack.isEmpty() || !rootIterated;
			}

			@Override
			public TreeNode<T, E> next() {
				if(!rootIterated) {
					stack.add(root);
					rootIterated = true;
				}
				TreeNode<T, E> cur = stack.pop();
				if(!cur.getChildren().isEmpty()) {
					Collections.reverse(cur.getChildren());
					for(TreeNode<T, E> node : cur.getChildren()) {
						stack.add(node);
					}
					Collections.reverse(cur.getChildren());
				}
				return cur;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
