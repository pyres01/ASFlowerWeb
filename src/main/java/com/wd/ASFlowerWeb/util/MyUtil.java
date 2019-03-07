package com.wd.ASFlowerWeb.util;

import java.sql.Timestamp;

/**
 * @author 韦丹
 *
 * 2019年3月6日
 *
 */
public class MyUtil {

	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
}
