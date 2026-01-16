package com.xiaomi.infra.galaxy.fds.model;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * An abstract class for all the notification configurations associated with an FDS bucket.
 */
public abstract class NotificationConfiguration {
  /**
   * Set of events for a notification configuration.
   */
  private Set<String> events = new HashSet<String>();

  /**
   * Creates a NotificationConfiguration with empty events.
   */
  protected NotificationConfiguration() {
  }

  /**
   * Creates a notification configuration with the given set of events.
   *
   * @param events
   *            the list of events for the notification configuration.
   */
  protected NotificationConfiguration(EnumSet<FDSEvent> events) {
    if (events != null) {
      for (FDSEvent fdsEvent : events) {
        this.events.add(fdsEvent.toString());
      }
    }
  }

  /**
   * Creates a notification configuration with the given set of events.
   *
   * @param events
   *            the list of events for the notification configuration.
   */
  protected NotificationConfiguration(String... events) {
    if (events != null) {
      for (String event : events) {
        this.events.add(event);
      }
    }
  }

  public Set<String> getEvents() {
    return events;
  }

  public void setEvents(Set<String> events) {
    this.events = events;
  }
}
