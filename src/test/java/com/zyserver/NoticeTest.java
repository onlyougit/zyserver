package com.zyserver;
import com.zyserver.common.ResponseJson;
import com.zyserver.frontservice.service.INoticeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeTest {

    @Autowired
    private INoticeService noticeService;

    @Test
    public void queryNotice() {
        ResponseJson<Object> responseJson = noticeService.queryNotice();
        System.out.println();
    }
}
