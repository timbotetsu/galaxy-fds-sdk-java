package com.xiaomi.infra.galaxy.fds.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Grantee")
@XmlAccessorType(XmlAccessType.NONE)
public class GranteeBean {
  @XmlElement(name = "ID")
  private String id;
  @XmlElement(name = "DisplayName")
  private String displayName;
  @XmlAttribute(name = "type", namespace = "http://www.w3.org/2001/XMLSchema-instance")
  private String type = "CanonicalUser";

  public GranteeBean() {}

  public GranteeBean(String id) {
    this.id = id;
  }

  public GranteeBean(String id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
