// Superclass for all trade events
public class TradeEvent {
    protected String senderId;
    protected long timestamp;
    protected double basePrice;

    // Parameterized constructor
    public TradeEvent(String senderId, long timestamp, double basePrice) {
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.basePrice = basePrice;
    }

    // Baseline dispatch: logs generic event details
    public void dispatch() {
        System.out.println("[GENERIC EVENT] Sender: " + senderId
                + " | Timestamp: " + timestamp
                + " | Base Price: " + basePrice);
    }
}
