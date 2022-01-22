import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.util.List;

public class HomeScreen extends GeneralScreen {

    public static void selectByScroll (AndroidDriver driver){
//        scroll to restaurants  "גיפט קארד למסעדות שף";
             driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
                + ".resourceId(\"il.co.mintapp.buyme:id/tab_title\")).scrollIntoView("
                + "new UiSelector().text(\"גיפט קארד למסעדות שף\"));").click();
//             small delay to take a picture, works good without it
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void selectBySwipe (AndroidDriver driver){
//        select category from the screen
        HelperFunctions.swipe(driver, 100,300,1000, 300, 200);
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/tab_title")).click();
    }

    public static void selectCategory (AndroidDriver driver, String gift){
//        select category from the screen
        List<MobileElement> tabs = driver.findElements(MobileBy.id("il.co.mintapp.buyme:id/tab_title"));
        for (MobileElement e : tabs  ){
            if (e.getText().contains(gift)){
                e.click();
            };
        };
    }

    public static void selectGiftCard (AndroidDriver driver, String giftValue){
//        select category from the screen
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/businessImage")).click();
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/priceEditText")).sendKeys(giftValue);
    }

    public static void clickSelectGift (AndroidDriver driver){
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/purchaseButton")).click();
    }

}


