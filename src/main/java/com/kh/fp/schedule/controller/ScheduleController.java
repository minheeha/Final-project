package com.kh.fp.schedule.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.fp.schedule.model.service.ScheduleService;
import com.kh.fp.schedule.model.vo.KinderClass;
import com.kh.fp.schedule.model.vo.Schedule;

@Controller
public class ScheduleController {
	
	
	@Autowired
	private ScheduleService scs;
	
	@RequestMapping(value="scheduleMain.sc")
	public String scheduleMain() {
	
		return "schedule/scheduleMain";
	}
	
	
	@RequestMapping(value="searchUserInfo.sc")
	public ModelAndView searchUserInfo(KinderClass kc, ModelAndView mv) {
		System.out.println("controller입장");
		System.out.println(kc);
		int result = 2;
		if(kc.getKinderNo() > 0) {
			result = 1;
		}
		mv.addObject("searchInfo", result);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping(value="selectKinderClass.sc")
	public ModelAndView selectKinderClass(KinderClass kc, ModelAndView mv) {
		
		KinderClass selectKc = scs.selectKinderClass(kc);
		
		mv.addObject("selectKc", selectKc);
		mv.setViewName("jsonView");
		return mv;
	}

	@RequestMapping(value="insertSchedule1.sc")
	public ModelAndView insertSchedule1(Schedule sc,ModelAndView mv) {
		
		System.out.println(sc);
		
			int result = scs.insertSchedule1(sc);
			
		mv.addObject("insertsc", result);
		mv.setViewName("jsonView");
		return mv;
	}

	@RequestMapping(value="insertSchedule2.sc")
	public ModelAndView insertSchedule2(Schedule sc,ModelAndView mv) {
		
		System.out.println(sc);
		
			int result = scs.insertSchedule2(sc);
			
		mv.addObject("insertsc", result);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping(value="searchSchedule.sc")
	public ModelAndView searchSchedule(ModelAndView mv) {
		HashMap<Schedule, Object> array = null;
		System.out.println("들어오긴했냐");
		array = scs.searchSchedule();
			
		mv.addObject("array", array);
		mv.setViewName("jsonView");
		return mv;
	}
	
	
	
	
	
}
