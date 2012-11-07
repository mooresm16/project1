/**Executes all methods in order to run program.
 *
 * @author Stephen Moore
 * @version 1.0
 */

import java.sql.*;
import java.util.*;
import java.text.*;

public class StoreApp
{
  DB mydb;
  Scanner sc;

  public StoreApp()
  {
    mydb = new DB();
    sc = new Scanner(System.in);
  }

  public void showMenu()
  {
    System.out.println();
    System.out.println("1 = Add customer");
    System.out.println("2 = List customers");
    System.out.println("3 = Find customer");
    System.out.println("4 = Add item");
    System.out.println("5 = List items");
    System.out.println("6 = Find item");
    System.out.println("7 = Make a sale");
    System.out.println("0 = Quit");
  }

  public void mainLoop() throws Exception
  {
    int choice = 999;
    while (choice != 0) {
      showMenu();
      choice = Validator.getInt(sc, "Enter choice: ", 0, 8);
      if (1 == choice) addCustomer();
      else if (2 == choice) listCustomers();
      else if (3 == choice) findCustomerByName();
      else if (4 == choice) addItem();
      else if (5 == choice) listItems();
      else if (6 == choice) findItemByName();
      else if (7 == choice) makeSale();
      else if (0 == choice) ;
      else System.out.println("\nInvalid Choice. Please try again.\n");
    } 
  }

  public void addCustomer()
  {
    int custId = 0;
    String first = Validator.getLine(sc, "Enter customer first name: ");
    String last  = Validator.getLine(sc, "Enter last name: ");
    String phone = Validator.getLine(sc, "Enter phone number (111-222-3333): ");
    Customer c = new Customer(custId, first, last, phone);
    String result = mydb.addCustomer(c);
    System.out.println(result);
  }

  public void addItem()
  {
    int itemId = 0;
    String name = Validator.getLine(sc, "Enter item name: ");
    int stock = Validator.getInt(sc, "How many are being added: ");
    double rPrice = Validator.getDouble(sc, "What is the retail price: ");
    double wPrice = Validator.getDouble(sc, "What is the wholesale price: ");
    Item d = new Item(itemId, name, stock, rPrice, wPrice );
    String result = mydb.addItem(d);
    System.out.println(result);
  }

  public void listCustomers()
  {
    int orderBy =
      Validator.getInt(sc, "1 = sort by custId, 2 = sort by name: ", 1, 2);
    int startRecord =
      Validator.getInt(sc, "Index of starting record: ", 1, 999999999);
    int numberToDisplay =
      Validator.getInt(sc, "How many records to display: ", 1, 999999999);

    ArrayList<Customer> cList =
      mydb.listCustomers(orderBy, startRecord, numberToDisplay);

    for (Customer c : cList)
    {
      System.out.printf("%-10s %-20s %-20s %-15s\n",
          c.getCustId(), c.getFirstName(), c.getLastName(), c.getPhone());
    }
  }


  public void listItems()
  {
    int orderBy =
      Validator.getInt(sc, "1 = sort by itemId, 2 = sort by name: ", 1, 2);
    int startRecord =
      Validator.getInt(sc, "Index of starting record: ", 1, 999999999);
    int numberToDisplay =
      Validator.getInt(sc, "How many records to display: ", 1, 999999999);

    ArrayList<Item> dList =
      mydb.listItems(orderBy, startRecord, numberToDisplay);

    for (Item d : dList)
    {
      System.out.printf("%-10s %-20s %-20s %-15s\n",
          d.getItemId(), d.getItemName(), d.getQtyInStock(), d.getRetailPrice(), d.getWholesalePrice());
    }
  }

  public void findCustomerByName()
  {
    String first = Validator.getLine(sc, "Enter customer first name: ");
    String last  = Validator.getLine(sc, "Enter last name: ");

    ArrayList<Customer> cList = mydb.findCustomerByName(first, last);

    for (Customer c : cList)
    {
      System.out.printf("%-10s %-20s %-20s %-15s\n",
          c.getCustId(), c.getFirstName(), c.getLastName(), c.getPhone());
    }
  }


  public void findItemByName()
  {
    String name = Validator.getLine(sc, "Enter item name: ");

    ArrayList<Item> dList = mydb.findItemByName(name);

    for (Item d : dList)
    {
      System.out.printf("%-10s %-20s %-20s %-15s\n",
          d.getItemId(), d.getItemName(), d.getQtyInStock(), d.getRetailPrice(), d.getWholesalePrice());
    }
  }

  public void makeSale()
  {
    int custId, itemId, qty, count=0;
    double price;
    String choice = "y", customer, date;
    ArrayList<LineItem> lines = new ArrayList<LineItem>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    custId = Validator.getInt(sc, "Enter customer id: ", 1, 999999999);
    date = sdf.format(new java.util.Date());

    while (choice.equalsIgnoreCase("y"))
    {
      count++;
      itemId = Validator.getInt(sc, ("Item id " + count + ": "), 1, 999999999);
      qty = Validator.getInt(sc, "Quantity: ", 0, 999999999);
      price = Validator.getDouble(sc, "Price: ", 0, 999999999);
      lines.add(new LineItem(0, itemId, qty, price));
      
      choice = Validator.getLine(sc, "Another item (y/n)? ");
    }

    Sale sale = new Sale(0, date, custId, lines);
    mydb.addSale(sale);
  }

  public static void main(String[] args) throws Exception
  {
    StoreApp store = new StoreApp();
    store.mainLoop();
  }
}

