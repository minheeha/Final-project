package com.kh.fp.homework.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.fp.homework.model.service.HomeworkService;
import com.kh.fp.homework.model.vo.HomeWorkChildren;
import com.kh.fp.member.model.vo.Attachment;
import com.kh.fp.returnHome.model.vo.ChildrenClass;

@Controller
public class HomeworkHowManyPeople {

	@Autowired
	private HomeworkService hs;

	@RequestMapping(value="howmanypeopleinClass.ih")
	public ModelAndView homeWork(ModelAndView mv,int userNo2) {

		int classNum = hs.selectClassNum(userNo2);

		ChildrenClass c = null;

		Attachment fileList = null;

		ArrayList<HomeWorkChildren> list =  hs.selectChildren(userNo2);
		System.out.println(list +"입니다bbb");
		mv.addObject("classNum",classNum);
		mv.addObject("list",list);
		mv.setViewName("homeworkDiary/homeworkIndividualWrite");
		return mv;
	}

	/*
	 * @RequestMapping(value="peopleinClass.ih") public ModelAndView
	 * whoInClass(ModelAndView mv, int userNo2) { ChildrenClass c = null;
	 * 
	 * Attachment fileList = null;
	 * 
	 * ArrayList<HomeWorkChildren> hmap = hs.selectChildren(userNo2);
	 * 
	 * mv.addObject("hmap",hmap);
	 * mv.setViewName("homeworkDiary/homeworkSelectPeople");
	 * 
	 * 
	 * return mv; }
	 */
}
