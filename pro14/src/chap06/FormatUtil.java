package chap06;

import java.text.DecimalFormat;

public class FormatUtil {
	public static String number(long number, String pattern) {
		DecimalFormat format = new DecimalFormat(pattern);
		return format.format(number);
	}
}
