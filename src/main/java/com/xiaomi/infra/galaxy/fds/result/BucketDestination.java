package com.xiaomi.infra.galaxy.fds.result;

public class BucketDestination {
  private String bucket;

  private InventoryFormat format;

  private String prefix = "";

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public InventoryFormat getFormat() {
    return format;
  }

  public void setFormat(InventoryFormat format) {
    this.format = format;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  @Override
  public String toString() {
    return "BucketDestination{" + "bucket='" + bucket + '\'' + ", format='" + format + '\''
        + ", prefix='" + prefix + '\'' + '}';
  }

  public enum InventoryFormat {
    CSV,
  }
}
