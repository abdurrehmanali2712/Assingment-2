// Array-backed LIFO stack with fixed capacity
public class TransactionStack {
    private char[] data;
    private int top;   // index of the top element (-1 when empty)

    public TransactionStack(int capacity) {
        data = new char[capacity];
        top = -1;
    }

    public void push(char c) {
        if (top == data.length - 1) {
            System.out.println("Stack overflow");
            return;
        }
        top++;
        data[top] = c;
    }

    public char pop() {
        if (isEmpty()) {
            System.out.println("Stack underflow");
            return '\0';
        }
        char c = data[top];
        top--;
        return c;
    }

    public char peek() {
        if (isEmpty()) {
            return '\0';
        }
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
