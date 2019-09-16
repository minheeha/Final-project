package com.kh.fp.notice.model.service;

import java.util.ArrayList;

import org.apache.tools.ant.Project;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kh.fp.note.model.vo.PageInfo;
import com.kh.fp.notice.model.dao.NoticeDao;
import com.kh.fp.notice.model.exception.NoticeException;
import com.kh.fp.notice.model.vo.Notice;
import com.kh.fp.notice.model.vo.NoticeWho;

@Service
public class NoticeServiceImpl implements NoticeService {
		
	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private NoticeDao nd;

	@Override
	public String insertNotice(Notice n) throws NoticeException {
		
		System.out.println("여기는 공지사항 서비스");
		
		String insertNotice = nd.insertNotice(sqlSession,n);
		
		return insertNotice;
		
	}

	@Override
	public ArrayList selectWho(int userNo) {
		System.out.println("여기는 누구일까 서비스");
		
		ArrayList Who = nd.selectWho(sqlSession,userNo);
		System.out.println(Who);
		return Who;
	}


	

	@Override
	public int getListCount(NoticeWho noticeWho) {
		
		return nd.getListCount(sqlSession,noticeWho);
	}

	

	@Override
	public ArrayList<Notice> selectProjectList(PageInfo pi, NoticeWho noticeWho) {
		
		ArrayList<Notice>list = nd.selectBoardList(sqlSession,pi,noticeWho);

		System.out.println("서비스"+list);
		
		return list;
	}


	@Override
	public Notice selectNotice(int bid) throws NoticeException {
		
		//int result = nd.updateCount(sqlSession,bid);
		
		Notice n;
		
		n = nd.selectOneNotice(sqlSession,bid);
		
		
		return n;
	}

	@Override
	public int UpdateNoticeStatus(int bid, String status) {

		int result = nd.updateNoticeStatus(bid,status,sqlSession);
		
		
		return result;
	}

	

}























