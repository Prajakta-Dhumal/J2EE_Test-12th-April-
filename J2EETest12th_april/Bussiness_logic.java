package J2EETest12th_april;

import javax.jws.soap.SOAPBinding;
import java.sql.*;

public class Bussiness_logic
{
    static Connection con;
    static {
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/j2eetest","root","sql@123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void display_product()
    {
        //DECLARE THE RESOURCES
        PreparedStatement pstmt;
        ResultSet rs;

        //WRITE THE QUERY
        String query="select * from product_data";
        //CREATE THE PLATFORM
        try {
            pstmt=con.prepareStatement(query);
            //FETCH THE VALUE FROM RESULTSET
            rs=pstmt.executeQuery();
            while (rs.next())
            {
                System.out.println("Product Id: "+rs.getInt(1));
                System.out.println("Product Name: "+rs.getString(2));
                System.out.println("Product quantity: "+rs.getInt(3));
                System.out.println("Product price: "+rs.getDouble(4));
                System.out.println("=================================================");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int add_cart(String pname,int qty)
    {
        //DECLARE THE RESOURCES
        PreparedStatement pstmt;
        ResultSet rs=null;
        int count=0;
        int db_qty=0;
        double price=0;

        //WRITE THE QUERY TO CHECK WHETHER THE PRODUCT IS PRESENT OR NOT IN PRODUCT_DATA TABLE
        String query1="select qty, price_per_product from product_data where pname=?";

        //WRITE THE ANOTHER QUERY TO ADD THE DATA INTO THE CART
        String query2="insert into cart(pname,qty,price_per_product) values(?,?,?)";

        //WRITE THE ANOTHER QUERY TO UPDATE THE QTY OF product_data
        String query3="update product_data set qty=? where pname=?";

        //CREATE THE PLATFORM FOR 1st QUERY
        try {
            pstmt=con.prepareStatement(query1);
            //SET THE VALUE AT PLACEHOLDER
            pstmt.setString(1,pname);
            //EXECUTE THE QUERY
            rs=pstmt.executeQuery();
            if (rs.next())
            {
               db_qty=rs.getInt(1);
               price=rs.getDouble(2);
               if (db_qty>=qty)
               {
                   //CREATE THE PLATFORM FOR 2nd QUERY
                   pstmt=con.prepareStatement(query2);
                   //SET THE VALUE AT PLACEHOLDER
                   pstmt.setString(1,pname);
                   pstmt.setInt(2,qty);
                   pstmt.setDouble(3,price);
                   //EXECUTE THE QUERY
                  count= pstmt.executeUpdate();

                  //CREATE THE PLAFORM FOR TO UPADTE THE QUERY
                   pstmt=con.prepareStatement(query3);
                   pstmt.setDouble(1,db_qty-qty);
                   pstmt.setString(2,pname);
                   //EXECUTE THE QUERY
                   count= pstmt.executeUpdate();
                   System.out.println("==============================");
                   System.out.println(count + " PRODUCT ADD SUCESSFULLY!!!");
                   System.out.println("================================");




               }
               else
               {
                   System.out.println("==========================");
                   System.out.println("PRODUCT OUT OF STOCK!!!");
                   System.out.println("==========================");
               }
            }
            else
            {
                System.out.println("==========================");
                System.out.println("PRODUCT NOT AVAILABLE!!!!");
                System.out.println("==========================");
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return count;
    }

    public void display_cart_details()
    {
        //DECLARE THE RESOURCES
        PreparedStatement pstmt;
        ResultSet rs=null;

        //WRITE THE QUERY
        String query="select * from cart";
        //CREATE THE PLATFORM
        try {
            pstmt= con.prepareStatement(query);
            rs=pstmt.executeQuery();
            while (rs.next())
            {
                System.out.println("Product name: "+rs.getString(1));
                System.out.println("Product qty: "+rs.getInt(2));
                System.out.println("Product price: "+rs.getDouble(3));
                System.out.println("=========================================");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getBill()
    {
        //DECLARE THE RESOURCES
        PreparedStatement pstmt;
        ResultSet rs;
        int qty=0;
        double amount=0;
        double totalAmount=0;
        double finalAmount=0;

        //write the query
        String query="select qty,price_per_product from cart";
        //CREATE THE PLATFORM
        try {
            pstmt=con.prepareStatement(query);
            //EXECUTE THE QUERY
           rs= pstmt.executeQuery();
           while (rs.next())
           {
              qty=qty+ rs.getInt(1);
              amount=amount+rs.getDouble(2);
              totalAmount=totalAmount+amount*qty;


           }

           finalAmount=totalAmount+totalAmount*0.18;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return finalAmount;
    }
}
