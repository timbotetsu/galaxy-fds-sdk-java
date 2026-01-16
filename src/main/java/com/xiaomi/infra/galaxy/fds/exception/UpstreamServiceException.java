package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

public class UpstreamServiceException extends GalaxyFDSException {

  public UpstreamServiceException() {
    super("An exception occurred in the upstream service");
  }

  public UpstreamServiceException(String message) {
    super(message);
  }

  public UpstreamServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public FDSError getError() {
    return FDSError.UpstreamServiceError;
  }
}
