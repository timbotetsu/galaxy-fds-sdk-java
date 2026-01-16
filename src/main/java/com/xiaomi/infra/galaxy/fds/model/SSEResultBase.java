package com.xiaomi.infra.galaxy.fds.model;

/**
 * Common abstract base class for result that contains server side encryption
 * (SSE) information.
 */
public abstract class SSEResultBase implements ServerSideEncryptionResult {
  private String sseAlgorithm;

  @Override
  public final String getSSEAlgorithm() {
    return sseAlgorithm;
  }

  @Override
  public final void setSSEAlgorithm(String algorithm) {
    this.sseAlgorithm = algorithm;
  }
}
