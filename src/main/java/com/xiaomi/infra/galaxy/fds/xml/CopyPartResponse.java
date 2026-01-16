package com.xiaomi.infra.galaxy.fds.xml;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Copy object Response.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CopyPartResult",
    namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
public class CopyPartResponse {

  @XmlJavaTypeAdapter(IsoDateAdapter.class)
  @XmlElement(name = "LastModified")
  private Date lastModified;

  @XmlElement(name = "ETag")
  private String eTag;

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
}
