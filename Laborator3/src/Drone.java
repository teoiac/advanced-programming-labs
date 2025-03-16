public class Drone extends Aircraft implements CargoCapable {
    private int batteryCapacity;
    private double maximumPayload;

    public Drone(String model, String name, double maximumPayload, int batteryCapacity) {
        super(model, name);
        this.maximumPayload = maximumPayload;
        this.batteryCapacity = batteryCapacity;
    }

    public Drone() {
        super("N/A", "N/A");
        this.maximumPayload = 0;
        this.batteryCapacity = 0;
    }

    @Override
    public double getMaximumPayload() {
        return this.maximumPayload;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public int getBatteryCapacity() {
        return this.batteryCapacity;
    }
}
