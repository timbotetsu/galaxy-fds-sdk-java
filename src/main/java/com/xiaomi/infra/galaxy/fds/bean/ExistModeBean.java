package com.xiaomi.infra.galaxy.fds.bean;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class ExistModeBean {
  String bucketName;
  String objectName;
  int mode;
  MetadataBean metadataBean;

  public ExistModeBean() {
  }

  public ExistModeBean(String bucketName, String objectName, int mode, MetadataBean metadataBean) {
    this.bucketName = bucketName;
    this.objectName = objectName;
    this.mode = mode;
    this.metadataBean = metadataBean;
  }

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public int getMode() {
    return mode;
  }

  public void setMode(int mode) {
    this.mode = mode;
  }

  public MetadataBean getMetadataBean() {
    return metadataBean;
  }

  public void setMetadataBean(MetadataBean metadataBean) {
    this.metadataBean = metadataBean;
  }
}
