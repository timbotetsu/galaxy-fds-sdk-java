package com.xiaomi.infra.galaxy.fds.auth.signature;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Utils {

  // According to <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html">
  // Protocol Parameters</a>, HTTP applications have historically allowed three
  // different formats for the representation of date/time stamps:
  //  Sun, 06 Nov 1994 08:49:37 GMT  ; RFC 822, updated by RFC 1123
  //  Sunday, 06-Nov-94 08:49:37 GMT ; RFC 850, obsoleted by RFC 1036
  //  Sun Nov  6 08:49:37 1994       ; ANSI C's asctime() format
  private static final ThreadLocal<SimpleDateFormat> RFC_822_DATE_FORMAT =
    new ThreadLocal<SimpleDateFormat>() {
      @Override protected SimpleDateFormat initialValue() {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format;
      }
    };

  private static final ThreadLocal<SimpleDateFormat> RFC_850_DATE_FORMAT =
    ThreadLocal.withInitial(() -> {
      SimpleDateFormat format = new SimpleDateFormat("EEEE, dd-MMM-yy HH:mm:ss z", Locale.US);
      format.setTimeZone(TimeZone.getTimeZone("GMT"));
      return format;
    });

  private static final ThreadLocal<SimpleDateFormat> ANSI_DATE_FORMAT =
    ThreadLocal.withInitial(() -> {
      SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US);
      format.setTimeZone(TimeZone.getTimeZone("GMT"));
      return format;
    });

  public static Map<String, List<String>> parseUriParameters(URI uri) {
    Map<String, List<String>> params = new LinkedHashMap<>();
    String query = uri.getQuery();

    if (query != null && !query.isEmpty()) {
      for (String param : query.split("&")) {
        String[] kv = param.split("=");
        String key = kv[0];
        String value = (kv.length >= 2) ? kv[1] : "";
        params.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
      }
    }
    return params;
  }

  public static Date parseDateTimeFromString(String datetime) {
    Date date = tryToParse(datetime, RFC_822_DATE_FORMAT.get());
    if (date == null) {
      date = tryToParse(datetime, RFC_850_DATE_FORMAT.get());
    }
    if (date == null) {
      date = tryToParse(datetime, ANSI_DATE_FORMAT.get());
    }
    return date;
  }

  public static long parseDateTimeToMilliseconds(String datetime) {
    Date date = parseDateTimeFromString(datetime);
    if (date != null) {
      return date.getTime();
    }
    return 0;
  }

  public static String getGMTDatetime(Date datetime) {
    return RFC_822_DATE_FORMAT.get().format(datetime);
  }

  private static Date tryToParse(String datetime, SimpleDateFormat format) {
    try {
      return format.parse(datetime);
    } catch (ParseException e) {
      return null;
    }
  }

  public static String calcMD5(String s)
    throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest m = MessageDigest.getInstance("MD5");
    m.update(s.getBytes("UTF8"));
    byte md5Bytes[] = m.digest();
    BigInteger bigInt = new BigInteger(1, md5Bytes);
    return bigInt.toString(16);
  }
}
