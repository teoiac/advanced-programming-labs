public class Airliner extends Aircraft implements PassengerCapable, CargoCapable {
    private int seatCount;
    private double maximumPayload;
    private double wingSpan;

    public Airliner(String model, String name, double wingSpan, int seatCount, double maximumPayload) {
        super(model, name);
        this.wingSpan = wingSpan;
        this.seatCount = seatCount;
        this.maximumPayload = maximumPayload;
    }

    public Airliner() {
        super("N/A", "N/A");
        this.maximumPayload = 0;
        this.wingSpan = 0;
        this.seatCount = 0;
    }

    @Override
    public int getSeatCount() {
        return seatCount;
    }

    @Override
    public double getMaximumPayload() {
        return maximumPayload;
    }

    public double getWingSpan() {
        return wingSpan;
    }
}
