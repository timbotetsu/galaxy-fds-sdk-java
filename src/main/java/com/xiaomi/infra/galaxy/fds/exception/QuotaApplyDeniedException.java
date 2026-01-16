package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/**
 * Created by yepeng on 18-7-31.
 */
public class QuotaApplyDeniedException extends GalaxyFDSException{
  private static final long serialVersionUID = -974711420428687663L;

  public QuotaApplyDeniedException() {
    super("Only admin is allowed to access Quota Apply");
  }

  public QuotaApplyDeniedException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public FDSError getError() {
    return FDSError.QuotaApplyDenied;
  }
}
