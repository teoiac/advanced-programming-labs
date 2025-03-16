public class Freighter extends Aircraft implements CargoCapable {
    private double maximumPayload;

    public Freighter(String model, String name, double wingSpan, double maximumPayload) {
        super(model, name);
        this.maximumPayload = maximumPayload;
    }

    public Freighter() {
        super("N/A", "N/A");
        this.maximumPayload = 0;
    }

    @Override
    public double getMaximumPayload() {
        return maximumPayload;
    }
}
