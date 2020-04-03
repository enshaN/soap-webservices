package com.soap.webservice.soapservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soap.webservice.soapservice.bean.Course;
import com.soap.webservice.soapservice.service.CourseService;
import com.soap.webservice.soapservice.service.CourseService.Status;

import https.soapservicecourse_com.courses.CourseDetail;
import https.soapservicecourse_com.courses.DeleteCourseDetailRequest;
import https.soapservicecourse_com.courses.DeleteCourseDetailResponse;
import https.soapservicecourse_com.courses.GetAllCourseDetailRequest;
import https.soapservicecourse_com.courses.GetAllCourseDetailResponse;
import https.soapservicecourse_com.courses.GetCourseDetailRequest;
import https.soapservicecourse_com.courses.GetCourseDetailResponse;

@Endpoint
public class CourseDetailEndpoint {
	@Autowired
	CourseService service;
	
	@PayloadRoot(namespace="https://soapservicecourse.com/courses",
				localPart="GetCourseDetailRequest")
	@ResponsePayload
	public GetCourseDetailResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailRequest request) {
		Course course=service.FindById(request.getId());
  
		if(course==null)
			throw new CourseNotFoundException("Invalid ID"+request.getId());
		
		return mapCourseDetail(course);
	}


	private GetCourseDetailResponse mapCourseDetail(Course course) {
		GetCourseDetailResponse response=new GetCourseDetailResponse();
		CourseDetail coursedetail = mapCourse(course);
		response.setCourseDetail(coursedetail);
		return response;
	}
	private GetAllCourseDetailResponse mapAllCourseDetail(List<Course> courses) {
		GetAllCourseDetailResponse response=new GetAllCourseDetailResponse();
		for(Course c:courses) {
			CourseDetail mapCourse=mapCourse(c);
			response.getCourseDetail().add(mapCourse);
		}
		return response;
	}

	private CourseDetail mapCourse(Course course) {
		CourseDetail coursedetail=new CourseDetail();
		coursedetail.setId(course.getId());
		coursedetail.setName(course.getName());
		coursedetail.setDescription(course.getDescription());
		return coursedetail;
	}
	
	
	@PayloadRoot(namespace="https://soapservicecourse.com/courses",
			localPart="GetAllCourseDetailRequest")
@ResponsePayload
public GetAllCourseDetailResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailRequest request) {
	List<Course> courses=service.FindAll();
    
	return mapAllCourseDetail(courses);
}
	
	@PayloadRoot(namespace="https://soapservicecourse.com/courses",
			localPart="DeleteCourseDetailRequest")
@ResponsePayload
public DeleteCourseDetailResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailRequest request) {
	Status status=service.deleteById(request.getId());
    DeleteCourseDetailResponse response =new DeleteCourseDetailResponse();
    response.setStatus(mapStatus(status));
	return response;
}


	private https.soapservicecourse_com.courses.Status mapStatus(Status status) {
		// TODO Auto-generated method stub
		if(status==Status.FAILURE)
			return https.soapservicecourse_com.courses.Status.FAILURE;
		return https.soapservicecourse_com.courses.Status.SUCCESS;
	}
}
