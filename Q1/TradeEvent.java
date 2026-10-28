// Superclass for all trade events // Store the basic information for a trade event
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

    // Baseline dispatch: logs generic event detail
    public void dispatch() {
        System.out.println("[GENERIC EVENT] Sender: " + senderId
                + " | Timestamp: " + timestamp
                + " | Base Price: " + basePrice);
    }
}
