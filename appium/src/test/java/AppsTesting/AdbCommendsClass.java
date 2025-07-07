package AppsTesting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AdbCommendsClass {

	

	public static void currentFocus(AndroidDriver driver) {
		try {
			ArrayList<String> focusWindow=runAdbCommand("adb", "shell", "dumpsys", "window", "|", "grep","mCurrentFocus");
			
			for(String value:focusWindow) {
				if(value.contains("com.jio.stb.screensaver/android.service.dreams.DreamActivity")) {
					System.out.println("Current is screen page, pressing back key to disable the screensaver");
					driver.pressKey(new KeyEvent(AndroidKey.BACK));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Printing app version name
	public static String printAppNameAndVersion(String appName) {
		appName = appName.toLowerCase();
		String appNameAndVersion = "";
		try {
			Map<String, String> map = Map.of("discoveryplus", "com.discoveryplus.tv.jiotv", "docubay",
					"com.epic.docubay", "hoichoi", "com.hoichoi.mitv", "jiohotstar", "in.startv.hotstar", "primevideo",
					"com.amazon.amazonvideo.livingroom", "sonyliv", "com.jiotv.sonyliv", "sunnxt", "com.suntv.sunnxt",
					"youtube", "com.jio.yt", "zee5", "com.zee5.aosp", "netflix", "com.netflix.ninja");

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (key.contains(appName)) {
					appNameAndVersion += key;
					ArrayList<String> list = runAdbCommand("adb", "shell", "dumpsys", "package", value, "|", "grep",
							"version");
					String temp = list.get(1);
					String[] appVersion = temp.split("=");
					String temp1 = appVersion[1];
					appNameAndVersion += "_" + temp1;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appNameAndVersion;
	}
	//PrintPackahe name
	public static String getPackageName(String appName) {
		appName = appName.toLowerCase();
		String packageName= "";
		try {
			Map<String, String> map = Map.of("discoveryplus", "com.discoveryplus.tv.jiotv", "docubay",
					"com.epic.docubay", "hoichoi", "com.hoichoi.mitv", "jiohotstar", "in.startv.hotstar", "primevideo",
					"com.amazon.amazonvideo.livingroom", "sonyliv", "com.jiotv.sonyliv", "sunnxt", "com.suntv.sunnxt",
					"youtube", "com.jio.yt", "zee5", "com.zee5.aosp", "netflix", "com.netflix.ninja");

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (key.contains(appName)) {
					 packageName=value;
					 break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return packageName;
	}
	
	public static String onlyDeviceName() {
		String deviceName="";
		try {
			ArrayList<String> list=runAdbCommand("adb", "shell", "getprop", "ro.product.model");
			deviceName+=list.get(0);
		}catch(Exception e) {
			System.out.println("Exception occureed while printing the device name");
			e.printStackTrace();
		}
		return deviceName;
	}
					
					
	

	// prints the device name with version
	public static String deviceName() {
		String deviceName = "";
		try {
			ArrayList<String> list = runAdbCommand("adb", "shell", "getprop", "ro.product.model");
			deviceName += list.get(0);

			ArrayList<String> list1 = runAdbCommand("adb", "shell", "getprop", "ro.build.fingerprint");
			System.out.println(list1);
			Thread.sleep(2000);
			String fingerPrint = list1.get(0);
			String[] str = fingerPrint.split("/");
			deviceName += "_" + str[3];

		} catch (Exception e) {
			e.printStackTrace();

		}
		return deviceName;
	}

	// prints the logs
	public static void logcatLogs(String appName) {
		
	    new Thread(() -> {
	        try {
	            // Date-based folder
	            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
	            String currentDate = dateFormat.format(Calendar.getInstance().getTime());

	            // Timestamp for filenames
	            String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());

	            // Base path
	            String basePath = System.getProperty("user.dir") + File.separator + "Results" + File.separator
	                    + "LogcatLogs" + File.separator + currentDate;
	            File dir = new File(basePath);
	            if (!dir.exists())
	                dir.mkdirs();

	            // Full log is always created
	            File fullLog = new File(dir, "FullLogs_" + appName + "_" + timeStamp + ".txt");
	            BufferedWriter fullWriter = new BufferedWriter(new FileWriter(fullLog));

	            // For Crash/ANR: declare but delay creation
	            BufferedWriter crashWriter = null;
	            BufferedWriter anrWriter = null;
	            boolean crashFileCreated = false;
	            boolean anrFileCreated = false;

	            // Clear old logs
	            Runtime.getRuntime().exec("adb logcat -c");

	            // Start logcat
	            ProcessBuilder pb = new ProcessBuilder("adb", "logcat", "-v", "time");
	            Process process = pb.start();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

	            String line;
	            System.out.println("Collecting logs... Press Ctrl+C to stop.");

	            while ((line = reader.readLine()) != null) {
	                fullWriter.write(line);
	                fullWriter.newLine();
	                fullWriter.flush();

	                // CRASH detection
	                if (line.contains("FATAL EXCEPTION") && line.contains(appName)) {
	                    if (!crashFileCreated) {
	                        File crashLog = new File(dir, "CrashLogs_" + appName + "_" + timeStamp + ".txt");
	                        crashWriter = new BufferedWriter(new FileWriter(crashLog));
	                        crashFileCreated = true;
	                    }
	                    System.out.println("[Crash] " + line);
	                    crashWriter.write("==== CRASH START ====\n");
	                    crashWriter.write(line + "\n");
	                    for (int i = 0; i < 15; i++) {
	                        String next = reader.readLine();
	                        if (next == null) break;
	                        crashWriter.write(next + "\n");
	                    }
	                    crashWriter.write("==== CRASH END ====\n\n");
	                    crashWriter.flush();
	                }

	                // ANR detection
	                else if (line.contains("ANR in") && line.contains(appName)) {
	                    if (!anrFileCreated) {
	                        File anrLog = new File(dir, "ANRLogs_" + appName + "_" + timeStamp + ".txt");
	                        anrWriter = new BufferedWriter(new FileWriter(anrLog));
	                        anrFileCreated = true;
	                    }
	                    System.out.println("[ANR] " + line);
	                    anrWriter.write("==== ANR START ====\n");
	                    anrWriter.write(line + "\n");
	                    for (int i = 0; i < 10; i++) {
	                        String next = reader.readLine();
	                        if (next == null) break;
	                        anrWriter.write(next + "\n");
	                    }
	                    anrWriter.write("==== ANR END ====\n\n");
	                    anrWriter.flush();
	                }
	            }

	            // Cleanup
	            fullWriter.close();
	            if (crashWriter != null) crashWriter.close();
	            if (anrWriter != null) anrWriter.close();
	            reader.close();
	            process.destroy();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }).start();
	    
	}
	
	public static Map<String, String> collectLogs(String appName, String methodName) {
	    Map<String, String> logFilePaths = new HashMap<>();
	    String appPackage = getPackageName(appName);

	    try {
	        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
	        String time = new SimpleDateFormat("HHmmss").format(new Date());

	        String basePath = System.getProperty("user.dir") + File.separator + "Results" +
	                File.separator + "LogcatLogs" + File.separator + date;
	        File dir = new File(basePath);
	        if (!dir.exists()) dir.mkdirs();

	        File fullLog = new File(dir, "FullLogs_" + appPackage + "_" + methodName + "_" + time + ".txt");
	        File crashLog = new File(dir, "CrashLogs_" + appPackage + "_" + methodName + "_" + time + ".txt");
	        File anrLog = new File(dir, "ANRLogs_" + appPackage + "_" + methodName + "_" + time + ".txt");

	        logFilePaths.put("full", fullLog.getAbsolutePath());

	        BufferedWriter fullWriter = new BufferedWriter(new FileWriter(fullLog));
	        BufferedWriter crashWriter = new BufferedWriter(new FileWriter(crashLog));
	        BufferedWriter anrWriter = new BufferedWriter(new FileWriter(anrLog));

	        boolean crashFound = false, anrFound = false;

	        ProcessBuilder pb = new ProcessBuilder("adb", "logcat", "-d", "-v", "time");
	        Process process = pb.start();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

	        String line;
	        while ((line = reader.readLine()) != null) {
	            fullWriter.write(line);
	            fullWriter.newLine();

	            if (line.contains("FATAL EXCEPTION") && line.contains(appPackage)) {
	                crashFound = true;
	                crashWriter.write("==== CRASH START ====\n" + line + "\n");
	                for (int i = 0; i < 15; i++) {
	                    String next = reader.readLine();
	                    if (next == null) break;
	                    crashWriter.write(next + "\n");
	                }
	                crashWriter.write("==== CRASH END ====\n\n");
	                logFilePaths.put("crash", crashLog.getAbsolutePath());
	            }

	            if (line.contains("ANR in") && line.contains(appPackage)) {
	                anrFound = true;
	                anrWriter.write("==== ANR START ====\n" + line + "\n");
	                for (int i = 0; i < 10; i++) {
	                    String next = reader.readLine();
	                    if (next == null) break;
	                    anrWriter.write(next + "\n");
	                }
	                anrWriter.write("==== ANR END ====\n\n");
	                logFilePaths.put("anr", anrLog.getAbsolutePath());
	            }

	            fullWriter.flush();
	        }

	        fullWriter.close();
	        crashWriter.close();
	        anrWriter.close();
	        reader.close();
	        process.destroy();

	        if (!crashFound) crashLog.delete();
	        if (!anrFound) anrLog.delete();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return logFilePaths;
	}


	

	// performs the root
	public static void root() {
		try {
			runAdbCommand("adb", "root");
		} catch (Exception e) {
			System.out.println("Exception occureed");
			e.printStackTrace();
		}
	}
	
	public static void startServer() {
		try {
			runAdbCommand("adb","kill-server");
			Thread.sleep(2000);
			runAdbCommand("adb","start-server");
		}
		catch(Exception e) {
			System.err.println("Exception occureed");
			e.printStackTrace();
		}
	}

	// perform the remount
	public static void remount() {
		try {
			runAdbCommand("adb", "remount");
		} catch (Exception e) {
			System.out.println("Exception occureed");
			e.printStackTrace();
		}
	}

	// perform the key event
	public static void keyEvent(String keyevent) {
		try {
			runAdbCommand("adb", "shell", "input", "keyevent", keyevent);
		} catch (Exception e) {
			System.out.println("Exception is occureed");
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

	// reboot the stb/tv
	public static void reboot() {
		try {
			runAdbCommand("adb", "reboot");
		} catch (Exception e) {
			System.out.println("Exception Occureed");
			e.printStackTrace();
		}
	}

	// Prints list of all packages
	public static void listPackages() {
		try {
			ArrayList<String> list = runAdbCommand("adb", "shell", "pm", "list", "packages");

		} catch (Exception e) {
			System.out.println("Exception occurred");
			e.printStackTrace();
		}
	}

	// Finds connected devices
	public static void devices() {
		try {
			runAdbCommand("adb", "devices");
		} catch (Exception e) {
			System.out.println("Exception occered while adb devices");
			e.printStackTrace();
		}
	}

	// first it will disconnect all the connected devices, then it will connect
	// which we provided ip, then it will do root and
	// remount
	public static void connectStb(String deviceIp) {
		try {
			
			//first disconnect all the connected devices
			disConnect();
			
			//First it will kill the server then it will start the server
			startServer();
			runAdbCommand("adb", "connect", deviceIp);
			root();
			remount();

		} catch (Exception e) {
			System.out.println("Exception is occureed");
			e.printStackTrace();
		}
	}

	// disconnect all the devices
	public static void disConnect() {
		try {
			runAdbCommand("adb", "disconnect");
		} catch (Exception e) {
			System.out.println("Exception is occureed");
			e.printStackTrace();
		}
	}

//execute the adb commends and return the console/buffer output
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
