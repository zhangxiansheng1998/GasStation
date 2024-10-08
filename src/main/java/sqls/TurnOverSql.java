package sqls;

import tools.Browser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class TurnOverSql {
    public static final Logger logger = Logger.getLogger(Browser.class.getName());
    public static void main(String[] args) {
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            logger.info("成功加载MySQL驱动！");
        } catch (ClassNotFoundException e1) {
            logger.info("找不到MySQL驱动!");
            e1.printStackTrace();
        }

        String url = "jdbc:mysql://rm-uf61hf197kf017dtsco.mysql.rds.aliyuncs.com:3306/hjw_prod";    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, "huijinwei_prod", "Thinkerx@123");//url 账号 密码
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！" + "\n");

            String sql = "UPDATE gas_biz_order set invoice_status=0,invoice_name='' where shop_name='新城加油站' order by create_time desc limit 1";    //要执行的SQL
            stmt.executeUpdate(sql);//创建数据对象
            logger.info("update sql successfully");

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            logger.info("数据库连接失败!");
            e.printStackTrace();
        }
    }
}
