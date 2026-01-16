package com.xiaomi.infra.galaxy.fds.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class MetadataBean {
  Map<String, String> rawMeta;

  public Map<String, String> getRawMeta() {
    return rawMeta;
  }

  public void setRawMeta(Map<String, String> rawMeta) {
    this.rawMeta = rawMeta;
  }

  public MetadataBean() {
    rawMeta = new HashMap<String, String>();
  }

  public MetadataBean(Map<String, String> rawMeta) {
    this.rawMeta = rawMeta;
  }

  public void addMeta(String key, String value) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);
    if (rawMeta == null) {
      rawMeta = new HashMap<>();
    }
    rawMeta.put(key, value);
  }
}
