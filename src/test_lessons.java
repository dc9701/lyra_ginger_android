private static AndroidDriver<AndroidElement> driver;
@BeforeClass
public void classInit() throws URISyntaxException, MalformedURLException {
 URL testAppUrl = getClass().getClassLoader().getResource("ApiDemos.apk");
 File testAppFile = Paths.get(Objects.requireNonNull(testAppUrl).toURI()).toFile();
 String testAppPath = testAppFile.getAbsolutePath();

 var desiredCaps = new DesiredCapabilities();
 desiredCaps.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
 desiredCaps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.android.apis");
 desiredCaps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
 desiredCaps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
 desiredCaps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".view.Controls1");
 desiredCaps.setCapability(MobileCapabilityType.APP, testAppPath);
 driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCaps);
 driver.closeApp();
}
@BeforeMethod
public void testInit() {
 if (driver != null) {
 driver.launchApp();
 driver.startActivity(new Activity("com.example.android.apis", ".view.Controls1"));
 }
}
@AfterMethod
public void testCleanup() {
 if (driver != null) {
 driver.closeApp();
 }
}