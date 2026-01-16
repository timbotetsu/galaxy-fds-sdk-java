package com.xiaomi.infra.galaxy.fds.model;

import org.apache.commons.lang3.StringUtils;

public enum MimeType {
  UNKNOWN("unknown"), IMAGE_BMP("image/bmp"), IMAGE_PNG("image/png"), IMAGE_JPEG("image/jpeg"), IMAGE_GIF("image/gif"), IMAGE_WEBP("image/webp"), IMAGE_TIFF("image/tiff");

  private String val;

  MimeType(String val) {
    this.val = val;
  }

  public String getVal() {
    return val;
  }

  public String getFormatVal() {
    if (this == UNKNOWN) {
      return null;
    }
    return val.substring("image/".length());
  }

  public static MimeType fromString(String type) {
    if (StringUtils.isBlank(type)) {
      return UNKNOWN;
    }
    type = type.toLowerCase().trim();
    if (type.endsWith("jpg")) {
      return IMAGE_JPEG;
    }

    for (MimeType mimeType : values()) {
      if (mimeType.val.endsWith(type)) {
        return mimeType;
      }
    }
    return UNKNOWN;
  }
}
