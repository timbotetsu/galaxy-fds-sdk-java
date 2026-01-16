package com.xiaomi.infra.galaxy.fds.model;

import com.google.gson.Gson;
import com.xiaomi.infra.galaxy.fds.Action;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class NotificationEvent {
  private String bucketName;
  private String objectName;
  private Action action;
  private String uuid;
  private String versionId;
  private Long timestamp;
  private Long spaceIncrement;
  private Integer objectIncrement;
  private List<AccessControlList.Grant> grants;
  private Map<String, String> metadata;

  public NotificationEvent(String bucketName, String objectName, Action action) {
    this.uuid = UUID.randomUUID().toString();
    this.bucketName = bucketName;
    this.objectName = objectName;
    this.action = action;
  }

  public NotificationEvent(String bucketName, String objectName, Action action, Long timestamp, Long spaceIncrement,
      Integer objectIncrement) {
    this(bucketName, objectName, action);
    this.timestamp = timestamp;
    this.spaceIncrement = spaceIncrement;
    this.objectIncrement = objectIncrement;
  }

  public NotificationEvent(String bucketName, String objectName, Action action, Long timestamp, Long spaceIncrement,
      Integer objectIncrement, List<AccessControlList.Grant> grants, Map<String, String> metadata) {
    this(bucketName, objectName, action, timestamp, spaceIncrement, objectIncrement);
    this.grants = grants;
    this.metadata = metadata;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public List<AccessControlList.Grant> getGrants() {
    return grants;
  }

  public void setGrants(List<AccessControlList.Grant> grants) {
    this.grants = grants;
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

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getVersionId() {
    return versionId;
  }

  public void setVersionId(String versionId) {
    this.versionId = versionId;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Long getSpaceIncrement() {
    return spaceIncrement;
  }

  public void setSpaceIncrement(Long spaceIncrement) {
    this.spaceIncrement = spaceIncrement;
  }

  public Integer getObjectIncrement() {
    return objectIncrement;
  }

  public void setObjectIncrement(Integer objectIncrement) {
    this.objectIncrement = objectIncrement;
  }

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public static NotificationEvent fromJson(String jsonStr) {
    Gson gson = new Gson();
    return gson.fromJson(jsonStr, NotificationEvent.class);
  }
}
