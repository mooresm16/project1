/**Retrieves item information.
 *
 * @author Stephen Moore
 * @version 1.0
 */

public class Item
{
  int    itemId;
  String itemName;
  int    qtyInStock;
  double retailPrice;
  double wholesalePrice;

  public Item(int iid, String in, int qis, double rp, double wsp)
  {
    itemId = iid;
    itemName = in;
    qtyInStock = qis;
    retailPrice = rp;
    wholesalePrice = wsp;
  }

  public int getItemId()
  {
    return itemId;
  }

  public String getItemName()
  {
    return itemName;
  }

  public int getQtyInStock()
  {
    return qtyInStock;
  }

  public double getRetailPrice()
  {
    return retailPrice;
  }

  public double getWholesalePrice()
  {
    return wholesalePrice;
  }

  public String toString()
  {
    return itemId + ", " + itemName + ", " + qtyInStock + ", " + retailPrice + ", " + wholesalePrice;
  }
}
