package com.xiaomi.infra.galaxy.fds.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AccessControlList {

  public enum Permission {
    // The READ permission: when applied to buckets, it means
    // allow the grantee to list the objects in the bucket; when
    // it applies to objects it means allow the grantee to read
    // the object data and metadata.
    READ(0x01),

    // The WRITE permission: when applied to buckets, it means
    // allow the grantee to create, overwrite and delete any
    // object in the bucket; it is not applicable for objects.
    WRITE(0x02),

    // The READ_OBJECTS permission: when applied to bucket, it means
    // allow the grantee to read any object in the bucket;
    // it is not applicable to object.
    READ_OBJECTS(0x04),

    // The SSO_WRITE permission: when applied to bucket, it means
    // users can put objects to the bucket with SSO auth
    // it is not applicable to object.
    SSO_WRITE(0x08),

    // The FULL_CONTROL permission: allows the grantee the READ
    // and WRITE permission on the bucket/object.
    FULL_CONTROL(0xff);

    private final int value;

    private Permission(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }

  }

  // Predefined user groups
  public enum UserGroups {
    ALL_USERS, AUTHENTICATED_USERS
  }

  public enum GrantType {
    USER, GROUP
  }

  public static class GrantKey implements Comparable<GrantKey> {
    protected final String granteeId;
    protected final GrantType type;

    public GrantKey(String granteeId, GrantType grantType) {
      this.granteeId = granteeId;
      this.type = grantType;
    }

    @Override public int compareTo(GrantKey o) {
      int ret = granteeId.compareTo(o.granteeId);
      if (ret == 0) {
        return type.compareTo(o.type);
      }
      return ret;
    }

    @Override public String toString() {
      return granteeId + ":" + type.name();
    }

    public static GrantKey fromString(String keyStr) {
      int idx = keyStr.lastIndexOf(":");
      if (idx < 0) {
        throw new IllegalArgumentException("Invalid key: " + keyStr);
      }
      return new GrantKey(keyStr.substring(0, idx), GrantType.valueOf(keyStr.substring(idx + 1)));
    }

    @Override public int hashCode() {
      return (granteeId.hashCode() ^ type.hashCode()) + granteeId.length();
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof GrantKey)) {
        return false;
      }
      final GrantKey oGrantKey = (GrantKey) o;
      return this.granteeId.equals(oGrantKey.granteeId) && this.type.equals(oGrantKey.type);
    }
  }

  public static class Grant {

    private String granteeId;
    private Permission permission;
    private GrantType type;

    public Grant(GrantKey grantKey, Permission permission) {
      this(grantKey.granteeId, permission, grantKey.type);
    }

    public Grant(String granteeId, Permission permission) {
      this(granteeId, permission, GrantType.USER);
    }

    public Grant(String granteeId, Permission permission, GrantType type) {
      this.granteeId = granteeId;
      this.permission = permission;
      this.type = type;
    }

    public String getGranteeId() {
      return granteeId;
    }

    public void setGranteeId(String granteeId) {
      this.granteeId = granteeId;
    }

    public Permission getPermission() {
      return permission;
    }

    public void setPermission(Permission permission) {
      this.permission = permission;
    }

    public GrantType getType() {
      return type;
    }

    public void setType(GrantType type) {
      this.type = type;
    }

    @Override public String toString() {
      return getGrantKey().toString() + ":" + permission.name();
    }

    public static Grant fromString(String string) {
      int idx = string.lastIndexOf(":");
      if (idx < 0) {
        throw new IllegalArgumentException("Invalid key: " + string);
      }
      String keyStr = string.substring(0, idx);
      String permissionStr = string.substring(idx + 1);
      return new Grant(GrantKey.fromString(keyStr), Permission.valueOf(permissionStr));
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Grant grant = (Grant) o;

      if (granteeId != null ? !granteeId.equals(grant.granteeId) : grant.granteeId != null) {
        return false;
      }

      if (permission != grant.permission) {
        return false;
      }

      if (type != grant.type) {
        return false;
      }

      return true;
    }

    @Override public int hashCode() {
      int result = granteeId != null ? granteeId.hashCode() : 0;
      result = 31 * result + (permission != null ? permission.hashCode() : 0);
      result = 31 * result + (type != null ? type.hashCode() : 0);
      return result;
    }

    protected GrantKey getGrantKey() {
      return new GrantKey(granteeId, type);
    }
  }

  private final Map<GrantKey, Integer> acls = new HashMap<GrantKey, Integer>();
  private String ownerId;

  public void addGrant(Grant grant) {
    GrantKey key = grant.getGrantKey();
    Integer perm = acls.get(key);

    if (perm == null) {
      acls.put(key, grant.getPermission().getValue());
    } else {
      perm = perm.intValue() | grant.getPermission().getValue();
      acls.put(key, perm);
    }
  }

  public void addGrants(List<Grant> grants) {
    for (Grant grant : grants) {
      addGrant(grant);
    }
  }

  public boolean checkPermission(String grantee, GrantType type, Permission permission) {
    Integer perm = acls.get(new GrantKey(grantee, type));
    if (perm != null) {
      return (permission.getValue() & perm.intValue()) == permission.getValue();
    }
    return false;
  }

  public boolean checkUserReadPermission(String grantee) {
    return checkPermission(grantee, GrantType.USER, Permission.READ);
  }

  public boolean checkUserWritePermission(String grantee) {
    return checkPermission(grantee, GrantType.USER, Permission.WRITE);
  }

  public boolean checkGroupReadPermission(String grantee) {
    return checkPermission(grantee, GrantType.GROUP, Permission.READ);
  }

  public boolean checkGroupReadObjectsPermission(String grantee) {
    return checkPermission(grantee, GrantType.GROUP, Permission.READ_OBJECTS);
  }

  public boolean checkGroupWritePermission(String grantee) {
    return checkPermission(grantee, GrantType.GROUP, Permission.WRITE);
  }

  public List<Grant> getGrantList() {
    List<Grant> grants = new LinkedList<Grant>();
    for (Map.Entry<GrantKey, Integer> entry : acls.entrySet()) {
      GrantKey grantKey = entry.getKey();
      if (entry.getValue().intValue() == Permission.FULL_CONTROL.getValue()) {
        grants.add(new Grant(grantKey, Permission.FULL_CONTROL));
      } else {
        for (Permission p : Permission.values()) {
          if (p.getValue() != Permission.FULL_CONTROL.getValue() && (p.getValue() & entry.getValue().intValue()) > 0) {
            grants.add(new Grant(grantKey, p));
          }
        }
      }
    }
    return grants;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }
}
