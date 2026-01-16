package com.xiaomi.infra.galaxy.fds.xml;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Metadata object represents one bucket.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BucketMetadata {
  @XmlElement(name = "Name")
  private String name;

  @XmlJavaTypeAdapter(IsoDateAdapter.class)
  @XmlElement(name = "CreationDate")
  private Date creationDate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }
}
