

/**
 * 
 * Bharat Radhakrishnan
 * G01034025
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class ExBst<T extends Comparable<T>> implements Iterable<T> {
	public static void main(String[] args) throws IOException {
		BufferedReader scanner = new BufferedReader(new FileReader(args[0]));
		ExBst<Integer> bst = new ExBst<Integer>();

		Map<String, Integer> items = new TreeMap<String, Integer>();

		String line;

		String newLine = System.getProperty("line.separator");

		while (null != (line = scanner.readLine())) {
			// String _line = line.substring(0, 10);

			String[] line_parts = line.split("\\s+");
			if (line_parts.length > 1) {
				StringBuilder desc = new StringBuilder(line_parts[0]);
				desc.setLength(1);
				for (int i = 2; i < line_parts.length; i++) {
					desc.append(line_parts[i]);
				}

				if (!line_parts[1].toString().contains("//")) {
					items.put(desc.toString(), Integer.parseInt((line_parts[1])));
				} else {
					items.put(desc.toString(), 0);
				}

			}

			for (Map.Entry<String, Integer> entry : items.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();

				if (key.contains("I")) {
					if (!bst.search(value)) {
						System.out.println("insert node " + value);
						bst.insert(value);
						System.out.println(value + " inserted");
					}
				}

				if (key.contains("F")) {
					int indicator;
					if (bst.search(value)) {
						indicator = 0;
						System.out.println("The value is found " + indicator );
					} else {
						indicator = -1;
						System.out.println("The value is not found " + indicator);
					}
				}

				if (key.contains("E")) {
					bst.resetBinaryTree();
					System.out.println(newLine+"The binary Tree is empty");
				}

				if (key.contains("R")) {
					bst.delete(value);
				}
				if (key.contains("D")) {

					bst.printInorder();

				}

			}

		}

	}

	void printInorder()

	{
		printInorder(root);
	}

	private Node<T> root;
	private Comparator<T> comparator;

	public ExBst() {
		root = null;
		comparator = null;
	}

	public void resetBinaryTree() {
		root = null;
	}

	public ExBst(Comparator<T> comp) {
		root = null;
		comparator = comp;
	}

	private int compare(T x, T y) {
		if (comparator == null)
			return x.compareTo(y);
		else
			return comparator.compare(x, y);
	}

	void printInorder(Node<T> node) {
		String newLine = System.getProperty("line.separator");
		if (node == null)
			return;

		/* first recur on left child */
		printInorder(node.left);

		/* then print the data of node */
		System.out.print( node.data + " ");

		/* now recur on right child */
		printInorder(node.right);
	}

	public void insert(T data) {
		root = insert(root, data);
	}

	private Node<T> insert(Node<T> p, T toInsert) {
		if (p == null)
			return new Node<T>(toInsert);

		if (compare(toInsert, p.data) == 0)
			return p;

		if (compare(toInsert, p.data) < 0)
			p.left = insert(p.left, toInsert);
		else
			p.right = insert(p.right, toInsert);
		return p;

	}

	public boolean search(T toSearch) {
		return search(root, toSearch);
	}

	private boolean search(Node<T> p, T toSearch) {
		if (p == null)
			return false;
		else if (compare(toSearch, p.data) == 0)
			return true;
		else if (compare(toSearch, p.data) < 0)
			return search(p.left, toSearch);
		else
			return search(p.right, toSearch);
	}

	public void delete(T toDelete) {
		root = delete(root, toDelete);
	}

	private Node<T> delete(Node<T> p, T toDelete) {

		if (compare(toDelete, p.data) < 0)
			p.left = delete(p.left, toDelete);
		else if (compare(toDelete, p.data) > 0) {
			if (!(p.right != null))
				p.right = delete(p.right, toDelete);
		} else {
			if (p.left == null)
				return p.right;
			else if (p.right == null)
				return p.left;
			else {
				// get data from the rightmost node in the left subtree
				p.data = retrieveData(p.left);
				// delete the rightmost node in the left subtree
				p.left = delete(p.left, p.data);
			}
		}
		return p;
	}

	private T retrieveData(Node<T> p) {
		while (p.right != null)
			p = p.right;

		return p.data;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (T data : this)
			sb.append(data.toString());

		return sb.toString();
	}

	public Iterator<T> iterator() {
		return new MyIterator();
	}

	// pre-order
	private class MyIterator implements Iterator<T> {
		Stack<Node<T>> stk = new Stack<Node<T>>();

		public MyIterator() {
			if (root != null)
				stk.push(root);
		}

		public boolean hasNext() {
			return !stk.isEmpty();
		}

		public T next() {
			Node<T> cur = stk.peek();
			if (cur.left != null) {
				stk.push(cur.left);
			} else {
				Node<T> tmp = stk.pop();
				while (tmp.right == null) {
					if (stk.isEmpty())
						return cur.data;
					tmp = stk.pop();
				}
				stk.push(tmp.right);
			}

			return cur.data;
		}// end of next()

	}

	

}// end of BST

class MyComparator implements Comparator<Integer> {
	public int compare(Integer x, Integer y) {
		return y - x;
	}

}

class Node<T extends Comparable<?>> {
	Node<T> left, right;
	T data;

	public Node(T data) {
		this.data = data;
	}
}
