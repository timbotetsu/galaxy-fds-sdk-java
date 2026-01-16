package com.xiaomi.infra.galaxy.fds.model;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: zhangjunbin@xiaomi.com
 */
public class AccessLogConfig {

  private String bucketName;
  private boolean enabled;
  private String logBucketName;
  private String logPrefix;
  private boolean cdnEnabled;
  private String cdnLogPrefix;

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getLogBucketName() {
    return logBucketName;
  }

  public void setLogBucketName(String logBucketName) {
    this.logBucketName = logBucketName;
  }

  public String getLogPrefix() {
    return logPrefix;
  }

  public void setLogPrefix(String logPrefix) {
    this.logPrefix = logPrefix;
  }

  public void setCdnEnabled(boolean cdnEnabled) {
    this.cdnEnabled = cdnEnabled;
  }

  public boolean isCdnEnabled() {
    return this.cdnEnabled;
  }

  public String getCdnLogPrefix() {
    return cdnLogPrefix;
  }

  public void setCdnLogPrefix(String cdnLogPrefix) {
    this.cdnLogPrefix = cdnLogPrefix;
  }
}
