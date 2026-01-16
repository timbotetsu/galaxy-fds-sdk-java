package com.xiaomi.infra.galaxy.fds;

public enum FDSError {

  QuotaApplyDenied("Quota Apply Denied", Common.HTTP_STATUS_FORBIDDEN),
  BucketAccessDenied("Bucket Access Denied", Common.HTTP_STATUS_FORBIDDEN),
  BucketAlreadyExists("Bucket Already Exists", Common.HTTP_STATUS_CONFLICT),
  BucketNotFound("Bucket Not Found", Common.HTTP_STATUS_NOT_FOUND),
  ObjectAccessDenied("Object Access Denied", Common.HTTP_STATUS_FORBIDDEN),
  ObjectAlreadyExists("Object Already Exists", Common.HTTP_STATUS_CONFLICT),
  ObjectNotFound("Object Not Found", Common.HTTP_STATUS_NOT_FOUND),
  BrokenObject("Object Data Broken", Common.HTTP_STATUS_INTERNAL_SERVER_ERROR),
  InternalServerError("Internal Server Error", Common.HTTP_STATUS_INTERNAL_SERVER_ERROR),
  RequestTimeout("Request Timeout", Common.HTTP_STATUS_BAD_REQUEST),
  InvalidRequest("Invalid Request", Common.HTTP_STATUS_BAD_REQUEST),
  SignatureDoesNotMatch("Signature Does Not Match", Common.HTTP_STATUS_FORBIDDEN),
  RequestTimeTooSkewed("Request Time Too Skewed", Common.HTTP_STATUS_FORBIDDEN),
  RequestExpired("Request Expired", Common.HTTP_STATUS_FORBIDDEN),
  InvalidOAuthParameters("Invalid OAuth Parameters", Common.HTTP_STATUS_BAD_REQUEST),
  VerifyOAuthAccessTokenError("Verify OAuth Access Token Error", Common.HTTP_STATUS_BAD_REQUEST),
  QuotaExceeded("Quota Exceeded", Common.HTTP_STATUS_BAD_REQUEST),
  ChecksumDoesNotMatch("Checksum Does Not Match", Common.HTTP_STATUS_BAD_REQUEST),
  RequestNotSupported("Request Not Supported", Common.HTTP_STATUS_METHOD_NOT_ALLOWED),
  InvalidRequestRange("Request Out of Range", Common.HTTP_STATUS_REQUESTED_RANGE_NOT_SATISFIABLE),
  AuthenticationFailed("Authentication Failed", Common.HTTP_STATUS_BAD_REQUEST),
  ServerTooBusy("Server Too Busy", Common.HTTP_STATUS_INTERNAL_SERVER_ERROR),
  TooManyRequests("Too Many Requests, Try Later", Common.HTTP_STATUS_TOO_MANY_REQUESTS),
  InvalidBucketName("Invalid Bucket Name", Common.HTTP_STATUS_BAD_REQUEST),
  InvalidTrashObjectName("Invalid Trash Object Name", Common.HTTP_STATUS_BAD_REQUEST),
  InvalidObjectName("Invalid Object Name", Common.HTTP_STATUS_BAD_REQUEST),
  KerberosAuthenticationFail("kerberos authentication failed", Common.HTTP_STATUS_AUTHENTICATION_FAIL_REQUEST),
  DomainMappingAccessDenied("Domain Mapping Access Denied", Common.HTTP_STATUS_FORBIDDEN),
  InvalidPartNumberOrSize("Invalid Part Number or Size", Common.HTTP_STATUS_BAD_REQUEST),
  DDLDisabled("DDL is Disabled", Common.HTTP_STATUS_FORBIDDEN),
  ConvertingMultimedia("Converting Multimedia Now", Common.HTTP_STATUS_PROCESSING),
  MirrorFailed("Mirror Source Unavailable", Common.HTTP_STATUS_MIRROR_FAILED),
  MultipartUploadExpired("Multipart Upload Expired", Common.HTTP_STATUS_BAD_REQUEST),
  OperationDenied("Operation Denied", Common.HTTP_STATUS_FORBIDDEN),
  CannotSetWriteRelatedPermission("Cannot Set Write Related Permission", Common.HTTP_STATUS_FORBIDDEN),
  UpstreamServiceError("Upstream Service Error", Common.HTTP_STATUS_BAD_GATEWAY),
  Unknown("Unknown", Common.HTTP_STATUS_BAD_REQUEST),
  Success("Success", Common.HTTP_STATUS_OK);

  private final String description;
  private final int status;

  FDSError(String description, int statusCode) {
    this.description = description;
    this.status = statusCode;
  }

  public String description() {
    return this.description;
  }

  public int status() {
    return this.status;
  }
}
