package com.xiaomi.infra.galaxy.fds.auth.sso;

/**
 * The SSO service token class.
 */
public class ServiceToken {

  private boolean tsl;
  private long uid;
  private long developerId;
  private String secret;
  private long timestamp;
  private String version;

  public static class Builder {

    private boolean tsl;
    private long uid;
    private String secret;
    private long timestamp;
    private String version;
    private long developerId;

    public ServiceToken build() {
      ServiceToken token = new ServiceToken();
      token.tsl = this.tsl;
      token.uid = this.uid;
      token.secret = this.secret;
      token.timestamp = this.timestamp;
      token.version = this.version;
      token.developerId = this.developerId;
      return token;
    }

    public Builder tsl(boolean tsl) {
      this.tsl = tsl;
      return this;
    }

    public Builder uid(long uid) {
      this.uid = uid;
      return this;
    }

    public Builder secret(String secret) {
      this.secret = secret;
      return this;
    }

    public Builder timestamp(long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder developerId(long developerId) {
      this.developerId = developerId;
      return this;
    }
  }

  public boolean isTsl() {
    return tsl;
  }

  public long getUid() {
    return uid;
  }

  public String getSecret() {
    return secret;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getVersion() {
    return version;
  }

  public long getDeveloperId() {
    return developerId;
  }

  @Override
  public String toString() {
    return "[ServiceToken: tsl=" + tsl +  ", uid=" + uid +
        ", timestamp=" + timestamp + ", version=" + version + "]";
  }
}
