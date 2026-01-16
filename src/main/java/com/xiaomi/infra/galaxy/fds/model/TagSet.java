package com.xiaomi.infra.galaxy.fds.model;

import java.util.HashMap;
import java.util.Map;

public class TagSet {
  Map<String, String> tags;

  public TagSet() {
    this.tags = new HashMap<String, String>();
  }

  public TagSet(Map<String, String> tags) {
    this.tags = new HashMap<String, String>();
    this.tags.putAll(tags);
  }

  public String getTag(String key) {
    return tags.get(key);
  }

  public void setTag(String key, String value) {
    tags.put(key, value);
  }

  public void setTags(Map<String, String> tags) {
    this.tags.putAll(tags);
  }

  public Map<String, String> getAllTags() {
    return tags;
  }

  public void clear() {
    tags.clear();
  }
}
