package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: zhangjunbin@xiaomi.com
 */
public class DDLDDisabledException extends GalaxyFDSException {

  private static final long serialVersionUID = -3118772366981446834L;

  public DDLDDisabledException() {
    super("DDL is disabled at the moment");
  }

  public DDLDDisabledException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public FDSError getError() {
    return FDSError.DDLDisabled;
  }
}
