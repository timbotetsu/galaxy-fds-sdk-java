package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

public class BucketPermissionMoreRestrictionsException extends GalaxyFDSException {
  private static final long serialVersionUID = -5962238754285942345L;

  public BucketPermissionMoreRestrictionsException() {
    this("According to the requirements of the Xiaomi company security department, we take back the user's write " +
      "related permission to the bucket when bucket allows outside accessing");
  }

  public BucketPermissionMoreRestrictionsException(String message) {
    super(message);
  }

  @Override public FDSError getError() {
    return FDSError.CannotSetWriteRelatedPermission;
  }
}
