import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class SenderScreen {

//    ensure the gift is selected
    public static Integer receiverStep(MobileDriver driver){
        Integer step = 0;
        if(driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/step3Text")).isEnabled()){
            step = 3;
        }else if (driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/step2Text")).isEnabled()){
            step = 2;
        } else if (driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/step1Text")).isEnabled()){
            step = 1;
        }
        return step;
    }

    public static void setReceiver(AndroidDriver driver){
//        set receiver

        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/toEditText")).clear();
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/toEditText")).sendKeys(Constants.RECEIVER);
//        select event
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/eventSpinner")).click();
//        select index 2 (text: תודה)
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"תודה\")").click();
//        send blessing
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/blessEditText")).clear();
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/blessEditText")).sendKeys(Constants.BLESSING);
    }

    public static void clickNext(AndroidDriver driver){
//        click next button
        HelperFunctions.swipe(driver,100, 1500, 100, 500, 1000);
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/goNextButton")).click();
    }

    public static void setSendOptions(AndroidDriver driver, String email){
//         select option 'Now"
        driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/nowRadioButton")).click();
//        select option by e-mail
        AndroidElement checkboxMail = (AndroidElement) driver.findElements(MobileBy.className("android.widget.CheckBox")).get(2);
        checkboxMail.click();
//        check whether mail field is displayed
        AndroidElement mailFiled = (AndroidElement) driver.findElements(MobileBy.className("android.widget.EditText")).get(0);
        mailFiled.clear();
        mailFiled.sendKeys(email);
    }

    public static void goBack(AndroidDriver driver){
//        go back to personnel screen
        Boolean flag = true;
        while (flag){
            driver.findElement(MobileBy.className("android.widget.ImageButton")).click();
            try {
                if (driver.findElement(MobileBy.id("il.co.mintapp.buyme:id/homeTab")).isDisplayed()) {
                    flag = false;
                }
            } catch(Exception e){
//                e.printStackTrace();
            }
        }
    };

}
