package com.xiaomi.infra.galaxy.fds;

public enum RequestParameter {

  RESPONSE_CONTENT_TYPE("response-content-type", "Content-Type"),
  RESPONSE_CACHE_CONTROL("response-cache-control", "Cache-Control"),
  RESPONSE_EXPIRES("response-expires", "Expires"),
  RESPONSE_CONTENT_ENCODING("response-content-encoding", "Content-Encoding"),
  RESPONSE_CONTENT_DISPOSITION("response-content-disposition", "Content-Disposition");

  private final String param;
  private final String header;

  private RequestParameter(String param, String header) {
    this.param = param;
    this.header = header;
  }

  public String getParam() {
    return param;
  }

  public String getHeader() {
    return header;
  }
}
