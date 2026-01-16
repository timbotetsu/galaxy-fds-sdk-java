package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Created by mi on 21-5-11.
 */
public class InvalidObjectNameException extends GalaxyFDSException {

  private static final long serialVersionUID = -6007094704747754588L;
  private String message;

  public InvalidObjectNameException(String message) {
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
