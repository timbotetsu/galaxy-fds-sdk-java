package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Created by zhangjunbin on 1/21/15.
 */
public class InvalidBucketNameException extends GalaxyFDSException {

  private static final long serialVersionUID = 436771419220254068L;

  private String message;

  public InvalidBucketNameException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public FDSError getError() {
    return FDSError.InvalidBucketName;
  }
}
