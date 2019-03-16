import java.util.ArrayList;

/* 51 29 68 90 36 40 22 59 44 99 77 60 27 83 15 75 3 */

public class BST<T extends Comparable<T>>
{
	private BSTNode<T> root;

	public BST(ArrayList<T> values)
	{
		root = null;
		for(T value: values) insertNode(value);
	}

	public String insertNode(T value)
	{
		if(root == null) {
			root = new BSTNode<T>(value);
			return getInOrder();
		}

		if(!_insertNode(value, root)) return value.toString() + " already exists, ignore.";

		return getInOrder();
	}

	public String deleteNode(T value)
	{
		String no_exist = value.toString() + " doesn't exist!";

		if(root == null) return no_exist;

		if(value.compareTo(root.getValue()) == 0) {
			if(root.isLeaf()) root = null;
			else if(root.hasLeft() && !root.hasRight()) root = root.getLeft();
			else if(root.hasRight() && !root.hasLeft()) root = root.getRight();
			else {
				T new_value = getMaximum(root.getLeft()).getValue();

				_deleteNode(new_value, root.getLeft(), root);
				root.setValue(new_value);
			}

			return getInOrder();
		}

		if(!_deleteNode(value, root, null)) return no_exist;

		return getInOrder();
	}

	public String getPredecessor(T value)
	{
		return _getXcessor(true, value, root, null);
	}

	public String getSuccessor(T value)
	{
		return _getXcessor(false, value, root, null);
	}

	public String getPreOrder()
	{
		return "Pre-order: " + traverse(0, root);
	}

	public String getInOrder()
	{
		return "In-order: " + traverse(1, root);
	}

	public String getPostOrder()
	{
		return "Post-order: " + traverse(2, root);
	}

	private String traverse(int order, BSTNode root)
	{
		if(root == null) return "";

		String leftString  = traverse(order, root.getLeft());
		String rootString  = root.toString() + " ";
		String rightString = traverse(order, root.getRight());

		if(order == 0)      return rootString + leftString + rightString;
		else if(order == 1) return leftString + rootString + rightString;
		else                return leftString + rightString + rootString;
	}

	private BSTNode<T> getMinimum(BSTNode<T> root)
	{
		return root.hasLeft() ? getMinimum(root.getLeft()) : root;
	}

	private BSTNode<T> getMaximum(BSTNode<T> root)
	{
		return root.hasRight() ? getMaximum(root.getRight()) : root;
	}

	private String _getXcessor(boolean get_predecessor, T value, BSTNode<T> root, BSTNode<T> last_ancestor)
	{
		if(root == null) return value.toString() + " doesn't exist!";

		int comparison = value.compareTo(root.getValue());

		if(comparison == 0) {
			if(get_predecessor) {
				if(root.hasLeft()) return getMaximum(root.getLeft()).toString();
			}

			if(root.hasRight()) return getMinimum(root.getRight()).toString();

			if(last_ancestor == null) return value.toString() + " does not have a predecessor!";

			return last_ancestor.toString();
		}
		else if(comparison < 0) {
			if(!get_predecessor) last_ancestor = root;
			return _getXcessor(get_predecessor, value, root.getLeft(), last_ancestor);
		}

		if(get_predecessor) last_ancestor = root;

		return _getXcessor(get_predecessor, value, root.getRight(), last_ancestor);
	}

	private boolean _insertNode(T value, BSTNode<T> root)
	{
		int comparison = value.compareTo(root.getValue());

		if(comparison == 0) return false;
		else if(comparison < 0) {
			if(root.hasLeft()) return _insertNode(value, root.getLeft());

			root.setLeft(new BSTNode<T>(value));
			return true;
		}

		if(root.hasRight()) return _insertNode(value, root.getRight());

		root.setRight(new BSTNode<T>(value));
		return true;
	}

	private boolean _deleteNode(T value, BSTNode<T> root, BSTNode<T> parent)
	{
		if(root == null) return false;

		int comparison = value.compareTo(root.getValue());

		if(comparison == 0) {
			boolean is_left_child = root.getValue().compareTo(parent.getValue()) < 0;

			if(root.isLeaf()) {
				if(is_left_child) parent.setLeft(null);
				else              parent.setRight(null);
			}
			else if(root.hasLeft() && !root.hasRight()) {
				if(is_left_child) parent.setLeft(root.getLeft());
				else              parent.setRight(root.getLeft());
			}
			else if(root.hasRight() && !root.hasLeft()) {
				if(is_left_child) parent.setLeft(root.getRight());
				else              parent.setRight(root.getRight());
			}
			else {
				T new_value = getMaximum(root.getLeft()).getValue();

				_deleteNode(new_value, root.getLeft(), root);
				root.setValue(new_value);
			}

			return true;
		}
		else if(comparison < 0) return _deleteNode(value, root.getLeft(), root);

		return _deleteNode(value, root.getRight(), root);
	}
}
