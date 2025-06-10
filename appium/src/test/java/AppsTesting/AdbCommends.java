package AppsTesting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdbCommends {
	public static void main(String[] args) {

		String deviceIp = "192.168.1.16"; // ✅ Replace with your device's actual IP
//	         String packageName="in.startv.hotstar";

		// Step 1: Set device to TCP/IP mode
		// runAdbCommand("adb", "tcpip", "5555");

		// Step 2: Connect over Wi-Fi
		// ArrayList<String> list=runAdbCommand("adb", "connect", deviceIp + ":5555");
//	        for(String value:list) {
//	        	System.out.println("Value is :"+value);
//	        }

		// runAdbCommand("adb","reboot");
		// runAdbCommand("adb","shell","pm","list","packages");
//	        ArrayList<String> list=runAdbCommand("adb","devices");
//	        for(String value:list) {
//	        	System.out.println("Value is :"+value);
//	        }
		// runAdbCommand("adb", "shell", "am",
		// "start","-n","com.netflix.ninja/.MainActivity");
		// adb shell dumpsys window | find "mCurrentFocus"
		// runAdbCommand("adb","shell","dumpsys","window","|","find","mCurrentFocus");
		// runAdbCommand("adb", "shell", "dumpsys", "window"
		// ,"|","find","mCurrentFocus");

		// runAdbCommand( "adb","shell", "monkey" ,"-p", "com.suntv.sunnxt","-c",
		// "android.intent.category.DEFAULT", "1");

		// runAdbCommand("adb","shell", "monkey", "-p", packageName, "-c",
		// "android.intent.category.LAUNCHER", "1");
		// runAdbCommand("adb","shell","dumpsys","media.audio_flinger","|","grep","-i","Format"
		// );
		// runAdbCommand("adb","disconnect");
		// adb shell cat /sys/class/video/frame_width
		// runAdbCommand("adb","shell","cat"," ","/sys/class/video/frame_width");
		// runAdbCommand("adb","shell","cat","
		// ","/sys/class/video_poll/primary_src_fmt");
		// runAdbCommand("adb", "shell", "input", "keyevent", "3");
		// runAdbCommand("adb","root");
		// runAdbCommand("adb","remount");
		// runAdbCommand("adb","shell","input","keyevent","KEYCODE_F8");
		// runAdbCommand("adb", "shell", "getprop", "ro.product.model");
		runAdbCommand("adb", "shell", "dumpsys", "package", "in.startv.hotstar", "|", "grep", "version");
		// ArrayList<String> list=runAdbCommand( "adb", "shell", "getprop",
		// "ro.build.fingerprint");
//	      String fingerPrint=list.get(0);
//	      String[] str=fingerPrint.split("/");
//	      for(int i=0;i<str.length;i++) {
//	    	  System.out.println(i+ "    "+str[i]);
//	      }
//	         
	}

	public static void appLaunch(String packageName) {
		try {
			runAdbCommand("adb", "shell", "monkey", "-p", packageName, "-c", "android.intent.category.LAUNCHER", "1");
		} catch (Exception e) {
			System.out.println("Exception is occureed while launching the app");
			e.printStackTrace();
		}
	}

	public static void root() {
		try {
			runAdbCommand("adb", "root");
		} catch (Exception e) {
			System.out.println("Exception occureed");
			e.printStackTrace();
		}
	}

	public static void setTcpIp(String port) {
		try {
			runAdbCommand("adb", "tcpip", port);
		} catch (Exception e) {
			System.out.println("Exception is occureed");
		}
	}

	public static void reboot() {
		try {
			runAdbCommand("adb", "reboot");
		} catch (Exception e) {
			System.out.println("Exception Occureed");
			e.printStackTrace();
		}
	}

	public static void listPackages() {
		try {
			ArrayList<String> list = runAdbCommand("adb", "shell", "pm", "list", "packages");

		} catch (Exception e) {
			System.out.println("Exception occurred");
			e.printStackTrace();
		}
	}

	public static void devices() {
		try {
			runAdbCommand("adb", "devices");
		} catch (Exception e) {
			System.out.println("Exception occered while adb devices");
			e.printStackTrace();
		}
	}

	public static void connect(String deviceIp) {
		try {
			ArrayList<String> list = runAdbCommand("abd", "connect", deviceIp + ":5555");
			for (String value : list) {
				if (!value.isEmpty()) {
					System.out.println(value);
				}
			}

		} catch (Exception e) {
			System.out.println("Exception is occureed");
			e.printStackTrace();
		}
	}

	public static void disConnect(String deviceIp) {
		try {
			runAdbCommand("abd", "disconnect", deviceIp + ":5555");
		} catch (Exception e) {
			System.out.println("Exception is occureed");
			e.printStackTrace();
		}
	}

	public static ArrayList<String> runAdbCommand(String... command) {
		ArrayList<String> list = new ArrayList<>();
		try {
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.redirectErrorStream(true);
			Process process = builder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				list.add(line);
			}

			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
