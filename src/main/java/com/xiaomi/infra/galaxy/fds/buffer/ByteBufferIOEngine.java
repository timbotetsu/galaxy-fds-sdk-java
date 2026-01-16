package com.xiaomi.infra.galaxy.fds.buffer;

/**
 * Copyright 2015, Xiaomi.
 * All rights reserved.
 * Author: linshangquan@xiaomi.com
 */
public class ByteBufferIOEngine implements IOEngine {

  private ByteBufferArray bufferArray;
  private final long capacity;
  private final boolean direct;

  public ByteBufferIOEngine(long capacity, boolean direct) {
    this.capacity = capacity;
    this.direct = direct;
    this.bufferArray = new ByteBufferArray(capacity, direct);
  }

  @Override public String toString() {
    return "ByteBufferIOEngine{" +
        ", capacity=" + capacity +
        ", direct=" + direct +
        '}';
  }

  @Override
  public int read(byte[] buf, int offset, int len, long srcOffset) {
    if (buf == null) {
      throw new NullPointerException();
    } else if ((offset < 0) || (offset > buf.length) || (len < 0) ||
        ((offset + len) > buf.length) || ((offset + len) < 0)) {
      throw new IndexOutOfBoundsException();
    }
    return bufferArray.getMultiple(srcOffset, len, buf, offset);
  }

  @Override
  public void write(byte[] buf, int offset, int len, long dstOffset) {
    if (buf == null) {
      throw new NullPointerException();
    } else if ((offset < 0) || (offset > buf.length) || (len < 0) ||
        ((offset + len) > buf.length) || ((offset + len) < 0)) {
      throw new IndexOutOfBoundsException();
    }
    bufferArray.putMultiple(dstOffset, len, buf, offset);
  }
}
