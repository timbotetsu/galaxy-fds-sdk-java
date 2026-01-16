package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

public class GalaxyFDSException extends Exception {

  private static final long serialVersionUID = -7688381775178948719L;
  private static String locationInfo = null;

  public GalaxyFDSException() { }

  public static void setLocationInfo(String locationInfo) {
    GalaxyFDSException.locationInfo = locationInfo;
  }

  public GalaxyFDSException(String message, Throwable cause) {
    super(message, cause);
  }

  public GalaxyFDSException(String message) {
    super(message, null);
  }

  public GalaxyFDSException(Throwable cause) {
      super(cause == null ? null : cause.getMessage(), cause);
  }

  public FDSError getError() {
    return FDSError.InternalServerError;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Galaxy FDS Error: ");
    sb.append(this.getError().description());
    String message = this.getMessage();
    if (message != null) {
      sb.append(" [").append(message).append("]");
    }
    if (locationInfo != null) {
      sb.append(" ").append(locationInfo);
    }
    return sb.toString();
  }
}
