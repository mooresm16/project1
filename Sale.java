/**Retrieves sale data.
 *
 * @author Stephen Moore
 * @version 1.0
 */

import java.util.*;

public class Sale
{
  int saleId;
  String datetime;
  int custId;
  ArrayList<LineItem> lines;

  public Sale (int sid, String dt, int cid, ArrayList<LineItem> li)
  {
    saleId = sid;
    datetime = dt;
    custId = cid;
    lines = li;
  }

  public int getSaleId()
  {
    return saleId;
  }

  public String getDateTime()
  {
    return datetime;
  }

  public int getCustId()
  {
    return custId;
  }

  public ArrayList<LineItem> getLines()
  {
    return lines;
  }

  public String toString()
  {
    return saleId + ", " + datetime + ", " + custId;
 }
}

