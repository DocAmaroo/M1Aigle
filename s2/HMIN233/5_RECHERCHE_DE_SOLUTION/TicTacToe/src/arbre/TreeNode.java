package src.arbre;

import java.util.ArrayList;
import java.util.LinkedList;

public class TreeNode<T> {

	private ArrayList<TreeNode<T>> children = new ArrayList<>();
	private T data = null;
	private TreeNode<T> parent;

	public TreeNode(){}

	public TreeNode(T data) {
		this.parent = null;
		this.data = data;
	}

	public TreeNode(TreeNode<T> parent, T data) {
		this.parent = parent;
		this.data = data;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T in) {
		this.data = in;
	}

	public TreeNode<T> getParent()
	{
		return parent;
	}

	public ArrayList<TreeNode<T>> getChildren() {
		return children;
	}	

	public void addChild(TreeNode<T> child) {
		children.add(child);
	}

	public void batchAddChildren(ArrayList<T> data) {
		for (T child : data) {
			this.children.add(new TreeNode<T>(this, child));
		}
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}

	public static void breathFirstDisplay (TreeNode<?> racine) {
		LinkedList<TreeNode<?>> file = new LinkedList<>();
		file.addLast(racine);

		while (!file.isEmpty()) {
			TreeNode<?> head = file.poll();
			System.out.println(head);
			if (!head.isLeaf()) file.addAll(head.getChildren());
		}
	}

	public static void depthFirstDisplay (TreeNode<?> racine) {
		System.out.println(racine);
		for (TreeNode<?> child : racine.getChildren()) {
			depthFirstDisplay(child);
		}
	}

	@Override
	public String toString() {
		return this.data.toString();
//		StringBuilder res = new StringBuilder();
//		res.append("\nNode: ").append(this.data);
//		res.append("\nParent: ").append((this.getParent() != null) ? this.getParent().getData() : "/");
//		res.append("\nEnfant: ");
//		if (getChildren() == null) { res.append("/"); }
//		else {
//			res.append("[ ");
//			int i = 0; for (TreeNode<T> child : children) {
//				res.append(child.getData());
//				if (++i < children.size()) { res.append(", "); }
//			}
//			res.append(" ]");
//		}
//
//		return res.toString();
	}
}
