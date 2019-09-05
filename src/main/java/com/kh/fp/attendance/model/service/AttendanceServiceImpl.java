package com.kh.fp.attendance.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.fp.attendance.model.dao.AttendanceDao;
import com.kh.fp.attendance.model.exception.DailyException;
import com.kh.fp.attendance.model.vo.Children;

@Service
public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	private AttendanceDao ad;
	@Autowired
	private SqlSessionTemplate sqlSession;


	@Override
	public ArrayList<Children> dailyAttendance() throws DailyException {
		return ad.dailyAttendance(sqlSession);

	}

}
