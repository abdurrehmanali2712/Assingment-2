public class TradingEngineApp {
    public static void main(String[] args) {
        long now = System.currentTimeMillis();

        // Superclass reference variables holding subclass objects
        TradeEvent event1 = new TradeEvent("SYSTEM", now, 100.0);
        TradeEvent event2 = new FIXOrderEvent("FIX-GATEWAY-1", now, 250.50, "ORD-1001");
        TradeEvent event3 = new ReutersQuoteEvent("REUTERS-FEED", now, 250.50, 250.40, 250.65);

        TradeEvent[] events = { event1, event2, event3 };

        System.out.println("=== Trading Engine: Dispatching Events ===");
        // Run-time polymorphism: the overridden dispatch() of the real object runs
        // Each event runs its own dispatch method
        for (TradeEvent e : events) {
            e.dispatch();
            System.out.println();
        }
    }
}
