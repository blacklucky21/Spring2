package com.kh.ajax.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.ajax.model.vo.Sample;
import com.kh.ajax.model.vo.User;

@Controller
public class TestController {

	@Autowired
	Sample sam;
//	@RequestMapping("testtest.do")
//	public void test() {
//		System.out.println(sam);
//	}

	// 1. ServletOutputStream을 이용한 방식으로 출력용 메소드 사용
	@RequestMapping(value = "test1", method = RequestMethod.POST)
	public void testMethod(HttpServletResponse response, String name, int age) throws IOException {

		PrintWriter out = response.getWriter();

		if (name.equals("신사임당") && age == 47) {
			out.append("ok");
			out.flush();
		} else {
			out.append("fail");
			out.flush();
		}

		out.close();
	}

	// 2. @ResponseBody를 이용한 방식

	@RequestMapping("test2")
	@ResponseBody // header에 같이 들어감
	public String test2Method(HttpServletResponse response) throws UnsupportedEncodingException {

		JSONObject job = new JSONObject();
		job.put("no", 123);
		job.put("title", "test return json object");
		job.put("writer", URLEncoder.encode("홍길동", "UTF-8"));
		job.put("content", URLEncoder.encode("JSON 객체를 뷰로 리턴하는 테스트입니다.", "utf-8"));

		return job.toJSONString();

	}

	// 3. @ResponseBody를 사용하지않고 스트림 써서 json객체 보내기

	@RequestMapping("test3")
	public void test3Method(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8"); // response type을 보낸다 ==> @ResponseBody를 사용하지않기 때문

		JSONObject job = new JSONObject();
		job.put("no", 123);
		job.put("title", "test return json object");
		job.put("writer", URLEncoder.encode("홍길동", "UTF-8"));
		job.put("content", URLEncoder.encode("JSON 객체를 뷰로 리턴하는 테스트입니다.", "utf-8"));

		PrintWriter out = response.getWriter();

		out.println(job);
		out.flush();
		out.close();

	}
	// 4. 스트림을 이용해서 json배열 보내기

	@RequestMapping("test4")
	public void test4Method(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");

		ArrayList<User> list = new ArrayList<>();

		list.add(new User("u1111", "p1111", "홍길동", 25, "h111@kh.org", "01011112222"));
		list.add(new User("u2222", "p2222", "홍길순", 25, "u222@kh.org", "01022223333"));
		list.add(new User("u3333", "p3333", "홍길녀", 25, "u333@kh.org", "01033334444"));
		list.add(new User("u4444", "p4444", "홍길남", 25, "p444@kh.org", "01044445555"));
		list.add(new User("u5555", "p5555", "홍길군", 25, "u555@kh.org", "01055556666"));

		// 1.List를 JSON배열로 담아주기
		JSONArray jarr = new JSONArray();
		for (User user : list) {
			JSONObject jUser = new JSONObject();

			jUser.put("userId", user.getUserId());
			jUser.put("userPwd", user.getUserPwd());
			jUser.put("userName", user.getUserName());
			jUser.put("age", user.getAge());
			jUser.put("email", user.getEmail());
			jUser.put("phone", user.getPhone());

			jarr.add(jUser);

		}

		// 2. 전송을 하기 위해 JSON객체로 담겨 있는 회원 정보들 JSON객체에 담기
		JSONObject sendJson = new JSONObject();
		sendJson.put("list", jarr);

		PrintWriter out = response.getWriter();
		out.print(sendJson);
		out.flush();
		out.close();

	}

	// 5. ModelAndView 이용하기

	@RequestMapping("test5")
	public ModelAndView test5Method(ModelAndView mv,HttpServletResponse response) {
		
		Map<String, Sample> map = new HashMap<String,Sample>();
		
		
		map.put("samp", sam);
		
		mv.addAllObjects(map);
		mv.setViewName("jsonView");
		
		response.setContentType("application/json;charset=utf-8");
		return mv;
	}
	
	
	// 6. @RequestBody를 이용한 방법
	
	@RequestMapping("test6")
	@ResponseBody
	public String test6Method(@RequestBody String param) throws ParseException { //JSON으로 받는것을 RequestParam을 사용해서 String으로 받는다.
		
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(param); // 반환값이 object이기때문에 JSONObject로 형변환 해줌
		
		String name = (String)jobj.get("name");
		int age = ((Long)jobj.get("age")).intValue();
		
		System.out.println(name +","+age);
		
		return "success";
		
		
		
	}
	
	//7. 반환 값으로 Responseentity<String>사용
	//@ResponseBody와 비슷하나 header 값을 변경시킬 수 있고 Http상태코드도 함께 전송 가능
	
	/*
	 * Spring에서는 HttpEntity라는 클래스 제공
	 * 	HttpEntity : HTTP프로토콜을 이용하는 통신의 header와 body관련 정보 저장 가능
	 * HttpEntity를 상속받는 클래스로 RequestEntity와 ResponseEntity가 있으며
	 * 각각 Request, Response의 역활을 함 
	 * 
	 */
	 
	@RequestMapping("test7")
	public ResponseEntity<String> test7Method(@RequestBody String param) throws ParseException {
		JSONParser parser = new JSONParser(); //JSON으로 보내줘서 파서 필요
		JSONArray jArr = (JSONArray)parser.parse(param);
		
		
		for(int i=0; i< jArr.size();i++) {
			JSONObject jobj = (JSONObject)jArr.get(i);
			
			String name = (String)jobj.get("name");
			int age = ((Long)jobj.get("age")).intValue();
			
			System.out.println(i+"번째 객체 : "+name+", "+age);
		}
		
		return new ResponseEntity<String>("success",HttpStatus.OK);
		
	}
	

}
