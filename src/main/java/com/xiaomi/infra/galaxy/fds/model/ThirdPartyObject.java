package com.xiaomi.infra.galaxy.fds.model;

import com.xiaomi.infra.galaxy.fds.bean.ThirdPartyObjectBean;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class ThirdPartyObject {
  private ThirdPartyObjectBean thirdPartyObjectBean;
  private FDSObjectMetadata objectMetadata;

  public ThirdPartyObjectBean getThirdPartyObjectBean() {
    return thirdPartyObjectBean;
  }

  public void setThirdPartyObjectBean(ThirdPartyObjectBean thirdPartyObjectBean) {
    this.thirdPartyObjectBean = thirdPartyObjectBean;
  }

  public FDSObjectMetadata getObjectMetadata() {
    return objectMetadata;
  }

  public void setObjectMetadata(FDSObjectMetadata objectMetadata) {
    this.objectMetadata = objectMetadata;
  }
}
