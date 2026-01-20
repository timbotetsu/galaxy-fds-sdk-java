package com.xiaomi.infra.galaxy.fds;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

public class JacksonUtils {

  private static class Holder {
    private static final JsonMapper INSTANCE = JsonMapper.builderWithJackson2Defaults().build();
  }

  public static JsonMapper mapper() {
    return Holder.INSTANCE;
  }

  public static String toJson(Object obj) {
    try {
      return mapper().writeValueAsString(obj);
    } catch (JacksonException e) {
      throw new RuntimeException("json serialize error");
    }
  }

}
