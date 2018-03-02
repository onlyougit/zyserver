package com.zyserver.frontservice.service;

import com.zyserver.common.ResponseJson;
import com.zyserver.entity.BankCard;


public interface INoticeService {

	ResponseJson<Object> queryNotice();
}