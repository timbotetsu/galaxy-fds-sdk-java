package com.xiaomi.infra.galaxy.fds.bean;

import java.util.UUID;

/**
 * Created by yepeng on 18-7-25.
 */
public class QuotaApply {
  public static final String DONE_PREFIX = "done";
  public static final String PENDING_PREFIX = "pending";
  public static final String QUOTA_APPLY_DELIMITER = "_";
  private static final long LONG_MAX = 9223372036854775807L;
  private static final long GByte = 1024 * 1024 * 1024;

  private String orgId;
  private String applyUser;
  private long applyTime;
  private ApplyStatus applyStatus;
  private long fdsQuota;
  private String applyId;
  private ApplyType applyType;

  public QuotaApply(){}

  public QuotaApply(String orgId,
                    String applyUser,
                    long applyTime,
                    ApplyStatus applyStatus,
                    long fdsQuota){
    this(orgId, applyUser, applyTime, applyStatus, ApplyType.SPACE, fdsQuota);
  }

  public QuotaApply(String orgId,
                    String applyUser,
                    long applyTime,
                    ApplyStatus applyStatus,
                    ApplyType applyType,
                    long fdsQuota){
    this.orgId = orgId;
    this.applyUser = applyUser;
    this.applyTime = applyTime;
    this.applyStatus = applyStatus;
    this.applyType = applyType;
    this.fdsQuota = fdsQuota;
    this.applyId = generateApplyId(orgId, applyTime);
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public void setApplyUser(String applyUser) {
    this.applyUser = applyUser;
  }

  public void setApplyTime(long applyTime) {
    this.applyTime = applyTime;
  }

  public void setApplyStatus(ApplyStatus applyStatus) {
    this.applyStatus = applyStatus;
  }

  public void setFdsQuota(long fdsQuota) {
    this.fdsQuota = fdsQuota;
  }

  public void setApplyId(String applyId) {
    this.applyId = applyId;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getApplyUser() {
    return applyUser;
  }

  public long getApplyTime() {
    return applyTime;
  }

  public ApplyStatus getApplyStatus() {
    return applyStatus;
  }

  public String getApplyId() {
    return applyId;
  }

  public long getFdsQuota() {
    return fdsQuota;
  }

  public ApplyType getApplyType() { return applyType; }

  public void setApplyType(ApplyType applyType) { this.applyType = applyType; }

  private String generateApplyId(String orgId, long applyTime){
    String uuid = UUID.randomUUID().toString();
    String prefix = null;
    if(applyStatus == ApplyStatus.PENDING){
      prefix = PENDING_PREFIX;
    } else {
      prefix =  DONE_PREFIX;
    }
    //for reverse order according to time
    String formatTime = formatTimeStamp(applyTime);
    return prefix + QUOTA_APPLY_DELIMITER + orgId + QUOTA_APPLY_DELIMITER + formatTime + QUOTA_APPLY_DELIMITER + uuid;
  }

  private String formatTimeStamp(long applyTime){
    String result =
        String.format("%020d", LONG_MAX - applyTime);
    return result;
  }

  public enum ApplyStatus {
    PENDING,
    ACCEPT,
    REJECT
  }

  public enum ApplyType{
    SPACE(GByte, "空间配额"),
    BUCKET_LIMIT(1, "Bucket配额"),
    CDN_PREFETCH(1, "CDN预取配额"),
    CDN_REFRESH(1, "CDN刷新配额");

    private long unit;
    private String displayName;

    ApplyType(long unit, String displayName) {
      this.unit = unit;
      this.displayName = displayName;
    }

    public long getUnit() {
      return unit;
    }

    public static ApplyType fromString(String str){
      for (ApplyType type : values()){
        if (type.name().equalsIgnoreCase(str)){
          return type;
        }
      }
      return null;
    }

    public String getDisplayName() {
      return displayName;
    }
  }
}
