import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class BuyMeMobileTests {
//    create Android driver
    private static AndroidDriver<MobileElement> driver;

//     create and attach reporter
    private static ExtentReports extent ;
    private static ExtentTest test ;

//    declare default path  for report, it will be rewritten from xml
    private static String reportPath = Constants.DEFAULT_PATH;

//    declare driver capabilities read from xml
    private static String appPackage;
    private static String appActivity;

//    set test stamp
    private static final String testTimeSpamp = String.valueOf(System.currentTimeMillis());


    @BeforeClass
    public static void setUp() throws IOException {
//        get reporter path from xml file
        try{
            reportPath  = HelperFunctions.getData(Constants.SOURCE,Constants.REPORT_PATH);
        }catch(ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        };

//      set up and attach reporter
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath + "BuyMeMobileSanity_report.html");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

//      set up report description and custom info
        test = extent.createTest("BuyMeMobileTest", "BuyMe app sanity test");
        extent.setSystemInfo("Environment", "Mobile Android");
        extent.setSystemInfo("Tester", "Elena");

//      log results
        test.log(Status.INFO, "Report system is initialized");

//      set up Android driver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");

//      read driver capabilities from the xml file
        try{
            appPackage  = HelperFunctions.getData(Constants.SOURCE,Constants.APP_PACKAGE);
            appActivity  = HelperFunctions.getData(Constants.SOURCE,Constants.APP_ACTIVITY);
        }catch(ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        };

//      set driver capabilities
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
//        start the driver
        try {
            driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub/"), capabilities);
            test.log(Status.INFO, "Android driver is set and ready ");
        }catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FATAL, "Driver Connection Failed! Test run fails " + e.getMessage());
        };

//      set implicit Wait
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void test00_appStart() throws IOException {
//        check if the screen is login
        try{
            Assert.assertTrue(IntroScreen.introLayout(driver));
            test.log(Status.INFO, "Test00: App Login screen is displayed", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test00_1")).build());
        }catch(Exception e){
            e.printStackTrace();
            test.log(Status.FAIL, "Test00: Login screen is not displayed:" + e.getMessage());
        };
    }

    @Test
    public void test01_LoginWithGoogle() throws IOException {
//      login with google
        try {
            IntroScreen.logInWithGoogle(driver);
            Assert.assertTrue(GeneralScreen.getScreenName(driver).contains(Constants.HOME_SCR));
            test.log(Status.PASS, "Test01: User is logged in", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test00_2")).build());
        }catch(Exception e){
            e.printStackTrace();
            test.log(Status.FAIL, "Test01: Fail to logIn:" + e.getMessage());
        };
    }

    @Test
    public void test02_printSiteInfo() throws IOException {
//      get print site info: check home screen is loaded
        if(GeneralScreen.getScreenName(driver).contains(Constants.HOME_SCR)){
                test.log(Status.INFO, "Test02: Home screen is opened", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test02")).build());
        }
//        1. goto personal screen
        HomeScreen.goToPersonal(driver);
        test.log(Status.INFO, "Test02: Personal screen is opened", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test02_1")).build());
//        2. goto about screen
        PersonalScreen.goToInfo(driver);
        test.log(Status.INFO, "Test02: About screen is opened", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test02_2")).build());
//        3.collect about info
        String about = PersonalScreen.getAppInfo(driver);
        if(about.contains("Error: ")){
            test.log(Status.FAIL, "Test02: error getting app info"+ about);
        }else{
            test.log(Status.PASS, "Test02: Collected Info: \n" + about);
        };
    }

    @Test
    public void test03_goBackHome() throws IOException {
//      go back to personnel screen, proceed to home
        try{
            PersonalScreen.closeInfo(driver);
            PersonalScreen.goToHome(driver);
            test.log(Status.PASS, "Test03: Home screen is reopened", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test03")).build());
        }catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Test03: Home screen is not reopened");
        };
    };

    @Test
    public void test10_SelectCategoryBySwipe() throws IOException {
        try{
//            refresh home screen
            HomeScreen.goToHome(driver);
            test.log(Status.INFO, "Test10: Ready for new category selection: Home screen is opened");
//            swipe and select
            HomeScreen.selectBySwipe(driver);
            test.log(Status.PASS, "Test10: Category by swipe is selected", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test10")).build());
        }catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Test10: Fail to select category by swipe");
        };
    }

    @Test
    public void test11_SelectCategoryByScroll() throws IOException {
        try{
//      refresh home screen
            HomeScreen.goToHome(driver);
            test.log(Status.INFO, "Test11: Ready for new category selection: Home screen is opened");
//      scroll and select
            HomeScreen.selectByScroll(driver);
            test.log(Status.PASS, "Test11: Category by scroll is selected", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test11")).build());

        }catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Test11: Fail to select category by scroll");
        }
    }


    @Test
    public void test12_SelectCategory() throws IOException {
//        select gift category
        try{
//       refresh home screen
            HomeScreen.goToHome(driver);
            test.log(Status.INFO, "Test12: Ready for another selection: Home screen is reopened");
            HomeScreen.selectCategory(driver, Constants.GIFT_CATEGORY);
            test.log(Status.PASS, "Test12: Category by name is selected", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test12")).build());
        }catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Test12: Fail to select category by name");
        };
    };

    @Test
    public void test13_SetGiftCard() throws IOException {
//        select gift card and set value
        try {
            HomeScreen.selectGiftCard(driver,Constants.GIFT_VALUE);
            test.log(Status.INFO, "Test13: The card is selected, gift value is added ", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test13")).build());
            HomeScreen.clickSelectGift(driver);
            Assert.assertTrue(GeneralScreen.getScreenName(driver).contains(Constants.RECEIVER_SCR));
            test.log(Status.PASS, "Test13: Gift is selected", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test13")).build());
        } catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Test13: Fail to select gift card and set value");
        }
    };

    @Test
    public void test21_receiverInfo()  {
//        check receiver screen is displayed
        try {
            Assert.assertTrue(SenderScreen.receiverStep(driver) > 0);
            test.log(Status.INFO, "Test21: Receiver screen step1 is available");
            SenderScreen.setReceiver(driver);
            test.log(Status.INFO, "Test21: Receiver info is filled out", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test21_1")).build());
            SenderScreen.clickNext(driver);
            test.log(Status.PASS, "Test21: Receiver info is set", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test21_2")).build());
        }catch(Exception e){
            e.printStackTrace();
            test.log(Status.FAIL, "Test21: Receiver info is not set");
        }
    }

    @Test
    public void test22_sendSettings()  {
        try {
            Assert.assertTrue(SenderScreen.receiverStep(driver) > 1);
            test.log(Status.INFO, "Test21: Receiver screen step2 is available");
            SenderScreen.setSendOptions(driver,Constants.EMAIL);
            test.log(Status.INFO, "Test22: Receiver options are selected", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test22_1")).build());
            SenderScreen.clickNext(driver);
            Assert.assertTrue(SenderScreen.receiverStep(driver) > 2);
            test.log(Status.PASS, "Test22: Receiver options are set", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test22_2")).build());
        }catch(Exception e){
            e.printStackTrace();
            test.log(Status.FAIL, "Test22: Fail to set receiver options");
        };
    }

    @Test
    public void test31_logOut()  {
        try {
//            go back to Home
            SenderScreen.goBack(driver);
            test.log(Status.INFO, "Test31: Home screen is reopened",MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test31_1" )).build());
//            goto personal screen
            HomeScreen.goToPersonal(driver);
            test.log(Status.INFO, "Test31: Personal screen is reopened", MediaEntityBuilder.createScreenCaptureFromPath(HelperFunctions.takeScreenShot(driver, reportPath + testTimeSpamp + "_test31_2")).build());
            PersonalScreen.logout(driver);
            Assert.assertTrue(GeneralScreen.getScreenName(driver).contains(Constants.HOME_SCR));
            test.log(Status.PASS, "Test31: User is logged out");
        }catch(Exception e){
            e.printStackTrace();
            test.log(Status.FAIL, "Test31: Fail to logOut");
        };
    }

    @AfterClass
    public static void afterClass() {
        test.log(Status.INFO, "All tests are completed");
//      close the driver
        driver.quit();
//      build and flush report
        extent.flush();
    }

}
