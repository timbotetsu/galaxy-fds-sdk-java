package com.xiaomi.infra.galaxy.fds.exception;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class CacheFullException extends Exception {

  private int requestedSize, bucketIndex;

  public CacheFullException(int requestedSize, int bucketIndex) {
    super();
    this.requestedSize = requestedSize;
    this.bucketIndex = bucketIndex;
  }

  public int bucketIndex() {
    return bucketIndex;
  }

  public int requestedSize() {
    return requestedSize;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(1024);
    sb.append("Allocator requested size ").append(requestedSize);
    sb.append(" for bucket ").append(bucketIndex);
    return sb.toString();
  }
}