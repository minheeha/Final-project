package com.kh.fp.album.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.kh.fp.album.model.service.AlbumService;
import com.kh.fp.album.model.vo.Album;
import com.kh.fp.album.model.vo.PageInfo;
import com.kh.fp.album.model.vo.Pagination;
import com.kh.fp.common.CommonUtils;
import com.kh.fp.homework.model.service.HomeworkService;
import com.kh.fp.homework.model.vo.HomeWorkChildren;
import com.kh.fp.member.model.vo.Attachment;
import com.kh.fp.member.model.vo.KinGardenClasses;
import com.kh.fp.member.model.vo.Member;
import com.kh.fp.member.model.vo.OnOff;
import com.kh.fp.returnHome.model.vo.ChildrenClass;



@Controller
@SessionAttributes("loginUser")
public class AlbumController {

	@Autowired
	private HomeworkService hs;
	@Autowired
	private AlbumService abs;

	@RequestMapping(value = "album.ab")
	public String albumList(Model model,HttpSession session, HttpServletRequest request,@ModelAttribute("loginUser") Member loginUser ,@SessionAttribute("of") OnOff of) {
	
		int userNo=0;
		int Tnum = 0;
		
		userNo=loginUser.getUserNo();
		System.out.println(userNo + "?????????.");
		ArrayList<Album> album;
		
		System.out.println("of ??? ????????? ?????? : " + of.getKinderNo());
		
	    String classification = loginUser.getClassification();
	    System.out.println("?????????"+classification);
	    int currentPage = 1;

	     if (request.getParameter("currentPage") != null) {
	         currentPage = Integer.parseInt(request.getParameter("currentPage"));
	      }
	    
	   
	     
	     int listCount = 0;
	     
	    if(classification.equals("?????????")) {
	         listCount = abs.getListCount(userNo);
	         
	         
	      }else if(classification.equals("?????????")) {
	    	KinGardenClasses CloginUser = (KinGardenClasses)session.getAttribute("childrenKing");
	  		userNo = CloginUser.getChildrenNo();
	  		System.out.println(userNo+"?????????");
	  		Tnum = abs.getTNum(userNo);
	  		
	  		if(Tnum == 0) {
	  			
	  			 return "album/albumList2";
	  		}
	  		
	  		
	  		listCount = abs.getTListCount(Tnum);
	  		
	  			
	      
	      }else {
	         listCount = abs.getTListCount(userNo);
	      }

	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount);

		
	    if(classification.equals("?????????")) {
	    	album = abs.selectAlbumRequestList(pi, userNo);
	    	model.addAttribute("album", album);
         }else if(classification.equals("?????????")) {
        	 album = abs.selectTAlbumRequestList(pi, Tnum);
        	 model.addAttribute("album", album);
         }else {
        	 album = abs.selectTAlbumRequestList(pi, userNo);
        	 model.addAttribute("album", album);
         }

		
		model.addAttribute("pi", pi);
		
		
		
	 return "album/albumList";
	}
	
	
	@RequestMapping(value = "albumWrite.ab")
	public String albumWrite(Album a,Model model,HttpSession session,HttpServletRequest request,
			@RequestParam("file") MultipartFile[] file ) {

		KinGardenClasses loginUser = (KinGardenClasses)session.getAttribute("teacherKing");
		
		int classNo = loginUser.getClassNo();
		int userNo = loginUser.getTeacherNo();
		
		a.setClassNo(classNo);
		a.setWriter(userNo);
		
		System.out.println("?????? ::::::"+a);
		
		int albumA = abs.insertAlbum(a);

		if(albumA >= 0) {
		
		String hv = request.getParameter("hv");
			
		String [] num = hv.split("&");
		
			
		System.out.println(hv);
			
		for(int i = 0; i < num.length; i++){
			
			System.out.println(num[i]+"?????????");
				
		}
		
		for(int i = 0; i < file.length; i++) {
			
			if (!file[i].getOriginalFilename().equals("")) {
			 System.out.println(file[i].getOriginalFilename()+"ss");
			 
			  Attachment at = new Attachment(); 
			  
			  String root = request.getSession().getServletContext().getRealPath("resources");
			  
			  String filePath = root + "\\uploadFiles"; 
			  
			  String originFileName = file[i].getOriginalFilename(); 
			  String ext =originFileName.substring(originFileName.lastIndexOf(".")); 
			  
			  String changeName = CommonUtils.getRandomString();
			  
			  try {
					at.setOrigineName(originFileName);
					at.setChangeName(changeName+ext);
					at.setFilePath(filePath);
					if(i == 0) {
					at.setFileLevel("1");
					}else {
					at.setFileLevel("2");
					}
					at.setAttachType("??????");
					at.setAlbumchildrenNo(num[i]);
					at.setBoardNo(1);

					file[i].transferTo(new File(filePath + "\\" + changeName + ext));

					System.out.println(at + "??????");

					int insertAttach = abs.insertAt(at);
			  
			  } catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  
			  }else {
			  
			  }
			  }
			 
		}
		
		
		return "redirect:album.ab";
	}	
	
	@RequestMapping(value = "albumWhoIn.ab")
	public ModelAndView albumWhoIn(ModelAndView mv, HttpSession session,HttpServletRequest request) {
		
		
		KinGardenClasses loginUser = (KinGardenClasses)session.getAttribute("teacherKing");
		
		int userNo2 = loginUser.getTeacherNo();
		int classNum = hs.selectClassNum(userNo2);
		String className = hs.selectClassName(userNo2);
		
		ChildrenClass c = null;

		Attachment fileList = null;
		
		ArrayList<HomeWorkChildren> list =  hs.selectChildren(userNo2);
		System.out.println("list::::::"+list);
		
		mv.addObject("classNum",classNum);
		mv.addObject("className",className);
		mv.addObject("list",list);
		mv.setViewName("album/albumWrite");
		
		
		return mv;
	}	
	
	@RequestMapping(value="selectOnt.ab")
	public String selectOne(Album a,Model model, HttpSession session,HttpServletRequest request) {
		
		int bid = Integer.parseInt(request.getParameter("bid"));
		System.out.println(bid);
		
		ArrayList<Album> selectAD = null;
		
		selectAD = abs.selectAlbum(bid);
		
		model.addAttribute("selectAD",selectAD);
		
		int cNum[] = null;

		System.out.println(selectAD.size() + "???");
		String anumArray[] = new String[selectAD.size()];
		int k = 0;
		
			String Pname = null;
			String Cname = null;
			
		  for(int i = 0; i < selectAD.size(); i++) { 
			  Pname = "";
			  Cname = "";
			  Album album = (Album)selectAD.get(i);

			  String num = album.getChildrenNum(); 
			  
			  System.out.println(num+"?????????");
			  
			  String[] number = num.split(",");
		  
			  for(int j = 0; j < number.length; j++) {
			  
			  System.out.println(number[j]+"ss"); 
			  
			  
			  if(!(number[j].equals(""))&& !(number[j].equals("999"))) {
				  
				  String name = number[j];
				  
				   Cname = abs.selectAlbum(name);
				   Pname += "#"+Cname;
				 
			  }
			  
			  if(number[j] =="999") {
				  
				  Pname = "#??????";
				  
			  }
			  selectAD.get(i).setChildrenName(Pname);
			  
			  }
			  System.out.println(Cname+"$");
			  
			  
		  }
		  System.out.println("??????"+Pname);
		  System.out.println("list:::"+selectAD);
		  
		  model.addAttribute("selectAD", selectAD);
		  
		  
		return "album/albumDetail";
		  
	}
	
	@RequestMapping(value = "albumSearch.ab",method = RequestMethod.POST)
	public void albumSearch(ModelAndView mv,HttpServletResponse response, HttpSession session,HttpServletRequest request,@ModelAttribute("loginUser") Member loginUser ) {
		
		String input = request.getParameter("input");
		int bid = Integer.parseInt(request.getParameter("bid"));
		
		String classification = loginUser.getClassification();
		ArrayList<Album> data = null;
		
		if(classification.equals("?????????")) {
	         int num = loginUser.getUserNo();
	      }else if(classification.equals("?????????")) {
	    		KinGardenClasses CloginUser = (KinGardenClasses)session.getAttribute("childrenKing");
		  		int userNo = CloginUser.getChildrenNo();
		  		System.out.println(userNo+"?????????");
		  		
		  		int no = abs.selectSomeThing(input,userNo);
				
				data = abs.selectSelectAlbum(no,bid);
				System.out.println("data::::::"+data);
		  	
	      
	      }else {
	    	  int num = loginUser.getUserNo();
	      }
		
		
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












