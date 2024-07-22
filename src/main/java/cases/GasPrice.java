package cases;

import elements.GasPriceElement;
import elements.LoginElement;
import elements.ShopElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tools.Browser;
import java.io.IOException;
import java.util.Objects;

import static elements.GasPriceElement.tell_zero_diesel;

public class GasPrice {

    public static WebDriver driver;
    public static Browser browser;
    public static GasPrice myObject;

    @BeforeClass
    public static void setUp() throws IOException{
        GasPrice.browser = new Browser();
        GasPrice.browser.InitConfigData();
        driver = GasPrice.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        GasPrice.browser.implicitlyWait(10);
        myObject = new GasPrice();
    }

    @Test(description = "3-1:登录系统")
    public void Login() {
        GasPrice.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        GasPrice.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        GasPrice.browser.explicitlyWait(By.xpath(LoginElement.login_button),20);
        GasPrice.browser.click(By.xpath(LoginElement.login_button));
        System.out.println("登录成功!");
    }

    @Test(dependsOnMethods = {"Login"}, description = "3-2:进入油价配置页面")
    public void GasPriceEdit() {
        GasPrice.browser.explicitlyWait(By.xpath(ShopElement.my_shop_button), 20);
        GasPrice.browser.click(By.xpath(ShopElement.my_shop_button));
        GasPrice.browser.click(By.xpath(ShopElement.shop_logo));
        GasPrice.browser.click(By.xpath(GasPriceElement.gas_price_edit));
        GasPrice.browser.explicitlyWait(By.xpath(GasPriceElement.gas_price_edit_text), 20);
        GasPrice.browser.Assert(By.xpath(GasPriceElement.gas_price_edit_text),"油价配置");
    }

    @Test(dependsOnMethods = {"GasPriceEdit"}, description = "3-3:新增0#柴油")
    public void AddZeroDieselPrice() throws InterruptedException {
        String tell_zero_diesel_text = GasPrice.browser.tell_elemnt(By.xpath(tell_zero_diesel));

        if (Objects.equals(tell_zero_diesel_text, "元素存在")) {
            GasPrice.browser.explicitlyWait(By.xpath(GasPriceElement.zero_diesel), 20);
            GasPrice.browser.click(By.xpath(GasPriceElement.zero_diesel));
            GasPrice.browser.click(By.xpath(GasPriceElement.edit_button));
            GasPrice.browser.click(By.xpath(GasPriceElement.delete_weekly_activity_vip));
            GasPrice.browser.click(By.xpath(GasPriceElement.confirm_button));
            System.out.println("0#柴油已存在，正在删除");
        } else {
            System.out.println("0#柴油不存在");
        }

        GasPrice.browser.click(By.xpath(GasPriceElement.add_disesl_price));
        GasPrice.browser.click(By.xpath(GasPriceElement.zero_disesl_button));
        GasPrice.browser.input(By.xpath(GasPriceElement.country_price), "7.5");
        GasPrice.browser.input(By.xpath(GasPriceElement.normal_price), "7.4");
        GasPrice.browser.input(By.xpath(GasPriceElement.vip_price), "7.3");
        GasPrice.browser.click(By.xpath(GasPriceElement.submit_button));
        System.out.println("0#柴油新增成功!");
        GasPrice.browser.refresh();
    }

    @Test(dependsOnMethods = {"AddZeroDieselPrice"}, description = "3-4:修改0#柴油国标价格")
    public void ModifyGasPrice() {
        GasPrice.browser.explicitlyWait(By.xpath(GasPriceElement.zero_diesel), 20);
        GasPrice.browser.click(By.xpath(GasPriceElement.zero_diesel));
        GasPrice.browser.click(By.xpath(GasPriceElement.edit_button));
        GasPrice.browser.select_and_delete(By.xpath(GasPriceElement.country_price));
        GasPrice.browser.input(By.xpath(GasPriceElement.country_price), String.valueOf(GasPrice.browser.GenerateGasPrice()));
        GasPrice.browser.click(By.xpath(GasPriceElement.submit_button));
        System.out.println("0#柴油国标价格修改成功!");
    }

    @Test(dependsOnMethods = {"ModifyGasPrice"}, description = "3-5:新增0#柴油周活动")
    public void AddWeeklyActivity() {
        GasPrice.browser.explicitlyWait(By.xpath(GasPriceElement.edit_button), 20);
        GasPrice.browser.click(By.xpath(GasPriceElement.edit_button));
        GasPrice.browser.click(By.xpath(GasPriceElement.add_weekly_activity_vip));
        GasPrice.browser.input(By.xpath(GasPriceElement.discount_price), "0.3");
        GasPrice.browser.click(By.xpath(GasPriceElement.weekly_activity_day));
        GasPrice.browser.click(By.xpath(GasPriceElement.submit_button));
        System.out.println("0#柴油周活动新增成功!");
    }

    @Test(dependsOnMethods = {"AddWeeklyActivity"}, description = "3-6:删除0#柴油周活动")
    public void DeleteWeeklyActivity() {
        GasPrice.browser.explicitlyWait(By.xpath(GasPriceElement.edit_button), 20);
        GasPrice.browser.click(By.xpath(GasPriceElement.edit_button));
        GasPrice.browser.click(By.xpath(GasPriceElement.delete_weekly_activity_vip));
        GasPrice.browser.click(By.xpath(GasPriceElement.confirm_button));
        System.out.println("0#柴油周活动删除成功!");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}

