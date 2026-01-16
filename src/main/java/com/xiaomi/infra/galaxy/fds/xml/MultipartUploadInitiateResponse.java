package com.xiaomi.infra.galaxy.fds.xml;

import com.xiaomi.infra.galaxy.fds.result.InitMultipartUploadResult;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Response for Initiate Multipart Upload request.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "InitiateMultipartUploadResult",
    namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
public class MultipartUploadInitiateResponse {

  @XmlElement(name = "Bucket")
  private String bucket;

  @XmlElement(name = "Key")
  private String key;

  @XmlElement(name = "UploadId")
  private String uploadID;

  public MultipartUploadInitiateResponse() {
  }

  public MultipartUploadInitiateResponse(InitMultipartUploadResult
      initMultipartUploadResult) {
    this.bucket = initMultipartUploadResult.getBucketName();
    this.key = initMultipartUploadResult.getObjectName();
    this.uploadID = initMultipartUploadResult.getUploadId();
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
}
