package com.zyserver.util.payment;

import com.zyserver.frontservice.service.impl.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtil {
    public static final Logger log = LoggerFactory.getLogger(SignUtil.class);
    public static String GetSHAstr(Map<String,String> Parm, String Key){
        if(Parm.containsKey("sign")){
            Parm.remove("sign");
        }
        List<String> SortStr = Ksort(Parm);
        String Md5Str = CreateLinkstring(Parm,SortStr);
        log.info("签名前串："+Md5Str);
        String sign =  SHAUtil.sign(Md5Str+Key, "utf-8");
        log.info("签名后串："+sign);
        return sign;
    }
    public static List<String> Ksort(Map<String,String> Parm){
        List<String> SMapKeyList = new ArrayList<String>(Parm.keySet());
        Collections.sort(SMapKeyList);
        return SMapKeyList;
    }
    public static boolean StrEmpty(String Temp){
        if(null == Temp || Temp.isEmpty()){
            return true;
        }
        return false;
    }
    public static String CreateLinkstring(Map<String,String> Parm,List<String> SortStr){
        String LinkStr = "";
        for(int i=0;i<SortStr.size();i++){
            if(!StrEmpty(Parm.get(SortStr.get(i).toString()))){
                LinkStr += SortStr.get(i) +"="+Parm.get(SortStr.get(i).toString());
                if((i+1)<SortStr.size()){
                    LinkStr +="&";
                }
            }
        }
        return LinkStr;
    }
}
