public class SmartPlug extends Devices{
    private double ampere;
    // getting ampere for any smart plug
    public double getAmpere() {
        return ampere;
    }

    public SmartPlug(String name){
        super(name);
        setStatus("Off");
    }

    public SmartPlug(String name, String status){
        super(name, status);
    }

    public SmartPlug(String name, String status, double ampere){
        super(name, status);
        this.ampere = ampere;
    }
}
