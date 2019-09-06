package com.kh.fp.dosage.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.fp.dosage.model.dao.DosageDao;
import com.kh.fp.dosage.model.vo.DosageVO;

@Service
public class DosageServiceImpl implements DosageService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private DosageDao dd;

	@Override
	public int insertDosageRequest(DosageVO d) {

		System.out.println("투약의뢰서 서비스 in!");

		int insertDosageRequest = dd.insertDosageRequest(sqlSession, d);

		return 0;
	}



}
