// Array-backed FIFO queue (circular) with front, rear and size pointers
public class RequestQueue {
    private char[] data;
    private int front;
    private int rear;
    private int size;

    public RequestQueue(int capacity) {
        data = new char[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(char c) {
        if (size == data.length) {
            System.out.println("Queue overflow");
            return;
        }
        rear = (rear + 1) % data.length;
        data[rear] = c;
        size++;
    }

    public char dequeue() {
        if (isEmpty()) {
            System.out.println("Queue underflow");
            return '\0';
        }
        char c = data[front];
        front = (front + 1) % data.length;
        size--;
        return c;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
