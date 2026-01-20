package com.xiaomi.infra.galaxy.fds.model;

import com.xiaomi.infra.galaxy.fds.JacksonUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

public class WebsiteConfig {
  private static final String INDEX_DOCUMENT = "indexDocument";
  private static final String ERROR_DOCUMENT = "errorDocument";
  private static final String REDIRECT_RULE = "redirectRule";

  private String indexDocument;
  private String errorDocument;
  private List<RedirectRule> redirectRules = new ArrayList<>();

  public String getIndexDocument() {
    return indexDocument;
  }

  public void setIndexDocument(String indexDocument) {
    this.indexDocument = indexDocument;
  }

  public void setErrorDocument(String errorDocument) {
    this.errorDocument = errorDocument;
  }

  public String getErrorDocument() {
    return errorDocument;
  }

  public void setRedirectRules(List<RedirectRule> redirectRules) {
    if (redirectRules != null && !redirectRules.isEmpty()) {
      for (RedirectRule redirectRule : redirectRules) {
        addRedirectRule(redirectRule);
      }
    }
  }

  public void addRedirectRule(RedirectRule redirectRule) {
    if (redirectRule != null && StringUtils.isNotBlank(redirectRule.getKeyPrefixEquals())) {
      redirectRules.add(redirectRule);
    }
  }

  public List<RedirectRule> getRedirectRules() {
    return redirectRules;
  }

  public String getRedirectObjectName(String objectName) {
    if (redirectRules != null && !redirectRules.isEmpty()) {
      for (RedirectRule redirectRule : redirectRules) {
        if (objectName.startsWith(redirectRule.getKeyPrefixEquals())) {
          if (StringUtils.isNotBlank(redirectRule.getReplaceKeyWith())) {
            return redirectRule.getReplaceKeyWith();
          }
          if (StringUtils.isNotBlank(redirectRule.getReplaceKeyPrefixWith())) {
            return redirectRule.getReplaceKeyPrefixWith() + objectName.substring(redirectRule.getKeyPrefixEquals().length());
          }
        }
      }
    }
    return null;
  }

  public static WebsiteConfig fromJson(String json) {
    JsonMapper mapper = JacksonUtils.mapper();
    ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
    WebsiteConfig config = new WebsiteConfig();

    if (objectNode.has(INDEX_DOCUMENT)) {
      config.setIndexDocument(objectNode.get(INDEX_DOCUMENT).asString());
    }

    if (objectNode.has(ERROR_DOCUMENT)) {
      config.setErrorDocument(objectNode.get(ERROR_DOCUMENT).asString());
    }

    if (objectNode.has(REDIRECT_RULE)) {
      ArrayNode redirectRulesArray = objectNode.withArray(REDIRECT_RULE);
      List<RedirectRule> redirectRules = new ArrayList<>();
      for (int i = 0; i < redirectRulesArray.size(); i++) {
        redirectRules.add(RedirectRule.fromJson(redirectRulesArray.get(i).toString()));
      }
      config.setRedirectRules(redirectRules);
    }

    return config;
  }

  public String toJson() {
    ObjectNode objectNode = JacksonUtils.mapper().createObjectNode();

    if (StringUtils.isNotBlank(indexDocument)) {
      objectNode.put(INDEX_DOCUMENT, indexDocument);
    }

    if (StringUtils.isNotBlank(errorDocument)) {
      objectNode.put(ERROR_DOCUMENT, errorDocument);
    }

    ArrayNode arrayNode = JacksonUtils.mapper().createArrayNode();
    for (RedirectRule redirectRule : redirectRules) {
      arrayNode.add(redirectRule.toJson());
    }
    if (!arrayNode.isEmpty()) {
      objectNode.set(REDIRECT_RULE, arrayNode);
    }
    return objectNode.toString();
  }

  public static class RedirectRule {
    private static final String KEY_PREFIX_EQUALS = "keyPrefixEquals";
    private static final String REPLACE_KEY_WITH = "replaceKeyWith";
    private static final String REPLACE_KEY_PREFIX_WITH = "replaceKeyPrefixWith";

    private String keyPrefixEquals;
    private String replaceKeyPrefixWith;
    private String replaceKeyWith;

    public void setKeyPrefixEquals(String keyPrefixEquals) {
      this.keyPrefixEquals = keyPrefixEquals;
    }

    public String getKeyPrefixEquals() {
      return keyPrefixEquals;
    }

    public void setReplaceKeyWith(String replaceKeyWith) {
      this.replaceKeyWith = replaceKeyWith;
    }

    public String getReplaceKeyWith() {
      return replaceKeyWith;
    }

    public void setReplaceKeyPrefixWith(String replaceKeyPrefixWith) {
      this.replaceKeyPrefixWith = replaceKeyPrefixWith;
    }

    public String getReplaceKeyPrefixWith() {
      return replaceKeyPrefixWith;
    }

    public static RedirectRule fromJson(String json) {
      JsonMapper mapper = JacksonUtils.mapper();
      ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
      RedirectRule rule = new RedirectRule();

      String keyPrefixEquals = objectNode.get(KEY_PREFIX_EQUALS).asString();
      if (StringUtils.isBlank(keyPrefixEquals)) {
        throw new RuntimeException("KeyPrefixEquals should not be null or empty");
      }
      rule.setKeyPrefixEquals(keyPrefixEquals);

      if (objectNode.has(REPLACE_KEY_WITH)) {
        rule.setReplaceKeyWith(objectNode.get(REPLACE_KEY_WITH).asString());
      }

      if (objectNode.has(REPLACE_KEY_PREFIX_WITH)) {
        rule.setReplaceKeyPrefixWith(objectNode.get(REPLACE_KEY_PREFIX_WITH).asString());
      }

      return rule;
    }

    public String toJson() {
      ObjectNode objectNode = JacksonUtils.mapper().createObjectNode();
      if (StringUtils.isNotBlank(keyPrefixEquals)) {
        objectNode.put(KEY_PREFIX_EQUALS, keyPrefixEquals);
      } else {
        throw new RuntimeException("keyPrefixEquals should not be null or empty.");
      }

      if (StringUtils.isNotBlank(replaceKeyPrefixWith)) {
        objectNode.put(REPLACE_KEY_PREFIX_WITH, replaceKeyPrefixWith);
      }

      if (StringUtils.isNotBlank(replaceKeyWith)) {
        objectNode.put(REPLACE_KEY_WITH, replaceKeyWith);
      }
      return objectNode.toString();
    }
  }
}
