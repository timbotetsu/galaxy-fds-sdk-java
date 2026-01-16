package com.xiaomi.infra.galaxy.fds.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Complete Multipart Upload request response.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CompleteMultipartUploadResult",
    namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
public class CompleteMultipartUploadResponse {

  @XmlElement(name = "Location")
  private String location;

  @XmlElement(name = "Bucket")
  private String bucket;

  @XmlElement(name = "Key")
  private String key;

  @XmlElement(name = "ETag")
  private String eTag;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getETag() {
    return eTag;
  }

  public void setETag(String tag) {
    this.eTag = tag;
  }
}
