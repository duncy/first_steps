package nz.duncy.first_steps.metallurgy;

public class TemperatureStorage {
    private int temperature;

    public TemperatureStorage(int temperature) {
        this.temperature = temperature;
    }

    public TemperatureStorage() {
        this(20);
    }


    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
