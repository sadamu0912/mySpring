package cn.shu.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
@SuppressWarnings("all")
public class XPathTest {
	public static void main(String[] args) throws JDOMException, IOException {
		 SAXBuilder sb=new SAXBuilder();   
		   Document doc=sb.build(XPathTest.class.getResource("/text.xml")); //构造文档对象  
		   Element root=doc.getRootElement(); //获取根元素  
		   XPath xpath = XPath.newInstance("//电话[../姓名='张三'][@类型='家庭']");
		     List list = xpath.selectNodes(root);
		     Iterator iter = list.iterator();
		     while (iter.hasNext()) {
		       Element item = (Element) iter.next();
		       System.err.println(item.getText());
		     }
		   
	}
}
