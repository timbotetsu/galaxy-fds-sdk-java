package com.xiaomi.infra.galaxy.fds.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Checksum;

/**
 * CRC-64 implementation with ability to combine checksums calculated over
 * different blocks of data.
 */
public class CRC64 implements Checksum {

  private final static long POLY = (long) 0xc96c5795d7870f42L; // ECMA-182
  /* CRC64 calculation table. */
  private final static long[][] table;

  /* Current CRC value. */
  private long value;

  static {
    table = new long[8][256];

    for (int n = 0; n < 256; n++) {
      long crc = n;
      for (int k = 0; k < 8; k++) {
        if ((crc & 1) == 1) {
          crc = (crc >>> 1) ^ POLY;
        } else {
          crc = (crc >>> 1);
        }
      }
      table[0][n] = crc;
    }

    for (int n = 0; n < 256; n++) {
      long crc = table[0][n];
      for (int k = 1; k < 8; k++) {
        crc = table[0][(int) (crc & 0xff)] ^ (crc >>> 8);
        table[k][n] = crc;
      }
    }
  }

  public CRC64() {
    this.value = 0;
  }

  public CRC64(long value) {
    this.value = value;
  }

  @Override
  public void update(int b) {
    this.update((byte) (b & 255));
  }

  public void update(byte b) {
    this.value = ~this.value;
    this.value = table[0][(int) (this.value ^ (long) b) & 255] ^ this.value >>> 8;
    this.value = ~this.value;
  }

  /**
   * Update CRC64 with new byte block.
   */
  @Override
  public void update(byte[] b, int off, int len) {
    this.value = ~this.value;

    int idx = off;
    while (len >= 8) {
      value = table[7][(int) (value & 0xff ^ (b[idx] & 0xff))]
        ^ table[6][(int) ((value >>> 8) & 0xff ^ (b[idx + 1] & 0xff))]
        ^ table[5][(int) ((value >>> 16) & 0xff ^ (b[idx + 2] & 0xff))]
        ^ table[4][(int) ((value >>> 24) & 0xff ^ (b[idx + 3] & 0xff))]
        ^ table[3][(int) ((value >>> 32) & 0xff ^ (b[idx + 4] & 0xff))]
        ^ table[2][(int) ((value >>> 40) & 0xff ^ (b[idx + 5] & 0xff))]
        ^ table[1][(int) ((value >>> 48) & 0xff ^ (b[idx + 6] & 0xff))]
        ^ table[0][(int) ((value >>> 56) ^ b[idx + 7] & 0xff)];
      idx += 8;
      len -= 8;
    }

    while (len > 0) {
      value = table[0][(int) ((this.value ^ b[idx]) & 0xff)] ^ (this.value >>> 8);
      idx++;
      len--;
    }

    this.value = ~this.value;
  }

  /**
   * Get long representation of current CRC64 value.
   */
  @Override
  public long getValue() {
    return this.value;
  }

  /**
   * Get 8 byte representation of current CRC64 value.
   */
  public byte[] getBytes() {
    byte[] b = new byte[8];
    for (int i = 0; i < 8; i++) {
      b[7 - i] = (byte) (this.value >>> (i * 8));
    }
    return b;
  }

  @Override
  public void reset() {
    this.value = 0L;
  }

  /**
   * Calculate the CRC64 of the given file's content.
   */
  public static CRC64 fromFile(File f) throws IOException {
    return fromInputStream(new FileInputStream(f));
  }

  /**
   * Calculate the CRC64 of the given {@link InputStream} until the end of the
   * stream has been reached.
   */
  public static CRC64 fromInputStream(InputStream in) throws IOException {
    try {
      CRC64 crc = new CRC64();
      byte[] b = new byte[65536];   //64K
      int l = 0;

      while ((l = in.read(b)) != -1) {
        crc.update(b, 0, l);
      }

      return crc;

    } finally {
      in.close();
    }
  }


  // dimension of GF(2) vectors (length of CRC)
  private static final int GF2_DIM = 64;

  private static long gf2MatrixTimes(long[] mat, long vec) {
    long sum = 0;
    int idx = 0;
    while (vec != 0) {
      if ((vec & 1) == 1) {
        sum ^= mat[idx];
      }
      vec >>>= 1;
      idx++;
    }
    return sum;
  }

  private static void gf2MatrixSquare(long[] square, long[] mat) {
    for (int n = 0; n < GF2_DIM; n++) {
      square[n] = gf2MatrixTimes(mat, mat[n]);
    }
  }

  /*
   * Return the CRC-64 of two sequential blocks, where summ1 is the CRC-64 of
   * the first block, summ2 is the CRC-64 of the second block, and len2 is the
   * length of the second block.
   */
  static public CRC64 combine(CRC64 summ1, CRC64 summ2, long len2) {
    if (len2 == 0) {
      return new CRC64(summ1.getValue());
    }

    long crc1 = summ1.getValue();
    long crc2 = summ2.getValue();

    return new CRC64(combine(crc1, crc2, len2));
  }

  /*
   * Return the CRC-64 of two sequential blocks, where crc1 is the CRC-64 of
   * the first block, crc2 is the CRC-64 of the second block, and len2 is the
   * length of the second block.
   */
  public static long combine(long crc1, long crc2, long len2) {
    if (len2 == 0L) {
      return crc1;
    } else {
      long[] even = new long[64];
      long[] odd = new long[64];
      odd[0] = -3932672073523589310L;
      long row = 1L;

      for (int n = 1; n < GF2_DIM; ++n) {
        odd[n] = row;
        row <<= 1;
      }

      gf2MatrixSquare(even, odd);
      gf2MatrixSquare(odd, even);

      do {
        gf2MatrixSquare(even, odd);
        if ((len2 & 1L) == 1L) {
          crc1 = gf2MatrixTimes(even, crc1);
        }

        len2 >>>= 1;
        if (len2 == 0L) {
          break;
        }

        gf2MatrixSquare(odd, even);
        if ((len2 & 1L) == 1L) {
          crc1 = gf2MatrixTimes(odd, crc1);
        }

        len2 >>>= 1;
      } while (len2 != 0L);

      crc1 ^= crc2;
      return crc1;
    }
  }
}
