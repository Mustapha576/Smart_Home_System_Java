public class Devices {
    private String name;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }
    // getting name for any device
    public String getName() {
        return this.name;
    }
    // getting status for any device
    public String getStatus() {
        return this.status;
    }

    public Devices(String name){
        this.name = name;
    }

    public Devices(String name, String status){
        this.name = name;
        this.status = status;
    }

}
