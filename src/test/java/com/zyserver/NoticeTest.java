package com.zyserver;
import com.zyserver.common.ResponseJson;
import com.zyserver.frontservice.service.ILoginService;
import com.zyserver.frontservice.service.INoticeService;
import com.zyserver.frontservice.service.impl.LoginService;
import com.zyserver.util.common.MD5Util;
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
    @Autowired
    private ILoginService loginService;

    @Test
    public void queryNotice() {
        System.out.println(MD5Util.HEXAndMd5("fjsdf=7899"));
    }
    @Test
    public void testRegist(){
        String phone = "13655971120";
        String userName = "FZ-10001";
        String password = "565656";
        loginService.registrationByUserName(phone,userName,password,"");
    }
}
