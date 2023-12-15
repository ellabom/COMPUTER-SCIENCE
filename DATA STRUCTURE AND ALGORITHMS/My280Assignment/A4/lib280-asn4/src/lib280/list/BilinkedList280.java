//	EMMANUELLA EYO 11291003	EEE917	CMPT 280

package lib280.list;


import lib280.base.BilinearIterator280;
import lib280.base.CursorPosition280;
import lib280.base.Pair280;
import lib280.exception.*;

/**	This list class incorporates the functions of an iterated
 dictionary such as has, obtain, search, goFirst, goForth,
 deleteItem, etc.  It also has the capabilities to iterate backwards
 in the list, goLast and goBack. */
public class BilinkedList280<I> extends LinkedList280<I> implements BilinearIterator280<I>
{
	/* 	Note that because firstRemainder() and remainder() should not cut links of the original list,
		the previous node reference of firstNode is not always correct.
		Also, the instance variable prev is generally kept up to date, but may not always be correct.
		Use previousNode() instead! */

	/**	Construct an empty list.
	 Analysis: Time = O(1) */
	public BilinkedList280()
	{
		super();
	}

	/**
	 * Create a BilinkedNode280 this Bilinked list.  This routine should be
	 * overridden for classes that extend this class that need a specialized node.
	 * @param item - element to store in the new node
	 * @return a new node containing item
	 */
	protected BilinkedNode280<I> createNewNode(I item)
	{
		// TODO
		return new BilinkedNode280<I>(item);

	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list
	 */
	public void insertFirst(I x)
	{
		// TODO
		BilinkedNode280<I> newItem = createNewNode(x);
		newItem.setNextNode(this.head);


		if(!this.isEmpty() && this.position == this.head)
			this.prevPosition = newItem;

		if(this.isEmpty())
			this.tail = newItem;
		this.head = newItem;

	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list
	 */
	public void insert(I x)
	{
		this.insertFirst(x);
	}

	/**
	 * Insert an item before the current position.
	 * @param x - The item to be inserted.
	 */
	public void insertBefore(I x) throws InvalidState280Exception {
		if( this.before() ) throw new InvalidState280Exception("Cannot insertBefore() when the cursor is already before the first element.");

		// If the item goes at the beginning or the end, handle those special cases.
		if( this.head == position ) {
			insertFirst(x);  // special case - inserting before first element
		}
		else if( this.after() ) {
			insertLast(x);   // special case - inserting at the end
		}
		else {
			// Otherwise, insert the node between the current position and the previous position.
			BilinkedNode280<I> newNode = createNewNode(x);
			newNode.setNextNode(position);
			newNode.setPreviousNode((BilinkedNode280<I>)this.prevPosition);
			prevPosition.setNextNode(newNode);
			((BilinkedNode280<I>)this.position).setPreviousNode(newNode);

			// since position didn't change, but we changed it's predecessor, prevPosition needs to be updated to be the new previous node.
			prevPosition = newNode;
		}
	}


	/**	Insert x before the current position and make it current item. <br>
	 Analysis: Time = O(1)
	 @param x item to be inserted before the current position */
	public void insertPriorGo(I x)
	{
		this.insertBefore(x);
		this.goBack();
	}

	/**	Insert x after the current item. <br>
	 Analysis: Time = O(1)
	 @param x item to be inserted after the current position */
	public void insertNext(I x)
	{
		if (isEmpty() || before())
			insertFirst(x);
		else if (this.position==lastNode())
			insertLast(x);
		else if (after()) // if after then have to deal with previous node
		{
			insertLast(x);
			this.position = this.prevPosition.nextNode();
		}
		else // in the list, so create a node and set the pointers to the new node
		{
			BilinkedNode280<I> temp = createNewNode(x);
			temp.setNextNode(this.position.nextNode());
			temp.setPreviousNode((BilinkedNode280<I>)this.position);
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(temp);
			this.position.setNextNode(temp);
		}
	}

	/**
	 * Insert a new element at the end of the list
	 * @param x item to be inserted at the end of the list
	 */
	public void insertLast(I x)
	{
		// TODO
		BilinkedNode280<I> newItem = createNewNode(x);
		newItem.setNextNode(null);

		if(!isEmpty() && this.after())
			this.prevPosition = newItem;

		if(this.isEmpty()) {
			this.head = newItem;
			this.tail = newItem;
		}
		else{
			newItem.setPreviousNode((BilinkedNode280<I>)this.tail);
			this.tail.setNextNode(newItem);
			this.tail = newItem;
		}

	}

	/**
	 * Delete the item at which the cursor is positioned
	 * @precond itemExists() must be true (the cursor must be positioned at some element)
	 */
	public void deleteItem() throws NoCurrentItem280Exception
	{
		// TODO
		if(!this.itemExists()) {
			throw new NoCurrentItem280Exception("There is no item at the cursor to delete.");
		}
		if(this.position == this.head) {
			this.deleteFirst();
			this.position = this.head;
		}
		else{
			//set the previous node to the point to the successor node.
			this.prevPosition.setNextNode(this.position.nextNode);

			//set the successor's previous node to this position's previous node
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(((BilinkedNode280<I>) this.position).previousNode());

			if(this.position == this.tail) {
				this.tail = this.prevPosition;
			}
			this.position = this.position.nextNode();

		}
	}


	@Override
	public void delete(I x) throws ItemNotFound280Exception {
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete from an empty list.");

		// Save cursor position
		LinkedIterator280<I> savePos = this.currentPosition();

		// Find the item to be deleted.
		search(x);
		if( !this.itemExists() ) throw new ItemNotFound280Exception("Item to be deleted wasn't in the list.");

		// If we are about to delete the item that the cursor was pointing at,
		// advance the cursor in the saved position, but leave the predecessor where
		// it is because it will remain the predecessor.
		if( this.position == savePos.cur ) savePos.cur = savePos.cur.nextNode();

		// If we are about to delete the predecessor to the cursor, the predecessor
		// must be moved back one item.
		if( this.position == savePos.prev ) {

			// If savePos.prev is the first node, then the first node is being deleted
			// and savePos.prev has to be null.
			if( savePos.prev == this.head ) savePos.prev = null;
			else {
				// Otherwise, Find the node preceding savePos.prev
				LinkedNode280<I> tmp = this.head;
				while(tmp.nextNode() != savePos.prev) tmp = tmp.nextNode();

				// Update the cursor position to be restored.
				savePos.prev = tmp;
			}
		}

		// Unlink the node to be deleted.
		if( this.prevPosition != null)
			// Set previous node to point to next node.
			// Only do this if the node we are deleting is not the first one.
			this.prevPosition.setNextNode(this.position.nextNode());

		if( this.position.nextNode() != null )
			// Set next node to point to previous node
			// But only do this if we are not deleting the last node.
			((BilinkedNode280<I>)this.position.nextNode()).setPreviousNode(((BilinkedNode280<I>)this.position).previousNode());

		// If we deleted the first or last node (or both, in the case
		// that the list only contained one element), update head/tail.
		if( this.position == this.head ) this.head = this.head.nextNode();
		if( this.position == this.tail ) this.tail = this.prevPosition;

		// Clean up references in the node being deleted.
		this.position.setNextNode(null);
		((BilinkedNode280<I>)this.position).setPreviousNode(null);

		// Restore the old, possibly modified cursor.
		this.goPosition(savePos);

	}
	/**
	 * Remove the first item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteFirst() throws ContainerEmpty280Exception
	{
		// TODO
		if(this.isEmpty()){
			throw new ContainerEmpty280Exception("Cannot delete an item from an empty list.");
		}

		if(this.prevPosition == this.head)
			this.prevPosition = null;

			//if the cursor is on the second node, set the prev pointer to null
		else if(this.position == this.head)
			this.position = this.position.nextNode();

		//if it is only one element in the list, set the head and tail to null
		if(this.head == this.tail)
			this.tail = null;

		BilinkedNode280<I> oldhead = (BilinkedNode280<I>) this.head;
		this.head = this.head.nextNode();
		oldhead.setNextNode(null);
	}

	/**
	 * Remove the last item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteLast() throws ContainerEmpty280Exception {
		// TODO
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete an item from an array list.");
		else if (this.head != null && this.head == this.tail)
			this.deleteFirst();

		else{

			if(this.position == this.tail){
				if(prevPosition==head){
					tail = head;
					position = head;
					prevPosition = null;
					this.head.nextNode = null;
				}

				BilinkedNode280<I> newPrev = (BilinkedNode280<I>) this.head;
				while(newPrev.nextNode() != this.prevPosition)
					newPrev = (BilinkedNode280<I>) newPrev.nextNode();
				this.position = this.prevPosition;
				this.prevPosition = newPrev;
			}

			//second last node
			BilinkedNode280<I> penultimate = ((BilinkedNode280<I>) this.head);
			while(penultimate.nextNode() != this.tail)
				penultimate = (BilinkedNode280<I>) penultimate.nextNode();

			if(this.after()){
				this.prevPosition = penultimate;
			}

			penultimate.setNextNode(null);
			this.tail = penultimate;
		}
	}



	/**
	 * Move the cursor to the last item in the list.
	 * @precond The list is not empty.
	 */
	public void goLast() throws ContainerEmpty280Exception
	{
		// TODO
		if(isEmpty())
			throw new ContainerEmpty280Exception("Cannot go to the last postion in an empty list");

		this.position = this.tail;
	}

	/**	Move back one item in the list.
	 Analysis: Time = O(1)
	 @precond !before()
	 */
	public void goBack() throws BeforeTheStart280Exception
	{
		// TODO
		if(this.before())
			throw new BeforeTheStart280Exception("Cannot move to the previous element. ");

		this.position = ((BilinkedNode280<I>)this.position).previousNode;

		if(this.after())
			this.goLast();
	}

	/**	Iterator for list initialized to first item.
	 Analysis: Time = O(1)
	 */
	public BilinkedIterator280<I> iterator()
	{
		return new BilinkedIterator280<I>(this);
	}

	/**	Go to the position in the list specified by c. <br>
	 Analysis: Time = O(1)
	 @param c position to which to go */
	@SuppressWarnings("unchecked")
	public void goPosition(CursorPosition280 c)
	{
		if (!(c instanceof BilinkedIterator280))
			throw new InvalidArgument280Exception("The cursor position parameter"
					+ " must be a BilinkedIterator280<I>");
		BilinkedIterator280<I> lc = (BilinkedIterator280<I>) c;
		this.position = lc.cur;
		this.prevPosition = lc.prev;
	}

	/**	The current position in this list.
	 Analysis: Time = O(1) */
	public BilinkedIterator280<I> currentPosition()
	{
		return  new BilinkedIterator280<I>(this, this.prevPosition, this.position);
	}


	/**	A shallow clone of this object.
	 Analysis: Time = O(1) */
	public BilinkedList280<I> clone() throws CloneNotSupportedException
	{
		return (BilinkedList280<I>) super.clone();
	}


	/* Regression test. */
	public static void main(String[] args) {
		// TODO

		BilinkedList280<String> List = new BilinkedList280<String>();

		if(!List.isEmpty()){
			System.out.println("Error, List is supposed to be empty.");
		}
		// test is creatnode(), insertFirst(), insertLast(), deleteFirst(),
		// deleteitem(), deleteLast(), goLast(), goBack()

		List.insert("Monday");
		List.insertFirst("Sunday");
		List.insertLast("Tuesday");
		List.insertLast("Wednesday");

		System.out.println("\n##insertFirst()/insertLast()\nList should be Sunday, Monday, Tuesday, Wednesday, ");
		System.out.print("	and it is: ");
		System.out.println(List);

		List.insertLast("December"); List.goFirst();
		List.insertBefore("January");


		//search and delete an item from the list
		List.search("Wednesday");
		List.deleteItem();

		System.out.println("\n##Search and deleteItem()\nList should be January, Sunday, Monday, Tuesday, December, ");
		System.out.print("	and it is: ");
		System.out.println(List);

		List.deleteFirst();
		System.out.println("\n##deleteFirst()\nList should be Sunday, Monday, Tuesday, December, ");
		System.out.print("	and it is: ");
		System.out.println(List);

		List.goLast(); List.insertNext("March");List.insertBefore("Friday");
		List.deleteItem();
		System.out.print("\n##goLast()/insertNext()/insertBefore()\nlastItem should be March ....");
		if(List.lastItem() == "March")
			System.out.println("and it is. Ok!");
		else System.out.println("and it is not. Error!");
		System.out.println(List);

		//deleteFirst and deleteLast()0
		List.deleteFirst();List.deleteLast();List.deleteLast();List.deleteFirst();

		System.out.println("\n##delteFirst()/deleteLast()\nLastItem should be Monday ...");
		if(List.lastItem() == "Tuesday" && List.firstItem() == "Tuesday")
			System.out.print("and it is. Ok!");
		else
			System.out.println("and it is not, Error!");
		System.out.println(List);



		// test goBack(), goLast()
		List.insertFirst("24");
		List.insertNext("12");
		List.insertLast("RedBull");
		List.insertLast("52");
		List.goLast();  			// cursor should be at the last element
		System.out.print("\nCursor should be at the last item");
		if(List.currentPosition().item() != List.lastItem())
			System.out.println("Error, cursor should be at the last position");
		else	System.out.println("...And it is.");


		BilinkedList280<Integer> intList = new BilinkedList280<Integer>();

		System.out.println("\nDeleting from an empty list");
		try{
			//test deleteFirst on an empty List
			intList.deleteFirst();
			intList.deleteLast();

		}catch (ContainerEmpty280Exception e){
			System.out.println("Caught an exception. Ok!");
		}

		System.out.println("\ngoLast() in an empty list");
		try{
			//test deleteFirst on an empty List
			intList.goLast();
		}catch (ContainerEmpty280Exception e){
			System.out.println("Caught an exception. Ok!");
		}

		System.out.println("\ngoBack() in an empty list");
		try{
			//test deleteFirst on an empty List
			intList.goBack();
		}catch (BeforeTheStart280Exception e){
			System.out.println("Caught an exception. Ok!");
		}

		System.out.println("\ninsertLast() in an empty list");
		intList.insertLast(4);
		if(intList.firstItem() == 4 && intList.lastItem() == 4){
			System.out.println("added 4 to the list using insertLast()");
		}else{
			System.out.println("Error! 4 not added to List");
		}
		intList.insertFirst(3);
		intList.insertLast(5);
		intList.insertLast(6);
		intList.goForth();

		// test deleteItem()
		System.out.println("\ndelete all the item in a list using deleteItem()");
		System.out.println(intList);
		try{
			while(intList.itemExists()){
				intList.deleteItem();
				System.out.println(intList);
				intList.goForth();
			}
		}catch(NoCurrentItem280Exception e){
			System.out.println("Caught an exception");
		}

	}
} 
