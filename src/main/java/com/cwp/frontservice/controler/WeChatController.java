package com.cwp.frontservice.controler;

import java.io.BufferedOutputStream;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwp.annotation.NeedSignIn;
import com.cwp.common.ApplicationError;
import com.cwp.common.ResponseJson;
import com.cwp.frontservice.service.IWeChatService;
import com.cwp.util.common.WebUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serverInterface/wechat")
public class WeChatController {
	@Autowired
	IWeChatService weChatService;

	//@NeedSignIn
	@RequestMapping(value = "/placeOrder",method=RequestMethod.POST)
    public ResponseJson<Object> addCwpBankCard(Integer customerId,BigDecimal amount, String tradeType, HttpServletRequest request){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(tradeType)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
    	try {
			responseJson = weChatService.placeOrder(customerId,amount,tradeType,WebUtil.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return responseJson;
    }
	//@NeedSignIn
	@RequestMapping(value = "/orderCallback")
    public void orderCallback(HttpServletRequest request, HttpServletResponse response){
    	try {
    		String inputLine;
			String notityXml = "";
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
			String resXml = weChatService.orderCallback(notityXml);
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}