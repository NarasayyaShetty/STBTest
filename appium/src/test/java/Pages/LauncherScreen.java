package Pages;

import java.util.Map;
import static AppsTesting.AdbCommendsClass.*;

public class LauncherScreen {

	public static void launchApp(String appName) {
		appName = appName.toLowerCase();
		try {

			Map<String, String> map = Map.of("discoveryplus",
					"com.discoveryplus.tv.jiotv/com.discoveryplus.tv.ui.DPlusJioTvActivity", "docubay",
					"com.epic.docubay/.ui.launcher.activity.LauncherActivity", "hoichoi",
					"com.hoichoi.mitv/com.viewlift.hoichoi.ui.splash.MainActivity", "jiohotstar",
					"in.startv.hotstar/com.hotstar.MainActivity", "primevideo",
					"com.amazon.amazonvideo.livingroom/com.amazon.ignition.IgnitionActivity", "sonyliv",
					"com.jiotv.sonyliv/com.sonyliv.ui.splash.SplashActivity", "sunnxt",
					"com.suntv.sunnxt/com.androidtv.myplex.ui.activity.SPlashActivity", "youtube",
					"com.jio.yt/dev.cobalt.app.MainActivity", "zee5",
					"com.zee5.aosp/com.zee5.android.launch.presentation.AppStartActivity", "netflix",
					" com.netflix.ninja/.MainActivity");
			String activityName = "";

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String app = entry.getKey();
				String packageN = entry.getValue();
				if (app.contains(appName)) {
					activityName = packageN;
					System.out.println(activityName);
					runAdbCommand("adb", "shell", "am", "start", "-n", activityName);
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Exception is occureed while launching the app");
			e.printStackTrace();
		}
	}

}
