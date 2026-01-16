package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class MirrorFailedException extends GalaxyFDSException {
  public MirrorFailedException() {
    super("Get mirror object failed");
  }

  public MirrorFailedException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override public FDSError getError() {
    return FDSError.MirrorFailed;
  }
}
