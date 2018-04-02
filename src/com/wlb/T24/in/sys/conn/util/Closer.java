package com.wlb.T24.in.sys.conn.util;

import java.io.Closeable;
import java.io.IOException;

public class Closer
{
  public static void close(Closeable[] closes)
  {
    try
    {
      Closeable[] arrayOfCloseable;
      if ((closes == null) || (closes.length <= 0)) return;
      int j = (arrayOfCloseable = closes).length; for (int i = 0; i < j; ++i) { Closeable close = arrayOfCloseable[i];
        if (close != null)
          close.close();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}