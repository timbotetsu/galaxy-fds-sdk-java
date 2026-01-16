package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

public class InvalidRangeParameterException extends GalaxyFDSException {
  private final long[] range;

  public InvalidRangeParameterException(long[] range) {
    super(rangeToStr(range), null);
    this.range = range;
  }

  @Override
  public FDSError getError() {
    return FDSError.InvalidRequestRange;
  }

  static String rangeToStr(long[] range) {
    if (range == null) {
      return "range parameter is null";
    } else if (range.length == 1) {
      return "[ " + range[0] + "]";
    } else if (range.length == 2) {
      return "[" + range[0] + ", " + range[1] + "]";
    } else {
      return "[" + range[0] + " " + range[1] + " " +  range[2] + "...]";
    }
  }

  @Override
  public String toString() {
    String str = super.toString();
    if (range != null) {
      str += rangeToStr(this.range) + " in request";
    }
    return str;
  }
}
