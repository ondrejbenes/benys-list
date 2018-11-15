package cz.beny.util.tree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TreeNodeTest {

	@SuppressWarnings("unused")
	@Test
	public void test() {
		TreeNode<String, Integer> root = new TreeNode<String, Integer>("root");
		root.getElements().add(new Integer(0));
		TreeNode<String, Integer> t1 = new TreeNode<String, Integer>(root, "t1", new Integer(1), new Integer(2), new Integer(3), new Integer(4));
		TreeNode<String, Integer> t2 = new TreeNode<String, Integer>(root, "t2", new Integer(5), new Integer(6), new Integer(7));
		TreeNode<String, Integer> t3 = new TreeNode<String, Integer>(t2, "t3", new Integer(8), new Integer(9));
		
		StringBuilder builder = new StringBuilder();
		
		for(TreeNode<String, Integer> node : root) {
			for (Integer integer : node.getElements()) {
				builder.append(integer);			
			}
		}
		
		assertEquals("0123456789", builder.toString());
	}
}
