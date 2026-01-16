package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.bean.OwnerBean;
import java.util.Date;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: shenjiaqi@xiaomi.com
 */
public class VersionSummary {
  /** The name of the bucket in which this version is stored */
  protected String bucketName;

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  protected String objectName;

  /** The version ID uniquely identifying this version of an object */
  private String versionId;

  /** True if this is the latest version of the associated object */
  private boolean latest;

  /** The date, according to FDS, when this version was last modified */
  private Date lastModified;

  /**
   * The owner of this version of the associated object - can be null if the
   * requester doesn't have permission to view object ownership information
   */
  private OwnerBean owner;

  /** The size of this version, in bytes */
  private long size;

  /** True if this object represents a delete marker */
  private boolean deleteMarker;

  /**
   * Gets the name of the bucket in which this version is stored.
   *
   * @return The name of the bucket in which this version is stored.
   *
   */
  public String getBucketName() {
    return bucketName;
  }

  /**
   * Sets the name of the bucket in which this version is stored.
   *
   * @param bucketName
   *            The name of the bucket in which this version is
   *            stored.
   *
   */
  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  /**
   * Sets the key under which this version is stored in FDS.
   *
   * @param key
   *            The key under which this version is stored in FDS.
   *
   */

  /**
   * Gets the version ID which uniquely identifies this version of an
   * object.
   * <p>
   * Objects created before versioning was enabled or when versioning is
   * suspended will be given the "null" version ID
   * </p>
   *
   * @return The version ID which uniquely identifies this version of an
   *         object.
   *
   */
  public String getVersionId() {
    return versionId;
  }

  /**
   * Sets the version ID which uniquely identifies this version of an object.
   *
   * @param id
   *            The version ID which uniquely identifies this version of an
   *            object.
   *
   */
  public void setVersionId(String id) {
    this.versionId = id;
  }

  /**
   * Returns whether or not this version is the latest version
   * for the associated object.
   *
   * @return The value <code>true</code> if this version is the
   * latest version for the associated object; returns the value
   * <code>false</code> if otherwise.
   */
  public boolean isLatest() {
    return this.latest;
  }

  /**
   * For internal use only.
   * Sets whether this version is the latest version for the associated
   * object. This method is intended to be used only by the client internals
   * and developers shouldn't need to use it.
   *
   * @param latest
   *            True if this version represents the latest version for the
   *            associated object in FDS.
   */
  public void setLatest(boolean latest) {
    this.latest = latest;
  }

  /**
   * Gets the date according to FDS at which this version was last
   * modified.
   *
   * @return The date according to FDS at which this version was last
   *         modified.
   *
   */
  public Date getLastModified() {
    return lastModified;
  }

  /**
   * Sets the date according to FDS at which this version was last
   * modified.
   *
   * @param lastModified
   *            The date according to FDS at which this version was
   *            last modified.
   *
   */
  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  /**
   * Gets the owner of this version.
   *
   * @return The owner of this version. Returns <code>null</code>
   *         if the requester doesn't have
   *         permission to see object ownership for this version.
   *
   */
  public OwnerBean getOwner() {
    return owner;
  }

  /**
   * For internal use only.
   * Sets the owner of this version. This method is intended to be used only
   * by the client internals and developers shouldn't need to use it.
   *
   * @param owner
   *            The owner of this version.
   */
  public void setOwner(OwnerBean owner) {
    this.owner = owner;
  }

  /**
   * Returns whether or not this version represents a delete marker.
   * <p>
   * Delete markers are special types of versions that have no data associated
   * with them. When deleting a versioned object,
   * a new delete marker is created as the latest
   * version of that object to mark that the object was deleted.
   * </p>
   *
   * @return The value <code>true</code> if this version represents a delete marker.
   *         Returns the value <code>false</code> if otherwise.
   */
  public boolean isDeleteMarker() {
    return deleteMarker;
  }

  /**
   * Intended for internal use. Sets the value of
   * the <code>isDeleteMarker</code> property to record if this is a delete marker or not.
   *
   * @param deleteMarker
   *            Specify <code>true</code> if this version summary represents a delete marker,
   *            otherwise <code>false</code> if it is a regular version summary.
   */
  public void setDeleteMarker(boolean deleteMarker) {
    this.deleteMarker = deleteMarker;
  }

  /**
   * Gets the size of this version in bytes.
   *
   * @return The size of this version in bytes.
   *
   */
  public long getSize() {
    return size;
  }

  /**
   * Sets the size of this version in bytes.
   *
   * @param size
   *            The size of this version in bytes.
   *
   */
  public void setSize(long size) {
    this.size = size;
  }

}
