package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

public class MultipartUploadExpiredException extends GalaxyFDSException {
  public MultipartUploadExpiredException() {
    super("This multipart upload has expired");
  }

  @Override
  public FDSError getError() {
    return FDSError.MultipartUploadExpired;
  }
}
