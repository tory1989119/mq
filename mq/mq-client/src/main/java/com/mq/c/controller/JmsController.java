package com.mq.c.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mq.c.model.MyMessage;
import com.mq.c.service.JmsSender;

@Controller
@RequestMapping("/jms")
public class JmsController {
	@Autowired
	JmsSender jmsSender;
	
	/**
	 * String
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("sendString.do")
	public String sendString(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> list = new ArrayList<String>();
		jmsSender.setSendAsync(true);
		for (int i = 0; i < 100; i++) {
			list.add(String.valueOf(i));
		}
		jmsSender.sendTextBatch(list);
		return "index";
	}
	
	/**
	 * map
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("sendMap.do")
	public String sendMap(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		jmsSender.setSendAsync(true);
		for (int i = 0; i < 100; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", String.valueOf(i));
			list.add(map);
		}
		jmsSender.sendMapBatch(list);
		return "index";
	}
	
	/**
	 * 对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("sendObject.do")
	public String sendObject(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<MyMessage> list = new ArrayList<MyMessage>();
		jmsSender.setSendAsync(true);
		for (int i = 0; i < 100; i++) {
			MyMessage message = new MyMessage();
			message.setA(String.valueOf(i));
			list.add(message);
		}
		jmsSender.sendObjectBatch(list);
		return "index";
	}
}
