import java.util.ArrayList;

public class InitialTime {

    public String getTime(int newTimeValue) {
        Input gettingTimeValue = new Input();
        ArrayList<ArrayList<String>> TimeValue = gettingTimeValue.getInput();

        String[] values = TimeValue.get(newTimeValue).get(1).split("-");
        if(values[1].length() == 1){
            values[1] = "0" + values[1];
        }
        String[] values2 = values[2].split("_");
        if(values2[0].length() == 1){
            values2[0] = "0" + values2[0];
        }
        String[] values3 = values2[1].split(":");
        if(values3[0].length() == 1){
            values3[0] = "0" + values3[0];
        }
        if (values3[1].length() == 1){
            values3[1] = "0" + values3[1];
        }
        if (values3[2].length() == 1){
            values3[2] = "0" + values3[2];
        }
        String time = values[0] + "-" + values[1] + "-" + values2[0] + "_" + values3[0] + ":" + values3[1] + ":" + values3[2];
        return time;
    }
}
