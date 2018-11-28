package com.easylinkin.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <pre>
 * 作用:关于异常的工具类
 * 注意:
 * 其他:
 * </pre>
 * 
 * @author chenwentao
 * @version 1.0, 2016-2-1
 * @see
 * @since
 */
public class Exceptions {

	private Exceptions() {
	}

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
}
