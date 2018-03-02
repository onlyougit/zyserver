package com.zyserver.frontservice.controler;

import com.zyserver.annotation.NeedSignIn;
import com.zyserver.common.ApplicationError;
import com.zyserver.common.ResponseJson;
import com.zyserver.entity.BankCard;
import com.zyserver.frontservice.service.IBankService;
import com.zyserver.frontservice.service.INoticeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serverInterface/notice")
public class NoticeController {
	Log log= LogFactory.getLog(NoticeController.class);
	
	@Autowired
	private INoticeService noticeService;

	@RequestMapping(value="/queryNotice",method=RequestMethod.POST)
	public ResponseJson<Object> queryNotice(){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		responseJson =  noticeService.queryNotice();
		return responseJson;
	}
}