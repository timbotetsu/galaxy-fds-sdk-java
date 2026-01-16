package com.xiaomi.infra.galaxy.fds.model;

import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.infra.galaxy.fds.auth.signature.Utils;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * Represents the object metadata that is stored with Galaxy FDS. This includes
 * custom user-supplied metadata, as well as the standard HTTP headers that
 * Galaxy FDS sends and receives (Content-Length, ETag, Content-MD5, etc.).
 */
public class FDSObjectMetadata {

  private final Map<String, String> metadata = new HashMap<String, String>();

  public static final String USER_DEFINED_META_PREFIX = Common.XIAOMI_META_HEADER_PREFIX;

  public static final String AMZ_USER_DEFINED_META_PREFIX = Common.AMZ_META_HEADER_PREFIX;

  public enum PredefinedMetadata {

    CacheControl(Common.CACHE_CONTROL), ContentEncoding(Common.CONTENT_ENCODING), ContentLength(
      Common.CONTENT_LENGTH), ContentRange(Common.CONTENT_RANGE), LastModified(
      Common.LAST_MODIFIED), ContentMD5(Common.CONTENT_MD5), ContentType(
      Common.CONTENT_TYPE), LastChecked(Common.LAST_CHECKED), UploadTime(
      Common.UPLOAD_TIME), ExpirationTime(Common.EXPIRATION_TIME), ObjectOwnerId(
      Common.OBJECT_OWNER_ID), FileMode(Common.FILE_MODE), MultipartUploadMode(
      Common.MULITPART_UPLOAD_MODE), PreviousVersionId(
      Common.PREVIOUS_VERSION_ID), ServerSideEncryption(
      Common.SERVER_SIDE_ENCRYPTION), StorageClass(Common.STORAGE_CLASS), OngoingRestore(
      Common.ONGOING_RESTORE), RestoreExpiryDate(Common.RESTORE_EXPIRY_DATE), ETag(
      Common.ETAG), ContentDisposition(Common.Content_Disposition), Crc64Ecma(
      Common.CRC64_ECMA), CopySource(Common.COPY_SOURCE), CopySourceRange(Common.COPY_SOURCE_RANGE);

    private final String header;

    private PredefinedMetadata(String header) {
      this.header = header;
    }

    public String getHeader() {
      return header;
    }
  }

  public void addHeader(String key, String value) {
    checkMetadata(key);
    metadata.put(key, value);
  }

  public void addUserMetadata(String key, String value) {
    checkMetadata(key);
    metadata.put(key, value);
  }

  public void setUserMetadata(Map<String, String> userMetadata) {
    for (Map.Entry<String, String> entry : userMetadata.entrySet()) {
      checkMetadata(entry.getKey());
      metadata.put(entry.getKey(), entry.getValue());
    }
  }

  public String getCacheControl() {
    return metadata.get(Common.CACHE_CONTROL);
  }

  public void setCacheControl(String cacheControl) {
    metadata.put(Common.CACHE_CONTROL, cacheControl);
  }

  public String getContentEncoding() {
    return metadata.get(Common.CONTENT_ENCODING);
  }

  public void setContentEncoding(String contentEncoding) {
    metadata.put(Common.CONTENT_ENCODING, contentEncoding);
  }

  public long getContentLength() {
    String contentLength = metadata.get(Common.CONTENT_LENGTH);
    if (contentLength != null) {
      return Long.parseLong(contentLength);
    }
    return -1;
  }

  public void setContentLength(long contentLength) {
    metadata.put(Common.CONTENT_LENGTH, Long.toString(contentLength));
  }

  public String getContentRange() {
    return metadata.get(Common.CONTENT_RANGE);
  }

  public void setContentRange(long begin, long end, long len) {
    metadata.put(Common.CONTENT_RANGE, "bytes " + begin + "-" + end + "/" + len);
  }

  public Date getLastModified() {
    String lastModified = metadata.get(Common.LAST_MODIFIED);
    if (lastModified != null) {
      return Utils.parseDateTimeFromString(lastModified);
    }
    return null;
  }

  public void setLastModified(Date lastModified) {
    metadata.put(Common.LAST_MODIFIED, Utils.getGMTDatetime(lastModified));
  }

  public String getLastChecked() {
    return metadata.get(Common.LAST_CHECKED);
  }

  public void setLastChecked(String timeString) {
    metadata.put(Common.LAST_CHECKED, timeString);
  }

  public String getCRC64() {
    return metadata.get(Common.CRC64_ECMA);
  }

  public String getETag() {
    return metadata.get(Common.ETAG);
  }

  public String getContentMD5() {
    return metadata.get(Common.CONTENT_MD5);
  }

  public void setContentMD5(String contentMD5) {
    metadata.put(Common.CONTENT_MD5, contentMD5);
  }

  public String getContentType() {
    return metadata.get(Common.CONTENT_TYPE);
  }

  public void setContentType(String contentType) {
    metadata.put(Common.CONTENT_TYPE, contentType);
  }

  public String getObjectOwnerId() {
    return metadata.get(Common.OBJECT_OWNER_ID);
  }

  public void setObjectOwnerId(String objectOwnerId) {
    metadata.put(Common.OBJECT_OWNER_ID, objectOwnerId);
  }

  public String getMultipartUploadMode() {
    return metadata.get(Common.MULITPART_UPLOAD_MODE);
  }

  public void setMultipartUploadMode(String multipartUploadMoid) {
    metadata.put(Common.MULITPART_UPLOAD_MODE, multipartUploadMoid);
  }

  public void setPreviousVersionId(String versionId) {
    metadata.put(Common.PREVIOUS_VERSION_ID, versionId);
  }

  public String getPreviousVersionId() {
    return metadata.get(Common.PREVIOUS_VERSION_ID);
  }

  public void setSSEAlgorithm(String algorithm) {
    metadata.put(Common.SERVER_SIDE_ENCRYPTION, algorithm);
  }

  public String getSSEAlgorithm() {
    return metadata.get(Common.SERVER_SIDE_ENCRYPTION);
  }

  public void setStorageClass(StorageClass storageClass) {
    if (storageClass != null) {
      metadata.put(Common.STORAGE_CLASS, storageClass.toString());
    } else {
      metadata.remove(Common.STORAGE_CLASS);
    }
  }

  public StorageClass getStorageClass() {
    String storageClassStr = metadata.get(Common.STORAGE_CLASS);
    if (storageClassStr != null && !storageClassStr.isEmpty()) {
      return StorageClass.fromValue(storageClassStr);
    } else {
      return null;
    }
  }

  public void setOngoingRestore(Boolean isOngoingRestore) {
    if (isOngoingRestore != null) {
      metadata.put(Common.ONGOING_RESTORE, Boolean.toString(isOngoingRestore).toLowerCase());
    } else {
      metadata.remove(Common.ONGOING_RESTORE);
    }
  }

  public Boolean getRestoreArchiveStatus() {
    String str = metadata.get(Common.ONGOING_RESTORE);
    return str != null ? Boolean.parseBoolean(str) : null;
  }

  public void setRestoreExpiryDate(Date date) {
    if (date != null) {
      metadata.put(Common.RESTORE_EXPIRY_DATE, date2GMTString(date));
    } else {
      metadata.remove(Common.RESTORE_EXPIRY_DATE);
    }
  }

  public Date getRestoreExpiryDate() {
    String str = metadata.get(Common.RESTORE_EXPIRY_DATE);
    return str != null ? string2GMTDate(str) : null;
  }

  public void setCopySource(String copySourceHeader) {
    if (StringUtils.isNotBlank(copySourceHeader)) {
      metadata.put(Common.COPY_SOURCE, copySourceHeader);
    }
  }

  public String getCopySource() {
    return metadata.get(Common.COPY_SOURCE);
  }

  public void setCopySourceRange(String copySourceRangeHeader) {
    if (StringUtils.isNotBlank(copySourceRangeHeader)) {
      metadata.put(Common.COPY_SOURCE_RANGE, copySourceRangeHeader);
    }
  }

  public String getCopySourceRange() {
    return metadata.get(Common.COPY_SOURCE_RANGE);
  }

  public void removeHeader(String header) {
    if (metadata.containsKey(header)) {
      metadata.remove(header);
    }
  }

  String getHeader(String header) {
    return metadata.get(header.toLowerCase());
  }

  public Map<String, String> getRawMetadata() {
    return Collections.unmodifiableMap(metadata);
  }

  public static FDSObjectMetadata parseObjectMetadata(Map<String, List<String>> headers) {
    FDSObjectMetadata metadata = new FDSObjectMetadata();

    Map<String, String> headerMap = new HashMap<>();
    if (headers != null) {
      for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
        String keyLowerCase = entry.getKey().toLowerCase();
        List<String> values = entry.getValue();

        if (values == null || values.isEmpty() || headerMap.containsKey(keyLowerCase)) {
          continue;
        }

        String firstValue = values.getFirst();
        headerMap.put(keyLowerCase, firstValue);
        if (keyLowerCase.startsWith(FDSObjectMetadata.USER_DEFINED_META_PREFIX)) {
          metadata.addHeader(keyLowerCase, firstValue);
        } else if (keyLowerCase.startsWith(FDSObjectMetadata.AMZ_USER_DEFINED_META_PREFIX)) {
          metadata.addHeader(getFDSUserDefinedMetaKey(keyLowerCase), firstValue);
        }
      }
    }

    for (PredefinedMetadata m : PredefinedMetadata.values()) {
      String value = headerMap.get(m.getHeader());
      if (value != null && !value.isEmpty()) {
        metadata.addHeader(m.getHeader(), value);
      }
    }

    return metadata;
  }

  private static String getFDSUserDefinedMetaKey(String amzUserDefinedMetaKey) {
    return FDSObjectMetadata.USER_DEFINED_META_PREFIX + amzUserDefinedMetaKey.substring(
      FDSObjectMetadata.AMZ_USER_DEFINED_META_PREFIX.length());
  }

  public void switchToAMZMetaKey() {
    Set<String> renameKeys = new HashSet<String>();
    for (String metaKey : metadata.keySet()) {
      if (metaKey.startsWith(Common.XIAOMI_HEADER_PREFIX)) {
        renameKeys.add(metaKey);
      }
    }

    for (String metaKey : renameKeys) {
      String amzMetaKey =
        Common.AMZ_HEADER_PREFIX + metaKey.substring(Common.XIAOMI_HEADER_PREFIX.length());
      metadata.put(amzMetaKey, metadata.remove(metaKey));
    }
  }

  public static boolean isPredefinedMetaData(String key) {
    for (PredefinedMetadata m : PredefinedMetadata.values()) {
      if (key.equalsIgnoreCase(m.getHeader())) {
        return true;
      }
    }
    return false;
  }

  private static void checkMetadata(String key) {
    boolean isValid = key.startsWith(FDSObjectMetadata.USER_DEFINED_META_PREFIX);

    if (!isValid) {
      for (PredefinedMetadata m : PredefinedMetadata.values()) {
        if (key.equals(m.getHeader())) {
          isValid = true;
          break;
        }
      }
    }

    if (!isValid) {
      throw new RuntimeException("Invalid metadata: " + key, null);
    }
  }

  private static final SimpleDateFormat gmtSDF =
    new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

  private static String date2GMTString(Date date) {
    synchronized (gmtSDF) {
      return gmtSDF.format(date);
    }
  }

  private static Date string2GMTDate(String str) {
    synchronized (gmtSDF) {
      try {
        return gmtSDF.parse(str);
      } catch (Exception e) {
        return null;
      }
    }
  }
}
