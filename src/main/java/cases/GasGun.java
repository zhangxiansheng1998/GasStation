package cases;

import elements.GasGunElement;
import elements.LoginElement;
import elements.ShopElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tools.Browser;
import tools.MyScreenshot;

import java.io.IOException;

public class GasGun {

    public static WebDriver driver;
    public static Browser browser;
    private static MyScreenshot screenshotHelper;

    @BeforeClass
    public static void setUp() throws IOException{
        GasGun.browser = new Browser();
        GasGun.browser.InitConfigData();
        driver = GasGun.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        screenshotHelper = new MyScreenshot(driver);  // 实例化截图类,调用screenshot()函数进行截图
        GasGun.browser.implicitlyWait(10);
    }

    @Test(description = "4-1:登录系统")
    public void Login() {
        GasGun.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        GasGun.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        GasGun.browser.explicitlyWait(By.xpath(LoginElement.login_button),20);
        GasGun.browser.click(By.xpath(LoginElement.login_button));
        System.out.println("登录成功!");
    }

    @Test(dependsOnMethods = {"Login"}, description = "4-2:进入油枪配置页面")
    public void GasGunEdit() {
        GasGun.browser.explicitlyWait(By.xpath(ShopElement.my_shop_button), 20);
        GasGun.browser.click(By.xpath(ShopElement.my_shop_button));
        GasGun.browser.click(By.xpath(ShopElement.shop_logo));
        GasGun.browser.click(By.xpath(GasGunElement.gas_gun_edit));
        GasGun.browser.explicitlyWait(By.xpath(GasGunElement.gas_gun_edit_text), 20);
        GasGun.browser.Assert(By.xpath(GasGunElement.gas_gun_edit_text),"油枪配置");
    }

    @Test(dependsOnMethods = {"GasGunEdit"}, description = "4-3:新增98#汽油油枪")
    public void AddGasGun() {
        GasGun.browser.explicitlyWait(By.xpath(GasGunElement.add_gas_gun), 20);
        GasGun.browser.click(By.xpath(GasGunElement.add_gas_gun));
        GasGun.browser.click(By.xpath(GasGunElement.gas_name_input));
        GasGun.browser.ClickDropdown("el-scrollbar__view el-select-dropdown__list","98#");
        GasGun.browser.input(By.xpath(GasGunElement.gas_gun_name_input), GasGun.browser.GenerateGasGunName());
        GasGun.browser.click(By.xpath(GasGunElement.submit_button));
        System.out.println("油枪新增成功!");
    }

    @Test(dependsOnMethods = {"AddGasGun"}, description = "4-4:禁用98#汽油油枪")
    public void ForbidGasGun() throws InterruptedException {
        Thread.sleep(2000);
        GasGun.browser.click(By.xpath(GasGunElement.ninety_eight_gasoline));
        Thread.sleep(2000);
        GasGun.browser.click(By.xpath(GasGunElement.forbid_button));
        GasGun.browser.click(By.xpath(GasGunElement.submit_button));
        GasGun.browser.explicitlyWait(By.xpath(GasGunElement.forbid_text), 20);
        GasGun.browser.Assert(By.xpath(GasGunElement.forbid_text),"禁用");
        System.out.println("98#汽油油枪禁用成功!");
    }

    @Test(dependsOnMethods = {"ForbidGasGun"}, description = "4-5:删除98#汽油油枪")
    public void DeleteGasGun() {
        GasGun.browser.explicitlyWait(By.xpath(GasGunElement.ninety_eight_gasoline), 20);
        GasGun.browser.click(By.xpath(GasGunElement.ninety_eight_gasoline));
        GasGun.browser.click(By.xpath(GasGunElement.delete_button));
        GasGun.browser.click(By.xpath(GasGunElement.confirm_button));
        System.out.println("98#汽油油枪删除成功!");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}

