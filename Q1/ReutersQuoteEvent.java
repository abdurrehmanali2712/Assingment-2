// Reuters quote event: outputs bid-ask spread analytics
public class ReutersQuoteEvent extends TradeEvent {
    private double bidPrice;
    private double askPrice;

    public ReutersQuoteEvent(String senderId, long timestamp, double basePrice,
                             double bidPrice, double askPrice) {
        super(senderId, timestamp, basePrice);
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    @Override
    public void dispatch() {
        double spread = askPrice - bidPrice;
        double spreadPercent = (spread / basePrice) * 100;
        System.out.println("[REUTERS QUOTE] Sender: " + senderId
                + " | Timestamp: " + timestamp);
        System.out.println("   Bid: " + bidPrice + " | Ask: " + askPrice);
        System.out.printf("   Bid-Ask Spread: %.4f (%.4f%% of base price)%n", spread, spreadPercent);
    }
}
