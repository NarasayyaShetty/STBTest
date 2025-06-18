package Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import static AppsTesting.AdbCommendsClass.*;

public class LogcatCollector {
	
	private Process process;
    private BufferedWriter fullWriter;
    private File fullLogFile;
    private Thread logThread;
    private String appPackage;
    
    public LogcatCollector(String appName) throws IOException {
        appPackage = getPackageName(appName);
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
        String time = new SimpleDateFormat("HHmmss").format(new Date());

        String basePath = System.getProperty("user.dir") + File.separator + "Results" +
                File.separator + "LogcatLogs" + File.separator + date;
        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();

        fullLogFile = new File(dir, "FullLogs_" + appPackage + "_" + time + ".txt");
        fullWriter = new BufferedWriter(new FileWriter(fullLogFile));
    }
    public void start() throws IOException {
        Runtime.getRuntime().exec("adb logcat -c");
        ProcessBuilder pb = new ProcessBuilder("adb", "logcat", "-v", "time");
        process = pb.start();

        logThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while (!Thread.currentThread().isInterrupted() && (line = reader.readLine()) != null) {
                    fullWriter.write(line + "\n");
                    fullWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        logThread.start();
    }
    public void stop() {
        try {
            if (logThread != null) logThread.interrupt();
            if (process != null) process.destroy();
            if (fullWriter != null) fullWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
