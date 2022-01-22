import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;

public class GeneralScreen {

    public static String getScreenName(AndroidDriver driver) {
//        return screen title
        String screenTitle;
        try {
            screenTitle = driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/main_toolbar_title")).getText();
        } catch (Exception e) {
            screenTitle = "Error: exception reported " + e.getMessage();
        }
        return screenTitle;
    };

    public static void goToPersonal(AndroidDriver driver){
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/profileTab")).click();
        Assert.assertTrue(driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/main_toolbar_title")).getText().contains(Constants.PERSONAL_SCR));
    };

    public static void goToHome(AndroidDriver driver){
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/homeTab")).click();
        Assert.assertTrue(driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/main_toolbar_title")).getText().contains(Constants.HOME_SCR));

    };

}
