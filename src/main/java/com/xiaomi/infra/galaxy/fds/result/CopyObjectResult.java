package com.xiaomi.infra.galaxy.fds.result;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CopyObjectResult extends PutObjectResult {
  public CopyObjectResult fromPutObjectResult(PutObjectResult putObjectResult) {
    this.setBucketName(putObjectResult.getBucketName());
    this.setAccessKeyId(putObjectResult.getAccessKeyId());
    this.setExpires(putObjectResult.getExpires());
    this.setObjectName(putObjectResult.getObjectName());
    this.setOutsideAccess(putObjectResult.getOutsideAccess());
    this.setPreviousVersionId(putObjectResult.getPreviousVersionId());
    this.setSignature(putObjectResult.getSignature());
    this.setSSEAlgorithm(putObjectResult.getSSEAlgorithm());;
    return this;
  }

  @Override
  public String toString() {
    return "CopyObjectResult{" +
            "bucketName='" + this.getBucketName() + '\'' +
            ", objectName='" + this.getObjectName() + '\'' +
            ", accessKeyId='" + this.getAccessKeyId() + '\'' +
            ", signature='" + this.getSignature() + '\'' +
            ", previousVersionId='" + this.getPreviousVersionId() + '\'' +
            ", expires=" + this.getExpires() +
            ", outsideAccess=" + this.getOutsideAccess() +
            '}';
  }
}
