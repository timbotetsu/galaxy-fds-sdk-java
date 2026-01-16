package com.xiaomi.infra.galaxy.fds.model;

/**
 * Created by yepeng on 18-6-21.
 */
public enum StorageClass {

  Standard("STANDARD"),

  StandardInfrequentAccess("STANDARD_IA"),

  Archive("ARCHIVE");

  public static StorageClass fromValue(String storageClassString) throws IllegalArgumentException {
    for (StorageClass storageClass : StorageClass.values()) {
      if (storageClass.toString().equalsIgnoreCase(storageClassString)) return storageClass;
    }

    throw new IllegalArgumentException("Cannot create enum from " + storageClassString + " value!");
  }

  private final String storageClassId;

  private StorageClass(String id) {
    this.storageClassId = id;
  }

  @Override
  public String toString() {
    return storageClassId;
  }
}
