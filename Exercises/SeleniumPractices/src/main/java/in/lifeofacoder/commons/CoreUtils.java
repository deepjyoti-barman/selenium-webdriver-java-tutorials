package in.lifeofacoder.commons;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class CoreUtils {

    /**
     * It captures the screenshot of the webpage and returns the absolute path of the location where the image is saved
     *
     * @param driver the current and active driver instance
     * @return The absolute path of captured image
     * @throws IOException
     * @author Deepjyoti Barman
     * @since Jan 26, 2020
     */
    public static String captureSnapshot(WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        String img = "image_" + getCurrentTimestamp() + ".png";
        String imgPath = "target/spark-reports/screenshots/" + img;

        FileUtils.copyFile(srcFile, new File(imgPath));
        return System.getProperty("user.dir") + "/" + imgPath;
    }

    public static String getDate() {
        return new SimpleDateFormat("MM/dd/yyyy").format(new Date());
    }

    public static String getDate(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static String getDate(String dateFormat, int noOfDaysToAddOrDeduct) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, noOfDaysToAddOrDeduct);
        return new SimpleDateFormat(dateFormat).format(calendar.getTime());
    }

    public static String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String getTime(String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(new Date());
    }

    public static String getTime(String timeFormat, int noOfHoursToAddOrDeduct) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, noOfHoursToAddOrDeduct);
        return new SimpleDateFormat(timeFormat).format(calendar.getTime());
    }

    /**
     * It converts the current timestamp into a String in the following format:
     *  format: DD-MM-YYYY hh:mm:ss a
     *
     * @return A String where the current timestamp is in 'DD-MM-YYYY hh:mm:ss a' format
     * @author Deepjyoti Barman
     * @since Jan 26, 2020
     */
    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss-a").format(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Shift the focus to the given element
     *
     * @param driver the current and active driver instance
     * @param element element on the webpage to focus
     * @return The given web element
     * @author Deepjyoti Barman
     * @since December 26, 2020
     */
    public static WebElement focus(WebDriver driver, WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    /**
     * Used to kill the all the 'chomedriver' processes running in background. Multi-support provided for Windows, Linux and Mac operating systems.
     *
     * @throws Exception
     * @author Deepjyoti Barman
     * @since September 09, 2020
     */
    public static void killChromeDriver() {
        final String OS                = System.getProperty("os.name").toLowerCase();
        final String FILE_PATH_WINDOWS = "process_kill_windows.bat";
        final String FILE_PATH_UNIX    = "process_kill_unix.sh";

        String[] cmd = OS.contains("windows") ? new String[] { "cmd", "/c", FILE_PATH_WINDOWS } : new String[] { "sh", "-p", FILE_PATH_UNIX };

        try {
            File file = new File(cmd[2]);

            if (file.exists() && file.isFile()) {
                // If we are running in Linux or Mac OS and the file does not have execute permission then provide the same
                if (!OS.contains("windows") && !file.canExecute())
                    Runtime.getRuntime().exec(new String[] { "chmod", "+x", FILE_PATH_UNIX });
            } else {
                throw new FileNotFoundException("File does not exist or file path is incorrect");
            }

            Process process       = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output  = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            // Once the process terminates normally, displays the output
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(output);
            } else {
                System.out.println("Abnormal termination of process, could not print the output");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for the key and returns its value if the key is found in properties file present in the file location given.
     *
     * @param key Key to be searched
     * @param fileLoc Absolute or relative path of the .properties file disk drive
     * @return Value of the given key
     * @author Deepjyoti Barman
     * @since January 20, 2021
     */
    public static String getProperty(String key, String fileLoc) throws IOException, InvalidArgumentException {
        Properties prop = new Properties();
        prop.load(new FileReader(fileLoc));
        final String value = prop.getProperty(key);

        if (value == null)
            throw new InvalidArgumentException("Key not found in the given properties file");

        return value.trim();
    }
}
