package com.bjgas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtils {

	static Calendar now;
	static Date today;

	static {
		// ȡ�õ�ǰ��ʱ��
		now = Calendar.getInstance();
		// ȡ�ý�������ڣ�ʱ���0�㿪ʼ����
		today = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE))
				.getTime();
	}

	/**
	 * ���ص�ǰʱ�䡣
	 * 
	 * @return
	 */
	public static Calendar getNow() {
		return now;
	}

	public static long getNowLong() {
		return toUnixTime(now.getTime());
	}

	/**
	 * ��ΪUnixʱ���
	 * 
	 * @param cd
	 * @return
	 */
	public static long toUnixTime(Date dt) {

		return dt.getTime() / 1000;
	}

	/**
	 * ��ΪUnixʱ���
	 * 
	 * @param dtStr
	 *            ��������yyyy-MM-dd��ʽ���ַ�����
	 * @return
	 */
	public static long toUnixTime(String dtStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = df.parse(String.valueOf(dtStr));
			return toUnixTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * �������ʱ���ʽ��ΪUnixʱ���
	 * 
	 * @param dtStr
	 *            ��������yyyy-MM-dd HH:mm:ss��ʽ���ַ�����
	 * @return
	 */
	public static long toUnixTimeDetail(String dtStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = df.parse(String.valueOf(dtStr));
			return toUnixTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * ��Unixʱ���ת��Ϊjava����
	 * 
	 * @param uTime
	 * @return
	 */
	public static Date fromUnixTime(long uTime) {
		return new java.util.Date(uTime * 1000);
	}

	/**
	 * ��Unixʱ���ת��Ϊjava���ڣ�����ʽ��Ϊyyyy-MM-dd HH:mm:ss����ʽ��
	 * 
	 * @param uTime
	 * @return
	 */
	public static String fromUnixTimeStr(long day) {
		return fromUnixTimeStr(day, "yyyy-MM-dd");
	}

	/**
	 * ��Unixʱ���ת��Ϊjava���ڣ�����ʽ��Ϊyyyy-MM-dd HH:mm:ss����ʽ��
	 * 
	 * @param uTime
	 * @return
	 */
	public static String fromUnixTimeStr(long day, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(fromUnixTime(day));

	}

	/**
	 * ��Unixtime����ʽ���ؽ�������
	 * 
	 * @return
	 */
	public static long getTodayLong() {
		return toUnixTime(today);
	}

	/**
	 * �����ڵ���ʽ���ؽ�������
	 * 
	 * @return
	 */
	public static Date getToday() {
		return today;
	}

	/**
	 * ���ַ�������ʽ���ؽ�������
	 * 
	 * @return
	 */
	public static String getTodaystr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(today);
	}

	/**
	 * ��yyMMdd�ַ�������ʽ���ؽ�������
	 * 
	 * @return
	 */
	public static String getTodaySimplestr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(today);
	}


}
