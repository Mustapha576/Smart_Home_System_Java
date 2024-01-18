
public class SmartLamp extends Devices {
    private final int Kelvin;
    private int Brightness;

    public SmartLamp(String name) {
        super(name);
        Kelvin = 4000;
        Brightness = 100;
        setStatus("Off");
    }

    public SmartLamp(String name, String status) {
        super(name, status);
        Kelvin = 4000;
        Brightness = 100;
    }

    public SmartLamp(String name, String status, int Kelvin, int Brightness) {
        super(name, status);
        this.Kelvin = Kelvin;
        this.Brightness = Brightness;
    }

    public void setBrightness(int brightness) {
        Brightness = brightness;
    }

    public int getKelvin() {
        return Kelvin;
    }

    public int getBrightness() {
        return Brightness;
    }
}
