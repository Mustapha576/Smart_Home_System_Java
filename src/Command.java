import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Command {
    Input lines = new Input();
    ArrayList<ArrayList<String>> each_line = lines.getInput();
    BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));
    ArrayList<String> deviceNames = new ArrayList<>();
    ArrayList<ArrayList<String>> smartLamp = new ArrayList<>();
    ArrayList<ArrayList<String>> smartColorLamp = new ArrayList<>();
    ArrayList<ArrayList<String>> smartCamera = new ArrayList<>();
    ArrayList<ArrayList<String>> smartPlug = new ArrayList<>();
    ArrayList<String> localDate = new ArrayList<>();

    //Doing our programme
    public Command() throws IOException {
        //Checking all the commands
        for (int i = 0; i < each_line.size(); i++) {
            //Writing the command into output
            ArrayList<String> line = each_line.get(i);
            StringBuilder sb = new StringBuilder();
            for(int h = 0; h < line.size(); h++){
                if (h == line.size() - 1){
                    sb.append(line.get(h));
                }
                else {
                    sb.append(line.get(h));
                    sb.append("\t");
                }
            }
            String command = sb.toString();
            output.write("COMMAND: " + command + "\n");

            //If SetInitialTime is wrong or not given in the first line, it is gonna error and stops.
        if (!line.get(0).equals("SetInitialTime") && i == 0) {
            output.write("ERROR: First command must be set initial time! Program is going to terminate!");
            break;
        }
        /*If SetInitialTime is correct and given in the first line, it is gonna set the time, if it is not correct,
        it will print an error.
         */
        else if(line.get(0).equals("SetInitialTime")){
            if(i == 0){
                try{
                    InitialTime date = new InitialTime();
                    localDate.add(date.getTime(0));
                    output.write("SUCCESS: Time has been set to " + date.getTime(0) + "!\n");
                }
                catch (Exception e){
                    output.write("ERROR: Format of the initial date is wrong! Program is going to terminate!");
                    break;
                }
            }
            else{
                output.write("ERROR: Erroneous command!\n");
            }
        }
        /*Add command for all devices and adding into a list for each devices, and also giving an error if there is any
        mistakes.
        */
        else if (line.get(0).equals("Add")) {
                switch (line.get(1)) {
                    case "SmartPlug":
                        if (line.size() < 3 || line.size() > 5) {
                            output.write("ERROR: Erroneous command!\n");
                        } else if (deviceNames.contains(line.get(2))) {
                            output.write("ERROR: There is already a smart device with same name!\n");
                        } else if (line.size() == 3) {
                            SmartPlug device = new SmartPlug(line.get(2));
                            ArrayList<String> properties = new ArrayList<>();
                            properties.add(device.getName());
                            properties.add(device.getStatus());
                            smartPlug.add(properties);
                            deviceNames.add(line.get(2));
                        } else if (line.size() == 4) {
                            if (Objects.equals(line.get(3), "On") || Objects.equals(line.get(3), "Off")) {
                                SmartPlug device = new SmartPlug(line.get(2), line.get(3));
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(device.getName());
                                properties.add(device.getStatus());
                                smartPlug.add(properties);
                                deviceNames.add(line.get(2));
                            } else {
                                output.write("ERROR: Erroneous command!\n");
                            }
                        } else if (line.size() == 5) {
                            if (Objects.equals(line.get(3), "On") || Objects.equals(line.get(3), "Off")) {
                                try {
                                    double ampere = Double.parseDouble(line.get(4));
                                    if (ampere <= 0) {
                                        output.write("ERROR: Ampere value must be a positive number!\n");
                                    } else {
                                        SmartPlug device = new SmartPlug(line.get(2), line.get(3), ampere);
                                        ArrayList<String> properties = new ArrayList<>();
                                        properties.add(device.getName());
                                        properties.add(device.getStatus());
                                        properties.add(String.valueOf(device.getAmpere()));
                                        smartPlug.add(properties);
                                        deviceNames.add(line.get(2));
                                    }
                                } catch (Exception e) {
                                    output.write("ERROR: Erroneous command!\n");
                                }
                            } else {
                                output.write("ERROR: Erroneous command!\n");
                            }
                        }
                        break;
                    case "SmartLamp":
                        if (line.size() < 3 || line.size() > 6) {
                            output.write("ERROR: Erroneous command!\n");
                        } else if (deviceNames.contains(line.get(2))) {
                            output.write("ERROR: There is already a smart device with same name!\n");
                        } else if (line.size() == 3) {
                            SmartLamp device = new SmartLamp(line.get(2));
                            ArrayList<String> properties = new ArrayList<>();
                            properties.add(device.getName());
                            properties.add(device.getStatus());
                            properties.add(String.valueOf(device.getKelvin()));
                            properties.add(String.valueOf(device.getBrightness()));
                            smartLamp.add(properties);
                            deviceNames.add(line.get(2));
                        } else if (line.size() == 4) {
                            if (Objects.equals(line.get(3), "On") || Objects.equals(line.get(3), "Off")) {
                                SmartLamp device = new SmartLamp(line.get(2), line.get(3));
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(device.getName());
                                properties.add(device.getStatus());
                                properties.add(String.valueOf(device.getKelvin()));
                                properties.add(String.valueOf(device.getBrightness()));
                                smartLamp.add(properties);
                                deviceNames.add(line.get(2));
                            } else {
                                output.write("ERROR: Erroneous command!\n");
                            }
                        } else if (line.size() == 6) {
                            if (Objects.equals(line.get(3), "On") || Objects.equals(line.get(3), "Off")) {
                                try {
                                    int KelvinValue = Integer.parseInt(line.get(4));
                                    int BrightnessValue = Integer.parseInt(line.get(5));
                                    if (KelvinValue > 6500 || KelvinValue < 2000) {
                                        output.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                    } else if (BrightnessValue > 100 || BrightnessValue < 0) {
                                        output.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                    } else {
                                        SmartLamp device = new SmartLamp(line.get(2), line.get(3), KelvinValue, BrightnessValue);
                                        ArrayList<String> properties = new ArrayList<>();
                                        properties.add(device.getName());
                                        properties.add(device.getStatus());
                                        properties.add(String.valueOf(device.getKelvin()));
                                        properties.add(String.valueOf(device.getBrightness()));
                                        smartLamp.add(properties);
                                        deviceNames.add(line.get(2));
                                    }
                                } catch (Exception e) {
                                    output.write("ERROR: Erroneous command!\n");
                                }
                            } else {
                                output.write("ERROR: Erroneous command!\n");
                            }
                        }
                        break;
                    case "SmartColorLamp":
                        if (line.size() < 3 || line.size() > 6) {
                            output.write("ERROR: Erroneous command!\n");
                        } else if (deviceNames.contains(line.get(2))) {
                            output.write("ERROR: There is already a smart device with same name!\n");
                        } else if (line.size() == 3) {
                            SmartColorLamp device = new SmartColorLamp(line.get(2));
                            ArrayList<String> properties = new ArrayList<>();
                            properties.add(device.getName());
                            properties.add(device.getStatus());
                            properties.add(String.valueOf(device.getKelvin()));
                            properties.add(String.valueOf(device.getBrightness()));
                            smartColorLamp.add(properties);
                            deviceNames.add(line.get(2));
                        } else if (line.size() == 4) {
                            if (Objects.equals(line.get(3), "On") || Objects.equals(line.get(3), "Off")) {
                                SmartColorLamp device = new SmartColorLamp(line.get(2), line.get(3));
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(device.getName());
                                properties.add(device.getStatus());
                                properties.add(String.valueOf(device.getKelvin()));
                                properties.add(String.valueOf(device.getBrightness()));
                                smartColorLamp.add(properties);
                                deviceNames.add(line.get(2));
                            } else {
                                output.write("ERROR: Erroneous command!\n");
                            }
                        } else if (line.size() == 6) {
                            if (Objects.equals(line.get(3), "On") || Objects.equals(line.get(3), "Off")) {
                                if (line.get(4).startsWith("0x")) {
                                    try {
                                        int a = Integer.decode(line.get(4));
                                        if (a > 0xFFFFFF || a < 0x000000) {
                                            output.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                                        } else {
                                            int BrightnessValue = Integer.parseInt(line.get(5));
                                            if (BrightnessValue > 100 || BrightnessValue < 0) {
                                                output.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                            } else {
                                                SmartColorLamp device = new SmartColorLamp(line.get(2), line.get(3), line.get(4), BrightnessValue);
                                                ArrayList<String> properties = new ArrayList<>();
                                                properties.add(device.getName());
                                                properties.add(device.getStatus());
                                                properties.add(device.getColorcode());
                                                properties.add(String.valueOf(device.getBrightness()));
                                                smartColorLamp.add(properties);
                                                deviceNames.add(line.get(2));
                                            }
                                        }
                                    } catch (Exception e) {
                                        output.write("ERROR: Erroneous command!\n");
                                    }
                                } else {
                                    try {
                                        int KelvinValue = Integer.parseInt(line.get(4));
                                        int BrightnessValue = Integer.parseInt(line.get(5));
                                        if (KelvinValue > 6500 || KelvinValue < 2000) {
                                            output.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                        } else if (BrightnessValue > 100 || BrightnessValue < 0) {
                                            output.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                        } else {
                                            SmartColorLamp device = new SmartColorLamp(line.get(2), line.get(3), KelvinValue, BrightnessValue);
                                            ArrayList<String> properties = new ArrayList<>();
                                            properties.add(device.getName());
                                            properties.add(device.getStatus());
                                            properties.add(String.valueOf(device.getKelvin()));
                                            properties.add(String.valueOf(device.getBrightness()));
                                            smartColorLamp.add(properties);
                                            deviceNames.add(line.get(2));
                                        }
                                    } catch (Exception e) {
                                        output.write("ERROR: Erroneous command!\n");
                                    }
                                }
                            } else {
                                output.write("ERROR: Erroneous command!\n");
                            }
                        }
                        break;
                    case "SmartCamera":
                        if (line.size() < 4 || line.size() > 5) {
                            output.write("ERROR: Erroneous command!\n");
                        } else if (deviceNames.contains(line.get(2))) {
                            output.write("ERROR: There is already a smart device with same name!\n");
                        } else if (line.size() == 4) {
                            try {
                                int consumedMegabyte = Integer.parseInt(line.get(3));
                                if (consumedMegabyte <= 0) {
                                    output.write("ERROR: Megabyte value must be a positive number!\n");
                                } else {
                                    SmartCamera device = new SmartCamera(line.get(2), consumedMegabyte);
                                    ArrayList<String> properties = new ArrayList<>();
                                    properties.add(device.getName());
                                    properties.add(device.getStatus());
                                    properties.add(String.valueOf(consumedMegabyte));
                                    smartCamera.add(properties);
                                    deviceNames.add(line.get(2));
                                }
                            } catch (Exception e) {
                                output.write("ERROR: Erroneous command!\n");
                            }

                        } else if (line.size() == 5) {
                            try {
                                int consumedMegabyte = Integer.parseInt(line.get(3));
                                if (consumedMegabyte <= 0) {
                                    output.write("ERROR: Megabyte value must be a positive number!\n");
                                } else if (Objects.equals(line.get(4), "On") || Objects.equals(line.get(4), "Off")) {
                                    SmartCamera device = new SmartCamera(line.get(2), consumedMegabyte, line.get(4));
                                    ArrayList<String> properties = new ArrayList<>();
                                    properties.add(device.getName());
                                    properties.add(device.getStatus());
                                    properties.add(String.valueOf(consumedMegabyte));
                                    smartCamera.add(properties);
                                    deviceNames.add(line.get(2));
                                }
                            } catch (Exception e) {
                                output.write("ERROR: Erroneous command!\n");
                            }
                        }
                        break;
                }
            }/* Setting kelvin value for smart lamp and smart color lamp, changing their current kelvin value and making
              * a new value, also giving an error if there is any mistakes.
              */

        else if (line.get(0).equals("SetKelvin")) {
                try {
                    int KelvinClock = 30;
                    for (int j = 0; j < smartLamp.size(); j++) {
                        ArrayList<String> equippedDevice = smartLamp.get(j);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            int newKelvinValue = Integer.parseInt(line.get(2));
                            if (newKelvinValue < 2000 || newKelvinValue > 6500) {
                                output.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                KelvinClock += 1;
                            } else {
                                int brightnessValue = Integer.parseInt(equippedDevice.get(3));
                                SmartLamp newDevice = new SmartLamp(equippedDevice.get(0), equippedDevice.get(1), newKelvinValue, brightnessValue);
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(newDevice.getName());
                                properties.add(newDevice.getStatus());
                                properties.add(String.valueOf(newKelvinValue));
                                properties.add(String.valueOf(brightnessValue));
                                smartLamp.add(properties);
                                smartLamp.remove(j);
                                KelvinClock += 1;
                                break;
                            }

                        }
                    }
                    for (int k = 0; k < smartColorLamp.size(); k++) {
                        ArrayList<String> equippedDevice = smartColorLamp.get(k);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            int newKelvinValue = Integer.parseInt(line.get(2));
                            if (newKelvinValue < 2000 || newKelvinValue > 6500) {
                                output.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                KelvinClock += 1;
                            } else {
                                int brightnessValue = Integer.parseInt(equippedDevice.get(3));
                                SmartLamp newDevice = new SmartLamp(equippedDevice.get(0), equippedDevice.get(1), newKelvinValue, brightnessValue);
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(newDevice.getName());
                                properties.add(newDevice.getStatus());
                                properties.add(String.valueOf(newKelvinValue));
                                properties.add(String.valueOf(brightnessValue));
                                smartColorLamp.add(properties);
                                smartColorLamp.remove(k);
                                KelvinClock += 1;
                                break;
                            }

                        }
                    }

                    if (KelvinClock == 30) {
                        output.write("ERROR: This device is not a smart lamp!\n");
                    }

                } catch (Exception e) {
                    output.write("ERROR: Erroneous command!\n");

                }
            } /* Setting brightness value for smart lamp and smart color lamp, changing their current brightness value
               *  and making a new value, also giving an error if there is any mistakes.
               */
        else if (line.get(0).equals("SetBrightness")) {
                try {
                    int clock = 30;
                    for (int j = 0; j < smartLamp.size(); j++) {
                        ArrayList<String> equippedDevice = smartLamp.get(j);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            int newBrightnessValue = Integer.parseInt(line.get(2));
                            if (newBrightnessValue < 0 || newBrightnessValue > 100) {
                                output.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                clock += 1;
                            } else {
                                int kelvinValue = Integer.parseInt(equippedDevice.get(2));
                                SmartLamp newDevice = new SmartLamp(equippedDevice.get(0), equippedDevice.get(1), kelvinValue, newBrightnessValue);
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(newDevice.getName());
                                properties.add(newDevice.getStatus());
                                properties.add(String.valueOf(kelvinValue));
                                properties.add(String.valueOf(newBrightnessValue));
                                smartLamp.add(properties);
                                smartLamp.remove(j);
                                clock += 1;
                                break;
                            }

                        }
                    }
                    for (int k = 0; k < smartColorLamp.size(); k++) {
                        ArrayList<String> equippedDevice = smartColorLamp.get(k);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            int newBrightnessValue = Integer.parseInt(line.get(2));
                            if (newBrightnessValue < 0 || newBrightnessValue > 100) {
                                output.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                clock += 1;
                            } else {
                                int kelvinValue = Integer.parseInt(equippedDevice.get(2));
                                SmartLamp newDevice = new SmartLamp(equippedDevice.get(0), equippedDevice.get(1), kelvinValue, newBrightnessValue);
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(newDevice.getName());
                                properties.add(newDevice.getStatus());
                                properties.add(String.valueOf(kelvinValue));
                                properties.add(String.valueOf(newBrightnessValue));
                                smartColorLamp.add(properties);
                                smartColorLamp.remove(k);
                                clock += 1;
                                break;
                            }
                        }
                    }

                    if (clock == 30) {
                        output.write("ERROR: This device is not a smart lamp!\n");
                    }

                } catch (Exception e) {
                    output.write("ERROR: Erroneous command!\n");
                }
            } /* Setting color code value for smart color lamp, changing their current color code value and making
               * a new value, also giving an error if there is any mistakes.
               */
        else if (line.get(0).equals("SetColorCode")) {
                try{
                    int ColorCodeClock = 30;
                    for (int j = 0; j < smartColorLamp.size(); j++) {
                        ArrayList<String> equippedDevice = smartColorLamp.get(j);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            int ColorCode = Integer.decode(line.get(2));
                            if (ColorCode > 0xFFFFFF || ColorCode < 0x000000) {
                                output.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                                ColorCodeClock += 1;
                            } else {
                                int BrightnessValue = Integer.parseInt(equippedDevice.get(3));
                                SmartLamp newDevice = new SmartLamp(equippedDevice.get(0), equippedDevice.get(1), ColorCode, BrightnessValue);
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(newDevice.getName());
                                properties.add(newDevice.getStatus());
                                properties.add(line.get(2));
                                properties.add(String.valueOf(BrightnessValue));
                                smartColorLamp.add(properties);
                                smartColorLamp.remove(j);
                                ColorCodeClock += 1;
                                break;
                            }
                        }
                    }
                    if (ColorCodeClock == 30) {
                        output.write("ERROR: This device is not a smart color lamp!\n");
                    }
                }catch (Exception e) {
                    output.write("ERROR: Erroneous command!\n");
                }

            }/* Setting kelvin and brightness value for smart lamp and smart color lamp, changing their current kelvin
              * and brightness value and making a new value, also giving an error if there is any mistakes.
              */
        else if (line.get(0).equals("SetWhite")) {
                try {
                    int Clock = 30;
                    for (int j = 0; j < smartLamp.size(); j++) {
                        ArrayList<String> equippedDevice = smartLamp.get(j);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            int newKelvinValue = Integer.parseInt(line.get(2));
                            int newBrightnessValue = Integer.parseInt(line.get(3));
                            if (newKelvinValue < 2000 || newKelvinValue > 6500) {
                                output.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                Clock += 1;
                            } else if (newBrightnessValue < 0 || newBrightnessValue > 100) {
                                output.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                Clock += 1;
                            } else {
                                SmartLamp newDevice = new SmartLamp(equippedDevice.get(0), equippedDevice.get(1), newKelvinValue, newBrightnessValue);
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(newDevice.getName());
                                properties.add(newDevice.getStatus());
                                properties.add(String.valueOf(newKelvinValue));
                                properties.add(String.valueOf(newBrightnessValue));
                                smartLamp.add(properties);
                                smartLamp.remove(j);
                                Clock += 1;
                                break;
                            }

                        }
                    }
                    for (int k = 0; k < smartColorLamp.size(); k++) {
                        ArrayList<String> equippedDevice = smartColorLamp.get(k);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            int newKelvinValue = Integer.parseInt(line.get(2));
                            int newBrightnessValue = Integer.parseInt(line.get(3));
                            if (newKelvinValue < 2000 || newKelvinValue > 6500) {
                                output.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                Clock += 1;
                            } else if (newBrightnessValue < 0 || newBrightnessValue > 100) {
                                output.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                Clock += 1;
                            } else {
                                SmartLamp newDevice = new SmartLamp(equippedDevice.get(0), equippedDevice.get(1), newKelvinValue, newBrightnessValue);
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(newDevice.getName());
                                properties.add(newDevice.getStatus());
                                properties.add(String.valueOf(newKelvinValue));
                                properties.add(String.valueOf(newBrightnessValue));
                                smartColorLamp.add(properties);
                                smartColorLamp.remove(k);
                                Clock += 1;
                                break;
                            }

                        }
                    }

                    if (Clock == 30) {
                        output.write("ERROR: This device is not a smart lamp!\n");
                    }

                } catch (Exception e) {
                    output.write("ERROR: Erroneous command!\n");

                }
            } /* Setting color code value for smart color lamp, changing their current color code value and making
               * a new value, also giving an error if there is any mistakes.
               */
        else if (line.get(0).equals("SetColor")) {
                try{
                    int ColorCodeClock = 30;
                    for (int j = 0; j < smartColorLamp.size(); j++) {
                        ArrayList<String> equippedDevice = smartColorLamp.get(j);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            int ColorCode = Integer.decode(line.get(2));
                            int newBrightnessValue = Integer.parseInt(line.get(3));
                            if (ColorCode > 0xFFFFFF || ColorCode < 0x000000) {
                                output.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                                ColorCodeClock += 1;
                            } else if (newBrightnessValue < 0 || newBrightnessValue > 100) {
                                output.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                ColorCodeClock += 1;
                            } else {
                                SmartLamp newDevice = new SmartLamp(equippedDevice.get(0), equippedDevice.get(1), ColorCode, newBrightnessValue);
                                ArrayList<String> properties = new ArrayList<>();
                                properties.add(newDevice.getName());
                                properties.add(newDevice.getStatus());
                                properties.add(line.get(2));
                                properties.add(String.valueOf(newBrightnessValue));
                                smartColorLamp.add(properties);
                                smartColorLamp.remove(j);
                                ColorCodeClock += 1;
                                break;
                            }
                        }
                    }
                    if (ColorCodeClock == 30) {
                        output.write("ERROR: This device is not a smart color lamp!\n");
                    }

                }catch (Exception e) {
                    output.write("ERROR: Erroneous command!\n");
                }
        }
        /* Changing name of device, if there is a device with the same name of the name which user wants to change into
         * or there is not such a device with programme gives an error.
         */
            else if(line.get(0).equals("ChangeName")) {
                if (line.size() != 3) {
                    output.write("ERROR: Erroneous command!\n");
                } else if (Objects.equals(line.get(1), line.get(2))) {
                    output.write("ERROR: Both of the names are the same, nothing changed!\n");
                } else {
                    int clock = 30;
                    // Looking for smart lamp devices
                    for (int devices = 0; devices < smartLamp.size(); devices++) {
                        ArrayList<String> equippedDevice = smartLamp.get(devices);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            if(deviceNames.contains(line.get(2))){
                                output.write("ERROR: There is already a smart device with same name!\n");
                                clock += 1;
                            }
                            else if(clock == 30){
                                smartLamp.remove(equippedDevice);
                                equippedDevice.set(0, line.get(2));
                                smartLamp.add(equippedDevice);
                            }
                        }
                    }
                    // Looking for smart color lamp devices
                    for (int devices = 0; devices < smartColorLamp.size(); devices++) {
                        ArrayList<String> equippedDevice = smartColorLamp.get(devices);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            if(deviceNames.contains(line.get(2))){
                                output.write("ERROR: There is already a smart device with same name!\n");
                                clock += 1;
                            }
                            else if(clock == 30){
                                smartColorLamp.remove(equippedDevice);
                                equippedDevice.set(0, line.get(2));
                                smartColorLamp.add(equippedDevice);
                            }
                        }
                    }
                    // Looking for smart plug devices
                    for (int devices = 0; devices < smartPlug.size(); devices++) {
                        ArrayList<String> equippedDevice = smartPlug.get(devices);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            if(deviceNames.contains(line.get(2))){
                                output.write("ERROR: There is already a smart device with same name!\n");
                                clock += 1;
                            }
                            else if(clock == 30){
                                smartPlug.remove(equippedDevice);
                                equippedDevice.set(0, line.get(2));
                                smartPlug.add(equippedDevice);
                            }
                        }
                    }
                    // Looking for smart camera devices
                    for (int devices = 0; devices < smartCamera.size(); devices++) {
                        ArrayList<String> equippedDevice = smartCamera.get(devices);
                        if (Objects.equals(equippedDevice.get(0), line.get(1))) {
                            if(deviceNames.contains(line.get(2))){
                                output.write("ERROR: There is already a smart device with same name!\n");
                                clock += 1;
                            }
                            else if(clock == 30){
                                smartCamera.remove(equippedDevice);
                                equippedDevice.set(0, line.get(2));
                                smartCamera.add(equippedDevice);
                            }
                        }
                    }
                }
            }
        /* Plugging in a device to smart plug, if that device is not a plug or there is not such a device or
         * there is already a device plugged in or ampere value is not positive, programme gives an appropriate error.
         */
            else if(line.get(0).equals("PlugIn")){
                try{
                    int clock = 30;
                    for(int devices = 0; devices < smartPlug.size(); devices++){
                        ArrayList<String> equippedDevice = smartPlug.get(devices);
                        if(Objects.equals(equippedDevice.get(0), line.get(1))){
                            if(equippedDevice.size() == 2){
                                double ampereValue = Double.parseDouble(line.get(2));
                                if(ampereValue > 0){
                                    equippedDevice.add(String.valueOf(ampereValue));
                                    smartPlug.add(equippedDevice);
                                    smartPlug.remove(devices);
                                    clock += 1;
                                    break;
                                }
                                else{
                                    output.write("ERROR: Ampere value must be a positive number!\n");
                                    clock += 1;
                                }
                            }
                            else{
                                output.write("ERROR: There is already an item plugged in to that plug!\n");
                                clock += 1;
                            }
                        }
                    }
                    if (clock == 30){
                        output.write("ERROR: This device is not a smart plug!\n");
                    }
                }catch (Exception e){
                    output.write("ERROR: Erroneous command!\n");
                }
            }
            /* Plugging out the device from smart plug, smart plug must update its total energy consumption when
             * a plug is plugged out.
             */
            else if(line.get(0).equals("PlugOut")){
                int clock = 30;
                for(int devices = 0; devices < smartPlug.size(); devices++){
                    ArrayList<String> equippedDevice = smartPlug.get(devices);
                    if(Objects.equals(equippedDevice.get(0), line.get(1))){
                        if(equippedDevice.size() == 3){
                            equippedDevice.remove(2);
                            smartPlug.add(equippedDevice);
                            smartPlug.remove(devices);
                            clock += 1;
                            break;
                        }
                        else{
                            output.write("ERROR: This plug has no item to plug out from that plug!\n");
                            clock += 1;
                        }
                    }
                }
                if (clock == 30){
                    output.write("ERROR: This device is not a smart plug!\n");
                }
            }
            /* Switching device to status of on or off. If there is not such a device or it is already on or off,
             * programme gives appropriate error.
             */
            else if(line.get(0).equals("Switch")){
                if(line.size() != 3){
                    output.write("ERROR: Erroneous command!\n");
                }
                else{
                    int clock = 30;
                    for(int devices = 0; devices < smartLamp.size(); devices++){
                        ArrayList<String> equippedDevice = smartLamp.get(devices);
                        if(Objects.equals(equippedDevice.get(0), line.get(1))){
                            if(Objects.equals(line.get(2), "On") || Objects.equals(line.get(2), "Off")){
                                if(!Objects.equals(equippedDevice.get(1), line.get(2))){
                                    equippedDevice.set(1, line.get(2));
                                    smartLamp.add(equippedDevice);
                                    smartLamp.remove(devices);
                                    clock += 1;
                                    break;
                                }else{
                                    output.write("ERROR: This device is already switched " + line.get(2) + "!\n");
                                    clock += 1;
                                    break;
                                }
                            }else{
                                output.write("ERROR: Erroneous command!\n");
                                clock += 1;
                                break;
                            }
                        }
                    }
                    for(int devices = 0; devices < smartColorLamp.size(); devices++){
                        ArrayList<String> equippedDevice = smartColorLamp.get(devices);
                        if(Objects.equals(equippedDevice.get(0), line.get(1))){
                            if(Objects.equals(line.get(2), "On") || Objects.equals(line.get(2), "Off")){
                                if(!Objects.equals(equippedDevice.get(1), line.get(2))){
                                    equippedDevice.set(1, line.get(2));
                                    smartColorLamp.add(equippedDevice);
                                    smartColorLamp.remove(devices);
                                    clock += 1;
                                    break;
                                }else{
                                    output.write("ERROR: This device is already switched " + line.get(2) + "!\n");
                                    clock += 1;
                                    break;
                                }
                            }else{
                                output.write("ERROR: Erroneous command!\n");
                                clock += 1;
                                break;
                            }
                        }
                    }
                    for(int devices = 0; devices < smartPlug.size(); devices++){
                        ArrayList<String> equippedDevice = smartPlug.get(devices);
                        if(Objects.equals(equippedDevice.get(0), line.get(1))){
                            if(Objects.equals(line.get(2), "On") || Objects.equals(line.get(2), "Off")){
                                if(!Objects.equals(equippedDevice.get(1), line.get(2))){
                                    equippedDevice.set(1, line.get(2));
                                    smartPlug.add(equippedDevice);
                                    smartPlug.remove(devices);
                                    clock += 1;
                                    break;
                                }else{
                                    output.write("ERROR: This device is already switched " + line.get(2) + "!\n");
                                    clock += 1;
                                    break;
                                }
                            }else{
                                output.write("ERROR: Erroneous command!\n");
                                clock += 1;
                                break;
                            }
                        }
                    }
                    for(int devices = 0; devices < smartCamera.size(); devices++){
                        ArrayList<String> equippedDevice = smartCamera.get(devices);
                        if(Objects.equals(equippedDevice.get(0), line.get(1))){
                            if(Objects.equals(line.get(2), "On") || Objects.equals(line.get(2), "Off")){
                                if(!Objects.equals(equippedDevice.get(1), line.get(2))){
                                    equippedDevice.set(1, line.get(2));
                                    smartCamera.add(equippedDevice);
                                    smartCamera.remove(devices);
                                    clock += 1;
                                    break;
                                }else{
                                    output.write("ERROR: This device is already switched " + line.get(2) + "!\n");
                                    clock += 1;
                                    break;
                                }
                            }else{
                                output.write("ERROR: Erroneous command!\n");
                                clock += 1;
                                break;
                            }
                        }
                    }
                    if (clock == 30){
                        output.write("ERROR: There is not such a device!\n");
                    }
                }
            }
            //Programme sets new time and if the time is behind from current time, it gives an error.
            else if(line.get(0).equals("SetTime")){
                try{
                    InitialTime date = new InitialTime();
                    String settingTime = date.getTime(i);
                    LocalDateTime InitialTime = LocalDateTime.parse(localDate.get(0), DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
                    LocalDateTime setTime = LocalDateTime.parse(settingTime, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
                    long diff = InitialTime.until(setTime, ChronoUnit.MINUTES);
                    if(diff < 0){
                        output.write("ERROR: Time cannot be reversed!\n");
                    }
                    else{
                        localDate.set(0, settingTime);
                    }
                }
                catch (Exception e){
                    output.write("ERROR: Time format is not correct!\n");
                }

        }
            // Skip minutes of current time and gives an error if minute is negative or 0.
            else if(line.get(0).equals("SkipMinutes")){
                if (line.size() != 2){
                    output.write("ERROR: Erroneous command!\n");
                }
                else{
                    try{
                        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                        Date d = timeFormat.parse(localDate.get(0));
                        long timeValue = Long.parseLong(line.get(1));

                        if(timeValue < 0){
                            output.write("ERROR: Time cannot be reversed!\n");
                        }
                        else if(timeValue == 0){
                            output.write("ERROR: There is nothing to skip!\n");
                        }
                        else{
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(d);
                            cal.add(Calendar.MINUTE, (int) timeValue);
                            String Time = timeFormat.format(cal.getTime());
                            localDate.set(0, Time);
                        }
                    }catch (Exception e){
                        output.write("ERROR: Erroneous command!\n");
                    }
                }
        }
            // Only checks for line size.
            else if(line.get(0).equals("SetSwitchTime")) {
            if (line.size() < 3) {
                output.write("ERROR: Erroneous command!\n");
            }
        }
            // Removes the device if there is no any errors.(Only for smart lamp and smart color lamp.)
            else if(line.get(0).equals("Remove")){
                if (line.size() != 2){
                    output.write("ERROR: Erroneous command!\n");
                }
                else{
                    if(!deviceNames.contains(line.get(1))){
                        output.write("ERROR: There is not such a device!\n");
                    }
                    else {
                        for(int devices = 0; devices < smartLamp.size(); devices++){
                            ArrayList<String> equippedDevice = smartLamp.get(devices);
                            if (Objects.equals(equippedDevice.get(0), line.get(1))){
                                output.write("SUCCESS: Information about removed smart device is as follows:\n");
                                output.write("Smart Lamp " + equippedDevice.get(0) + " is off and its" +
                                        " kelvin value is " + equippedDevice.get(2) +"K with " + equippedDevice.get(3) +
                                        "% brightness, and its time to switch its status is null.\n");
                                smartLamp.remove(devices);
                                deviceNames.remove(equippedDevice.get(0));
                                break;
                            }
                        }
                        for(int devices = 0; devices < smartColorLamp.size(); devices++){
                            ArrayList<String> equippedDevice = smartColorLamp.get(devices);
                            if (Objects.equals(equippedDevice.get(0), line.get(1))){
                                if (equippedDevice.get(2).startsWith("0x")){
                                    output.write("SUCCESS: Information about removed smart device is as follows:\n");
                                    output.write("Smart Color Lamp " + equippedDevice.get(0) + " is off and its " +
                                            "color value is " + equippedDevice.get(2) +" with " + equippedDevice.get(3) +
                                            "% brightness, and its time to switch its status is null.\n");
                                    smartColorLamp.remove(devices);
                                    deviceNames.remove(equippedDevice.get(0));
                                    break;
                                }
                                else{
                                    output.write("SUCCESS: Information about removed smart device is as follows:\n");
                                    output.write("Smart Color Lamp " + equippedDevice.get(0) + " is off and its " +
                                            "color value is " + equippedDevice.get(2) +"K with " + equippedDevice.get(3) +
                                            "% brightness, and its time to switch its status is null.\n");
                                    smartColorLamp.remove(devices);
                                    deviceNames.remove(equippedDevice.get(0));
                                    break;
                                }

                            }
                        }

                    }
                }
        }
            // Only checks for the line size.
            else if(line.get(0).equals("ZReport")){
                if(line.size() != 1){
                    output.write("ERROR: Erroneous command!\n");
                }
                else{
                    output.write("Time is:\t" + localDate.get(0) + "\n");
                }
        }
            // Only checks for the line size.
            else if(line.get(0).equals("Nop")){
                if(line.size() != 1){
                    output.write("ERROR: Erroneous command!\n");
                }

        }
            // If any illegal command happens it gives an error.
            else{
                output.write("ERROR: Erroneous command!\n");
        }
        }
        output.close();
    }

}

