package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Created by zhangjunbin on 4/29/15.
 */
public class ChecksumDoesNotMatchException extends GalaxyFDSException {

  private static final long serialVersionUID = -5962238757745947478L;

  public ChecksumDoesNotMatchException() {
  }

  public ChecksumDoesNotMatchException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public FDSError getError() {
    return FDSError.ChecksumDoesNotMatch;
  }
}
