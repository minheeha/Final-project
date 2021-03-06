package com.kh.fp.homework.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.kh.fp.common.CommonUtils;
import com.kh.fp.homework.model.exception.HomeWorkException;
import com.kh.fp.homework.model.service.HomeworkService;
import com.kh.fp.homework.model.vo.HomeWorkApply;
import com.kh.fp.homework.model.vo.IndividualHomework;
import com.kh.fp.homework.model.vo.PageInfo;
import com.kh.fp.homework.model.vo.Pagination;
import com.kh.fp.homework.model.vo.homework;
import com.kh.fp.member.model.vo.Attachment;
import com.kh.fp.member.model.vo.KinGardenClasses;
import com.kh.fp.member.model.vo.Member;
import com.kh.fp.notice.model.service.NoticeService;
import com.kh.fp.notice.model.vo.NoticeWho;

@Controller
@SessionAttributes("loginUser")
public class HomeWorkController {

	@Autowired
	private HomeworkService hs;
	
	
	@RequestMapping(value = "homeWorkWrite.hw")
	public String homeWorkWrite(homework h,HttpSession session, Model model, HttpServletRequest request,
			@RequestParam(name = "photo", required = false) MultipartFile photo) {
		
		KinGardenClasses loginUser = (KinGardenClasses)session.getAttribute("teacherKing");
		
		int userNo = loginUser.getTeacherNo();
		int classNo = loginUser.getClassNo();
		int KinderNo = loginUser.getKinderNo();
		System.out.println("???????????????"+loginUser);
		h.setBoardtype("?????????");
		h.setKinderNo(KinderNo);
		h.setClassNum(classNo);
		h.setIndividualContent("??????");
		h.setWriter(userNo);

		System.out.println(h);
		try {
			hs.insertHomeWork(h);

		} catch (HomeWorkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(photo+"ss");
		if (!photo.getOriginalFilename().equals("")) {

			Attachment at = new Attachment();

			String root = request.getSession().getServletContext().getRealPath("resources");

			String filePath = root + "\\uploadFiles";

			String originFileName = photo.getOriginalFilename();
			String ext = originFileName.substring(originFileName.lastIndexOf("."));
			String changeName = CommonUtils.getRandomString();

			try {
				at.setOrigineName(originFileName);
				at.setChangeName(changeName+ext);
				at.setFilePath(filePath);
				at.setFileLevel("1");
				at.setAttachType("?????????");
				at.setUserNo(userNo);
				at.setBoardNo(1);

				photo.transferTo(new File(filePath + "\\" + changeName + ext));

				System.out.println(at + "??????");

				int insertAttach = hs.insertAt(at);

			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {

		}

		return "redirect:/homeworklist.hw";

	}
	
	@RequestMapping(value = "homeWorkIndiWrite.hw")
	public String homeWorkIndiWrite(homework h,HttpSession session, Model model, HttpServletRequest request,
			@RequestParam(name = "photo", required = false) MultipartFile photo) {
		KinGardenClasses loginUser = (KinGardenClasses)session.getAttribute("teacherKing");
		
		int userNo = loginUser.getTeacherNo();
		int classNo = loginUser.getClassNo();
		int KinderNo = loginUser.getKinderNo();
		System.out.println(userNo + "??????");
		
		h.setBoardtype("?????????");
		h.setKinderNo(KinderNo);
		h.setClassNum(classNo);
		h.setIndividualContent("??????");
		h.setWriter(userNo);

		System.out.println(h);
		try {
			hs.insertHomeWork(h);

		} catch (HomeWorkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(photo.getOriginalFilename()+"ss");
		
		if (!photo.getOriginalFilename().equals("")) {
			
			System.out.println("???????????????.");
			Attachment at = new Attachment();	

			String root = request.getSession().getServletContext().getRealPath("resources");

			String filePath = root + "\\uploadFiles";

			String originFileName = photo.getOriginalFilename();
			String ext = originFileName.substring(originFileName.lastIndexOf("."));
			String changeName = CommonUtils.getRandomString();

			try {
				at.setOrigineName(originFileName);
				at.setChangeName(changeName+ext);
				at.setFilePath(filePath);
				at.setFileLevel("1");
				at.setAttachType("?????????");
				at.setUserNo(userNo);
				at.setBoardNo(1);

				photo.transferTo(new File(filePath + "\\" + changeName + ext));

				System.out.println(at + "??????");

				int insertAttach = hs.insertAt(at);

			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {

		}
		
		int userNo2 = loginUser.getTeacherNo();
		
		System.out.println("userNo2" + userNo2);
		
		
		
		
		return "redirect:/howmanypeopleinClass.ih";

	}
	
	
	@RequestMapping(value = "homeWorkIndividualWrite.hw")
	public String homeWorkIndividualWrite(Model model,HttpSession session, HttpServletRequest request) {
			System.out.println("????????? ????????????");
		KinGardenClasses loginUser = (KinGardenClasses)session.getAttribute("teacherKing");
		
		
		String Numarray = request.getParameter("array");
		String NameArray = request.getParameter("content");
		
		String[] childrenNum = Numarray.split(",");
		String [] childrenName = NameArray.split(",");
		
		ArrayList<IndividualHomework> homeless = new ArrayList<>();
		IndividualHomework abc = null;
		
		int selectBoardNum = hs.selectBoardNum();
		
		for(int j = 0; j < childrenNum.length; j++) {
		
			abc= new IndividualHomework();
			
			abc.setChildrenNo(Integer.parseInt(childrenNum[j]));
			abc.setBoardContent(childrenName[j]);
			abc.setBoardNum(selectBoardNum);
			
			homeless.add(abc);
		}
		
		int insertIndiHomeWork = hs.insertIndiHomework(homeless);
		
		System.out.println(homeless+"????????????????????? ??????????????????");
		return "redirect:/homeworklist.hw";

	}
	
	
	@RequestMapping(value = "homeworklist.hw")
	public String homeworklist(Model model,HttpSession session,NoticeWho noticeWho, HttpServletRequest request,@ModelAttribute("loginUser") Member loginUser) {

	
		
		
		KinGardenClasses TloginUser = (KinGardenClasses)session.getAttribute("teacherKing");
		KinGardenClasses CloginUser = (KinGardenClasses)session.getAttribute("childrenKing");

		int userNo = 0;
		String userC = null;
		int currentPage = 1;
		int listCount = 0;
		ArrayList<NoticeWho> Teacher = null;
		
		userNo=loginUser.getUserNo();
		String classification = loginUser.getClassification();
		
		if(classification.equals("?????????")) {
			
			userNo = loginUser.getUserNo();
			listCount = hs.getKListCount(userNo);
			
			
		}else if(classification.equals("?????????")) {
			
			userNo = CloginUser.getChildrenNo();
			int KNo = CloginUser.getKinderNo();
			System.out.println(KNo+"?????? ??????");
			
			Teacher = hs.selectTeacher(userNo);
			
			if(Teacher == null) {
				
			return "homeworkDiary/homeworkDiaryList2";
				
			}
			System.out.println(Teacher+"?????????");
			
			
			NoticeWho teacher = (NoticeWho)Teacher.get(0);
			int teacherClassNum = teacher.getClassNum();
			int teacherNum = teacher.getTeacherNum();
			
			noticeWho.setClassNum(teacherClassNum);
			noticeWho.setKinderNum(KNo);
			noticeWho.setTeacherNum(teacherNum);
			System.out.println(noticeWho+"?????????");
			
			listCount = hs.getCListCount(noticeWho);
			System.out.println("??????"+listCount);
		}else {
			userNo = TloginUser.getTeacherNo();
			userC = "?????????";
			System.out.println("?????????");
			listCount = hs.getTListCount(userNo);
			
		}
		

		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		
		ArrayList<homework> nList;
		
		if(classification.equals("?????????")) {
			
			if(listCount != 0) {
				
				System.out.println(listCount + "???");
					
				PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
				
				nList = hs.selectKhomework(pi, userNo);
					
				model.addAttribute("nList", nList);
				model.addAttribute("pi", pi);
					
				}
		}else if(classification.equals("?????????")) {
			System.out.println("????????? ?????????");
			if(listCount != 0) {
			
			System.out.println(listCount + "???");
				
			PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
			
			System.out.println("zzzzz"+noticeWho);
			nList = hs.selectChomework(pi, noticeWho);
			
			model.addAttribute("nList", nList);
			model.addAttribute("pi", pi);
				
		
		
			}
		}else {
			
				if(listCount != 0) {
					
					System.out.println(listCount + "???");
						
					PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
						
					nList = hs.selectThomework(pi, userNo);
					
					model.addAttribute("nList", nList);
					model.addAttribute("pi", pi);
						
					}
			
			
			
		}
		
		return "homeworkDiary/homeworkDiaryList";

	}
	

		
	@RequestMapping(value = "homeworkDetail.hw")
	public String homeworkDetail(Model model,HttpSession session, HttpServletRequest request) {
		
		int bid = Integer.parseInt(request.getParameter("bid"));
		
		KinGardenClasses TloginUser = (KinGardenClasses)session.getAttribute("teacherKing");
		KinGardenClasses CloginUser = (KinGardenClasses)session.getAttribute("childrenKing");
		
		
		ArrayList<homework> HList;
		int userNo = 0;
		String userC = null;
		int currentPage = 1;
		
		if(TloginUser != null) {
			
			userNo = TloginUser.getTeacherNo();
			System.out.println(userNo+"?????????");

			HList = hs.SelectOneT(userNo,bid);
			
			System.out.println(bid + "zz");
			System.out.println(userNo + "ss");
			
			model.addAttribute("HList",HList);
			System.out.println("????????????" + HList);
			
		}else if(CloginUser != null) {
			
			userNo = CloginUser.getChildrenNo();
			System.out.println(userNo+"??????");
			int tNo = CloginUser.getTeacherNo();
			System.out.println(tNo+"????????? ??????");
			
			userC = "??????";
			HList = hs.SelectOneT(userNo,bid);
			
			System.out.println("??????");
			model.addAttribute("HList",HList);
			System.out.println("????????????" + HList);
			
		}
		

		return "homeworkDiary/homeworkDetail";
	}
	
	@RequestMapping(value = "updateHomework.hw")
	public String updateHomework(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		int bid = Integer.parseInt(request.getParameter("bid"));
		
		String status = "Y";
	
		int result = hs.UpdateNoticeStatus(bid,status);
		
		System.out.println(result);
		

		return "homeworklist.hw";

	}
	
	@RequestMapping(value = "homeworkApply.hw")
	public ModelAndView homeworkApply(ModelAndView mv,int bid,HttpSession session,@ModelAttribute("loginUser") Member loginUser,HttpServletRequest request, HttpServletResponse response) {
		
		String userName = null;

		String content = request.getParameter("content");
		System.out.println(content);
		
		int userNo = 0;
		userNo=loginUser.getUserNo();
		
		String classification = loginUser.getClassification();
		
		
		KinGardenClasses TloginUser = (KinGardenClasses)session.getAttribute("teacherKing");
		KinGardenClasses CloginUser = (KinGardenClasses)session.getAttribute("childrenKing");
		
		if(classification.equals("?????????")) {
	         userName = "?????????";
	         int result = hs.insertApply(bid,content,userName);

	         
	      }else if(classification.equals("?????????")) {
	    	 CloginUser = (KinGardenClasses)session.getAttribute("childrenKing");
	    	 userName = CloginUser.getChildrenName()+"?????????";
	    	
	    	 int result = hs.insertApply(bid,content,userName);
	    	
	    	
	      
	      }else {
	    	  TloginUser = (KinGardenClasses)session.getAttribute("teacherKing");
	    	  userName = TloginUser.getClassName()+"?????????";
	    	  
	    	  int result = hs.insertApply(bid,content,userName);
	    	  
	    	  
	    	  System.out.println(userName);
	    	  
	      }
		
		
		
		
		mv.setViewName("jsonView");
		
		return mv;

	}
	
	
	@RequestMapping(value = "homeworkApplyStart.hw",method = RequestMethod.POST)
	public void homeworkApplyStart(ModelAndView mv,HttpSession session,@ModelAttribute("loginUser") Member loginUser ,int bid,HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println(bid+"????????????");
		int userNo = 0;
		
		
	    	
	    ArrayList<HomeWorkApply> data = new ArrayList<>();
	   
	    data =  hs.SearchApply(bid);
	    System.out.println("ajax:::::::"+data);	  
	    
	    HashMap<String,Object> hmap =new HashMap<String,Object>();
	    
	    hmap.put("data", data);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		try {
			new Gson().toJson(data,response.getWriter());
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}















