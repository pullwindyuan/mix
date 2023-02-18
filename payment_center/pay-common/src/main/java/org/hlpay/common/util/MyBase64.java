 package org.hlpay.common.util;







 public class MyBase64
 {
   public static final String encode(byte[] src, int startIndex, int srclen) {
     if (src == null) return null;
     if (startIndex + srclen > src.length) srclen = src.length - startIndex;
     byte[] src2 = new byte[srclen];
     System.arraycopy(src, startIndex, src2, 0, srclen);
     return encode(src2);
   }

   public static final String encode(byte[] src, int srclen) {
     if (src == null) return null;
     if (srclen > src.length) srclen = src.length;
     byte[] data = new byte[srclen + 2];
     System.arraycopy(src, 0, data, 0, srclen);
     byte[] dest = new byte[data.length / 3 * 4];

     for (int sidx = 0, didx = 0; sidx < srclen; sidx += 3, didx += 4) {
       dest[didx] = (byte)(data[sidx] >>> 2 & 0x3F);
       dest[didx + 1] = (byte)(data[sidx + 1] >>> 4 & 0xF | data[sidx] << 4 & 0x3F);
       dest[didx + 2] = (byte)(data[sidx + 2] >>> 6 & 0x3 | data[sidx + 1] << 2 & 0x3F);
       dest[didx + 3] = (byte)(data[sidx + 2] & 0x3F);
     }

     int idx;
     for (idx = 0; idx < dest.length; idx++) {
       if (dest[idx] < 26) { dest[idx] = (byte)(dest[idx] + 65); }
       else if (dest[idx] < 52) { dest[idx] = (byte)(dest[idx] + 97 - 26); }
       else if (dest[idx] < 62) { dest[idx] = (byte)(dest[idx] + 48 - 52); }
       else if (dest[idx] < 63) { dest[idx] = 43; }
       else { dest[idx] = 47; }

     }

     for (idx = dest.length - 1; idx > srclen * 4 / 3; idx--) {
       dest[idx] = 61;
     }
     return new String(dest);
   }

   public static final String encode(byte[] d) {
     return encode(d, d.length);
   }

   public static String encode_64(byte[] bin) throws Exception {
     String b64 = encode(bin);
     StringBuffer sb = new StringBuffer();
     for (int offset = 0; offset < b64.length(); offset += 64) {
       int idx_begin = offset;
       int idx_end = Math.min(offset + 64, b64.length());
       String s = b64.substring(idx_begin, idx_end);
       sb.append(s).append('\n');
     }

     b64 = sb.toString();
     return b64;
   }

   public static final byte[] decode(String str) {
     if (str == null) return new byte[0];
     str = str.trim();
     if (str.length() == 0) return new byte[0];
     return decode(str.getBytes());
   }
   private static final byte[] decode(byte[] data) {
     int tail = data.length;
     for (; data[tail - 1] == 61; tail--);
     byte[] dest = new byte[tail - data.length / 4];
     for (int i = 0; i < data.length; i++) {
       data[i] = decode_pre_byte(data[i]);
     }

     int sidx, didx;
     for (sidx = 0, didx = 0; didx < dest.length - 2; sidx += 4, didx += 3) {
       dest[didx] = (byte)(data[sidx] << 2 & 0xFF | data[sidx + 1] >>> 4 & 0x3);
       dest[didx + 1] = (byte)(data[sidx + 1] << 4 & 0xFF | data[sidx + 2] >>> 2 & 0xF);
       dest[didx + 2] = (byte)(data[sidx + 2] << 6 & 0xFF | data[sidx + 3] & 0x3F);
     }
     if (didx < dest.length) {
       dest[didx] = (byte)(data[sidx] << 2 & 0xFF | data[sidx + 1] >>> 4 & 0x3);
     }
     if (++didx < dest.length) {
       dest[didx] = (byte)(data[sidx + 1] << 4 & 0xFF | data[sidx + 2] >>> 2 & 0xF);
     }
     return dest;
   }
   private static final byte decode_pre_byte(byte b0) {
     byte b = 0;
     if (b0 >= 48 && b0 <= 57) { b = (byte)(b0 - -4); }
     else if (b0 >= 97 && b0 <= 122) { b = (byte)(b0 - 71); }
     else if (b0 >= 65 && b0 <= 90) { b = (byte)(b0 - 65); }
     else if (b0 == 61) { b = 0; }
     else if (b0 == 43) { b = 62; }
     else if (b0 == 47) { b = 63; }
      return b;
   }



   public static final void main(String[] args) throws Exception {
     String s = "pay做最好的开源聚合支付系统";
     System.out.println(encode(s.getBytes()));
   }
 }

