package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Created by maxiaoxin on 18-1-29.
 */
public class ConvertingMultimediaException extends GalaxyFDSException{
  private static final long serialVersionUID = 6867994043799397945L;

  public ConvertingMultimediaException() { }

  public ConvertingMultimediaException(String message) {
    super(message, null);
  }

  public ConvertingMultimediaException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public FDSError getError() {
    return FDSError.ConvertingMultimedia;
  }
}
