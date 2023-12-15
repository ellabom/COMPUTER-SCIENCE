//EMMANUELLA EYO	11291003	CMPT280		eee917
package lib280.dispenser;

import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.ContainerFull280Exception;
import lib280.tree.ArrayedBinaryTreeIterator280;
import lib280.tree.IterableArrayedHeap280;

public class PriorityQueue280<I extends Comparable<? super I>> {
	
	// This is the heap that we are restricting.
	// Items in the priority queue get stored in the heap.
	protected IterableArrayedHeap280<I> items;
	
	
	/**
	 * Create a new priorty queue with a given capacity.
	 * @param cap The maximum number of items that can be in the queue.
	 */
	public PriorityQueue280(int cap) {
		items = new IterableArrayedHeap280<I>(cap);
	}
	
	public String toString() {
		return items.toString();	
	}

	// TODO
	// Add Priority Queue ADT methods (from the specification) here.

	/**
	 * Insert item into t in priority order
	 * @precond !q.isFull
	 * @param item value to be inserted
	 * @throws ContainerFull280Exception q cannot be full
	 */
	public void insert(I item) throws ContainerFull280Exception {
		items.insert(item);
	}

	/**
	 * Check if Queue is full
	 * @return true if t is full, false otherwise
	 */
	public boolean isFull(){ return items.isFull();}

	/**
	 * Check if Queue is empty
	 * @return true if t is empty, false otherise
	 */
	public boolean isEmpty(){ return items.count() == 0;}

	/**
	 * Obtain number of items in q
	 * @return number of items in queue
	 */
	public int count(){
		return items.count();
	}

	/**
	 * returns the largest item in q
	 * @precond !q.isEmpty
	 * @throws ContainerEmpty280Exception if queue is empty
	 * @return the largest item in q
	 */
	public I maxItem() throws ContainerEmpty280Exception {
		if(items.isEmpty()){
			throw new ContainerEmpty280Exception("Cannot obtain highest priority item from queue");
		}

		ArrayedBinaryTreeIterator280<I> iterator = items.iterator();
		//highest priority is always at the root
		iterator.goFirst();				// move cursor to the first item
		return iterator.item();			// return item

	}

	/**
	 * Obtain the smallest item in q
	 * @return the smallest lowest priority item in q
	 * @precond !p.isEmpty
	 * @throws ContainerEmpty280Exception is q is empty
	 */
	public I minItem() throws ContainerEmpty280Exception{
		if(this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot obtain the lowest priority item from empty queue");


		ArrayedBinaryTreeIterator280<I> iterator = items.iterator();
		iterator.goFirst();

		if(this.count() == 1){
			return iterator.item();
		}

		I lowest = iterator.item();
		// lowest priority is in the last element
		while(iterator.itemExists()){

			if(iterator.item().compareTo(lowest) < 0){
				lowest = iterator.item();
			}

			iterator.goForth();
		}

		return lowest;
	}

	/**
	 * remove the largest item in the q from q
	 * @precond !q.isEmpty()
	 * @throws ContainerEmpty280Exception if queue is empty
	 */
	public void deleteMax() throws ContainerEmpty280Exception{
		if(items.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete in an empty queue");

		ArrayedBinaryTreeIterator280<I> iterator = items.iterator();
		iterator.goFirst();

		items.deleteItem();
	}

	/**
	 * Remove the lowest item in the queue
	 * @precond: !q.isEmpty
	 * @throws ContainerEmpty280Exception is queue is emptuy
	 */
	public void deleteMin() throws ContainerEmpty280Exception{
		if(this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete lowest priority in an empty queue");

		ArrayedBinaryTreeIterator280<I> iterator = items.iterator();
		I min = this.minItem();


		//while itemExists
		//if iterator.item is not equal to minimum, goForth()
		while(iterator.itemExists() && iterator.item().compareTo(min) != 0)
				iterator.goForth();

		items.deleteAtPosition(iterator);
	}

	/**
	 * remove all occurrences of the highest priority item are deleted from q
	 * @precond !q.isEmpty
	 * @throws ContainerEmpty280Exception is q is empty
	 */
	public void deleteAllMax() throws ContainerEmpty280Exception{
		if(this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete in an empty queue");

		ArrayedBinaryTreeIterator280<I> iterator = items.iterator();
		iterator.goFirst();
		I max = this.maxItem();

		while(items.itemExists() && iterator.item().compareTo(max) == 0)
			deleteMax();


	}



	public static void main(String args[]) {
		class PriorityItem<I> implements Comparable<PriorityItem<I>> {
			I item;
			Double priority;
						
			public PriorityItem(I item, Double priority) {
				super();
				this.item = item;
				this.priority = priority;
			}

			public int compareTo(PriorityItem<I> o) {
				return this.priority.compareTo(o.priority);
			}
			
			public String toString() {
				return this.item + ":" + this.priority;
			}
		}
		
		PriorityQueue280<PriorityItem<String>> Q = new PriorityQueue280<PriorityItem<String>>(5);
		
		// Test isEmpty()
		if( !Q.isEmpty()) 
			System.out.println("Error: Queue is empty, but isEmpty() says it isn't.");
		
		// Test insert() and maxItem()
		Q.insert(new PriorityItem<String>("Sing", 5.0));
		if( Q.maxItem().item.compareTo("Sing") != 0) {
			System.out.println("??Error: Front of queue should be 'Sing' but it's not. It is: " + Q.maxItem().item);
		}
		
		// Test isEmpty() when queue not empty
		if( Q.isEmpty()) 
			System.out.println("Error: Queue is not empty, but isEmpty() says it is.");
		
		// test count()
		if( Q.count() != 1 ) {
			System.out.println("Error: Count should be 1 but it's not.");			
		}

		// test minItem() with one element
		if( Q.minItem().item.compareTo("Sing")!=0) {
			System.out.println("Error: min priority item should be 'Sing' but it's not.");
		}	
		
		// insert more items
		Q.insert(new PriorityItem<String>("Fly", 5.0));
		if( Q.maxItem().item.compareTo("Sing")!=0) System.out.println("Front of queue should be 'Sing' but it's not.");
		Q.insert(new PriorityItem<String>("Dance", 3.0));
		if( Q.maxItem().item.compareTo("Sing")!=0) System.out.println("Front of queue should be 'Sing' but it's not.");
		Q.insert(new PriorityItem<String>("Jump", 7.0));
		if( Q.maxItem().item.compareTo("Jump")!=0) System.out.println("Front of queue should be 'Jump' but it's not.");

		if(Q.minItem().item.compareTo("Dance") != 0) System.out.println("minItem() should be 'Dance' but it's not.");

		if( Q.count() != 4 ) {
			System.out.println("Error: Count should be 4 but it's not.");
		}
		
		// Test isFull() when not full
		if( Q.isFull()) 
			System.out.println("Error: Queue is not full, but isFull() says it is.");
		
		Q.insert(new PriorityItem<String>("Eat", 10.0));
		if( Q.maxItem().item.compareTo("Eat")!=0) System.out.println("Front of queue should be 'Eat' but it's not.");

		if( !Q.isFull()) 
			System.out.println("Error: Queue is full, but isFull() says it isn't.");

		// Test insertion on full queue
		try {
			Q.insert(new PriorityItem<String>("Sleep", 15.0));
			System.out.println("Expected ContainerFull280Exception inserting to full queue but got none.");
		}
		catch(ContainerFull280Exception e) {
			// Expected exception
		}
		catch(Exception e) {
			System.out.println("Expected ContainerFull280Exception inserting to full queue but got a different exception.");
			e.printStackTrace();
		}
		
		// test deleteMin
		Q.deleteMin();
		if(Q.minItem().item.compareTo("Sing") != 0) System.out.println("Min item should be 'Sing', but it isn't.");

		Q.insert(new PriorityItem<String>("Dig", 1.0));
		if(Q.minItem().item.compareTo("Dig") != 0) System.out.println("minItem() should be 'Dig' but it's not.");

		// Test deleteMax
		Q.deleteMax();
		if( Q.maxItem().item.compareTo("Jump")!=0) System.out.println("Front of queue should be 'Jump' but it's not.");

		Q.deleteMax();
		if( Q.maxItem().item.compareTo("Fly")!=0) System.out.println("Front of queue should be 'Fly' but it's not.");

		if(Q.minItem().item.compareTo("Dig") != 0) System.out.println("minItem() should be 'Dig' but it's not.");

		Q.deleteMin();
		if( Q.maxItem().item.compareTo("Fly")!=0) System.out.println("Front of queue should be 'Fly' but it's not.");

		Q.insert(new PriorityItem<String>("Scream", 2.0));
		Q.insert(new PriorityItem<String>("Run", 2.0));

		if( Q.maxItem().item.compareTo("Fly")!=0) System.out.println("Front of queue should be 'Fly' but it's not.");
		// test deleteAllMax()
		Q.deleteAllMax();
		if( Q.maxItem().item.compareTo("Scream")!=0) System.out.println("Front of queue should be 'Scream' but it's not.");
		if( Q.minItem().item.compareTo("Scream") != 0) System.out.println("minItem() should be 'Scream' but it's not.");
		Q.deleteAllMax();

		// Queue should now be empty again.
		if( !Q.isEmpty())
			System.out.println("Error: Queue is empty, but isEmpty() says it isn't.");
		System.out.println("Regression test complete.");
	}


}
