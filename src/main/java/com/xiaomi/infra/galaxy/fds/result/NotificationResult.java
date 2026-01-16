package com.xiaomi.infra.galaxy.fds.result;

public class NotificationResult {
  public enum NotificationMethod {
    PUT,
    POST,
    DELETE
  }
  
  private String bucketName;
  private String objectName;
  private long updateTime;
  private long contentLength;
  private String method;
  
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
  public long getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }
  public long getContentLength() {
    return contentLength;
  }
  public void setContentLength(long contentLength) {
    this.contentLength = contentLength;
  }
  public String getMethod() {
    return method;
  }
  public void setMethod(String method) {
    this.method = method;
  }
}
