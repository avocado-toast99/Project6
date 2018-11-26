


/*

Group Members:

Hana'a Al-Lohibi            1675753
Bashair Atif Abdulrazak     1505459  
Maha Osama  Assagran        1605216

Algorithms and Data Structures
Section CH 
Project 6 - Implementing Edmonds Karp's Algorithm



 */
package edmonds_algorithm;

/**
 *
 * @author Lenovo
 */
public class QueueLL {
     // top: reference variable to the top of the stack (same as "head" of linked list)
    private QueueNode front;
	private QueueNode back;
    
    // CONSTRUCTOR
    public QueueLL() {
        front = null;
		back = null;
    }
	
	
	/* Below are MANY methods that are used on stacks.
	 * 
	 * Examples:
	 * isEmpty, PUSH, POP, PEEK, and more.
	 */
	
	
	//
	// boolean | isEmpty()
	//
	public boolean isEmpty() {
		return front == null;
	}
	
	
	//
	// void | PrintQueue()
	//
	public void PrintQueue() {
		PrintQueue(front);
	}
	//
	// void | PrintQueue(QueueNode)
	//
	private void PrintQueue(QueueNode front) {
		// We need to traverse...so we need a help ptr
		QueueNode helpPtr = front;
		// Traverse to correct insertion point
		while (helpPtr != null) {
			// Print the data value of the node
			System.out.print(helpPtr.getData() + ", ");
			// Step one node over
			helpPtr = helpPtr.getNext();
		}
		System.out.println();
	}
	
	
	//
	// boolean | search(int)
	//
	public boolean search(int data) {
		return search(front, data);
	}
	//
	// boolean | search(QueueNode, int)
	//
	private boolean search(QueueNode p, int data) {
		// To search, we must traverse. Therefore, we need helpPtr.
		QueueNode helpPtr = p;
		while (helpPtr != null) {
			if (helpPtr.getData() == data)
				return true;
			helpPtr = helpPtr.getNext(); // step one node over		
		}
		return false;
	}
	
	
	//
	// void | enqueue(int)
	//
	public void enqueue(int data) {
		if (isEmpty()) {
			front = back = enqueue(front, back, data);
		}
		else {
			back = enqueue(front, back, data);
		}
		
	}
	//
	// QueueNode | enqueue(QueueNode, QueueNode, int)
	//
	private QueueNode enqueue(QueueNode front, QueueNode back, int data) {
		// Make a new QueueNode with "data" as the data value
		QueueNode temp = new QueueNode(data);
		
		// Now, if the list is empty, return the reference for temp
		// and save this reference into both "front" and "back"
		// Why? Since this is the only node in the queue, it will be the front and back node
		if (isEmpty()) {
			return temp;
		}
		// ELSE, the queue is not empty. We need to insert temp at the back of the queue.
		// So save the address of the new node into the next of back.
		// Then, make back "traverse" one node over, so it now points to the new back node.
		// Finally, return the updated address of back.
		else {
                    // set for the new node
			back.setNext(temp);
                        // and let it be on the new node
			back = back.getNext();
			return back;
		}
	}
	
	
	//
	// QueueNode | dequeue()
	//
	public QueueNode dequeue() {
		QueueNode temp = front;
		front = dequeue(front);
		if (front == null)
			back = null;
		return temp;
	}
	//
	// QueueNode | dequeue(QueueNode)
	//
	private QueueNode dequeue(QueueNode front) {
		front = front.getNext();
		return front;
	}
	
	
	//
	// int | peek()
	//
	public int peek() {
		// Invoke the peek method with front as a parameter
		int frontValue = peek(front);
		
		// return topValue
		return frontValue;
	}
	//
	// int | peek(QueueNode)
	//
	private int peek(QueueNode front) {
		// Return the data value of the front node.
		// You can see that we do NOT dequeue. We are only returning the data value.
		return front.getData();
	}
}// c