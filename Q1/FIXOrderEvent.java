// FIX order event: a stock purchase request with a 0.15% exchange fee
public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp, double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice); // call parent constructor
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double fee = basePrice * 0.0015;     // 0.15% transaction fee
        double totalCost = basePrice + fee;
        System.out.println("[FIX ORDER] Sender: " + senderId
                + " | ClOrdID: " + clOrdId
                + " | Timestamp: " + timestamp);
        System.out.println("   Executing BUY order at price: " + basePrice);
        System.out.printf("   Exchange fee (0.15%%): %.4f%n", fee);
        System.out.printf("   Total cost: %.4f%n", totalCost);
    }
}
