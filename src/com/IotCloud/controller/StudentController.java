package com.IotCloud.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.IotCloud.constant.ParameterKeys;
import com.IotCloud.data.StudentTestResult;
import com.IotCloud.data.TestResult;
import com.IotCloud.model.Student;
import com.IotCloud.service.StudentService;
import com.IotCloud.util.CommonUtil;
import com.IotCloud.util.ResponseFilter;

@RestController
public class StudentController {

	@Autowired
	StudentService studentService;

	private static Logger logger = Logger.getLogger(StudentController.class);

	@RequestMapping(value = "/loadStudents", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadStudents(HttpServletRequest request) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			MultipartHttpServletRequest re = resolver.resolveMultipart(request);

			MultipartFile fileM = re.getFile("studentInfo");
			CommonsMultipartFile cf = (CommonsMultipartFile) fileM;
			InputStream inputStream = cf.getInputStream();
			String message = studentService.loadStudentsFromXml(CommonUtil.getSessionUser(request), inputStream);
			res.put(ParameterKeys.STATE, 0);
			res.put("message", message);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入学生信息异常", e);
			res.put(ParameterKeys.STATE, 1);
			res.put("message", "参数格式错误");
			return res;
		}
	}

	@RequestMapping(value = "/deleteStudents", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStudents(HttpServletRequest request, @RequestBody List<Student> studentList) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}
		try {
			boolean state = studentService.deleteStudents(CommonUtil.getSessionUser(request), studentList);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除学生信息异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/getSchoolList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSchoolList(HttpServletRequest request) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			List<String> schoolList = studentService.getSchoolNameListByAdminId(CommonUtil.getSessionUser(request));
			res.put("schoolList", schoolList);
			res.put(ParameterKeys.STATE, 0);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取学校列表异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/getClassList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getClassList(HttpServletRequest request, @RequestParam("schoolName") String schoolName) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			List<String> classList = studentService.getClassNameListBySchool(CommonUtil.getSessionUser(request),
					schoolName);
			res.put("classList", classList);
			res.put(ParameterKeys.STATE, 0);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取班级列表异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/getStudentList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getStudentList(HttpServletRequest request,
			@RequestParam(value = "schoolName", required = false) String schoolName,
			@RequestParam(value = "className", required = false) String className,
			@RequestParam(value = "gender", required = false) Integer gender) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			List<Student> studentList = studentService.getStudentList(CommonUtil.getSessionUser(request), schoolName,
					className, gender);
			res.put("studentList", studentList);
			res.put(ParameterKeys.STATE, 0);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取学生列表异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/getIndvTestResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getIndvTestResult(HttpServletRequest request,
			@RequestParam(value = "testerNo") long testerNo,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}
		try {
			Student student = studentService.getStudentByTesterNo(CommonUtil.getSessionUser(request), testerNo);
			if (student == null) {
				res.put(ParameterKeys.STATE, 1);
				res.put("message", "没有找到对应的学生");
				return res;
			} else {
				res.put("studentInfo", student);
			}
			List<TestResult> testResultList = studentService.getRecordListByStudent(student, startTime, endTime);
			res.put("testResultList", testResultList);
			res.put("message", "查询成功");
			res.put(ParameterKeys.STATE, 0);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询学生个人考试成绩异常", e);
			res.put(ParameterKeys.STATE, 1);
			res.put("message", "出现异常");
			return res;
		}
	}

	@RequestMapping(value = "/getTestResultByItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTestResultByItem(HttpServletRequest request,
			@RequestParam(value = "itemName") String itemName, @RequestParam(value = "gender") int gender,
			@RequestParam(value = "schoolName", required = false) String schoolName,
			@RequestParam(value = "className", required = false) String className,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}
		try {
			List<StudentTestResult> testResultList = studentService.getTestResultByItem(
					CommonUtil.getSessionUser(request), schoolName, className, gender, itemName, startTime, endTime);
			res.put("testResultList", testResultList);
			res.put("message", "查询成功");
			res.put(ParameterKeys.STATE, 0);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询单项所有学生考试成绩异常", e);
			res.put(ParameterKeys.STATE, 1);
			res.put("message", "出现异常");
			return res;
		}
	}
}
