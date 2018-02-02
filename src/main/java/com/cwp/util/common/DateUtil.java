package com.cwp.util.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateUtil {

	public static final Logger log = LoggerFactory.getLogger(DateUtil.class);
    public static final String TIME1 = "09:30:00";
    public static final String TIME2 = "11:30:00";
    public static final String TIME3 = "13:00:00";
    public static final String TIME4 = "15:00:00";
    public static final String TIME5 = "15:10:00";
    /**
     * 今天之前的日期毫秒值
     * @param date
     * @return
     */
	public static Long isPreToday(Date date){
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2017-11-16 01:00:00");
        System.out.println(date.getTime());*/
        long current = System.currentTimeMillis();
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        return date.getTime()-zero;
	}

	public static String getProductId(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		String time =  formatter.format(new Date());
		return time;
	}
    public static boolean isToday(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String param = sdf.format(date);
        String today = sdf.format(new Date());
        if(param.equals(today)){
            return true;
        }
        return false;
    }
    /**
     * 判断股票交易时间
     * @param date
     * @return
     * @throws ParseException
     */
    public static boolean judgeSharesTime(Date date) {
    	try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Long date1 = simpleDateFormat.parse(simpleDateFormat.format(date)).getTime();
            Long date2 = simpleDateFormat.parse(TIME1).getTime();
            Long date3 = simpleDateFormat.parse(TIME2).getTime();
            Long date4 = simpleDateFormat.parse(TIME3).getTime();
            Long date5 = simpleDateFormat.parse(TIME4).getTime();
            if((date1>=date2 && date1<=date3) || (date1>=date4 && date1<=date5)){
                return true;
            }else{
                return false;
            }
    	}catch(ParseException e){
    		log.info(e.getMessage());
    		return false;
    	}
    }

    /**
     * 判断股票交易时间
     * @param date
     * @return
     * @throws ParseException
     */
    public static boolean judgeScheduledSharesTime(Date date) {
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Long date1 = simpleDateFormat.parse(simpleDateFormat.format(date)).getTime();
            Long date2 = simpleDateFormat.parse(TIME1).getTime();
            Long date3 = simpleDateFormat.parse(TIME2).getTime();
            Long date4 = simpleDateFormat.parse(TIME3).getTime();
            Long date5 = simpleDateFormat.parse(TIME5).getTime();
            if((date1>=date2 && date1<=date3) || (date1>=date4 && date1<=date5)){
                return true;
            }else{
                return false;
            }
        }catch(ParseException e){
            log.info(e.getMessage());
            return false;
        }
    }
    /**
     * 判断是不是节假日
     * @return
     */
    public static boolean isHoliday(){
    	FileUtil fileUtil = new FileUtil();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar = Calendar.getInstance();
    	if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ||
				calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
			return false;
		}
    	List<String> holiday = fileUtil.readHoliday();
    	Date date = calendar.getTime();
		String today = sdf.format(date);
		if(holiday.contains(today)){
			return false;
		}
		return true;
    }
    /**
     * 判断是不是周末
     * @return
     */
    public static boolean isWeekend() {
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ||
				calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
			return true;
		}
		return false;
	}
    
}
