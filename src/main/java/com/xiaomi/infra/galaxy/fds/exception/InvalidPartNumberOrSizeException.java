package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: zhangjunbin@xiaomi.com
 */
public class InvalidPartNumberOrSizeException extends GalaxyFDSException {
  private static final long serialVersionUID = -980535900742856349L;

  public InvalidPartNumberOrSizeException() { }

  public InvalidPartNumberOrSizeException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public FDSError getError() {
    return FDSError.InvalidPartNumberOrSize;
  }
}
