package com.xiaomi.infra.galaxy.fds.model;

public enum FDSEvent {
  ObjectCreated("ObjectCreated:*"),

  ObjectCreatedByPut("ObjectCreated:Put"),

  ObjectRemoved("ObjectRemoved:*"),

  ObjectRemovedDelete("ObjectRemoved:Delete");

  private final String event;

  private FDSEvent(String event) {
    this.event = event;
  }

  @Override
  public String toString() {
    return this.event;
  }
}
