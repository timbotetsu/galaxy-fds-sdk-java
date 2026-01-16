package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

public class QuotaExceededException extends GalaxyFDSException {

  private static final long serialVersionUID = 8289328383984276237L;

  public QuotaExceededException(String s, Throwable t) {
    super(s, t);
  }

  @Override
  public FDSError getError() {
    return FDSError.QuotaExceeded;
  }

}
