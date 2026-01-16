package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.bean.BucketBean;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: zhangjunbin@xiaomi.com
 */
@XmlRootElement
public class ListAllAuthorizedBucketsResult {
  private List<BucketBean> buckets;

  public ListAllAuthorizedBucketsResult() {
  }

  public ListAllAuthorizedBucketsResult(List<BucketBean> buckets) {
    this.buckets = buckets;
  }

  public List<BucketBean> getBuckets() {
    return buckets;
  }

  public void setBuckets(List<BucketBean> buckets) {
    this.buckets = buckets;
  }
}
