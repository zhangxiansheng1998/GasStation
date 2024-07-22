package tools;
import java.sql.*;

public class DbConnection {

    public static void main(String[] args) {
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        } catch (ClassNotFoundException e1) {
            System.out.println("找不到MySQL驱动!");
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

            // 查询语句
            String sql = "select shop_id,user_id from shop where shop_name='新城加油站';";    //要执行的SQL
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            while (rs.next()) {
                long shop_id = rs.getLong("shop_id"); // 或者使用列的索引 rs.getLong(1)
                String user_id = rs.getString("user_id");
                System.out.println("select sql successfully");
                System.out.println("shop_id:" + shop_id);
                System.out.println("user_id:" + user_id);
            }

            // 更新语句
            String sql2 = "UPDATE gas_biz_order set invoice_status=0,invoice_name='' where shop_name='新城加油站' order by create_time desc limit 1";    //要执行的SQL
            stmt.executeUpdate(sql2);//创建数据对象
            System.out.println("update sql successfully");

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("数据库连接失败!");
            e.printStackTrace();
        }
    }
}
