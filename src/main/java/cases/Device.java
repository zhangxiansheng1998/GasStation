package cases;

import elements.*;
import elements.DeviceElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tools.Browser;
import tools.MyScreenshot;
import java.io.IOException;
import java.util.logging.Logger;

public class Device {
    public static WebDriver driver;
    public static Browser browser;
    private static MyScreenshot screenshotHelper;
    public static final Logger logger = Logger.getLogger(Browser.class.getName());

    @BeforeClass
    public static void setUp() throws IOException {
        Device.browser = new Browser();
        Device.browser.InitConfigData();
        driver = Device.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        screenshotHelper = new MyScreenshot(driver);  // 实例化截图类,调用screenshot()函数进行截图
        Device.browser.implicitlyWait(10);
    }

    @Test(description = "9-1:登录系统")
    public void Login() {
        Device.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        Device.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        Device.browser.explicitlyWait(By.xpath(LoginElement.login_button),10);
        Device.browser.click(By.xpath(LoginElement.login_button));
        logger.info("登录成功!");
    }

    @Test(dependsOnMethods = {"Login"}, description = "9-2:进入设备管理页面")
    public void DeviceManagement() {
        Device.browser.explicitlyWait(By.xpath(ShopElement.my_shop_button), 10);
        Device.browser.click(By.xpath(ShopElement.my_shop_button));
        Device.browser.click(By.xpath(ShopElement.shop_logo));
        Device.browser.click(By.xpath(DeviceElement.device_management));
        Device.browser.explicitlyWait(By.xpath(DeviceElement.device_management_text), 10);
        Device.browser.Assert(By.xpath(DeviceElement.device_management_text),"设备管理");
    }

    @Test(dependsOnMethods = {"DeviceManagement"}, description = "9-3:新增设备")
    public void AddDevice() throws InterruptedException {
        Device.browser.explicitlyWait(By.xpath(DeviceElement.add_device_button), 10);
        Device.browser.click(By.xpath(DeviceElement.add_device_button));
        Device.browser.input(By.xpath(DeviceElement.device_name), Device.browser.GenerateGasGunName());
        Device.browser.input(By.xpath(DeviceElement.device_number), Device.browser.GenerateGasGunName());
        Device.browser.click(By.xpath(DeviceElement.submit_button));
        logger.info("设备新增成功!");
    }

    @Test(dependsOnMethods = {"AddDevice"}, description = "9-4:修改设备")
    public void ModifyDevice() throws InterruptedException {
        Device.browser.explicitlyWait(By.xpath(DeviceElement.edit_button), 10);
        Device.browser.click(By.xpath(DeviceElement.edit_button));
        Device.browser.click(By.xpath(DeviceElement.printer_print_button));
        Device.browser.click(By.xpath(DeviceElement.submit_button));
        Device.browser.Assert(By.xpath(DeviceElement.modify_device_text),"操作成功！");
        logger.info("设备修改成功!");
    }

    @Test(dependsOnMethods = {"ModifyDevice"}, description = "9-5:删除设备")
    public void DeleteDevice() throws InterruptedException {
        Device.browser.explicitlyWait(By.xpath(DeviceElement.delete_button), 10);
        Device.browser.click(By.xpath(DeviceElement.delete_button));
        Device.browser.click(By.xpath(DeviceElement.delete_confirm_button));
        Device.browser.Assert(By.xpath(DeviceElement.modify_device_text),"删除成功");
        logger.info("设备删除成功!");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
