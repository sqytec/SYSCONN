package com.wlb.T24.in.sys.conn.util;

public abstract interface Constants
{
  public static final String CHAR_ENCODING_GBK = "GBK";
  public static final String CHAR_ENCODING_BIG5 = "BIG5_HKSCS";
  public static final String CHAR_ENCODING_UTF8 = "UTF-8";
  public static final String CHAR_ENCODING_CP285 = "CP285";
  public static final byte GBK_BEGIN = 29;
  public static final byte BIG5_BEGIN = 30;
  public static final byte UTF8_BEGIN = 28;
  public static final byte DBCS_END = 31;
  public static final String DEFAULT_ENCODING = "CP285";
  public static final String DEFAULT_DBCS_ENCODING = "GBK";
  public static final String DATA_TYPE_NUMERIC = "numeric";
  public static final String CONFIG_FILE = "config.properties";
}