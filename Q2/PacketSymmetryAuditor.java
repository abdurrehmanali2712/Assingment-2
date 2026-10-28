import java.util.Scanner;

public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        String payload;

        // Use command line argument if given, otherwise ask for input
        if (args.length > 0) {
            payload = args[0];
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter packet payload: ");
            payload = sc.nextLine();
        }

        // Ignore case so "Level" and "level" are treated the same
        payload = payload.toLowerCase(); // Convert input to lowercase before checking symmetry
        int n = payload.length();

        TransactionStack stack = new TransactionStack(n);
        RequestQueue queue = new RequestQueue(n);

        System.out.println("=== Packet Symmetry Audit ===");
        System.out.println("Payload: " + payload);

        // Push into the stack and enqueue into the queue at the same time
        for (int i = 0; i < n; i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        // Pop and dequeue together and compare
        boolean symmetric = true;
        while (!stack.isEmpty() && !queue.isEmpty()) {
            char fromStack = stack.pop();      // last character
            char fromQueue = queue.dequeue();  // first character
            String result = (fromStack == fromQueue) ? "MATCH" : "MISMATCH";
            System.out.println("Pop: " + fromStack + " | Dequeue: " + fromQueue + " -> " + result);
            if (fromStack != fromQueue) {
                symmetric = false;
            }
        }

        if (symmetric) {
            System.out.println("RESULT: Packet is SYMMETRIC (palindrome signature verified).");
        } else {
            System.out.println("RESULT: Packet is NOT symmetric.");
        }
    }
}
