package com.xiaomi.infra.galaxy.fds.exception;

public class GalaxyFDSClientException extends GalaxyFDSException {

  private static final long serialVersionUID = -1734780212731437463L;

  public GalaxyFDSClientException() {}

  public GalaxyFDSClientException(String message) {
    super(message, null);
  }

  public GalaxyFDSClientException(Throwable cause) {
    super(null, cause);
  }

  public GalaxyFDSClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
