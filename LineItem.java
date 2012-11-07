/**Retrieves inventory data.
 *
 * @author Stephen Moore
 * @version 1.0
 */

public class LineItem
{
  int saleId;
  int itemId;
  int qty;
  double price;

  public LineItem (int sid, int i, int q, double p)
  {
    saleId = sid;
    itemId = i;
    qty = q;
    price = p;
  }

  public int getSaleId()
  {
    return saleId;
  }

  public int getItemId()
  {
    return itemId;
  }

  public int getQty()
  {
    return qty;
  }

  public double getPrice()
  {
    return price;
  }

  public String toString()
  {
    return saleId + ", " + itemId + ", " + qty + ", " + price;
  }
}

