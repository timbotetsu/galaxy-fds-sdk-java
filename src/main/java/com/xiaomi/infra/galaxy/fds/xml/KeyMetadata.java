package com.xiaomi.infra.galaxy.fds.xml;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Metadata object represents one key in the object store.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class KeyMetadata {

  @XmlElement(name = "Key")
  private String key; // or the Object Name

  @XmlJavaTypeAdapter(IsoDateAdapter.class)
  @XmlElement(name = "LastModified")
  private Date lastModified;

  @XmlElement(name = "ETag")
  private String eTag;

  @XmlElement(name = "Size")
  private long size;

  @XmlElement(name = "StorageClass")
  private String storageClass;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public String getETag() {
    return eTag;
  }

  public void setETag(String tag) {
    this.eTag = tag;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getStorageClass() {
    return storageClass;
  }

  public void setStorageClass(String storageClass) {
    this.storageClass = storageClass;
  }
}
