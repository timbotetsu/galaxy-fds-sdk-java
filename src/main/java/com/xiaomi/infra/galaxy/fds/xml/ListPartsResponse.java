package com.xiaomi.infra.galaxy.fds.xml;

import com.xiaomi.infra.galaxy.fds.result.UploadPartResult;
import com.xiaomi.infra.galaxy.fds.result.UploadPartResultList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Request for list parts of a multipart upload request.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ListPartsResult",
    namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
public class ListPartsResponse {

  @XmlElement(name = "Bucket")
  private String bucket;

  @XmlElement(name = "Key")
  private String key;

  @XmlElement(name = "UploadId")
  private String uploadID;

  @XmlElement(name = "StorageClass")
  private String storageClass;

  @XmlElement(name = "PartNumberMarker")
  private int partNumberMarker;

  @XmlElement(name = "NextPartNumberMarker")
  private int nextPartNumberMarker;

  @XmlElement(name = "MaxParts")
  private int maxParts;

  @XmlElement(name = "IsTruncated")
  private boolean truncated;

  @XmlElement(name = "Part")
  private List<Part> partList = new ArrayList<Part>();

  public ListPartsResponse() {
  }

  public ListPartsResponse(String bucket, String object, String uploadId,
      int partNumberMarker, int maxParts, UploadPartResultList uploadPartResultList) {
    this.bucket = bucket;
    this.key = object;
    this.uploadID = uploadId;
    this.partNumberMarker = partNumberMarker;
    this.maxParts = maxParts;
    int count = 0;
    this.truncated = false;
    this.nextPartNumberMarker = partNumberMarker;
    for (UploadPartResult result : uploadPartResultList.getUploadPartResultList()) {
      if (result.getPartNumber() <= partNumberMarker) {
        continue;
      }
      if (count >= maxParts) {
        truncated = true;
        break;
      }
      Part part = new Part();
      part.setPartNumber(result.getPartNumber());
      part.setETag(result.getEtag());
      part.setSize(result.getPartSize());
      part.setLastModified(new Date(result.getLastModified()));
      partList.add(part);
      this.nextPartNumberMarker = result.getPartNumber();
      count++;
    }
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

  public String getUploadID() {
    return uploadID;
  }

  public void setUploadID(String uploadID) {
    this.uploadID = uploadID;
  }

  public String getStorageClass() {
    return storageClass;
  }

  public void setStorageClass(String storageClass) {
    this.storageClass = storageClass;
  }

  public int getPartNumberMarker() {
    return partNumberMarker;
  }

  public void setPartNumberMarker(int partNumberMarker) {
    this.partNumberMarker = partNumberMarker;
  }

  public int getNextPartNumberMarker() {
    return nextPartNumberMarker;
  }

  public void setNextPartNumberMarker(int nextPartNumberMarker) {
    this.nextPartNumberMarker = nextPartNumberMarker;
  }

  public int getMaxParts() {
    return maxParts;
  }

  public void setMaxParts(int maxParts) {
    this.maxParts = maxParts;
  }

  public boolean getTruncated() {
    return truncated;
  }

  public void setTruncated(boolean truncated) {
    this.truncated = truncated;
  }

  public List<Part> getPartList() {
    return partList;
  }

  public void setPartList(List<Part> partList) {
    this.partList = partList;
  }

  public void addPart(Part part) {
    this.partList.add(part);
  }

  /**
   * Part information.
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlRootElement(name = "Part")
  public static class Part {

    @XmlElement(name = "PartNumber")
    private int partNumber;

    @XmlJavaTypeAdapter(IsoDateAdapter.class)
    @XmlElement(name = "LastModified")
    private Date lastModified;

    @XmlElement(name = "ETag")
    private String eTag;


    @XmlElement(name = "Size")
    private long size;

    public int getPartNumber() {
      return partNumber;
    }

    public void setPartNumber(int partNumber) {
      this.partNumber = partNumber;
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
  }
}
