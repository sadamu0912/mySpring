package cn.shu.test;

import cn.shu.IocFactory.ApplicationContext;
import cn.shu.IocFactory.impl.ClassPathXMLApplicationContext;
import cn.shu.service.StudentService;

public class Test {
    public static void main(String[] args) {
    	 ApplicationContext context = new ClassPathXMLApplicationContext("applicationContext.xml");
            StudentService  stuServ = (StudentService) context.getBean("StudentService");
            stuServ.getStudent().selfIntroDuction();
	}
    
}
  