package com.xiaomi.infra.galaxy.fds.model;

import com.xiaomi.infra.galaxy.fds.Common;

/**
 * Interface for service responses that include the server-side-encryption
 * related headers.
 *
 * @see Common#SERVER_SIDE_ENCRYPTION
 */
public interface ServerSideEncryptionResult {

  /**
   * Returns the server-side encryption algorithm if the object is encrypted
   * using AWS-managed keys. Otherwise returns null.
   */
  public String getSSEAlgorithm();

  /**
   * Sets the server-side encryption algorithm for the response.
   *
   * @param algorithm
   *            The server-side encryption algorithm for the response.
   */
  public void setSSEAlgorithm(String algorithm);
}

