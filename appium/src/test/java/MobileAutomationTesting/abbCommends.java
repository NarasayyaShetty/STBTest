package MobileAutomationTesting;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class abbCommends {
	 public static void main(String[] args) {
         String deviceIp = "192.168.1.16"; // ✅ Replace with your device's actual IP
         String packageName="in.startv.hotstar";

         // Step 1: Set device to TCP/IP mode
        // runAdbCommand("adb", "tcpip", "5555");

         // Step 2: Connect over Wi-Fi
        // runAdbCommand("adb", "connect", deviceIp + ":5555");

        // runAdbCommand("adb","reboot");
         runAdbCommand("adb","devices");
       //  runAdbCommand("adb","shell", "monkey", "-p", packageName, "-c", "android.intent.category.LAUNCHER", "1");
       //  runAdbCommand("adb","shell","dumpsys","media.audio_flinger","|","grep","-i","Format" );
     }

     public static void runAdbCommand(String... command) {
         try {
             ProcessBuilder builder = new ProcessBuilder(command);
             builder.redirectErrorStream(true);
             Process process = builder.start();

             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(process.getInputStream())
             );
             String line;
             while ((line = reader.readLine()) != null) {
                 System.out.println(line);
             }

             process.waitFor();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

}
