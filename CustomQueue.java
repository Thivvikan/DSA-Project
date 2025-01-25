// CostomQueue class for the waiting queue.
class CustomQueue {
    private Node front, rear;

// Node class for the queue.
    class Node {
        String nic;
        Node next;  
        // COnstuctor of node.
        Node(String nic) {
            this.nic = nic;
            this.next = null;
        }
    }
// enqueue method to add new.
    public void enqueue(String nic) {
        Node newNode = new Node(nic);
        if (rear != null) {
            rear.next = newNode;
        }
        rear = newNode;
        if (front == null) {
            front = rear;
        }
    }
// dequeue method to remove.
    public String dequeue() {
        if (front == null) {
            return null;
        }
        String nic = front.nic;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return nic;
    }
// method to check.
    public boolean isEmpty() {
        return front == null;
    }
}