/**Creates new database and database entries.
 *
 * @author Stephen Moore
 * @version 1.0
 */

import java.sql.*;
import java.util.ArrayList;

  public class DB {
  private Connection connect = null;
  private String dbURL = "jdbc:mysql://localhost/mooresm16";
  private String username = "mooresm16";
  private String password = "mooresm16";

  public DB() {
    getConnection();
  }

  private void getConnection()
  {
    try
    {
      connect = DriverManager.getConnection(dbURL, username, password);
    }
    catch (SQLException e)
    {
      System.out.println("Exception thrown calling getConnection.\n" + e.getMessage());
    }
  }

  public String addCustomer(Customer c)
  {
    String result = "";
    PreparedStatement ps = null;
    try
    {
      String q = "insert into customer (custId, firstName, lastName, phone) "
        + "values (null, ?, ?, ?)";
      ps = connect.prepareStatement(q);
      ps.setString(1, c.getFirstName());
      ps.setString(2, c.getLastName());
      ps.setString(3, c.getPhone());
      ps.executeUpdate();
      result = c.getFirstName() + " " + c.getLastName() + " has been added.";
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }
    return result;
  }

  public String addItem(Item d)
  {
    String result = "";
    PreparedStatement ps = null;
    try
    {
      String q = "insert into item (itemId, itemName, qtyInStock, retailPrice, wholesalePrice) "
        + "values (null, ?, ?, ?, ?)";
      ps = connect.prepareStatement(q);
      ps.setString(1, d.getItemName());
      ps.setInt   (2, d.getQtyInStock());
      ps.setDouble(3, d.getRetailPrice());
      ps.setDouble(4, d.getWholesalePrice());
      ps.executeUpdate();
      result = d.getItemName() + " " + d.getRetailPrice() + " has been added.";
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }
    return result;
  }


  public ArrayList<Customer> listCustomers(int orderBy,
      int startRecord, int numberToDisplay)
  {
    ArrayList<Customer> cList = new ArrayList<Customer>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    try
    {
      String q = "";
      if (1==orderBy)
      {
        q = "select custId, firstName, lastName, phone from customer "
          + "order by custId limit ?, ?";
      } 
      else
      {
        q = "select custId, firstName, lastName, phone from customer "
          + "order by lastName, firstName limit ?, ?";
      }
      ps = connect.prepareStatement(q);
      ps.setInt(1, startRecord-1);
      ps.setInt(2, numberToDisplay);
      rs = ps.executeQuery();

      while (rs.next())
      {
        cList.add( new Customer(
              rs.getInt("custId"), rs.getString("firstName"),
              rs.getString("lastName"), rs.getString("phone") ) );
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }

    return cList;
  }


  public ArrayList<Item> listItems(int orderBy,
      int startRecord, int numberToDisplay)
  {
    ArrayList<Item> dList = new ArrayList<Item>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    try
    {
      String q = "";
      if (1==orderBy)
      {
        q = "select itemId, itemName, qtyInStock, retailPrice, wholesalePrice from item "
          + "order by itemId limit ?, ?";
      } 
      else
      {
        q = "select itemId, itemName, qtyInStock, retailPrice, wholesalePrice from item "
          + "order by itemName limit ?, ?";
      }
      ps = connect.prepareStatement(q);
      ps.setInt(1, startRecord-1);
      ps.setInt(2, numberToDisplay);
      rs = ps.executeQuery();

      while (rs.next())
      {
        dList.add( new Item(
              rs.getInt("itemId"), rs.getString("itemName"),
              rs.getInt("qtyInStock"), rs.getDouble("retailPrice"),
              rs.getDouble("wholesalePrice") ) );
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }

    return dList;
  }

  public ArrayList<Customer> findCustomerByName(String first, String last)
  {
    ArrayList<Customer> cList = new ArrayList<Customer>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    try
    {
      String q = "select custId, firstName, lastName, phone from customer "
          + "where firstName like ? and lastName like ? order by custId";
      ps = connect.prepareStatement(q);
      ps.setString(1, first + "%");
      ps.setString(2, last + "%");
      rs = ps.executeQuery();

      while (rs.next())
      {
        cList.add( new Customer(
              rs.getInt("custId"), rs.getString("firstName"),
              rs.getString("lastName"), rs.getString("phone") ) );
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }

    return cList;
  }


  public ArrayList<Item> findItemByName(String name)
  {
    ArrayList<Item> dList = new ArrayList<Item>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    try
    {
      String q = "select itemId, itemName, qtyInStock, retailPrice, wholesalePrice from item "
          + "where itemName like ? order by itemId";
      ps = connect.prepareStatement(q);
      ps.setString(1, name + "%");
      rs = ps.executeQuery();

      while (rs.next())
      {
        dList.add( new Item(
              rs.getInt("itemId"), rs.getString("itemName"),
              rs.getInt("qtyInStock"), rs.getDouble("retailPrice"),
              rs.getDouble("wholesalePrice") ) );
      }
      rs.close();
      ps.close();
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }

    return dList;
  }

  public void addSale(Sale sale)
  {
    String result = "", q = "";
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      q = "insert into sale (saleId, date, custId) "
        + "values (null, ?, ?)";
      ps = connect.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, sale.getDateTime());
      ps.setInt(2, sale.getCustId());
      ps.executeUpdate();

      rs = ps.getGeneratedKeys();
      int saleId = -1;
      if (rs.next())
      {
        saleId = rs.getInt(1);

        q = "insert into transact (saleId, itemId, qty, price) "
          + "values (?, ?, ?, ?)";
        ps = connect.prepareStatement(q);

        for (LineItem line : sale.getLines())
        {
          ps.setInt(1, saleId);
          ps.setInt(2, line.getItemId());
          ps.setInt(3, line.getQty());
          ps.setDouble(4, line.getPrice());
          ps.executeUpdate();
        }
      }
      else System.out.println("Error getting saleId");
    }
    catch (SQLException e)
    {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("\nQUERY: " + ps.toString());
    }
  }

  public static void main(String[] args) throws Exception
  {
    DB mydb = new DB();

    ArrayList<Customer> cList = mydb.listCustomers(2, 1, 20);

    for (Customer c : cList)
    {
      System.out.printf("%10s, %20s, %20s, %15s\n",
          c.getCustId(), c.getFirstName(), c.getLastName(), c.getPhone());
    }
  }
}


