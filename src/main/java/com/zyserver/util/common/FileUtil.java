package com.zyserver.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	public static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 一行一行读取文件
	 * @return
	 */
	public List<String> readHoliday() {
		InputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		List<String> holidayList = new ArrayList<>();
		try {
			String holiday = "";
			fis = this.getClass().getClassLoader().getResourceAsStream("files/holiday.txt");
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			while ((holiday = br.readLine()) != null) {
				holidayList.add(holiday.split(",")[0]);
			}
		}catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				log.info(e.getMessage());
				e.printStackTrace();
			}
		}
		return holidayList;
	}
}
