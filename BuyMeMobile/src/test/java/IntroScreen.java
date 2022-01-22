import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class IntroScreen {
    public static Boolean introLayout(AndroidDriver driver){
        Boolean intLayout = false;
        intLayout = driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/registrationDetailsTitle")).isDisplayed();
        return intLayout;
    }

    public static void logInWithGoogle(AndroidDriver driver){
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/googleButton")).click();
    }

}
