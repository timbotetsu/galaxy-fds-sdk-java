package com.xiaomi.infra.galaxy.fds.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiaomi.infra.galaxy.fds.JacksonUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: zhangjunbin@xiaomi.com
 * Here is an example of bucket lifecycle configuration.
 * {
 * "rules": [
 * {
 * "enabled": true,
 * "prefix": "log",
 * "actions": {
 * "nonCurrentVersionExpiration": {
 * "days": 7
 * },
 * "expiration": {
 * "days":30
 * }
 * }
 * },
 * {
 * "enabled": true,
 * "prefix": "images",
 * "actions": {
 * "nonCurrentVersionExpiration": {
 * "days": 7
 * },
 * "expiration": {
 * "days":30
 * },
 * "abortIncompleteMultipartUpload": {
 * "days":7
 * }
 * }
 * }
 * ]
 * }
 */
public class LifecycleConfig {
  private static final Log LOG = LogFactory.getLog(LifecycleConfig.class);
  public static final long MAX_EXPIRATION_DAYS = 90000L;
  public static final long MAX_EXPIRATION_MILLIS = MAX_EXPIRATION_DAYS * 24 * 60 * 60 * 1000;
  public static final long MAX_NONCURRENT_EXPIRATION_DAYS = 30L;
  public static final long MAX_NONCURRENT_EXPIRATION_MILLIS = MAX_NONCURRENT_EXPIRATION_DAYS * 24 * 60 * 60 * 1000;
  public static final long MAX_MULTIPART_EXPIRATION_DAYS = 30L;
  public static final long MAX_MULTIPART_EXPIRATION_MILLIS = MAX_MULTIPART_EXPIRATION_DAYS * 24 * 60 * 60 * 1000;
  public static final long MAX_STORAGECLASS_EXPIRATION_DAYS = 90000L;
  public static final long MAX_STORAGECLASS_EXPIRATION_MILLIS = MAX_STORAGECLASS_EXPIRATION_DAYS * 24 * 60 * 60 * 1000;

  private static final String RULES = "rules";

  private final Map<String, Rule> ruleMap = new TreeMap<String, Rule>();
  private List<Rule> ruleList = new ArrayList<Rule>();

  // Fields used by convenience methods.
  private Map<String, List<ActionBase>> actionListMap = new HashMap<String, List<ActionBase>>();
  private List<String> prefixList = null;

  public List<Rule> getRuleList() {
    return ruleList;
  }

  public void setRuleList(List<Rule> ruleList) {
    this.ruleMap.clear();
    for (Rule rule : ruleList) {
      if (StringUtils.isBlank(rule.getId())) {
        rule.setId(genRuleId());
      }
      this.ruleMap.put(rule.getId(), rule);
    }
    this.ruleList = new ArrayList<Rule>(this.ruleMap.values());
    this.prefixList = null;
    this.actionListMap.clear();
  }

  private String genRuleId() {
    int defaultId = 1;
    while (this.ruleMap.containsKey(String.valueOf(defaultId))) {
      defaultId++;
    }
    return String.valueOf(defaultId);
  }

  /**
   * If rule.id exist, just update
   * otherwise a new id will be set
   *
   * @param rule
   */
  public void addOrUpdateRule(Rule rule) {
    Rule copy = Rule.copy(rule);
    if (copy == null) {
      return;
    }

    if (StringUtils.isBlank(copy.getId())) {
      copy.setId(genRuleId());
    }
    this.ruleMap.put(copy.getId(), copy);
    this.ruleList = new ArrayList<Rule>(this.ruleMap.values());
    this.prefixList = null;
    this.actionListMap.clear();
  }

  /**
   * Get tule by id, null if id not exists
   *
   * @param id
   * @return
   */
  public Rule getRuleById(String id) {
    return ruleMap.get(id);
  }

  /**
   * Convenience method to get all actions of a rule.
   *
   * @param prefix Prefix to identify the rule.
   * @return All actions in the rule identified prefix.
   */
  public List<ActionBase> getActionList(String prefix) {
    List<ActionBase> res = this.actionListMap.get(prefix);
    if (res != null) {
      return res;
    }

    for (Rule rule : ruleList) {
      if (rule.getPrefix().equals(prefix)) {
        res = new ArrayList<ActionBase>();
        for (ActionBase action : rule.getActions()) {
          res.add(action);
        }
        actionListMap.put(prefix, res);
      }
    }
    return res;
  }

  /**
   * Convenience method to get all prefixes of this lifecycle configuration
   *
   * @return All prefixes sorted by prefix length, longer first.
   */
  public List<String> getPrefixList() {
    if (this.prefixList != null) {
      return this.prefixList;
    }

    this.prefixList = new ArrayList<String>();
    for (Rule rule : ruleList) {
      this.prefixList.add(rule.getPrefix());
    }
    Collections.sort(prefixList, new Comparator<String>() {
      @Override public int compare(String o1, String o2) {
        return o2.length() - o1.length();
      }
    });
    return this.prefixList;
  }

  public void removeDisabledRules() {
    List<Rule> tmpRuleList = new ArrayList<Rule>();
    for (Rule rule : ruleList) {
      if (rule.isEnabled()) {
        tmpRuleList.add(rule);
      }
    }
    ruleList = tmpRuleList;
  }

  public static LifecycleConfig fromJson(String json) {
    return fromJson(json, false);
  }

  public static LifecycleConfig fromJson(String json, boolean adjustParams) {
    JsonMapper mapper = JacksonUtils.mapper();
    ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
    LifecycleConfig lifecycleConfig = new LifecycleConfig();
    List<Rule> rules = new ArrayList<>();
    ArrayNode arrayNode = objectNode.withArray(RULES);

    for (int i = 0; i < arrayNode.size(); i++) {
      rules.add(Rule.fromJson(arrayNode.get(i).asString(), adjustParams));
    }
    lifecycleConfig.setRuleList(rules);
    return lifecycleConfig;
  }

  public String toJson() {
    JsonMapper mapper = JacksonUtils.mapper();
    ObjectNode objectNode = mapper.createObjectNode();
    ArrayNode arrayNode = mapper.createArrayNode();
    if (ruleList != null) {
      for (Rule rule : ruleList) {
        arrayNode.add(rule.toJson());
      }
    }
    objectNode.set(RULES, arrayNode);
    return objectNode.toString();
  }

  public static class Rule {
    private static final String ID = "id";
    private static final String PREFIX = "prefix";
    private static final String ENABLED = "enabled";
    private static final String ACTIONS = "actions";

    private String id;
    private String prefix;
    private boolean enabled;
    private List<ActionBase> actions;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getPrefix() {
      return prefix;
    }

    public void setPrefix(String prefix) {
      this.prefix = prefix;
    }

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    public List<ActionBase> getActions() {
      return actions;
    }

    public void setActions(List<ActionBase> actions) {
      this.actions = actions;
    }

    /**
     * Copy rule
     *
     * @param rule
     * @return return null if copy failed
     */
    public static Rule copy(Rule rule) {
      return fromJson(rule.toJson());
    }

    public static Rule fromJson(String json) {
      return fromJson(json, false);
    }

    public static Rule fromJson(String json, boolean adjustParams) {
      JsonMapper mapper = JacksonUtils.mapper();
      ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
      Rule rule = new Rule();
      if (objectNode.has(ID)) {
        rule.setId(objectNode.get(ID).asString());
      } else {
        rule.setId("");
      }
      rule.setPrefix(objectNode.get(PREFIX).asString());
      rule.setEnabled(objectNode.get(ENABLED).asBoolean());
      List<ActionBase> actions = new ArrayList<>();
      JsonNode jsonNode = objectNode.get(ACTIONS);

      Iterator<?> keys = jsonNode.iterator();
      while (keys.hasNext()) {
        String key = (String) keys.next();
        String value = jsonNode.get(key).asString();
        if (key.equalsIgnoreCase(Expiration.NAME)) {
          actions.add(Expiration.fromJson(value, adjustParams));
        } else if (key.equalsIgnoreCase(NonCurrentVersionExpiration.NAME)) {
          actions.add(NonCurrentVersionExpiration.fromJson(value, adjustParams));
        } else if (key.equalsIgnoreCase(AbortIncompleteMultipartUpload.NAME)) {
          actions.add(AbortIncompleteMultipartUpload.fromJson(value, adjustParams));
        } else if (key.equalsIgnoreCase(LifecycleStorageClass.NAME)) {
          actions.add(LifecycleStorageClass.fromJson(value, adjustParams));
        } else {
          throw new RuntimeException("Unrecognized action: " + key);
        }
      }
      rule.setActions(actions);
      return rule;
    }

    public String toJson() {
      JsonMapper mapper = JacksonUtils.mapper();
      ObjectNode objectNode = mapper.createObjectNode();
      if (id != null) {
        objectNode.put(ID, this.id);
      }
      if (prefix != null) {
        objectNode.put(PREFIX, this.prefix);
      }
      objectNode.put(ENABLED, this.enabled);

      if (actions != null) {
        ObjectNode actionObjectNode = mapper.createObjectNode();
        for (ActionBase actionBase : actions) {
          actionObjectNode.put(actionBase.name(), actionBase.toJson());
        }
        objectNode.set(ACTIONS, actionObjectNode);
      }

      return objectNode.toString();
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Rule rule = (Rule) o;

      if (id != null && !id.equals(rule.id)) {
        return false;
      }
      if (id == null && rule.id != null) {
        return false;
      }
      if (enabled != rule.enabled) {
        return false;
      }
      if (prefix != null ? !prefix.equals(rule.prefix) : rule.prefix != null) {
        return false;
      }
      return isCollectionEqual(actions, rule.actions);
    }
  }

  @Override public int hashCode() {
    int result = 0;
    result = 31 * result + (ruleList != null ? ruleList.hashCode() : 0);
    result = 31 * result + (actionListMap != null ? actionListMap.hashCode() : 0);
    result = 31 * result + (prefixList != null ? prefixList.hashCode() : 0);
    return result;
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LifecycleConfig that = (LifecycleConfig) o;

    return isCollectionEqual(ruleList, that.ruleList);
  }

  public abstract static class ActionBase {
    protected static final JsonMapper JSON_MAPPER = JacksonUtils.mapper();

    protected String name;

    ActionBase(String name) {
      this.name = name;
    }

    public String name() {
      return this.name;
    }

    public abstract String toJson();
  }

  public static class Expiration extends ActionBase {
    @JsonIgnore private static final String NAME = "expiration";

    public double days;

    public Expiration() {
      super(NAME);
    }

    public long getMilliseconds() {
      return (long) (days * 24 * 60 * 60 * 1000);
    }

    public static Expiration fromJson(String json) {
      return fromJson(json, false);
    }

    public static Expiration fromJson(String json, boolean adjustParams) {
      Expiration ret = JSON_MAPPER.readValue(json, Expiration.class);
      if (adjustParams) {
        ret.days = Math.max(0, ret.days);
        ret.days = Math.min(MAX_EXPIRATION_DAYS, ret.days);
      }

      if (ret.days < 0 || ret.days > MAX_EXPIRATION_DAYS) {
        throw new IllegalArgumentException("object expiration days must be in range [0," + MAX_EXPIRATION_DAYS + "]");
      }
      return ret;
    }

    @Override public String toJson() {
      return JSON_MAPPER.writeValueAsString(this);
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Expiration that = (Expiration) o;
      if (name != null ? !name.equals(that.name) : that.name != null) {
        return false;
      }

      return getMilliseconds() == that.getMilliseconds();
    }
  }

  public static class NonCurrentVersionExpiration extends ActionBase {
    @JsonIgnore private static final String NAME = "nonCurrentVersionExpiration";

    public double days;

    public NonCurrentVersionExpiration() {
      super(NAME);
    }

    public long getMilliseconds() {
      return (long) (days * 24 * 60 * 60 * 1000);
    }

    public static NonCurrentVersionExpiration fromJson(String json) {
      return fromJson(json, false);
    }

    public static NonCurrentVersionExpiration fromJson(String json, boolean adjustParams) {
      NonCurrentVersionExpiration ret = JSON_MAPPER.readValue(json, NonCurrentVersionExpiration.class);
      if (adjustParams) {
        ret.days = Math.max(0, ret.days);
        ret.days = Math.min(MAX_NONCURRENT_EXPIRATION_DAYS, ret.days);
      }

      if (ret.days < 0 || ret.days > MAX_NONCURRENT_EXPIRATION_DAYS) {
        throw new IllegalArgumentException("non-current object expiration days must be in range [0," + MAX_NONCURRENT_EXPIRATION_DAYS + "]");
      }
      return ret;
    }

    @Override public String toJson() {
      return JSON_MAPPER.writeValueAsString(this);
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      NonCurrentVersionExpiration that = (NonCurrentVersionExpiration) o;
      if (name != null ? !name.equals(that.name) : that.name != null) {
        return false;
      }

      return getMilliseconds() == that.getMilliseconds();
    }
  }

  public static class AbortIncompleteMultipartUpload extends ActionBase {
    @JsonIgnore private static final String NAME = "abortIncompleteMultipartUpload";

    public double days;

    public AbortIncompleteMultipartUpload() {
      super(NAME);
    }

    public long getMilliseconds() {
      return (long) (days * 24 * 60 * 60 * 1000);
    }

    public static AbortIncompleteMultipartUpload fromJson(String json) {
      return fromJson(json, false);
    }

    public static AbortIncompleteMultipartUpload fromJson(String json, boolean adjustParams) {
      AbortIncompleteMultipartUpload ret = JSON_MAPPER.readValue(json, AbortIncompleteMultipartUpload.class);

      if (adjustParams) {
        ret.days = Math.max(0, ret.days);
        ret.days = Math.min(MAX_MULTIPART_EXPIRATION_DAYS, ret.days);
      }

      if (ret.days < 0 || ret.days > MAX_MULTIPART_EXPIRATION_DAYS) {
        throw new IllegalArgumentException("non-current object expiration days must be in range [0," + MAX_MULTIPART_EXPIRATION_DAYS + "]");
      }
      return ret;
    }

    @Override public String toJson() {
      return JSON_MAPPER.writeValueAsString(this);
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      AbortIncompleteMultipartUpload that = (AbortIncompleteMultipartUpload) o;
      if (name != null ? !name.equals(that.name) : that.name != null) {
        return false;
      }

      return getMilliseconds() == that.getMilliseconds();
    }
  }

  public static class LifecycleStorageClass extends ActionBase {
    @JsonIgnore public static final String NAME = "lifeCycleStorageClass";

    public double days;

    public String storageClass;

    public String targetBucket;

    public LifecycleStorageClass() {
      super(NAME);
    }

    public long getMilliseconds() {
      return (long) (days * 24 * 60 * 60 * 1000);
    }

    public String getStorageClass() {
      return storageClass;
    }

    public StorageClass getStorage() {
      return StringUtils.isBlank(storageClass) ? null : StorageClass.fromValue(storageClass);
    }

    public static LifecycleStorageClass fromJson(String json) {
      return fromJson(json, false);
    }

    public static LifecycleStorageClass fromJson(String json, boolean adjustParams) {
      LifecycleStorageClass ret = JSON_MAPPER.readValue(json, LifecycleStorageClass.class);

      if (adjustParams) {
        ret.days = Math.max(0, ret.days);
        ret.days = Math.min(MAX_STORAGECLASS_EXPIRATION_DAYS, ret.days);
      }

      if (ret.days < 0 || ret.days > MAX_STORAGECLASS_EXPIRATION_DAYS) {
        throw new IllegalArgumentException("StorageClass expiration days must be in range [0," + MAX_STORAGECLASS_EXPIRATION_DAYS + "]");
      }
      return ret;
    }

    @Override public String toJson() {
      return JSON_MAPPER.writeValueAsString(this);
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      LifecycleStorageClass that = (LifecycleStorageClass) o;
      if (name != null ? !name.equals(that.name) : that.name != null) {
        return false;
      }
      if (storageClass == null) {
        return getMilliseconds() == that.getMilliseconds() && that.getStorageClass() == null;
      } else {
        return getMilliseconds() == that.getMilliseconds() && storageClass.equals(that.getStorageClass());
      }

    }
  }

  /**
   * Compare two collections equality
   * treat collection as set and compare because a rule/action appears more than one time makes no sense
   *
   * @param c1
   * @param c2
   * @param <T>
   * @return
   */
  public static <T> boolean isCollectionEqual(Collection<T> c1, Collection<T> c2) {
    if (c1 == null || c2 == null) {
      return c1 == c2;
    }

    return c1.containsAll(c2) && c2.containsAll(c1);
  }

  /**
   * Get object expiration time in milliseconds
   *
   * @param objectName
   * @return MAX_EXPIRATION_MILLIS if not matched
   */
  public long getExpiration(String objectName) {
    long ret = MAX_EXPIRATION_MILLIS;

    if (StringUtils.isBlank(objectName)) {
      return ret;
    }

    Expiration action = getMatchedAction(objectName, Expiration.class);
    if (action != null) {
      ret = action.getMilliseconds();
    }
    return ret;
  }

  /**
   * Get non-current version expiration time in milliseconds
   *
   * @param objectName
   * @return null if not matched
   */
  public Long getNonCurrentVersionExpiration(String objectName) {
    if (StringUtils.isBlank(objectName)) {
      return null;
    }

    NonCurrentVersionExpiration action = getMatchedAction(objectName, NonCurrentVersionExpiration.class);
    if (action != null) {
      return Math.min(action.getMilliseconds(), MAX_NONCURRENT_EXPIRATION_MILLIS);
    }
    return null;
  }

  /**
   * Get multipart-upload task expiration time in milliseconds
   *
   * @param objectName
   * @return null if not matched
   */
  public Long getIncompleteMultipartUploadExpiration(String objectName) {
    if (StringUtils.isBlank(objectName)) {
      return null;
    }

    AbortIncompleteMultipartUpload action = getMatchedAction(objectName, AbortIncompleteMultipartUpload.class);
    if (action != null) {
      return Math.min(action.getMilliseconds(), MAX_MULTIPART_EXPIRATION_MILLIS);
    }
    return null;
  }

  /**
   * Get matched action of specified object name
   * If no action is matched, return null
   * If more than one action is matched, return the one has longest prefix
   *
   * @param objectName
   * @param cls
   * @param <T>
   * @return
   */
  public <T extends ActionBase> T getMatchedAction(String objectName, Class<T> cls) {
    if (StringUtils.isBlank(objectName)) {
      return null;
    }

    int matchedPrefixLength = -1;
    T matched = null;
    for (Rule rule : ruleList) {
      if (!rule.isEnabled() || !objectName.startsWith(rule.getPrefix())) {
        continue;
      }
      if (rule.getPrefix().length() < matchedPrefixLength) {
        continue;
      }
      for (ActionBase action : rule.getActions()) {
        if (cls.isInstance(action)) {
          matchedPrefixLength = rule.getPrefix().length();
          matched = (T) action;
          break;
        }
      }
    }
    return matched;
  }
}
