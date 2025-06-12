package AppsTesting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static AppsTesting.AdbCommendsClass.*;

public class PlayerCommends {

	public static String getAudioOutput() {
		String latestFormat = "";
		try {
			ArrayList<String> audio = runAdbCommand("adb","shell","dumpsys","media.audio_flinger","|","grep","-i","Format");
			for (String line : audio) {
				if (line.contains("[AML_HAL]")) {
					break; // Stop at HAL info
				} else if (line.contains("Processing format")) {
					// Extract hex code from line
					String[] parts = line.trim().split(":");
					if (parts.length > 1) {
						String hexCode = parts[1].trim().split(" ")[0];
						latestFormat = mapAudioFormatToUserFriendly(hexCode);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while getting audio output:");
			e.printStackTrace();
			AdbCommends.root();
			return getAudioOutput(); // Retry
		}
		return latestFormat;
	}

	public static String mapAudioFormatToUserFriendly(String hexCode) {
	    switch (hexCode.toLowerCase()) {
	        case "0x1":
	        case "0x2":
	            return "PCM";

	        case "0x40000001": // AC3
	        case "0x40000002": // E-AC3
	        case "0xa000000":  // E-AC3 (usually 5.1, not Atmos by default)
	        case "0x5":
	        case "0x6":
	        case "0x7":
	            return "Dolby";

	        case "0xa000001": // E-AC3 JOC (Dolby Atmos)
	        case "0x80000000": // TrueHD (Atmos container)
	        case "0x5f000000":
	        case "0x5f000001":
	        case "0x5f000002":
	            return "Atmos";

	        default:
	            return "Unknown (" + hexCode + ")";
	    }
	}


	public static String getVisionOutput() {
		String s = "";
		try {
			ArrayList<String> vision = runAdbCommand("adb", "shell", "cat", " ",
					"/sys/class/video_poll/primary_src_fmt");
			s = vision.get(0);
		} catch (Exception e) {
			System.out.println("Exception is occureed");
			AdbCommends.root();
			getVisionOutput();
		}
		return s;
	}

	public static String getVideoResolution() throws InterruptedException {
		String s = "";

		try {
			ArrayList<String> width = runAdbCommand("adb", "shell", "cat", " ", "/sys/class/video/frame_width");
			ArrayList<String> height = runAdbCommand("adb", "shell", "cat", " ", "/sys/class/video/frame_height");
			s += width.get(0) + " " + height.get(0);

		} catch (Exception e) {
			System.out.println("Exception occureed");
			root();
			remount();
			getVideoResolution();
		}
		return s;
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
