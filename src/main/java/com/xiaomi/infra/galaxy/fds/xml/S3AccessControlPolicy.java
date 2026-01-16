package com.xiaomi.infra.galaxy.fds.xml;

import com.xiaomi.infra.galaxy.fds.model.AccessControlList;
import com.xiaomi.infra.galaxy.fds.result.AccessControlPolicy;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AccessControlPolicy",
    namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
@XmlAccessorType(XmlAccessType.NONE)
public class S3AccessControlPolicy {
  @XmlElement(name = "Owner")
  private OwnerBean owner;
  @XmlElementWrapper(name = "AccessControlList")
  @XmlElement(name = "Grant")
  private List<GrantBean> accessControlList;

  public S3AccessControlPolicy() {}

  public S3AccessControlPolicy(OwnerBean owner, List<GrantBean> acl) {
    this.owner = owner;
    this.accessControlList = acl;
  }

  public static S3AccessControlPolicy from(String content) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance(S3AccessControlPolicy.class);
    Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
    S3AccessControlPolicy s3AccessControlPolicy =
        (S3AccessControlPolicy) unMarshaller.unmarshal(
            new StringReader(content));
    return s3AccessControlPolicy;
  }


  public S3AccessControlPolicy(AccessControlPolicy accessControlPolicy) {
    this.owner = new OwnerBean();
    this.owner.setId(accessControlPolicy.getOwner().getId());
    this.owner.setDisplayName(accessControlPolicy.getOwner().getDisplayName());

    this.accessControlList = new ArrayList<GrantBean>();
    for (com.xiaomi.infra.galaxy.fds.bean.GrantBean grantBean
        : accessControlPolicy.getAccessControlList()) {
      if (grantBean.getType() == AccessControlList.GrantType.GROUP) {
        continue;
      }

      GrantBean s3GrantBean = new GrantBean();

      GranteeBean s3GranteeBean = new GranteeBean();
      if (grantBean.getGrantee() != null) {
        s3GranteeBean.setId(grantBean.getGrantee().getId());
        s3GranteeBean.setDisplayName(grantBean.getGrantee().getDisplayName());
      }

      s3GrantBean.setGrantee(s3GranteeBean);
      s3GrantBean.setPermission(grantBean.getPermission());

      this.accessControlList.add(s3GrantBean);
    }
  }

  public AccessControlPolicy generateAccessControlPolicy() {
    AccessControlPolicy accessControlPolicy = new AccessControlPolicy();
    com.xiaomi.infra.galaxy.fds.bean.OwnerBean ownerBean
        = new com.xiaomi.infra.galaxy.fds.bean.OwnerBean();
    if (this.owner != null) {
      ownerBean.setId(this.owner.getId());
      ownerBean.setDisplayName(this.owner.getDisplayName());
      accessControlPolicy.setOwner(ownerBean);
    }

    if (this.accessControlList != null) {
      List<com.xiaomi.infra.galaxy.fds.bean.GrantBean> accessControlList
          = new ArrayList<com.xiaomi.infra.galaxy.fds.bean.GrantBean>();
      for (GrantBean s3GrantBean : this.accessControlList) {
        com.xiaomi.infra.galaxy.fds.bean.GrantBean grantBean =
            new com.xiaomi.infra.galaxy.fds.bean.GrantBean();

        com.xiaomi.infra.galaxy.fds.bean.GranteeBean granteeBean
            = new com.xiaomi.infra.galaxy.fds.bean.GranteeBean();
        if (s3GrantBean.getGrantee() != null) {
          granteeBean.setId(s3GrantBean.getGrantee().getId());
          granteeBean.setDisplayName(s3GrantBean.getGrantee().getDisplayName());
          grantBean.setType(AccessControlList.GrantType.USER);
        }

        grantBean.setGrantee(granteeBean);
        grantBean.setPermission(s3GrantBean.getPermission());

        accessControlList.add(grantBean);

      }
      accessControlPolicy.setAccessControlList(accessControlList);
    }
    return accessControlPolicy;
  }

  public OwnerBean getOwner() {
    return owner;
  }

  public void setOwner(OwnerBean owner) {
    this.owner = owner;
  }

  public List<GrantBean> getAccessControlList() {
    return accessControlList;
  }

  public void setAccessControlList(List<GrantBean> accessControlList) {
    this.accessControlList = accessControlList;
  }
}
