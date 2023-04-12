package J2EETest12th_april;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main_app
{
    public static void main(String[] args)
    {

        selectOption();

    }
    public static void selectOption()
    {
        Scanner sc=new Scanner(System.in);
        int choice=0;
        Bussiness_logic b1 = new Bussiness_logic();
        boolean status=true;
        while (status)
        {

            try {

                System.out.println("Enter you mode of selection:\n1.Display Product\n2.Add Product to the cart\n3.Display card item\n4.Display Bill\n5.Exit");
                choice = sc.nextInt();


                if (choice == 1) {
                    //CALL THE DISPLAYPRODUCT METHOD OF BUSSINESS_LOGIC CLASS
                    b1.display_product();

                } else if (choice == 2) {
                    System.out.println("Enter the pname: ");
                    String pname = sc.next();
                    System.out.println("Enter the quantity");
                    int qty = sc.nextInt();
                    //CALL THE ADD_PRODUCT METHOD
                    b1.add_cart(pname, qty);

                } else if (choice == 3) {
                    //CALL THE DISPLAY_CART METHOD
                    b1.display_cart_details();

                } else if (choice == 4) {
                    //CALL THE DISPLAY BILL METHOD
                    double finalAmount = b1.getBill();

                    System.out.println("=============================");
                    System.out.println("FINAL BILL IS :" + finalAmount);
                    System.out.println("=============================");


                } else if (choice == 5)

                    status = false;
                else
                    System.out.println("Invalid Choice!!!");
            }
            catch (InputMismatchException e)
            {

                System.out.println("Invalid Choice Entered");
                selectOption();



            }

        }

    }
}
