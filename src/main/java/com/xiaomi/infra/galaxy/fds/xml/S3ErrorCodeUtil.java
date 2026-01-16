package com.xiaomi.infra.galaxy.fds.xml;

import com.xiaomi.infra.galaxy.fds.FDSError;
import java.util.HashMap;
import java.util.Map;

public class S3ErrorCodeUtil {
  private static Map<FDSError, S3ErrorCode> fds2S3ErrorCodeMapping =
      new HashMap<FDSError, S3ErrorCode>();

  public static void registerErrorCodeMapping(FDSError fdsError, S3ErrorCode s3ErrorCode) {
    fds2S3ErrorCodeMapping.put(fdsError, s3ErrorCode);
  }

  public static S3ErrorCode getS3ErrorCode(FDSError fdsError) {
    return fds2S3ErrorCodeMapping.get(fdsError);
  }

  public static S3Error getS3Error(FDSError fdsError, String description, String requestId) {
    S3ErrorCode s3ErrorCode = getS3ErrorCode(fdsError);
    S3Error s3Error;
    if (s3ErrorCode != null) {
      s3Error = new S3Error(s3ErrorCode.name(), s3ErrorCode.getDescription(), requestId);
    } else {
      s3Error = new S3Error(fdsError.description(), description, requestId);
    }
    return s3Error;
  }

  static {
    registerErrorCodeMapping(FDSError.ObjectNotFound, S3ErrorCode.NoSuchKey);
    registerErrorCodeMapping(FDSError.BucketNotFound, S3ErrorCode.NoSuchBucket);
    registerErrorCodeMapping(FDSError.ObjectAccessDenied, S3ErrorCode.AccessDenied);
    registerErrorCodeMapping(FDSError.BucketAccessDenied, S3ErrorCode.AccessDenied);
    registerErrorCodeMapping(FDSError.ChecksumDoesNotMatch, S3ErrorCode.BadDigest);
    registerErrorCodeMapping(FDSError.BucketAlreadyExists, S3ErrorCode.BucketAlreadyExists);
  }
}
