package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.model.SSEResultBase;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UploadPartResult extends SSEResultBase implements Comparable {
  private int partNumber;
  private String etag;
  private long partSize;
  private long crc64Ecma;
  private long lastModified;

  public UploadPartResult() { }

  public UploadPartResult(int partNumber, long partSize, String etag, long crc64Ecma, long ts) {
    this.partNumber = partNumber;
    this.etag = etag;
    this.partSize = partSize;
    this.crc64Ecma = crc64Ecma;
    this.lastModified = ts;
  }

  public int getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(int partNumber) {
    this.partNumber = partNumber;
  }

  public String getEtag() {
    return etag;
  }

  public void setEtag(String etag) {
    this.etag = etag;
  }

  public long getPartSize() {
    return partSize;
  }

  public void setPartSize(long partSize) {
    this.partSize = partSize;
  }

  public long getCrc64Ecma() {
    return crc64Ecma;
  }

  public void setCrc64Ecma(long crc64Ecma) {
    this.crc64Ecma = crc64Ecma;
  }

  public long getLastModified() {
    return lastModified;
  }

  public void setLastModified(long lastModified) {
    this.lastModified = lastModified;
  }

  @Override
  public int compareTo(Object o) {
    UploadPartResult that = (UploadPartResult)o;
    return new Integer(partNumber).compareTo(that.getPartNumber());
  }
}
