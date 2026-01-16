package com.xiaomi.infra.galaxy.fds.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * Directory name ("key prefix") in case of listing.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CommonPrefix {

  @XmlElement(name = "Prefix")
  private String prefix;

  public CommonPrefix(String prefix) {
    this.prefix = prefix;
  }

  public CommonPrefix() {
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
}
