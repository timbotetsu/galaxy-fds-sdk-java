package com.xiaomi.infra.galaxy.fds.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Response from the ListBucket RPC Call.
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "ListAllMyBucketsResult", namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
public class ListBucketResponse {
  public ListBucketResponse() {
  }

  public ListBucketResponse(OwnerBean owner, List<BucketMetadata> buckets) {
    this.owner = owner;
    this.buckets = buckets;
  }

  @XmlElement(name = "Owner") private OwnerBean owner;
  @XmlElementWrapper(name = "Buckets") @XmlElement(name = "Bucket") private List<BucketMetadata> buckets = new ArrayList<BucketMetadata>();

  public List<BucketMetadata> getBuckets() {
    return buckets;
  }

  public int getBucketsNum() {
    return buckets.size();
  }

  public void setBuckets(List<BucketMetadata> buckets) {
    this.buckets = buckets;
  }

  public void addBucket(BucketMetadata bucket) {
    buckets.add(bucket);
  }

  public OwnerBean getOwner() {
    return owner;
  }

  public void setOwner(OwnerBean owner) {
    this.owner = owner;
  }
}
