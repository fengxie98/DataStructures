import java.util.ArrayList;

public class BSTNode<T extends Comparable<T>>
{
	private T          value;
	private BSTNode<T> left;
	private BSTNode<T> right;

	public BSTNode(T value)
	{
		this.value = value;
		left = null;
		right = null;
	}

	public T getValue()
	{
		return value;
	}

	public BSTNode<T> getLeft()
	{
		return left;
	}

	public BSTNode<T> getRight()
	{
		return right;
	}

	public void setValue(T value)
	{
		this.value = value;
	}

	public void setLeft(BSTNode<T> node)
	{
		left = node;
	}

	public void setRight(BSTNode<T> node)
	{
		right = node;
	}

	public boolean hasLeft()
	{
		return left != null;
	}

	public boolean hasRight()
	{
		return right != null;
	}

	public boolean isLeaf()
	{
		return left == null && right == null;
	}

	public String toString()
	{
		return value.toString();
	}
}
