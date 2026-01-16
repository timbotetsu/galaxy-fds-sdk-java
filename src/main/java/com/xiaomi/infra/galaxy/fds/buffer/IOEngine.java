package com.xiaomi.infra.galaxy.fds.buffer;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public interface IOEngine {
  int read(byte[] buf, int offset, int len, long srcOffset);

  void write(byte[] buf, int offset, int len, long dstOffset);
}
