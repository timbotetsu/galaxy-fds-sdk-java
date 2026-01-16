package com.xiaomi.infra.galaxy.fds.bean;

import com.xiaomi.infra.galaxy.fds.model.StorageClass;

public class BucketBean {

  private String name;

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  private String orgId;
  private long creationTime;
  private long usedSpace;
  private long numObjects;
  private Boolean allowOutsideAccess;
  private Boolean enableSSE;
  private StorageClass storageClass;

  public BucketBean() {}

  public BucketBean(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(long creationTime) {
    this.creationTime = creationTime;
  }

  public long getUsedSpace() {
    return usedSpace;
  }

  public void setUsedSpace(long usedSpace) {
    this.usedSpace = usedSpace;
  }

  public long getNumObjects() {
    return numObjects;
  }

  public void setNumObjects(long numObjects) {
    this.numObjects = numObjects;
  }

  public Boolean getAllowOutsideAccess() {
    return allowOutsideAccess;
  }

  public void setAllowOutsideAccess(Boolean allowOutsideAccess) {
    this.allowOutsideAccess = allowOutsideAccess;
  }

  public Boolean getEnableSSE() {
    return enableSSE;
  }

  public void setEnableSSE(Boolean enableSSE) {
    this.enableSSE = enableSSE;
  }

  public StorageClass getStorageClass() {
    return storageClass;
  }

  public void setStorageClass(StorageClass storageClass) {
    this.storageClass = storageClass;
  }
}
