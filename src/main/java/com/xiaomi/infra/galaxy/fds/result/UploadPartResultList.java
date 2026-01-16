package com.xiaomi.infra.galaxy.fds.result;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UploadPartResultList {
  private List<UploadPartResult> uploadPartResultList = new ArrayList<UploadPartResult>();

  public UploadPartResultList() {
  }

  public UploadPartResultList(List<UploadPartResult> uploadPartResults) {
    this.uploadPartResultList = uploadPartResults;
  }

  public List<UploadPartResult> getUploadPartResultList() {
    return uploadPartResultList;
  }

  public void setUploadPartResultList(List<UploadPartResult> uploadPartResultList) {
    this.uploadPartResultList = uploadPartResultList;
  }

  public void addUploadPartResult(UploadPartResult u) {
    this.uploadPartResultList.add(u);
  }
}
