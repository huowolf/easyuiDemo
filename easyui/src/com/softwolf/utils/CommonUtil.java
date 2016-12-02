package com.softwolf.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.softwolf.pojo.User;

public class CommonUtil {

	/**
	 * 获取一个类的所有属性名
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <Entity> List<String> getFileds(Class<Entity> clazz) throws Exception{
		List<String> fieldNames = new ArrayList<>();
		
		Field[] fields = clazz.getDeclaredFields();//获得属性
		for (Field field : fields) {
			fieldNames.add(field.getName());
		}
		return fieldNames;	
	}
	

	/**
	 * 获取一个对象的所有属性值
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public static List<Object> getFiledValues(Object obj) throws Exception{
		
		List<Object> filedValues = new ArrayList<>();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());  
            Method getMethod = pd.getReadMethod();		//获得get方法  
            Object value = getMethod.invoke(obj);		//执行get方法返回一个Object
            
            filedValues.add(value);
		}
		
		return filedValues;
	}
	
	
	
	
	@Test
	public void testGetFields() throws Exception{
		
		List<String> fields = CommonUtil.getFileds(User.class);
		for (String field : fields) {
			System.out.println(field);
		}
	}
	
	
	
	
	@Test
	public void testGetFieldValues() throws Exception{
		User u = new User();
		u.setId(2);
		u.setAge(21);
		u.setName("张三");
		u.setPwd("zhangsna");
		u.setDescription("我爱编程");
		List<Object> objs = CommonUtil.getFiledValues(u);
		for (Object obj : objs) {
			System.out.println(obj);
		}
		
	}
}
