package com.xiaomi.infra.galaxy.fds.exception;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class BucketAllocatorException extends RuntimeException {
  public BucketAllocatorException(String message) {
    super(message);
  }

  public BucketAllocatorException() {
  }
}

