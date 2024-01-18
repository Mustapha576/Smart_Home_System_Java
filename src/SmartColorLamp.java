public class SmartColorLamp extends SmartLamp{
    private String colorcode;
    // getting colorcode for smart color lamp
    public String getColorcode() {
        return colorcode;
    }
    // getting name for smart color lamp
    public SmartColorLamp(String name){
        super(name);
    }

    public SmartColorLamp(String name, String status){
        super(name, status);
    }

    public SmartColorLamp(String name, String status, int Kelvin, int Brightness){
        super(name, status, Kelvin, Brightness);
    }

    public SmartColorLamp(String name, String status, String colorcode, int Brightness){
        super(name, status);
        this.colorcode = colorcode;
        setBrightness(Brightness);
    }
}
