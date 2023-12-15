//EMMANUELLA EYO    CMPT 280     EEE917

package lib280.tree;

import lib280.base.Dispenser280;
import lib280.exception.ContainerFull280Exception;
import lib280.exception.DuplicateItems280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class ArrayedHeap280<I extends Comparable<? super I>> extends ArrayedBinaryTree280<I> implements Dispenser280<I> {
    /**
     * Constructor.
     *
     * @param cap Maximum number of elements that can be in the lib280.tree.
     */
    public ArrayedHeap280(int cap) {
        super(cap);
        items = (I[]) new Comparable[capacity + 1];
    }

    @Override
    public void insert(I x) throws ContainerFull280Exception, DuplicateItems280Exception {
        if( this.isFull() ) throw new ContainerFull280Exception("Cannot add item to a tree that is full.");
        else {
            // If the cursor is in the after position at the array offset
            // where the new item is about to be inserted, move it over so
            // that the cursor stays in the after position.
            count ++;
            items[count] = x;
        }
        this.currentNode = 1;

        if(count == 1)
            return;

        int i = count;
        while(i > 1 && items[i].compareTo(items[findParent(i)])>0){

            int parent = findParent(i);
            I tempP = items[parent];
            items[parent] = items[i];
            items[i] = tempP;
            i = parent;

        }

    }

    @Override
    public void deleteItem() throws NoCurrentItem280Exception {

        // If we aren't deleting the bottom-right-most node (last node in the array),
        // overwrite the current node with the last node.

        if(this.count > 1)
            this.currentNode = 1;
            this.items[this.currentNode] = this.items[this.count];

        // Either way, erase the bottom-right-most node
        this.items[this.count] = null;
        this.count--;

        // If we deleted the last item, move the cursor back one.
        if( this.count == 0) {
            this.currentNode = 0;
        }


        int i = 1;
        while(findLeftChild(i) < count){

            // check if left child is less than right child, choose right child
            int child = findLeftChild(i);

            if(child <= count && items[child].compareTo(items[findRightChild(i)]) < 0) {
                child = findRightChild(i);
            }

            if(items[i].compareTo(items[child]) < 0) {

                I temp = items[i];
                items[i] = items[child];
                items[child] = temp;
                i = child;
            }
        }
    }

    /**
     * Helper for the regression test.  Verifies the heap property for all nodes.
     */
    private boolean hasHeapProperty() {
        for(int i=1; i <= count; i++) {
            if( findRightChild(i) <= count ) {  // if i Has two children...
                // ... and i is smaller than either of them, , then the heap property is violated.
                if( items[i].compareTo(items[findRightChild(i)]) < 0 ) return false;
                if( items[i].compareTo(items[findLeftChild(i)]) < 0 ) return false;
            }
            else if( findLeftChild(i) <= count ) {  // if n has one child...
                // ... and i is smaller than it, then the heap property is violated.
                if( items[i].compareTo(items[findLeftChild(i)]) < 0 ) return false;
            }
            else break;  // Neither child exists.  So we're done.
        }
        return true;
    }

    /**
     * Regression test
     */
    public static void main(String[] args) {

        ArrayedHeap280<Integer> H = new ArrayedHeap280<Integer>(10);

        // Empty heap should have the heap property.
        if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");

        // Insert items 1 through 10, checking after each insertion that
        // the heap property is retained, and that the top of the heap is correctly i.
        for(int i = 1; i <= 10; i++) {
            H.insert(i);
            if(H.item() != i) System.out.println("Expected current item to be " + i + ", got " + H.item());
            if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");
        }

        // Remove the elements 10 through 1 from the heap, chekcing
        // after each deletion that the heap property is retained and that
        // the correct item is at the top of the heap.
        for(int i = 10; i >= 1; i--) {
            // Remove the element i.
            H.deleteItem();
            // If we've removed item 1, the heap should be empty.
            if(i==1) {
                if( !H.isEmpty() ) System.out.println("Expected the heap to be empty, but it wasn't.");
            }
            else {
                // Otherwise, the item left at the top of the heap should be equal to i-1.
                if(H.item() != i-1) System.out.println("Expected current item to be " + i + ", got " + H.item());
                if(!H.hasHeapProperty()) System.out.println("Does not have heap property.");
            }
        }

        System.out.println("Regression Test Complete.");
    }
}
