package com.xiaomi.infra.galaxy.fds.model;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class TimestampAntiStealingLinkConfig {
  private boolean enabled;
  private String primaryKey;
  private String secondaryKey;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getSecondaryKey() {
    return secondaryKey;
  }

  public void setSecondaryKey(String secondaryKey) {
    this.secondaryKey = secondaryKey;
  }
}