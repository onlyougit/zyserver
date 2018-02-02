package test;

import com.zyserver.frontservice.pojo.Result;
import com.zyserver.frontservice.pojo.Root;
import com.zyserver.util.common.HttpsUtil;
import com.zyserver.util.common.MD5Util;

public class XmlTest {
	public  static final String hehe = "https://101.132.99.108:13134/queryaccount";
	/*public static void main(String[] args) throws Exception{
		StringBuilder queryDepositUrl = new StringBuilder();
		queryDepositUrl.append(hehe);
		queryDepositUrl.append("?sa=");
		queryDepositUrl.append("GCB");
		queryDepositUrl.append("&sapass=");
		queryDepositUrl.append("888888");
		queryDepositUrl.append("&account=");
		queryDepositUrl.append(81035062);
		Result result = HttpsUtil.deposit("81035065",100,"GCB","888888");
		
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?>");
		xml.append("<root> ");
		xml.append("<Result>  ");
		xml.append("<Error>  ");
		xml.append("<Code>0</Code>		");
		xml.append("<Message>成功</Message>		");
		xml.append("</Error>		");
		xml.append("<RequestID>0</RequestID>		");
		xml.append("<Summary>		");
		xml.append("<Balance>299.00</Balance>			");
		xml.append("<PreBalance>0.00</PreBalance>		");
		xml.append("<Available>299.00</Available>			");
		xml.append("<Margin>0.00</Margin>		");
		xml.append("<MarginFrozen>0.00</MarginFrozen>			");
		xml.append("<CommissionFrozen>0.00</CommissionFrozen>	");
		xml.append("<PositionProfitFloat>0.00</PositionProfitFloat>		");
		xml.append("<CloseProfitFloat>0.00</CloseProfitFloat>			");
		xml.append("<Commission>0.00</Commission>			");
		xml.append("<PositionProfit>0.00</PositionProfit>			");
		xml.append("<CloseProfit>0.00</CloseProfit>			");
		xml.append("<Deposit>299.00</Deposit>		");
		xml.append("<Withdraw>0.00</Withdraw>		");
		xml.append("<Credit>0.00</Credit>			");
		xml.append("<BaseCapital>0.00</BaseCapital>			");
		xml.append("<EverMaxBalance>299.00</EverMaxBalance>			");
		xml.append("</Summary>	");
		xml.append("</Result>   ");
		xml.append("</root>  ");
		//String response = HttpsUtil.httpGet(queryDepositUrl.toString());
		XMLSerializer xmlSerializer = new XMLSerializer();
	    JSON jsonObj = xmlSerializer.read(xml.toString());
	    String jsonStr = jsonObj.toString();
		System.out.println(jsonStr);
		Result root = com.alibaba.fastjson.JSON.parseObject(jsonStr.toString().substring(1,jsonStr.toString().length()-1), Result.class);
		System.out.println(root);
		//test();
	}*/
	/*public static void main(String[] args) {
		System.out.println(MD5Util.HEXAndMd5("1"+"q15368884a63848f788073ca5f841dcf5"+"4bc20253cf574076a7d3c07f8573081c").toUpperCase());
	}*/
}
