package com.xiaomi.infra.galaxy.fds.model;

import java.util.EnumSet;

/**
 * Represents the Talos configuration for an FDS bucket.
 */
public class TalosConfiguration extends NotificationConfiguration {
  private String topic;

  public TalosConfiguration() {
    super();
  }

  /**
   * Creates a new topic configuration with the given topic and set of events.
   *
   * @param topic
   *            the Talos Topic to which the notifications are to be sent.
   * @param events
   *            the events for which the notifications are to be sent
   */
  public TalosConfiguration(String topic, EnumSet<FDSEvent> events) {
    super(events);
    this.topic = topic;
  }

  /**
   * Creates a new topic configuration with the given topic and set of events.
   *
   * @param topic
   *            the Talos Topic to which the notifications are to be sent.
   * @param events
   *            the events for which the notifications are to be sent
   */
  public TalosConfiguration(String topic, String... events) {
    super(events);
    this.topic = topic;
  }

  /**
   * Returns the topic for this notification configuration.
   */
  public String getTopic() {
    return topic;
  }

  /**
   * Sets the topic for this configuration
   *
   * @param topic
   *            topic For Talos
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * Fluent method to set the topic for this configuration
   *
   * @param topic
   *            topic For Talos
   * @return This object for method chaining
   */
  public TalosConfiguration withTopic(String topic) {
    setTopic(topic);
    return this;
  }
}