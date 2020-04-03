package com.soap.webservice.soapservice.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soap.webservice.soapservice.bean.Course;

@Component
public class CourseService {
	
	public enum Status{
		SUCCESS,FAILURE;
	}
	

	private static List<Course> course = new ArrayList<Course>();
	
	static {
		Course course1 =new Course(1,"Math","add yo");
		course.add(course1);
		
		Course course2 =new Course(2,"English","talk yo");
		course.add(course2);
		
		Course course3 =new Course(3,"geography","earth yo");
		course.add(course3);
		
		Course course4 =new Course(4,"politics","ew yo");
		course.add(course4);
		
		Course course5 =new Course(5,"Computer","its the yo");
		course.add(course5);
		
	}
	
	public Course FindById(int id) {
		for(Course c:course) {
			if(c.getId()==id)
				return c;
		}
		return null;
	}
	
	public List<Course> FindAll(){
		return course;
	}
	
	public Status deleteById(int id) {
		Iterator<Course> iter=course.iterator();
		while(iter.hasNext()) {
			Course c=iter.next();
			if(c.getId()==id) {
				iter.remove();
			    return Status.SUCCESS;}
		}
		return Status.FAILURE;
	}
	
}
