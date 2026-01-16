package com.xiaomi.infra.galaxy.fds.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CopyObjectBean {
  private String srcBucketName;
  private String srcObjectName;

  public String getSrcBucketName() {
    return srcBucketName;
  }

  public void setSrcBucketName(String srcBucketName) {
    this.srcBucketName = srcBucketName;
  }

  public String getSrcObjectName() {
    return srcObjectName;
  }

  public void setSrcObjectName(String srcObjectName) {
    this.srcObjectName = srcObjectName;
  }

  public CopyObjectBean() {
  }

  public CopyObjectBean(String srcBucketName, String srcObjectName) {
    this.srcBucketName = srcBucketName;
    this.srcObjectName = srcObjectName;
  }
}
