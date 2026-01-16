package com.xiaomi.infra.galaxy.fds.xml;

public enum S3ErrorCode {
  NoSuchKey("The specified key does not exist."),
  NoSuchBucket("The specified bucket does not exist."),
  AccessDenied("Access Denied"),
  BadDigest("The Content-MD5 you specified did not match what we received."),
  BucketAlreadyExists("The requested bucket name is not available. The bucket namespace is shared by all users of the system. Please select a different name and try again."),

  ;

  private String description;
  S3ErrorCode(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
