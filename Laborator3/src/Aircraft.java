public abstract class Aircraft implements Comparable<Aircraft> {
    private String model;
    private String name;

    public Aircraft(String model, String name) {
        this.model = model;
        this.name = name;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(Aircraft other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Aircraft [model=" + this.model + ", name=" + this.name + "]";
    }

}
