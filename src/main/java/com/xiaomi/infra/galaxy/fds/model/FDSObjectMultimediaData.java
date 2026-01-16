package com.xiaomi.infra.galaxy.fds.model;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by maxiaoxin on 18-1-16.
 */
public class FDSObjectMultimediaData {
  public static final MimeType[] SUPPORT_IMAGE_TYPES = { MimeType.IMAGE_PNG, MimeType.IMAGE_BMP, MimeType.IMAGE_JPEG, MimeType.IMAGE_WEBP };
  public static final String DEFAULT_TYPE = "default";
  private static final String DELIMITER = ":";
  private static final String SUB_DELIMITER = "_";

  public static class MediaInfo {
    private String md5;
    private String uri;
    private MimeType mimeType;
    private long size;

    private transient int width = 0, height = 0, quality = 0;
    private transient String processParams = null;
    private transient InputStream content = null;

    public String getProcessParams() {
      return processParams;
    }

    public void setProcessParams(String processParams) {
      this.processParams = processParams;
    }

    public String getUri() {
      return uri;
    }

    public void setUri(String uri) {
      this.uri = uri;
    }

    public long getSize() {
      return size;
    }

    public void setSize(long size) {
      this.size = size;
    }

    public InputStream getContent() {
      return content;
    }

    public void setContent(InputStream content) {
      this.content = content;
    }

    public String getMd5() {
      return md5;
    }

    public void setMd5(String md5) {
      this.md5 = md5;
    }

    public MimeType getMimeType() {
      return mimeType;
    }

    public void setMimeType(String mimeType) {
      this.mimeType = MimeType.fromString(mimeType);
    }

    public void setMimeType(MimeType mimeType) {
      this.mimeType = mimeType;
    }

    public int getQuality() {
      return quality;
    }

    public void setQuality(int quality) {
      this.quality = quality;
    }

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    public String getMediaKey() {
      return FDSObjectMultimediaData.getMediaKey(mimeType, width, height, quality, processParams);
    }
  }

  private Map<String, MediaInfo> mediaInfos = new TreeMap<String, MediaInfo>();

  public Iterable<MediaInfo> getMediaInfos() {
    return mediaInfos.values();
  }

  public Map<String, MediaInfo> getMediaInfoMap() {
    return Collections.unmodifiableMap(mediaInfos);
  }

  public FDSObjectMultimediaData() {
  }

  /**
   * Combine mimetype, width, height, quality as a key, mainly used in hbase table
   *
   * @param mimeType Remove "image/" and convert to upper case; if mimetype is null or empty, use "ORIGIN"
   * @param width    If width <= 0, ignore
   * @param height   If Height <= 0, ignore
   * @param quality  If quality <= 0 or quality > 100, ignore
   * @return image:mimetype:width:height:quality, may ignore some fields
   */
  public static String getMediaKey(String mimeType, int width, int height, int quality, String processParams) {
    return getMediaKey(MimeType.fromString(mimeType), width, height, quality, processParams);
  }

  /**
   * Combine mimetype, width, height, quality as a key, mainly used in hbase table
   *
   * @param mimeType "default" or valid mimetype
   * @param width    If width <= 0, ignore
   * @param height   If Height <= 0, ignore
   * @param quality  If quality <= 0 or quality > 100, ignore
   * @return image:mimetype:width:height:quality, may ignore some fields
   */
  public static String getMediaKey(MimeType mimeType, int width, int height, int quality, String processParams) {
    StringBuilder sb = new StringBuilder();
    if (mimeType == null || mimeType == MimeType.UNKNOWN) {
      sb.append(DEFAULT_TYPE);
    } else {
      sb.append(mimeType.getVal());
    }
    if (width > 0) {
      sb.append(DELIMITER);
      sb.append("w");
      sb.append(SUB_DELIMITER);
      sb.append(width);
    }
    if (height > 0) {
      sb.append(DELIMITER);
      sb.append("h");
      sb.append(SUB_DELIMITER);
      sb.append(height);
    }
    if (quality > 0 && quality <= 100) {
      sb.append(DELIMITER);
      sb.append("q");
      sb.append(SUB_DELIMITER);
      sb.append(quality);
    }
    if (StringUtils.isNotBlank(processParams)) {
      sb.append(DELIMITER);
      sb.append("p");
      sb.append(SUB_DELIMITER);
      sb.append(processParams);
    }
    return sb.toString();
  }

  public static void setMediaInfoByKey(MediaInfo mediaInfo, String mediaKey) {
    if (mediaInfo == null || StringUtils.isBlank(mediaKey)) {
      return;
    }

    String[] splits = mediaKey.trim().toLowerCase().split(DELIMITER);
    if (mediaInfo.getMimeType() == null || mediaInfo.getMimeType() == MimeType.UNKNOWN) {
      mediaInfo.setMimeType(splits[0]);
    }

    for (int i = 1; i < splits.length; ++i) {
      String[] kv = splits[i].split(SUB_DELIMITER);
      if (kv.length < 2) {
        continue;
      }
      if ("w".equals(kv[0])) {
        mediaInfo.width = Integer.parseInt(kv[1]);
      } else if ("h".equals(kv[0])) {
        mediaInfo.height = Integer.parseInt(kv[1]);
      } else if ("q".equals(kv[0])) {
        mediaInfo.quality = Integer.parseInt(kv[1]);
      } else if ("p".equals(kv[0])) {
        mediaInfo.processParams = kv[1];
      }
    }

    mediaInfo.width = mediaInfo.width > 0 ? mediaInfo.width : 0;
    mediaInfo.height = mediaInfo.height > 0 ? mediaInfo.height : 0;
    mediaInfo.quality = mediaInfo.quality > 0 && mediaInfo.quality <= 100 ? mediaInfo.quality : 0;
  }

  public void updateMediaInfo(MimeType mimeType, MediaInfo mediaInfo) {
    if (mediaInfo == null || mimeType == null) {
      return;
    }
    mediaInfos.put(getMediaKey(mimeType, mediaInfo.width, mediaInfo.height, mediaInfo.quality, mediaInfo.processParams), mediaInfo);
  }

  public MediaInfo getMediaInfo(String mimeType, int width, int height, int quality, String processParams) {
    String key = getMediaKey(mimeType, width, height, quality, processParams);
    return mediaInfos.get(key);
  }

  public MediaInfo getMediaInfo(MimeType mimeType, int width, int height, int quality, String processParams) {
    String key = getMediaKey(mimeType, width, height, quality, processParams);
    return mediaInfos.get(key);
  }

  public static boolean isSupportedType(MimeType mimeType) {
    for (MimeType type : SUPPORT_IMAGE_TYPES) {
      if (type == mimeType) {
        return true;
      }
    }
    return false;
  }

  public static String getImageInfoKey(String md5) {
    StringBuilder sb = new StringBuilder();
    if (StringUtils.isBlank(md5)) {
      sb.append(DEFAULT_TYPE);
    } else {
      sb.append(md5);
    }
    sb.append(DELIMITER);
    sb.append("image");
    sb.append(DELIMITER);
    sb.append("info");
    return sb.toString();
  }
}
