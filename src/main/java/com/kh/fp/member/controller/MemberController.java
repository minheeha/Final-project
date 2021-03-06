package com.kh.fp.member.controller;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.fp.common.CommonUtils;
import com.kh.fp.common.Pagination;
import com.kh.fp.member.model.exception.JoinException;
import com.kh.fp.member.model.exception.LoginException;
import com.kh.fp.member.model.service.MemberService;
import com.kh.fp.member.model.vo.Attachment;
import com.kh.fp.member.model.vo.KidMember;
import com.kh.fp.member.model.vo.KinGardenClass;
import com.kh.fp.member.model.vo.KinGardenClasses;
import com.kh.fp.member.model.vo.KinderGarden;
import com.kh.fp.member.model.vo.Member;
import com.kh.fp.member.model.vo.OnOff;
import com.kh.fp.note.model.vo.PageInfo;

@Controller
@SessionAttributes({"loginUser","teacherKing" ,"childrenKing" , "at" , "of" ,"kga"})
public class MemberController {
	
	
	  @Autowired
	  private MemberService ms;
	  
	  @Autowired
	  private BCryptPasswordEncoder passwordEncoder;
	 
	  
	   public static String nullcheck(String str,  String Defaultvalue ) throws Exception
	    {
	         String ReturnDefault = "" ;
	         if (str == null)
	         {
	             ReturnDefault =  Defaultvalue ;
	         }
	         else if (str == "" )
	        {
	             ReturnDefault =  Defaultvalue ;
	         }
	         else
	         {
	                     ReturnDefault = str ;
	         }
	          return ReturnDefault ;
	    }
	   
	   
	  
	   public static String base64Encode(String str)  throws java.io.IOException {
	       sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	       byte[] strByte = str.getBytes();
	       String result = encoder.encode(strByte);
	       return result ;
	   }

	 
	   public static String base64Decode(String str)  throws java.io.IOException {
	       sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	       byte[] strByte = decoder.decodeBuffer(str);
	       String result = new String(strByte);
	       return result ;
	   }

	
	@RequestMapping(value="loginPage.me")
	public String loginPage() {
		
		return "account/join5";
	}
	
	
	
	@RequestMapping(value="login.me")
	public String login(Member m , Model model) {

		try {

			Member loginUser = ms.login(m);
			
			Attachment at = ms.childrenAt(loginUser);
			
			OnOff of = null;
			
			KinderGarden kga = null;
			
			if(loginUser.getClassification().equals("?????????")) {
				
			 of = ms.selectOnOff(loginUser);
			 
			 kga = ms.selectKinderName(loginUser.getUserNo());
			
			//???????????? ?????? 
			String usingDate = ms.selectUsingDate(loginUser.getUserNo());
			if(usingDate != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date originDate = new Date();
				Date today = new Date();
				originDate = format.parse(usingDate);
				int usingStatus = today.compareTo(originDate);
				
				loginUser.setUsingStatus(usingStatus);
			}
			
		
				int childrenCount = ms.childrenCount(loginUser);
				int teacherCount = ms.teacherCount(loginUser);
				int childrenCountN = ms.childrenCountN(loginUser);
				int teacherCountN = ms.teacherCountN(loginUser);
				
				model.addAttribute("childrenCount", childrenCount);
				model.addAttribute("teacherCount",teacherCount);
				model.addAttribute("childrenCountN", childrenCountN);
				model.addAttribute("teacherCountN",teacherCountN);
				
				
			}else if(loginUser.getClassification().equals("?????????")) {
				
				int teacherYn = ms.teacherYn(loginUser);
				
				if(teacherYn > 0) {
					
					int result = loginUser.getUserNo();
					
					KinGardenClasses teacherKing = ms.teacherKing(loginUser);
					
					loginUser.setUserNo(teacherKing.getKinderNo());
					
					kga = ms.selectKinderName(loginUser.getUserNo());
					
					of = ms.selectOnOff(loginUser);
					
					//???????????? ?????? 
					String usingDate = ms.selectUsingDate(kga.getKinderNo());
					if(usingDate != null) {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						Date originDate = new Date();
						Date today = new Date();
						originDate = format.parse(usingDate);
						int usingStatus = today.compareTo(originDate);
						
						loginUser.setUsingStatus(usingStatus);
					}
					
					
					int childrenCount = ms.childrenCount(loginUser);
					int childrenCountN = ms.childrenCountN(loginUser);
					
					
					model.addAttribute("teacherKing" , teacherKing);
					model.addAttribute("childrenCount", childrenCount);
					model.addAttribute("childrenCountN", childrenCountN);
					
					loginUser.setUserNo(result);
					
				}else {
					
					int teacherYYn = ms.teacherYYn(loginUser);
					
					if(teacherYYn > 0 ) {
						
						model.addAttribute("msg" ," ?????? ????????? ???????????? ???????????????. ?????? ???????????? ?????? ?????????. ");
						
						return "account/join5";
						
					}else {
						

						model.addAttribute("msg","????????? ?????? ???????????????. ????????? ??? ????????? ??????????????????.");
						model.addAttribute("teacher" , loginUser);
						return "join/searchGarden";
						
					}
					
					
				}
				
				
			}else if(loginUser.getClassification().equals("?????????")) {
				
				KidMember km = ms.childrenMember(loginUser);
				
				int childrenYn = ms.childrenYn(km);
				
				if(childrenYn > 0) {
					
					int result = loginUser.getUserNo();
					
					KinGardenClasses childrenKing = ms.childrenKing(km);
	
					model.addAttribute("childrenKing" , childrenKing);
					
					loginUser.setUserNo(childrenKing.getKinderNo());
					
				    kga = ms.selectKinderName(loginUser.getUserNo());
	
					of = ms.selectOnOff(loginUser);
					
					loginUser.setUserNo(result);
					
					//???????????? ?????? 
					String usingDate = ms.selectUsingDate(kga.getKinderNo());
					if(usingDate != null) {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						Date originDate = new Date();
						Date today = new Date();
						originDate = format.parse(usingDate);
						int usingStatus = today.compareTo(originDate);
						
						loginUser.setUsingStatus(usingStatus);
					}
					
				}else {
					
					int childrenYYn = ms.childrenYYn(km);
					
					if(childrenYYn > 0) {
						
						model.addAttribute("msg","?????? ????????? ???????????? ???????????????. ?????? ???????????? ?????? ?????????.");
						return "account/join5";
						
					}else {
						
						model.addAttribute("km",km);
						model.addAttribute("msg","????????? ?????? ???????????????. ????????? ??? ????????? ??????????????????.");
						return "join/searchGarden";
						
					}

				}
				
				
				
			}else if(loginUser.getClassification().equals("?????????")) {
				
				return "redirect:companyList.ad";
				
			}
			
			
			ArrayList list = ms.selectNolist(kga);
			
			model.addAttribute("kga",kga);
			model.addAttribute("of",of);
			model.addAttribute("at",at);
			model.addAttribute("loginUser",loginUser);
			model.addAttribute("list",list);
						
			return "main/parentsMain";
			
		
		} catch (LoginException e) {
			
			model.addAttribute("msg" ,e.getMessage());
			
			
			return "account/join5";

		
		} catch (ParseException e) {
			
			model.addAttribute("msg", "??????????????? ????????? ??? ????????????.");
			return "account/join5";
		}
		
		
	}
	
	@RequestMapping(value="logout.me")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		
		
		return "account/join5";
	}
	
	
	
	@RequestMapping(value="joinPage5.me")
	public String joinPage5(Member m , Model model){
		
		
		String encPassword = passwordEncoder.encode(m.getUserPwd());
		
		m.setUserPwd(encPassword);
		
		int result;
		
		try {
			
			result = ms.insertUser(m);
			
			
			Member selectNo = null;
			
			if(result > 0) {
				
				selectNo = ms.selectnumber(m);

			}
			
			if(selectNo != null ) {
				
				if(m.getClassification().equals("?????????")) {
					
					model.addAttribute("select",selectNo);
					
					return "join/kinrandEnrollment";
					
				}else if(m.getClassification().equals("?????????")) {
					
					model.addAttribute("select",selectNo);
					
					return "join/jointeacher";
				}else {
					model.addAttribute("select",selectNo);
					
					return "join/joinkid";
				}
				
			}
			
			return "";
			
			
		} catch (JoinException e) {
			
			model.addAttribute("msg" , e.getMessage());

			return "account/join4";
		}
		
		

		
	}
	
	@RequestMapping(value="sendemail.me")
	public ModelAndView sendEmail(String email ,String email2, ModelAndView mv) {
		
		System.out.println("email2 ??? ??? : " + email2);
		System.out.println("email ??? ??? : " + email);
		
		String host = "smtp.naver.com";
		String subject = "???????????? ???????????? ??????";
		String fromName = "???????????? ?????????";
		String from = "rudgus1005";
		String password = "alsrudgus0@";
		String to1 = email;
		
		int random = 0;
		
		random = (int)(Math.random()*99999)+10000;
		
		String content = null;
		
		if(email2 != null) {
			
			if(email2.equals("???????????????")) {
				
				content = "???????????? ??????????????? ??????????????? [" + random + "] ?????????. ????????? ??????????????????";
				
			}else {
				
				content = "???????????? ???????????? ??????????????? [" + random + "] ?????????. ????????? ??????????????????";
			}
			
		}
		
		
		
		try {
		
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol","smtp");
		props.put("mail.smtp.host",host);
		props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.user",from);
		props.put("mail.smtp.auth","true");
		
		Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(from,password);
			}
			
		});
		
		Message msg = new MimeMessage(mailSession);
		
		msg.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName,"UTF-8","B")));
		
		InternetAddress[] address1 = {new InternetAddress(to1)};
		msg.setRecipients(Message.RecipientType.TO,address1);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setContent(content, "text/html;charset=euc-kr");
		
		
		Transport.send(msg);
		
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		mv.addObject("random", random);
		mv.setViewName("jsonView");
		
		
		
		return mv;
	
		
	}
	
	@RequestMapping(value="idcheck.me")
	public ModelAndView idcheck(Member m, ModelAndView mv) {
	
		
		Member idcheck = ms.idcheck(m);
		
		
		int result = 0;
		
		if(idcheck != null) {
			result = 1;
		}else {
			result = 2;
		}
		
		
		mv.addObject("idcheck", result);
		mv.setViewName("jsonView");
		
		
		return mv;
	}
	
	@RequestMapping(value="kidjoin.me")
	public String kidjoin(KidMember km ,Model model ,HttpServletRequest request, @RequestParam(name="photo", required=false) MultipartFile photo) {
		
		Attachment at = new Attachment();
		
		km.setBirth(km.getBirth1() + "/" + km.getBirth2() + "/" + km.getBirth3());
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		String filePath = root + "\\uploadFiles";
		
		String originFileName = photo.getOriginalFilename();
		
		String ext = originFileName.substring(originFileName.lastIndexOf("."));
		
		String changeName = CommonUtils.getRandomString(); 
		
		try {
			
			at.setOrigineName(originFileName);
			at.setChangeName(changeName + ext);
			at.setFilePath(filePath);
			at.setFileLevel("1");
			at.setAttachType("??????");
			at.setUserNo(km.getUserNo());
			
	
			photo.transferTo(new File(filePath + "\\" + changeName + ext));
			
			int resultat = ms.insertAttachment(at);
		
			int result = ms.insertkid(km);
			
			if(result > 0 ) {
				
				int select = km.getUserNo();
				
				int selectki = ms.selectkid(select);
				
				
				km.setChildrenNo(selectki);
				
			}
			
			model.addAttribute("km",km);
			
			
		} catch (Exception e) {
			
			new File(filePath + "\\" + changeName + ext).delete();
			
			model.addAttribute("msg", "?????? ?????? ?????? ??????(BABY)??? ?????? ????????? ??????????????????. ?????? ??????????????????!");
			

			return "account/join5";
	
		}
		
		
		
		
		return "join/searchGarden";
	}
	
	@RequestMapping(value="teacherjoin.me")
	public String teacherjoin(Model model , Member mb ,HttpServletRequest request , @RequestParam(name="photo", required=false) MultipartFile photo) {
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		String filePath = root + "\\uploadFiles";
		
		String originFileName = photo.getOriginalFilename();
		
		String ext = originFileName.substring(originFileName.lastIndexOf("."));
		
		String changeName = CommonUtils.getRandomString(); 
		
		Attachment at = new Attachment();
		
		try {
			
			photo.transferTo(new File(filePath + "\\" + changeName + ext));
			
			at.setOrigineName(originFileName);
			at.setChangeName(changeName + ext);
			at.setFilePath(filePath);
			at.setFileLevel("1");
			at.setAttachType("??????");
			at.setUserNo(mb.getUserNo());
			
			int result = ms.insertTeacherphoto(at);
			
			Member teacher = ms.teacherAt(mb);
			
			model.addAttribute("teacher" , teacher);
			
			
			
			
		} catch (IllegalStateException | IOException e) {

			
			e.printStackTrace();
		}
		
		
		return "join/searchGarden";
	}
	
	
	
	@RequestMapping(value="kinrand.me")
	public String kininset(KinderGarden kg ,Model model) {
		
		kg.setAddress(kg.getSido()+ " " + kg.getSigungu() + " " + kg.getAddress3());
		
		int result = ms.kininsert(kg); 
		
		if(result > 0) {
			
			KinGardenClass kc = new KinGardenClass();
			
			kc.setKinderNo(kg.getKinderNo());
			kc.setClassName(kg.getClassName());		
			
			int insert = ms.Kinclassinsert(kc); 
			
			ArrayList list = ms.kinclassselect(kc);
			
			//int classinsert = ms.classinsert(list);
			
			int classonoff = ms.classonoff(kg);
			
			System.out.println("??????????????? kg : " + kg);
			
			model.addAttribute("kg" , kg);
			
		}
		
		
		return "join/joinkinder";
	}

	@RequestMapping(value="teacheron.me")
	public String teacherOn(Member m ,Model model, int currentPage , int currentPage2) {
		
		
		int listCount = ms.teacherMeCount(m);
		
		int listCount2 = ms.teacherMe2Count(m);
		
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		PageInfo pi2 = Pagination.getPageInfo(currentPage2, listCount2);
		
		
		ArrayList listMe = ms.teacherMe(m,pi2);
		
		ArrayList listMe2 = ms.teacherMe2(m,pi);
		
		ArrayList listMe3 = ms.childrenMe3(m);
		
		model.addAttribute("listMe", listMe);
		model.addAttribute("listMe2",listMe2);
		model.addAttribute("listMe3",listMe3);
		model.addAttribute("pi",pi);
		model.addAttribute("pi2",pi2);

		
		
		
		return "kingteacher/teachermanagement";
	}
	
	@RequestMapping(value="childrenMe.me")
	public String childrenMe(Member m , Model model , int currentPage , int currentPage2) {
		
		int listCount = ms.childrenMeCount(m);
		
		int listCount2 = ms.childrenMeCount2(m);
		
	
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		PageInfo pi2 = Pagination.getPageInfo(currentPage2,listCount2);
	
		ArrayList listMe = ms.childrenMe(m, pi2);
		
		ArrayList listMe2 = ms.childrenMe2(m , pi);
		
		ArrayList listMe3 = ms.childrenMe3(m);
		
		
		model.addAttribute("listMe",listMe);
		model.addAttribute("listMe2", listMe2);
		model.addAttribute("listMe3", listMe3);
		model.addAttribute("pi",pi);
		model.addAttribute("pi2",pi2);

		
		
		return"kingteacher/childrenmanagement";
	}
	

	@RequestMapping(value = "IndiviualhomeWorkWrite.me")
	public ModelAndView individual(ModelAndView mv, HttpServletRequest request, String[] array) {
		System.out.println("????????????????");
		System.out.println("array:::" + array);
		String[] num = request.getParameterValues("array");
		HttpSession session = request.getSession();

		session.setAttribute("num", num);
		System.out.println(num);

		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping(value="phoneMe.me")
	public ModelAndView phoneMe(ModelAndView mv , HttpServletRequest request) throws IOException, Exception {

		
		String msg2 = "";
		int random = (int)(Math.random()* 99999)+1; 
		
		msg2 = "???????????? ?????? ?????? ?????????. " + random;

	    String  action  = nullcheck(request.getParameter("action"), "");
	    
	    System.out.println("??????? ????????? ? " + random);
	    
	    if(action.equals("go")) {

	        
	        String sms_url = "https://sslsms.cafe24.com/sms_sender.php"; // SMS ???????????? URL
	        String user_id = base64Encode("rudgus1005"); // SMS?????????
	        String secure = base64Encode("7c1496e3ee0322fb676d324aaff8b66e");//?????????
	        String msg = base64Encode(nullcheck(msg2 , ""));
	        String rphone = base64Encode(nullcheck(request.getParameter("rphone"), ""));
	        String sphone1 = base64Encode(nullcheck(request.getParameter("sphone1"), ""));
	        String sphone2 = base64Encode(nullcheck(request.getParameter("sphone2"), ""));
	        String sphone3 = base64Encode(nullcheck(request.getParameter("sphone3"), ""));
	        String rdate = base64Encode(nullcheck(request.getParameter("rdate"), ""));
	        String rtime = base64Encode(nullcheck(request.getParameter("rtime"), ""));
	        String mode = base64Encode("1");
	        String subject = "";
	      
	        String testflag = base64Encode(nullcheck(request.getParameter("testflag"), ""));
	        String destination = base64Encode(nullcheck(request.getParameter("destination"), ""));
	        String repeatFlag = base64Encode(nullcheck(request.getParameter("repeatFlag"), ""));
	        String repeatNum = base64Encode(nullcheck(request.getParameter("repeatNum"), ""));
	        String repeatTime = base64Encode(nullcheck(request.getParameter("repeatTime"), ""));
	        String returnurl = nullcheck(request.getParameter("returnurl"), "");
	        String nointeractive = nullcheck(request.getParameter("nointeractive"), "");
	        String smsType = base64Encode(nullcheck(request.getParameter("smsType"), ""));

	        String[] host_info = sms_url.split("/");
	        String host = host_info[2];
	        String path = "/" + host_info[3];
	        int port = 80;

	        // ????????? ?????? ?????? ??????
	        String arrKey[]
	            = new String[] {"user_id","secure","msg", "rphone","sphone1","sphone2","sphone3","rdate","rtime"
	                        ,"mode","testflag","destination","repeatFlag","repeatNum", "repeatTime", "smsType", "subject"};
	        String valKey[]= new String[arrKey.length] ;
	        valKey[0] = user_id;
	        valKey[1] = secure;
	        valKey[2] = msg;
	        valKey[3] = rphone;
	        valKey[4] = sphone1;
	        valKey[5] = sphone2;
	        valKey[6] = sphone3;
	        valKey[7] = rdate;
	        valKey[8] = rtime;
	        valKey[9] = mode;
	        valKey[10] = testflag;
	        valKey[11] = destination;
	        valKey[12] = repeatFlag;
	        valKey[13] = repeatNum;
	        valKey[14] = repeatTime;
	        valKey[15] = smsType;
	        valKey[16] = subject;

	        String boundary = "";
	        Random rnd = new Random();
	        String rndKey = Integer.toString(rnd.nextInt(32000));
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytData = rndKey.getBytes();
	        md.update(bytData);
	        byte[] digest = md.digest();
	        for(int i =0;i<digest.length;i++)
	        {
	            boundary = boundary + Integer.toHexString(digest[i] & 0xFF);
	        }
	        boundary = "---------------------"+boundary.substring(0,11);

	        // ?????? ??????
	        String data = "";
	        String index = "";
	        String value = "";
	        for (int i=0;i<arrKey.length; i++)
	        {
	            index =  arrKey[i];
	            value = valKey[i];
	            data +="--"+boundary+"\r\n";
	            data += "Content-Disposition: form-data; name=\""+index+"\"\r\n";
	            data += "\r\n"+value+"\r\n";
	            data +="--"+boundary+"\r\n";
	        }

	        //out.println(data);

	        InetAddress addr = InetAddress.getByName(host);
	        Socket socket = new Socket(host, port);
	        // ?????? ??????
	        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
	        wr.write("POST "+path+" HTTP/1.0\r\n");
	        wr.write("Content-Length: "+data.length()+"\r\n");
	        wr.write("Content-type: multipart/form-data, boundary="+boundary+"\r\n");
	        wr.write("\r\n");

	        // ????????? ??????
	        wr.write(data);
	        wr.flush();

	        // ????????? ??????
	        BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
	        String line;
	        String alert = "";
	        ArrayList tmpArr = new ArrayList();
	        while ((line = rd.readLine()) != null) {
	            tmpArr.add(line);
	        }
	        wr.close();
	        rd.close();

	        String tmpMsg = (String)tmpArr.get(tmpArr.size()-1);
	        String[] rMsg = tmpMsg.split(",");
	        String Result= rMsg[0]; //????????????
	        String Count= ""; //????????????
	        if(rMsg.length>1) {Count= rMsg[1]; }

	     

	    }
	    
	            
	    try {
	           String apiUrl =  "https://sslsms.cafe24.com/smsSenderPhone.php";
	            String userAgent = "Mozilla/5.0";
	            String postParams = "userId=rudgus1005&passwd=7c1496e3ee0322fb676d324aaff8b66e";
	            URL obj = new URL(apiUrl);
	            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	            con.setRequestMethod("POST");
	            con.setRequestProperty("User-Agent", userAgent);

	            // For POST only - START
	            con.setDoOutput(true);
	            OutputStream os = con.getOutputStream();
	            os.write(postParams.getBytes());
	            os.flush();
	            os.close();
	            // For POST only - END

	            int responseCode = con.getResponseCode();

	            if (responseCode == HttpURLConnection.HTTP_OK) { //success
	                BufferedReader in = new BufferedReader(new InputStreamReader(
	                        con.getInputStream()));
	                String inputLine;
	                StringBuffer buf = new StringBuffer();

	                while ((inputLine = in.readLine()) != null) {
	                    buf.append(inputLine);
	                }
	                in.close();
	            } else {
	            }
	    } catch(IOException ex){

	    }
	            
	    
	    mv.addObject("random" , random);
		mv.setViewName("jsonView");
		
		System.out.println("??????????????? mv ??? : " + mv);
		return mv;
	}
	
	@RequestMapping(value="selectId.me")
	public ModelAndView selectId(ModelAndView mv , String phone) {
		
		System.out.println("????????? phone :" + phone);
		
		Member me = ms.selectId(phone);
		
		mv.addObject("me", me);
		mv.setViewName("jsonView");
		
		System.out.println("??? ????????? ?????? : " + me);
		
		
		return mv;
	}
	
	@RequestMapping(value="selectemialId")
	public ModelAndView selectemailId(ModelAndView mv , String email) {
		

		
		Member me = ms.selectemailId(email);
		
		mv.addObject("me",me);
		mv.setViewName("jsonView");
		
		return mv;
		
	}
	
	@RequestMapping(value="findPwdId.me")
	public ModelAndView findPwdId(ModelAndView mv , String findId) {
		
		Member id = ms.findPwdId(findId);
		
		mv.addObject("id",id);
		mv.setViewName("jsonView");
		
		
		return mv;
	}
	
	@RequestMapping(value="RePwd.me")
	public ModelAndView RePwd(ModelAndView mv , Member mb) {
		
		String encPassword = passwordEncoder.encode(mb.getUserPwd());
		
		mb.setUserPwd(encPassword);
		
		int updatePwd = ms.RePwd(mb);
		
		mv.addObject("updatePwd",updatePwd);
		mv.setViewName("jsonView");
		
		
		
		return mv;
	}
	
	@RequestMapping(value="joinkinder.me")
	public String joinkinder(Model model , Member mb ,HttpServletRequest request , @RequestParam(name="photo", required=false) MultipartFile photo) {
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		String filePath = root + "\\uploadFiles";
		
		String originFileName = photo.getOriginalFilename();
		
		String ext = originFileName.substring(originFileName.lastIndexOf("."));
		
		String changeName = CommonUtils.getRandomString(); 
		
		Attachment at = new Attachment();
		
		
		try {
			
			photo.transferTo(new File(filePath + "\\" + changeName + ext));
			
			at.setOrigineName(originFileName);
			at.setChangeName(changeName + ext);
			at.setFilePath(filePath);
			at.setFileLevel("1");
			at.setAttachType("??????");
			at.setUserNo(mb.getUserNo());
			
			int result = ms.insertTeacherphoto(at);
			
			Member teacher = ms.teacherAt(mb);
			
			model.addAttribute("msg" , "??????????????? ?????? ???????????????!");
			
			
			
			
		} catch (IllegalStateException | IOException e) {

			
			e.printStackTrace();
		}
		
		
		return "account/join5";
	}
	
	@RequestMapping(value="Myimgchange.me")
	public String myimgchange(Attachment at ,Model model ,HttpServletRequest request, @RequestParam(name="photo", required=false) MultipartFile photo) {
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		String filePath = root + "\\uploadFiles";
		
		String originFileName = photo.getOriginalFilename();
		
		String ext = originFileName.substring(originFileName.lastIndexOf("."));
		
		String changeName = CommonUtils.getRandomString(); 
		
		String[] delete = null;
		
		delete = at.getChangeName().split("/");
		

		
		try {
		
			new File(filePath + "\\" + delete[4]).delete();

			
			at.setOrigineName(originFileName);
			at.setChangeName(changeName + ext);
			at.setFilePath(filePath);
			at.setFileLevel("1");
			at.setAttachType("??????");
			
			
			photo.transferTo(new File(filePath + "\\" + changeName + ext));
			
			int result = ms.myimgchange(at);
			
			if(result > 0) {
				model.addAttribute("at",at);
				model.addAttribute("msg","??????????????? ?????????????????????.");
				
			}
			
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "member/MyPage";
	}
	
	
	@RequestMapping(value="Myinfochange.me")
	public String Myinfochange(Member mb , Model model , @SessionAttribute("loginUser") Member loginUser) {
		
		mb.setEmail(loginUser.getEmail());
		mb.setUserId(loginUser.getUserId());
		mb.setPhone(loginUser.getPhone());
		mb.setEnrollDate(loginUser.getEnrollDate());
		mb.setDeleteDate(loginUser.getDeleteDate());
		mb.setClassification(loginUser.getClassification());
		mb.setStatus(loginUser.getStatus());
		mb.setUsingStatus(loginUser.getUsingStatus());
		
		if(mb.getUserPwd() != "") {
			String encPassword = passwordEncoder.encode(mb.getUserPwd());
			mb.setUserPwd(encPassword);
			
		}
		
		int result = ms.myinfochange(mb);
		
		if(result > 0) {
			
			model.addAttribute("msg" , "??????????????? ?????? ???????????????.");
			model.addAttribute("loginUser",mb);
			
		}

		
		return "member/MyPage";
	}
	
	@RequestMapping(value="myPageUserPwdcheck.me")
	public String myPageUserPwdcheck(Member mb , Model model , @SessionAttribute("loginUser") Member loginUser) {
		
		boolean userpwd = passwordEncoder.matches(mb.getUserPwd(), loginUser.getUserPwd());
		
		if(userpwd) {
			
			return "member/MyPage";
			
		}else {
			
			model.addAttribute("msg", "??????????????? ????????????.");
			
			return "main/parentsMain";
			
		}
	

	}
	
	@RequestMapping(value="experience.me")
	public String experience(Model model ,String classification) {

		Member loginUser = null;
		OnOff of = null;
		KinderGarden kga = null;
		
		if(classification.equals("?????????")) {
			
			classification = "??????????????????";
			loginUser = ms.experience(classification);
			
			KidMember km = ms.childrenMember(loginUser);
			
			int childrenYn = ms.childrenYn(km);
			
			int result = loginUser.getUserNo();
			
			KinGardenClasses childrenKing = ms.childrenKing(km);

			model.addAttribute("childrenKing" , childrenKing);
			
			loginUser.setUserNo(childrenKing.getKinderNo());
			
			kga = ms.selectKinderName(loginUser.getUserNo());

			of = ms.selectOnOff(loginUser);
			
			loginUser.setUserNo(result);
			
		}else if(classification.equals("?????????")) {
			
			classification = "??????????????????";
			loginUser = ms.experience(classification);
			of = ms.selectOnOff(loginUser);
			 kga = ms.selectKinderName(loginUser.getUserNo());
			 
			 int childrenCount = ms.childrenCount(loginUser);
				int teacherCount = ms.teacherCount(loginUser);
				int childrenCountN = ms.childrenCountN(loginUser);
				int teacherCountN = ms.teacherCountN(loginUser);
				
				model.addAttribute("childrenCount", childrenCount);
				model.addAttribute("teacherCount",teacherCount);
				model.addAttribute("childrenCountN", childrenCountN);
				model.addAttribute("teacherCountN",teacherCountN);
				
			
		}else {
			
			classification = "??????????????????";
			
			loginUser = ms.experience(classification);
			
			int result = loginUser.getUserNo();
			
			KinGardenClasses teacherKing = ms.teacherKing(loginUser);
			
			loginUser.setUserNo(teacherKing.getKinderNo());
			
			kga = ms.selectKinderName(loginUser.getUserNo());
			
			of = ms.selectOnOff(loginUser);
			
			int childrenCount = ms.childrenCount(loginUser);
			int childrenCountN = ms.childrenCountN(loginUser);
			
			
			model.addAttribute("teacherKing" , teacherKing);
			model.addAttribute("childrenCount", childrenCount);
			model.addAttribute("childrenCountN", childrenCountN);
			
			loginUser.setUserNo(result);
			
		}
		
		Attachment at = ms.childrenAt(loginUser);

		
		model.addAttribute("loginUser" , loginUser);
		model.addAttribute("at",at);
		model.addAttribute("of",of);
		model.addAttribute("kga",kga);
		
		
		
		return "main/parentsMain";
	}

 
	

}
