package com.xiaomi.infra.galaxy.fds.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

public class BucketNotificationConfiguration {
  private Map<String, NotificationConfiguration> configurations = null;

  public BucketNotificationConfiguration() {
    this.configurations = new HashMap<String, NotificationConfiguration>();
  }

  public BucketNotificationConfiguration(String name,
      NotificationConfiguration notificationConfiguration) {
    this.configurations = new HashMap<String, NotificationConfiguration>();
    addConfiguration(name, notificationConfiguration);
  }

  public BucketNotificationConfiguration addConfiguration(String name,
      NotificationConfiguration notificationConfiguration) {
    configurations.put(name, notificationConfiguration);
    return this;
  }

  public Map<String, NotificationConfiguration> getConfigurations() {
    return configurations;
  }

  public void setConfigurations(
      Map<String, NotificationConfiguration> configurations) {
    this.configurations = configurations;
  }

  public String toJson() {
    Gson gson = getGson();
    return gson.toJson(this);
  }

  public static BucketNotificationConfiguration fromJson(String json) {
    Gson gson = getGson();
    return gson.fromJson(json, BucketNotificationConfiguration.class);
  }

  static Gson getGson() {
    GsonBuilder gsonBilder = new GsonBuilder();
    gsonBilder.registerTypeAdapter(NotificationConfiguration.class,
        new NotificationConfigurationAdapter());
    return gsonBilder.create();
  }
}
