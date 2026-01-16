package com.xiaomi.infra.galaxy.fds.acl;

import com.xiaomi.infra.galaxy.fds.model.AccessControlList.Grant;
import com.xiaomi.infra.galaxy.fds.model.AccessControlList.GrantType;
import com.xiaomi.infra.galaxy.fds.model.AccessControlList.Permission;
import com.xiaomi.infra.galaxy.fds.model.AccessControlList.UserGroups;
import java.util.ArrayList;
import java.util.List;

public enum CannedAcl {
  // Set of predefined ACLs
  PUBLIC_READ(UserGroups.ALL_USERS, Permission.READ),
  PUBLIC_WRITE(UserGroups.ALL_USERS, Permission.WRITE),
  PUBLIC_READ_OBJECTS(UserGroups.ALL_USERS, Permission.READ_OBJECTS),
  PUBLIC_SSO_WRITE(UserGroups.ALL_USERS, Permission.SSO_WRITE),
  AUTHENTICATED_READ(UserGroups.AUTHENTICATED_USERS, Permission.READ),
  AUTHENTICATED_WRITE(UserGroups.AUTHENTICATED_USERS, Permission.WRITE),
  AUTHENTICATED_READ_OBJECTS(UserGroups.AUTHENTICATED_USERS, Permission.READ_OBJECTS),
  AUTHENTICATED_SSO_WRITE(UserGroups.AUTHENTICATED_USERS, Permission.SSO_WRITE);

  private final UserGroups group;
  private final Permission permission;

  private CannedAcl(UserGroups group, Permission permission) {
    this.group = group;
    this.permission = permission;
  }

  public Grant getGrant() {
    return new Grant(group.name(), permission, GrantType.GROUP);
  }

  public static List<Grant> parseFromString(String acls) {
    String[] cannedAcls = acls.split(",");
    List<Grant> grants = new ArrayList<Grant>(cannedAcls.length);
    for (String a : cannedAcls) {
      CannedAcl acl = CannedAcl.valueOf(a.trim());
      grants.add(acl.getGrant());
    }
    return  grants;
  }

  public static List<Grant> parseFromAmzString(String acl, CannedAclType cannedAclType) {
    List<Grant> grants = new ArrayList<Grant>(1);

    if (acl.equalsIgnoreCase("public-read")) {
      grants.add(PUBLIC_READ.getGrant());
    }

    if (cannedAclType == CannedAclType.Bucket
        && acl.equalsIgnoreCase("public-read-write")) {
      grants.add(PUBLIC_WRITE.getGrant());
    }

    return grants;
  }
}
