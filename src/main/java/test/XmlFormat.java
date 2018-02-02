package test;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
public class XmlFormat {

	/*public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"GB2312\" ?><root>	<Result>		<Error>			<Code>0</Code>			<Message>成功</Message>		</Error>		<RequestID>0</RequestID>		<Summary>			<Balance>0.00</Balance>			<PreBalance>0.00</PreBalance>			<Available>0.00</Available>			<Margin>0.00</Margin>			<MarginFrozen>0.00</MarginFrozen>			<CommissionFrozen>0.00</CommissionFrozen>			<PositionProfitFloat>0.00</PositionProfitFloat>			<CloseProfitFloat>0.00</CloseProfitFloat>			<Commission>0.00</Commission>			<PositionProfit>0.00</PositionProfit>			<CloseProfit>0.00</CloseProfit>			<Deposit>0.00</Deposit>			<Withdraw>0.00</Withdraw>			<Credit>0.00</Credit>			<BaseCapital>0.00</BaseCapital>			<EverMaxBalance>0.00</EverMaxBalance>			<MaxBalance>0.00</MaxBalance>			<BaseCommission>0.00</BaseCommission>		</Summary>	</Result></root>";
		System.out.println(format(xml));
	}*/
	public static String format(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	public static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
