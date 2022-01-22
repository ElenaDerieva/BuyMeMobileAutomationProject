import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.By;

public class PersonalScreen extends GeneralScreen {

    public static void goToInfo(AndroidDriver driver){
//        goto about sub screen
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"על BUYME\")").click();
        Assert.assertTrue(driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/main_toolbar_title")).getText().contains(Constants.ABOUT_SCR));
    };

    public static void closeInfo(AndroidDriver driver){
//        go back to personnel screen
        driver.findElement(By.className("android.widget.ImageButton")).click();
//        Assert.assertTrue(driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/main_toolbar_title")).getText().contains(Constants.PERSONAL_SCR));
    };

    public static String getAppInfo(AndroidDriver driver){
//       get application info: toolbar title, title text, content text
        String appInfo;
        try{
            appInfo = driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/main_toolbar_title")).getText() +
                    "\n" + driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/titleText")).getText() +
                    "\n" + driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/contentText")).getText();
        }catch(Exception e){
            appInfo = "Error: exception reported " + e.getMessage();
        }
        return appInfo;
    };

    public static void logout(AndroidDriver driver){
//        logout the user
        HelperFunctions.swipe(driver,100, 1500, 100, 500, 1000);
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"יציאה\")").click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(MobileBy.id("android:id/button1")).click();
    }

}
