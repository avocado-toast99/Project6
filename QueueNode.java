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
public class QueueNode {
   private int data;
    private QueueNode next;
    
    // CONSTRUCTORS
    public QueueNode() {
        data = 0;
        next = null;
    }
    
    public QueueNode(int data) {
        this.data = data;
        next = null;
    }
    
    public QueueNode(int data, QueueNode next) {
        this.data = data;
        this.next = next;
    }

    // ACCESSORS
    public int getData() {
        return data;
    }

    public QueueNode getNext() {
        return next;
    }


    // MUTATORS
    public void setData(int data) {
        this.data = data;
    }

    public void setNext(QueueNode next) {
        this.next = next;
    }
} //clss 