package com.xiaomi.infra.galaxy.fds.xml;

import com.xiaomi.infra.galaxy.fds.bean.ObjectBean;
import com.xiaomi.infra.galaxy.fds.result.ListObjectsResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ListBucketResult",
    namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
public class ListObjectV2Response {

  @XmlElement(name = "Name")
  private String name;

  @XmlElement(name = "Prefix")
  private String prefix;

  @XmlElement(name = "MaxKeys")
  private int maxKeys;

  @XmlElement(name = "KeyCount")
  private int keyCount;

  @XmlElement(name = "Delimiter")
  private String delimiter = "/";

  @XmlElement(name = "EncodingType")
  private String encodingType = "url";

  @XmlElement(name = "IsTruncated")
  private boolean isTruncated;

  @XmlElement(name = "NextContinuationToken")
  private String nextToken;

  @XmlElement(name = "ContinuationToken")
  private String continueToken;

  @XmlElement(name = "StartAfter")
  private String startAfter;

  @XmlElement(name = "Contents")
  private List<KeyMetadata> contents = new ArrayList<KeyMetadata>();

  @XmlElement(name = "CommonPrefixes")
  private List<CommonPrefix> commonPrefixes = new ArrayList<CommonPrefix>();

  public ListObjectV2Response() {
  }

  public ListObjectV2Response(ListObjectsResult fdsResult) {
    this.name = fdsResult.getName();
    this.prefix = fdsResult.getPrefix();
    this.maxKeys = fdsResult.getMaxKeys();
    this.isTruncated = fdsResult.isTruncated();
    this.delimiter = fdsResult.getDelimiter();
    List<ObjectBean> objects = fdsResult.getObjects();
    this.contents = new LinkedList<KeyMetadata>();
    for (ObjectBean bean : objects) {
      KeyMetadata keyMetadata = new KeyMetadata();
      keyMetadata.setKey(bean.getName());
      keyMetadata.setETag(bean.getEtag());
      keyMetadata.setSize(bean.getSize());
      if (bean.getUploadTime() != 0) {
        keyMetadata.setLastModified(
            new Date(bean.getUploadTime()));
      }
      contents.add(keyMetadata);
    }
    keyCount = contents.size();
    List<String> commonPrefixes = fdsResult.getCommonPrefixes();
    this.commonPrefixes = new LinkedList<CommonPrefix>();
    for (String commonPrefix : commonPrefixes) {
      this.commonPrefixes.add(new CommonPrefix(commonPrefix));
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public int getMaxKeys() {
    return maxKeys;
  }

  public void setMaxKeys(int maxKeys) {
    this.maxKeys = maxKeys;
  }

  public String getDelimiter() {
    return delimiter;
  }

  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }

  public String getEncodingType() {
    return encodingType;
  }

  public void setEncodingType(String encodingType) {
    this.encodingType = encodingType;
  }

  public boolean isTruncated() {
    return isTruncated;
  }

  public void setTruncated(boolean truncated) {
    isTruncated = truncated;
  }

  public List<KeyMetadata> getContents() {
    return contents;
  }

  public void setContents(
      List<KeyMetadata> contents) {
    this.contents = contents;
  }

  public List<CommonPrefix> getCommonPrefixes() {
    return commonPrefixes;
  }

  public void setCommonPrefixes(
      List<CommonPrefix> commonPrefixes) {
    this.commonPrefixes = commonPrefixes;
  }

  public void addKey(KeyMetadata keyMetadata) {
    contents.add(keyMetadata);
  }

  public void addPrefix(String relativeKeyName) {
    commonPrefixes.add(new CommonPrefix(relativeKeyName));
  }

  public String getNextToken() {
    return nextToken;
  }

  public void setNextToken(String nextToken) {
    this.nextToken = nextToken;
  }

  public String getContinueToken() {
    return continueToken;
  }

  public void setContinueToken(String continueToken) {
    this.continueToken = continueToken;
  }

  public int getKeyCount() {
    return keyCount;
  }

  public void setKeyCount(int keyCount) {
    this.keyCount = keyCount;
  }

  public String getStartAfter() {
    return startAfter;
  }

  public void setStartAfter(String startAfter) {
    this.startAfter = startAfter;
  }
}
