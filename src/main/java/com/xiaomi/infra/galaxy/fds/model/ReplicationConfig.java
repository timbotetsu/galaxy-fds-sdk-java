package com.xiaomi.infra.galaxy.fds.model;

import java.util.ArrayList;
import java.util.List;

public class ReplicationConfig {
  private String targetBucket;
  private String targetEndpiont;
  private List<String> resources = new ArrayList<String>();
  private int priority;

  public void setTargetEndpiont(String targetEndpiont) {
    this.targetEndpiont = targetEndpiont;
  }

  public void setResources(List<String> resources) {
    this.resources = resources;
  }

  public void setTargetBucket(String targetBucket) {
    this.targetBucket = targetBucket;
  }

  public List<String> getResources() {
    return resources;
  }

  public void addResource(String prefix) {
    resources.add(prefix);
  }

  public String getTargetEndpiont() {
    return targetEndpiont;
  }

  public String getTargetBucket() {
    return targetBucket;
  }

  public int getPriority() {
    return priority;
  }

  public boolean isValid(String objectName) {
    if (targetBucket == null || targetEndpiont == null) {
      return false;
    }
    if (!resources.isEmpty()) {
      for (String s : resources) {
        if (objectName.startsWith(s)) {
          return true;
        }
      }
      return false;
    }
    return true;
  }
}
