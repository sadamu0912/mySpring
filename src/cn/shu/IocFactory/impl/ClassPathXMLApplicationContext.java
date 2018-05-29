package cn.shu.IocFactory.impl;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import cn.shu.IocFactory.ApplicationContext;

public class ClassPathXMLApplicationContext implements ApplicationContext{
	
	
	    private File file;
	    private Map map = new HashMap();

	    public ClassPathXMLApplicationContext(String config_file) {
	        URL url = this.getClass().getClassLoader().getResource(config_file);

	        try {
				file = new File(url.toURI());
				XMLParsing();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }

            private void XMLParsing() throws Exception {     
	    	SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(file);
	        XPath xpath = XPath.newInstance("//bean");
	        List beans = xpath.selectNodes(doc);
	        Iterator i = beans.iterator();
	        while (i.hasNext()) {
	            Element bean = (Element) i.next();
	            String id = bean.getAttributeValue("id");
	            String cls = bean.getAttributeValue("class");
	            Object obj = Class.forName(cls).newInstance();
	            Method[] method = obj.getClass().getDeclaredMethods();
	            List<Element> list = bean.getChildren("property");
	            for (Element el : list) {
	                for (int n = 0; n < method.length; n++) {
	                    String name = method[n].getName();
	                    String temp = null;
	                    if (name.startsWith("set")) {
	                        temp = name.substring(3, name.length()).toLowerCase();
	                        if (el.getAttribute("name") != null) {
	                            if (temp.equals(el.getAttribute("name").getValue())) {
	                                method[n].invoke(obj, el.getAttribute("value").getValue());
	                            }
	                        } else {
	                            method[n].invoke(obj,map.get(el.getAttribute("ref").getValue()));
	                        }
	                    }
	                }
	            }
	            map.put(id, obj);
	        }
	    }
            
            private void XMLParsing2() throws Exception{
            	SAXBuilder sb = new  SAXBuilder();
            	Document document = sb.build(file);
            	XPath xpath = XPath.newInstance("//bean");
            	// get bean list
            	List beans =xpath.selectNodes(document);
            	Iterator iterator = beans.iterator();
            	while (iterator.hasNext()){
            		//and then get id and class of the bean ,instanciate the object 
            		Element bean =(Element) iterator.next(); 
            		String id =bean.getAttributeValue("id");
            		String clazz = bean.getAttributeValue("class");
            		Object obj = Class.forName(clazz).newInstance();
            		//
            		
            	}
            	
            } 

	    public Object getBean(String name) {
	        return map.get(name);
	    }

}
