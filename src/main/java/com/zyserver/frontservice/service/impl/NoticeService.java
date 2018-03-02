package com.zyserver.frontservice.service.impl;

import com.zyserver.common.ResponseJson;
import com.zyserver.entity.Notice;
import com.zyserver.frontservice.service.INoticeService;
import com.zyserver.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("noticeService")
public class NoticeService implements INoticeService {
    @Autowired
    private NoticeRepository noticeRepository;
    @Override
    public ResponseJson<Object> queryNotice() {
        ResponseJson<Object> responseJson = new ResponseJson<>();
        List<Notice> noticeList = noticeRepository.findByStatusOrderByIdDesc(1);
        responseJson.setData(noticeList);
        return responseJson;
    }
}
