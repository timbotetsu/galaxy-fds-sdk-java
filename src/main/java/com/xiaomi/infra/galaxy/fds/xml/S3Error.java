package com.xiaomi.infra.galaxy.fds.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Error")
public class S3Error {
  @XmlElement(name = "Code")
  private String code;
  @XmlElement(name = "Message")
  private String message;
  @XmlElement(name = "Resource")
  private String resource;
  @XmlElement(name = "RequestId")
  private String requestId;

  public S3Error() {
  }

  public S3Error(String code, String message, String resource, String requestId) {
    this.code = code;
    this.message = message;
    this.resource = resource;
    this.requestId = requestId;
  }

  public S3Error(String code, String message, String requestId) {
    this.code = code;
    this.message = message;
    this.requestId = requestId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  @Override
  public String toString() {
    return "S3Error{" + "code='" + code + '\'' + ", message='" + message + '\'' + ", resource='"
        + resource + '\'' + ", requestId='" + requestId + '\'' + '}';
  }
}
