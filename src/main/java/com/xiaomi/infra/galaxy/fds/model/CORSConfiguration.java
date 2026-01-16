package com.xiaomi.infra.galaxy.fds.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Here is an example of bucket CORS configuration
 * {
 * "rules":[
 * {
 * "id":"id1"
 * "AllowOrigin":"*.example.com"
 * },
 * {
 * "id":"id2"
 * "AllowOrigin":"*"
 * }
 * ]
 * }
 */

public class CORSConfiguration {
  private static final String RULES = "rules";

  private final Map<String, CORSRule> ruleMap = new HashMap<String, CORSRule>();
  private List<String> allowOriginList = new ArrayList<String>();

  public void setRuleList(Collection<CORSRule> ruleList) {
    this.ruleMap.clear();
    for (CORSRule rule : ruleList) {
      if (StringUtils.isBlank(rule.getId())) {
        rule.setId(genRuleId());
      }
      this.ruleMap.put(rule.getId(), rule);
    }
    for (CORSRule rule : this.ruleMap.values()) {
      this.allowOriginList.add(rule.getAllowedOrigin());
    }
  }

  public Collection<CORSRule> getRuleList() {
    return this.ruleMap.values();
  }

  /**
   * If rule.id exist, just update
   * otherwise a new id will be set
   *
   * @param rule
   */
  public void addOrUpdateRule(CORSRule rule) {
    CORSRule copy = CORSRule.copy(rule);
    if (copy == null) {
      return;
    }

    if (StringUtils.isBlank(copy.getId())) {
      copy.setId(genRuleId());
    }

    if (ruleMap.get(copy.getId()) != null) {
      allowOriginList.remove(ruleMap.get(copy.getId()).getAllowedOrigin());
    }
    allowOriginList.add(copy.getAllowedOrigin());
    this.ruleMap.put(copy.getId(), copy);
  }

  /**
   * If id exist, delete this rule
   *
   * @param id
   */
  public void deleteRule(String id) {
    if (ruleMap.get(id) != null) {
      allowOriginList.remove(ruleMap.get(id).getAllowedOrigin());
    }
    this.ruleMap.remove(id);
  }

  /**
   * Get tule by id, null if id not exists
   *
   * @param id
   * @return
   */
  public CORSRule getRuleById(String id) {
    return ruleMap.get(id);
  }

  /**
   * Convenience method to get all allowOrigins of this cors configuration
   *
   * @return All allowOrigins
   */
  public List<String> getAllowOriginList() {
    return this.allowOriginList;
  }

  public static CORSConfiguration fromJson(String json) throws JSONException {
    JSONObject jsonObject = new JSONObject(json);
    CORSConfiguration corsConfiguration = new CORSConfiguration();
    List<CORSRule> rules = new ArrayList<CORSRule>();
    JSONArray rulesArray = jsonObject.getJSONArray(RULES);

    for (int i = 0; i < rulesArray.length(); i++) {
      rules.add(CORSRule.fromJson(rulesArray.getString(i)));
    }
    corsConfiguration.setRuleList(rules);
    return corsConfiguration;
  }

  public String toJson() throws JSONException {
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    for (CORSRule rule : this.ruleMap.values()) {
      jsonArray.put(new JSONObject(rule.toJson()));
    }
    jsonObject.put(RULES, jsonArray);
    return jsonObject.toString();
  }

  private String genRuleId() {
    int defaultId = 1;
    while (this.ruleMap.containsKey(String.valueOf(defaultId))) {
      defaultId++;
    }
    return String.valueOf(defaultId);
  }

  public static class CORSRule {
    private static final String ID = "id";
    private static final String ALLOWORIGIN = "allowOrigin";

    private String allowedOrigin;
    private String id;

    public CORSRule() {
    }

    public CORSRule(String allowedOrigin) {
      this.allowedOrigin = allowedOrigin;
    }

    public CORSRule(String allowedOrigin, String id) {
      this.allowedOrigin = allowedOrigin;
      this.id = id;
    }

    public String getAllowedOrigin() {
      return allowedOrigin;
    }

    public void setAllowedOrigin(String allowedOrigin) {
      this.allowedOrigin = allowedOrigin;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    /**
     * Copy rule
     *
     * @param rule
     * @return return null if copy failed
     */
    public static CORSRule copy(CORSRule rule) {
      try {
        return fromJson(rule.toJson());
      } catch (JSONException e) {
        return null;
      }
    }

    public static CORSRule fromJson(String json) throws JSONException {
      JSONObject jsonObject = new JSONObject(json);
      CORSRule rule = new CORSRule();
      if (jsonObject.has(ID)) {
        rule.setId(jsonObject.getString(ID));
      } else {
        rule.setId(null);
      }
      if (jsonObject.has(ALLOWORIGIN)) {
        rule.setAllowedOrigin(jsonObject.getString(ALLOWORIGIN));
      } else {
        rule.setAllowedOrigin(null);
      }
      return rule;
    }

    public String toJson() throws JSONException {
      JSONObject jsonObject = new JSONObject();
      if (id != null) {
        jsonObject.put(ID, id);
      }
      if (allowedOrigin != null) {
        jsonObject.put(ALLOWORIGIN, allowedOrigin);
      }

      return jsonObject.toString();
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      CORSRule rule = (CORSRule) o;

      if (id != null ? !id.equals(rule.id) : rule.id != null) {
        return false;
      }
      if (allowedOrigin != null ? !allowedOrigin.equals(rule.allowedOrigin) : rule.allowedOrigin != null) {
        return false;
      }
      return true;
    }
  }
}
