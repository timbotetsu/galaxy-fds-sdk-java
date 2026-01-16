package com.xiaomi.infra.galaxy.fds;

public class Common {

  public static final String XIAOMI_HEADER_PREFIX = "x-xiaomi-";
  public static final String XIAOMI_META_HEADER_PREFIX =
      XIAOMI_HEADER_PREFIX + "meta-";
  public static final String MULITPART_UPLOAD_MODE = XIAOMI_HEADER_PREFIX
      + "multipart-upload-mode";
  public static final String COPY_SOURCE = XIAOMI_HEADER_PREFIX + "copy-source";
  public static final String COPY_SOURCE_RANGE = XIAOMI_HEADER_PREFIX + "copy-source-range";
  public static final String KS3_HEADER_PREFIX = "x-kss-";
  public static final String KS3_META_HEADER_PREFIX = KS3_HEADER_PREFIX + "meta-";

  public static final String AMZ_HEADER_PREFIX = "x-amz-";
  public static final String AMZ_META_HEADER_PREFIX = AMZ_HEADER_PREFIX + "meta-";

  // Required query parameters for pre-signed uri
  public static final String GALAXY_ACCESS_KEY_ID = "GalaxyAccessKeyId";
  public static final String AWS_ACCESS_KEY_ID = "AWSAccessKeyId";
  public static final String SIGNATURE = "Signature";
  public static final String EXPIRES = "Expires";
  public static final String AUTHENTICATION = "Authentication";

  // Http headers used for authentication
  public static final String AUTHORIZATION = "authorization";
  public static final String CONTENT_MD5 = "content-md5";
  public static final String CONTENT_TYPE = "content-type";
  public static final String DATE = "date";
  public static final String TRASH_BUCKET_NAME = "trash";

  public static final int REQUEST_TIME_LIMIT = 15 * 60 * 1000;

  // Pre-defined object metadata headers
  public static final String CACHE_CONTROL = "cache-control";
  public static final String CONTENT_ENCODING = "content-encoding";
  public static final String CONTENT_LENGTH = "content-length";
  public static final String LAST_MODIFIED = "last-modified";
  public static final String LAST_CHECKED = "last-checked";
  public static final String UPLOAD_TIME = "upload-time";
  public static final String DELETED_TIME = "deleted-time";
  public static final String RANGE = "range";
  public static final String CONTENT_RANGE = "content-range";
  public static final String EXPIRATION_TIME = "expiration-time";
  public static final String OBJECT_OWNER_ID = "object-owner-id";
  public static final String FILE_MODE = "file-mode";
  public static final String OBJECT_VERSION_ID = "object-version-id";
  public static final String PREVIOUS_VERSION_ID = "pre-version-id";
  public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
  public static final String IF_NONE_MATCH = "If-None-Match";
  public static final String ORIGIN = "Origin";
  public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
  public static final String USER_AGENT= "user-agent";
  public static final String ETAG = "ETag";
  public static final String Content_Disposition = "content-disposition";

  // kerberos const
  public static final String NEGOTIATE = "Negotiate";

  // kerberos header
  public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
  public static final String PROXY_USER = "proxy-user";
  public static final String KERBEROS_TOKEN = "kerberos-token";


  // Header for optional server-side encryption algorithm
  public static final String SERVER_SIDE_ENCRYPTION = XIAOMI_HEADER_PREFIX + "server-side-encryption";

  // Request properties used for metrics collection
  public static final String ACTION = "action";
  public static final String REQUEST_METRICS = "request-metrics";
  public static final String METRICS_COLLECTOR = "metrics-collector";

  public static final String STORAGE_CLASS=XIAOMI_HEADER_PREFIX + "storage-class";

  public static final String ONGOING_RESTORE = XIAOMI_HEADER_PREFIX + "ongoing-restore";
  public static final String RESTORE_EXPIRY_DATE = XIAOMI_HEADER_PREFIX + "restore-expiry";

  public static final String RESTORE_RESULT=XIAOMI_HEADER_PREFIX + "storage-class";

  public static final String CRC64_ECMA = XIAOMI_HEADER_PREFIX + "hash-crc64ecma";

  /**
   * The default uri for fds service base uri
   */
  public static final String DEFAULT_FDS_SERVICE_BASE_URI = "http://files.fds.api.xiaomi.com/";

  /**
   * The default uri for cdn service uri
   */
  public static final String DEFAULT_CDN_SERVICE_URI = "http://cdn.fds.api.xiaomi.com/";

  /**
   * Http Status Code
   */
  public static final int HTTP_STATUS_PROCESSING = 102;
  public static final int HTTP_STATUS_OK = 200;
  public static final int HTTP_STATUS_BAD_REQUEST = 400;
  public static final int HTTP_STATUS_AUTHENTICATION_FAIL_REQUEST = 401;
  public static final int HTTP_STATUS_FORBIDDEN = 403;
  public static final int HTTP_STATUS_CONFLICT = 409;
  public static final int HTTP_STATUS_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
  public static final int HTTP_STATUS_NOT_FOUND = 404;
  public static final int HTTP_STATUS_TOO_MANY_REQUESTS = 429;
  public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;
  public static final int HTTP_STATUS_NOT_IMPLEMENTED = 501;
  public static final int HTTP_STATUS_BAD_GATEWAY = 502;
  public static final int HTTP_STATUS_MIRROR_FAILED = 424;
  public static final int HTTP_STATUS_METHOD_NOT_ALLOWED = 405;

  /**
   * galaxy-hadoop
   */
  public static final String GALAXY_HADOOP="galaxy-hadoop";
}
