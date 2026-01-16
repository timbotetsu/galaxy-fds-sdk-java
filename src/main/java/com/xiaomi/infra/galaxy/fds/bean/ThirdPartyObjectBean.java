package com.xiaomi.infra.galaxy.fds.bean;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class ThirdPartyObjectBean {
  private String url;
  private long startPos;
  private long stopPos;

  public ThirdPartyObjectBean() {
  }

  public ThirdPartyObjectBean(String url, long startPos, long stopPos) {
    this.url = url;
    this.startPos = startPos;
    this.stopPos = stopPos;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public long getStartPos() {
    return startPos;
  }

  public void setStartPos(long startPos) {
    this.startPos = startPos;
  }

  public long getStopPos() {
    return stopPos;
  }

  public void setStopPos(long stopPos) {
    this.stopPos = stopPos;
  }
}
