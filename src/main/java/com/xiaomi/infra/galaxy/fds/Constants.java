package com.xiaomi.infra.galaxy.fds;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: zhangjunbin@xiaomi.com
 */
public class Constants {
  public static final long DEFAULT_SPACE_LIMIT = 1 * 1024 * 1024 * 1024;
  public static final String TRASH_BUCKET_NAME = "trash";
  public static final int MD5_BYTES_LENGTH = 16;
  public enum Clusters {
    /* do not change value already assigned */
    staging(0),
    cnbj0(1),
    cnbj1(2),
    tjwqsrv(3), // a little break here, -> cnbj2
    awsbj0(4),
    awsusor0(5),
    awssgp0(6),
    awssgp1(7),
    awsde0(8),
    none(-1);

    int id;
    private Clusters(int id) {
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }
}
