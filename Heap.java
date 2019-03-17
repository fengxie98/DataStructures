import java.util.ArrayList;
import java.util.Collections;

public class Heap<T extends Comparable<T>>
{
	private ArrayList<T> heap;
	private ArrayList<T> values;
	private int          size;

	public void setValues(ArrayList<T> values)
	{
		this.values = new ArrayList<>(values);
		heap = new ArrayList<>(values.size() + 1);

		for(int i = 1; i <= values.size() + 1; ++i) heap.add(null);

		size = values.size();
	}

	public int buildSequential()
	{
		int swaps = 0;
		int index = 1;

		for(T value: values) {
			boolean done = false;
			int current_index = index;

			heap.set(index, value);

			while(!done) {
				int parent_index = getParent(current_index);

				if(parent_index == 0) {
					/* this is the first element inserted, or the new root */
					done = true;
				}
				else {
					if(value.compareTo(heap.get(parent_index)) < 0) {
						/* the parent is greater; no more swapping */
						done = true;
					}
					else {
						Collections.swap(heap, parent_index, current_index);
						current_index = parent_index;
						++swaps;
					}
				}
			}

			++index;
		}

		return swaps;
	}

	public int buildOptimal()
	{
		int swaps = 0;

		heap = new ArrayList<>(values);

		heap.add(0, null);
		size = values.size();

		int start = getParent(size);

		while(start > 0) swaps += moveDown(start--);

		return swaps;
	}

	public void deleteRoot()
	{
		if(size == 0) return;
		else if(size == 1) {
			heap.set(1, null);
			size = 0;
		}

		heap.set(1, heap.get(size));
		heap.set(size, null);

		--size;

		moveDown(1);
	}

	public String asString(int count)
	{
		String result = "";

		for(int i = 1; i <= Math.min(count, heap.size() - 1); ++i) {
			result += heap.get(i).toString() + ",";
		}

		return result;
	}

	private int moveDown(int index)
	{
		int swaps = 0;
		boolean done = false;

		while(!done) {
			int left_index = getLeft(index);
			int right_index = getRight(index);
			int higher_index;

			if(left_index > size && right_index > size) {
				/* this item does not have any valid children -> a leaf node */
				done = true;
				break;
			}
			else if(left_index > size) higher_index = right_index;
			else if(right_index > size) higher_index = left_index;
			else {
				if(heap.get(left_index).compareTo(heap.get(right_index)) > 0) higher_index = left_index;
				else higher_index = right_index;
			}

			if(heap.get(index).compareTo(heap.get(higher_index)) < 0) {
				Collections.swap(heap, index, higher_index);
				++swaps;
				index = higher_index;
			}
			else {
				/* the node is in its proper place */
				done = true;
			}
		}

		return swaps;
	}

	private int getParent(int index)
	{
		return index / 2;
	}

	private int getLeft(int index)
	{
		return index * 2;
	}

	private int getRight(int index)
	{
		return index * 2 + 1;
	}
}
