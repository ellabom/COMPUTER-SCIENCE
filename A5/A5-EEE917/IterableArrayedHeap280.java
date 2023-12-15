//EMMANUELLA EYO	eee917	CMPT 280	11291003
package lib280.tree;

import lib280.exception.NoCurrentItem280Exception;

public class IterableArrayedHeap280<I extends Comparable<? super I>> extends ArrayedHeap280<I> {

	/**
	 * Create an iterable heap with a given capacity.
	 * @param cap The maximum number of elements that can be in the heap.
	 */
	public IterableArrayedHeap280(int cap) {
		super(cap);
	}

	// TODO
	// Add iterator() and deleteAtPosition() methods here.
	public ArrayedBinaryTreeIterator280<I> iterator(){
		return new ArrayedBinaryTreeIterator280<I>(this);
	}

	/**
	 * delete a specific item in the heap (not the root)
	 */
	public void deleteAtPosition(ArrayedBinaryTreeIterator280<I> obj) throws NoCurrentItem280Exception {


		//if we aren't deleting the bottom-right-most node
		// overwrite the current node with the last node
		if (this.count > 1)
			this.items[obj.currentNode] = this.items[count];

		this.count --;


		//if we deleted the last item, move the item back onr
		if(this.count == 0){
			this.currentNode = 0;
		}


		int curNode = obj.currentNode;
		while(findLeftChild(curNode) < count){

			int child = findLeftChild(curNode);

			if(child <= count && items[child].compareTo(items[findRightChild(curNode)]) < 0){
				child = findRightChild(curNode);
			}

			if(items[curNode].compareTo(items[curNode]) <  0){

				I temp = items[curNode];
				items[curNode] = items[child];
				items[child] = temp;
				curNode = child;
			}
		}
	}
	
}
