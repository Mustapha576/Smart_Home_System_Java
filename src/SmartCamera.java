public class SmartCamera extends Devices{
    private final int megabyte;

    public SmartCamera(String name, int megabyte){
        super(name);
        this.megabyte = megabyte;
        setStatus("Off");
    }

    public SmartCamera(String name, int megabyte, String status){
        super(name, status);
        this.megabyte = megabyte;
    }
}
