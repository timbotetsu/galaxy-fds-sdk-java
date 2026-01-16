package com.xiaomi.infra.galaxy.fds.result;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class InventoryConfiguration {

  private BucketDestination bucketDestination;

  private String id;

  private boolean isEnabled;

  private String prefix = "";

  private Frequency frequency;

  private Set<OptionalField> optionalFields = new HashSet<OptionalField>();

  public BucketDestination getBucketDestination() {
    return bucketDestination;
  }

  public void setBucketDestination(BucketDestination bucketDestination) {
    this.bucketDestination = bucketDestination;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public Frequency getFrequency() {
    return frequency;
  }

  public void setFrequency(Frequency frequency) {
    this.frequency = frequency;
  }

  public void addOptionalField(OptionalField field) {
    if (optionalFields == null) {
      optionalFields = new HashSet<OptionalField>();
    }
    optionalFields.add(field);
  }

  public Set<OptionalField> getOptionalFields() {
    return optionalFields;
  }

  public void setOptionalFields(Set<OptionalField> optionalFields) {
    this.optionalFields = optionalFields;
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InventoryConfiguration that = (InventoryConfiguration) o;
    return isEnabled == that.isEnabled && Objects.equals(bucketDestination, that.bucketDestination) && Objects.equals(id, that.id) && Objects.equals(prefix,
      that.prefix) && frequency == that.frequency && Objects.equals(optionalFields, that.optionalFields);
  }

  @Override public int hashCode() {
    return Objects.hash(bucketDestination, id, isEnabled, prefix, frequency, optionalFields);
  }

  @Override public String toString() {
    return "InventoryConfiguration{" + "bucketDestination=" + bucketDestination + ", id='" + id + '\'' + ", isEnabled=" + isEnabled + ", prefix='" + prefix
      + '\'' + ", frequency=" + frequency + ", optionalFields=" + optionalFields + '}';
  }

  public enum Frequency {
    Weekly, Daily,
  }

  public enum OptionalField {
    Size, LastModifiedDate, StorageClass, EncryptionStatus,
  }

}
