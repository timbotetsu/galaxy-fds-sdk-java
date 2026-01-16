package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

public class KerberosAuthenticationException extends GalaxyFDSException {
  private static final long serialVersionUID = 7719002330871888192L;

  public KerberosAuthenticationException() {
    super("Authentication failed");
  }

  public KerberosAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

  public KerberosAuthenticationException(String message) {
    super(message);
  }

  @Override
  public FDSError getError() {
    return FDSError.KerberosAuthenticationFail;
  }

}
