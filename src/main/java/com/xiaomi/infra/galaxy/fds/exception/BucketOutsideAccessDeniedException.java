package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class BucketOutsideAccessDeniedException extends GalaxyFDSException {
  public BucketOutsideAccessDeniedException() {
    super("Bucket is not allowed outside access");
  }

  public BucketOutsideAccessDeniedException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public FDSError getError() {
    return FDSError.BucketAccessDenied;
  }
}
