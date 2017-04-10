package cn.catecat.orders.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取当前时间进行添加时间
 * @author 钟思平
 *
 */
public class DateTool {
	public static String dateUtil(long dateTime){
		Date time = new Date(dateTime);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String current = sdf.format(time);
		return current;
	}
}
