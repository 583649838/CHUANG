package com.bestplus.chuangshangjiuzhi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 转换工具 <br/>
 * 负责数据类型之间的转换如int和byte之间的转换，时间格式，小数点格式转换等等<br/>
 * 
 * @author hbn
 * 
 */
public class ConversionTools {
	/**
	 * int类型转换成byte类型
	 * 
	 * @param intValue
	 * @return
	 */
	public static byte[] intToBytes(int intValue) {
		byte[] returnByte = new byte[4];
		for (int i = 0; i < 4; i++) {
			returnByte[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);

		}
		return returnByte;
	}

	/**
	 * byte类型转换成int类型
	 * 
	 * @param byteValue
	 * @return
	 */
	public static int bytesToInt(byte[] byteValue) {
		int returnValue = 0;
		for (int i = 0; i < byteValue.length; i++) {
			returnValue += (byteValue[i] & 0xFF) << (8 * (3 - i));
		}
		return returnValue;
	}

	/**
	 * short类型转换byte类型
	 * 
	 * @param shortValue
	 * @return
	 */
	public static byte[] shortToBytes(short shortValue) {
		byte[] returnByte = new byte[2];
		returnByte[1] = (byte) (shortValue & 0xff);
		returnByte[0] = (byte) ((shortValue >> 8) & 0xff);
		return returnByte;
	}

	/**
	 * byte类型转换short类型
	 * 
	 * @param byteValue
	 * @return
	 */
	public static short bytesToShort(byte[] byteValue) {
		return (short) (byteValue[1] & 0xff | (byteValue[0] & 0xff) << 8);
	}

	/**
	 * byte类型转换字符
	 * 
	 * @param byteValue
	 * @return
	 */
	public static String bytesToString(byte[] byteValue) {
		return new String(byteValue);
	}

	/**
	 * 截取部分byte
	 * 
	 * @param byteValue
	 * @return
	 */
	public static byte[] subByteToBytes(byte[] byteValue, int beginIndex,
			int endIndex) {
		byte returnByte[] = new byte[endIndex - beginIndex];
		for (int i = 0; i < (endIndex - beginIndex); i++) {
			returnByte[i] = byteValue[beginIndex + i];
		}
		return returnByte;
	}

	/**
	 * 将多个字节数组合并成字节数组
	 * 
	 * @param srcArrays
	 * @return
	 */
	public static byte[] arraysByteToBytes(List<byte[]> srcArrays) {
		byte[] returnByte = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			for (byte[] srcArray : srcArrays) {
				bos.write(srcArray);
			}
			bos.flush();
			returnByte = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
			}
		}
		return returnByte;
	}

	/**
	 * 将字符流转换成字
	 * 
	 * @param in
	 * @return
	 */
	public static byte[] streamToBytes(InputStream in) {
		byte[] returnByte = null;
		if (in != null) {
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] data = new byte[1024];
				int index = in.read(data);
				while (index > -1) {
					out.write(data, 0, index);
					index = in.read(data);
				}
				returnByte = out.toByteArray();
				out.close();
				return returnByte;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * int数组转换byte数组
	 * 
	 * @param intValue
	 * @return
	 */
	public static byte[] intsToBytes(int[] intsValue) {
		byte returnByte[] = new byte[intsValue.length * 4];
		for (int k = 0; k < intsValue.length; k++) {
			int intValue = intsValue[k];
			byte[] intByte = new byte[4];
			for (int i = 0; i < 4; i++) {
				intByte[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);

			}
			System.arraycopy(intByte, 0, returnByte, k * 4, intByte.length);
		}

		return returnByte;
	}

	/**
	 * byte数组转换int数组
	 * 
	 * @param byteValue
	 * @return
	 */
	public static int[] bytesToInts(byte[] byteValue) {

		if (byteValue.length % 4 == 0) {
			int[] returnValue = new int[byteValue.length / 4];
			for (int k = 0; k < byteValue.length / 4; k++) {
				int intValue = 0;
				for (int i = 0; i < 4; i++) {
					intValue += (byteValue[i] & 0xFF) << (8 * (3 - i));
				}
				returnValue[k] = intValue;
			}
			return returnValue;
		} else {
			return null;
		}
	}
}
