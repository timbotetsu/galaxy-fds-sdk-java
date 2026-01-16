package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class ObjectOutsideAccessDeniedException extends GalaxyFDSException {
  public ObjectOutsideAccessDeniedException() {
    super("Object is not allowed outside access");
  }

  public ObjectOutsideAccessDeniedException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public FDSError getError() {
    return FDSError.ObjectAccessDenied;
  }
}
