package com.xiaomi.infra.galaxy.fds.bean;

import java.util.Date;

public class ObjectBean {

  private String name;
  private String etag;
  private OwnerBean owner;
  private Date lastModified;
  private long uploadTime;
  private long size;
  private String versionId;
  private Boolean allowOutsideAccess;
  private MetadataBean metadataBean;

  public ObjectBean() {}

  public ObjectBean(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEtag() {
    return etag;
  }

  public void setEtag(String etag) {
    this.etag = etag;
  }

  public OwnerBean getOwner() {
    return owner;
  }

  public void setOwner(OwnerBean owner) {
    this.owner = owner;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public long getUploadTime() {
    return uploadTime;
  }

  public void setUploadTime(long uploadTime) {
    this.uploadTime = uploadTime;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getVersionId() {
    return versionId;
  }

  public Boolean getAllowOutsideAccess() {
    return allowOutsideAccess;
  }

  public void setAllowOutsideAccess(Boolean allowOutsideAccess) {
    this.allowOutsideAccess = allowOutsideAccess;
  }

  public MetadataBean getMetadataBean() {
    return metadataBean;
  }

  public void setMetadataBean(MetadataBean metadataBean) {
    this.metadataBean = metadataBean;
  }
}
