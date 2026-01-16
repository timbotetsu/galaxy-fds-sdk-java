package com.xiaomi.infra.galaxy.fds.xml;

import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * A converter to convert Instant to standard date string.
 */
public class IsoDateAdapter extends XmlAdapter<String, Date> {

  protected SimpleDateFormat iso8601DateParser;

  public final String DATA_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public IsoDateAdapter() {
    iso8601DateParser =
        new SimpleDateFormat(DATA_PATTERN);
  }

  @Override
  public Date unmarshal(String v) throws Exception {
    return iso8601DateParser.parse(v);
  }

  @Override
  public String marshal(Date v) throws Exception {
    return iso8601DateParser.format(v);
  }
}
