package com.xiaomi.infra.galaxy.fds.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Response for multi object delete request.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DeleteResult",
    namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
public class MultiDeleteResponse {

  public MultiDeleteResponse() {
  }

  public MultiDeleteResponse(List<Map<String, Object>> errorList) {
    this.errors = new ArrayList<Error>();
    for (Map<String, Object> exceptionDetail : errorList) {
      Error error = new Error();
      error.setCode(String.valueOf(exceptionDetail.get("error_code")));
      error.setKey((String) exceptionDetail.get("object_name"));
      error.setMessage((String) exceptionDetail.get("error_description"));
      errors.add(error);
    }
  }

  @XmlElement(name = "Deleted")
  private List<DeletedObject> deletedObjects = new ArrayList<DeletedObject>();

  @XmlElement(name = "Error")
  private List<Error> errors = new ArrayList<Error>();

  public void addDeleted(DeletedObject deletedObject) {
    deletedObjects.add(deletedObject);
  }

  public void addError(Error error) {
    errors.add(error);
  }

  public List<DeletedObject> getDeletedObjects() {
    return deletedObjects;
  }

  public void setDeletedObjects(
      List<DeletedObject> deletedObjects) {
    this.deletedObjects = deletedObjects;
  }

  public List<Error> getErrors() {
    return errors;
  }

  public void setErrors(
      List<Error> errors) {
    this.errors = errors;
  }

  /**
   * JAXB entity for child element.
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlRootElement(name = "Deleted", namespace = "http://s3.amazonaws"
      + ".com/doc/2006-03-01/")
  public static class DeletedObject {

    @XmlElement(name = "Key")
    private String key;

    private String versionId;

    public DeletedObject() {
    }

    public DeletedObject(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String getVersionId() {
      return versionId;
    }

    public void setVersionId(String versionId) {
      this.versionId = versionId;
    }
  }

  /**
   * JAXB entity for child element.
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlRootElement(name = "Error", namespace = "http://s3.amazonaws"
      + ".com/doc/2006-03-01/")
  public static class Error {

    @XmlElement(name = "Key")
    private String key;

    @XmlElement(name = "Code")
    private String code;

    @XmlElement(name = "Message")
    private String message;

    public Error() {
    }

    public Error(String key, String code, String message) {
      this.key = key;
      this.code = code;
      this.message = message;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
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
  }
}
