package com.xiaomi.infra.galaxy.fds.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

  public static WebsiteConfig fromJson(String json) throws JSONException {
    JSONObject jsonObject = new JSONObject(json);
    WebsiteConfig config = new WebsiteConfig();

    if (jsonObject.has(INDEX_DOCUMENT)) {
      config.setIndexDocument(jsonObject.getString(INDEX_DOCUMENT));
    }

    if (jsonObject.has(ERROR_DOCUMENT)) {
      config.setErrorDocument(jsonObject.getString(ERROR_DOCUMENT));
    }

    if (jsonObject.has(REDIRECT_RULE)) {
      JSONArray redirectRulesArray = jsonObject.getJSONArray(REDIRECT_RULE);
      List<RedirectRule> redirectRules = new ArrayList<RedirectRule>();
      for (int i = 0; i < redirectRulesArray.length(); i++) {
        redirectRules.add(RedirectRule.fromJson(redirectRulesArray.getString(i)));
      }
      config.setRedirectRules(redirectRules);
    }

    return config;
  }

  public String toJson() throws JSONException {
    JSONObject jsonObject = new JSONObject();

    if (StringUtils.isNotBlank(indexDocument)) {
      jsonObject.put(INDEX_DOCUMENT, indexDocument);
    }

    if (StringUtils.isNotBlank(errorDocument)) {
      jsonObject.put(ERROR_DOCUMENT, errorDocument);
    }

    JSONArray jsonArray = new JSONArray();
    for (RedirectRule redirectRule : redirectRules) {
      jsonArray.put(new JSONObject(redirectRule.toJson()));
    }
    if (jsonArray.length() > 0) {
      jsonObject.put(REDIRECT_RULE, jsonArray);
    }
    return jsonObject.toString();
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

    public static RedirectRule fromJson(String json) throws JSONException {
      JSONObject jsonObject = new JSONObject(json);
      RedirectRule rule = new RedirectRule();

      String keyPrefixEquals = jsonObject.getString(KEY_PREFIX_EQUALS);
      if (StringUtils.isBlank(keyPrefixEquals)) {
        throw new JSONException("KeyPrefixEquals should not be null or empty");
      }
      rule.setKeyPrefixEquals(keyPrefixEquals);

      if (jsonObject.has(REPLACE_KEY_WITH)) {
        rule.setReplaceKeyWith(jsonObject.getString(REPLACE_KEY_WITH));
      }

      if (jsonObject.has(REPLACE_KEY_PREFIX_WITH)) {
        rule.setReplaceKeyPrefixWith(jsonObject.getString(REPLACE_KEY_PREFIX_WITH));
      }

      return rule;
    }

    public String toJson() throws JSONException {
      JSONObject jsonObject = new JSONObject();
      if (StringUtils.isNotBlank(keyPrefixEquals)) {
        jsonObject.put(KEY_PREFIX_EQUALS, keyPrefixEquals);
      } else {
        throw new JSONException("keyPrefixEquals should not be null or empty.");
      }

      if (StringUtils.isNotBlank(replaceKeyPrefixWith)) {
        jsonObject.put(REPLACE_KEY_PREFIX_WITH, replaceKeyPrefixWith);
      }

      if (StringUtils.isNotBlank(replaceKeyWith)) {
        jsonObject.put(REPLACE_KEY_WITH, replaceKeyWith);
      }
      return jsonObject.toString();
    }
  }
}
