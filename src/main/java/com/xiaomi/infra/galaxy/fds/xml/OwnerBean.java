package com.xiaomi.infra.galaxy.fds.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Owner")
@XmlAccessorType(XmlAccessType.NONE)
public class OwnerBean {
  @XmlElement(name = "ID")
  private String id;
  @XmlElement(name = "DisplayName")
  private String displayName;

  public OwnerBean() {}

  public OwnerBean(String id) {
    this.id = id;
  }

  public OwnerBean(String id, String displayName) {
    this.id = id;
    this.setDisplayName(displayName);
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
}
