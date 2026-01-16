package com.xiaomi.infra.galaxy.fds.xml;

import com.xiaomi.infra.galaxy.fds.model.AccessControlList.Permission;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Grant")
@XmlAccessorType(XmlAccessType.NONE)
public class GrantBean {
  @XmlElement(name = "Grantee")
  private GranteeBean grantee;
  @XmlElement(name = "Permission")
  private Permission permission;

  public GrantBean() {}

  public GrantBean(GranteeBean grantee, Permission permission) {
    this.grantee = grantee;
    this.permission = permission;
  }

  public GranteeBean getGrantee() {
    return grantee;
  }

  public void setGrantee(GranteeBean grantee) {
    this.grantee = grantee;
  }

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }

}
