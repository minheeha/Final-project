package com.kh.fp.note.model.service;

import java.util.ArrayList;

import com.kh.fp.member.model.vo.Member;
import com.kh.fp.note.model.exception.NoteException;
import com.kh.fp.note.model.vo.noteKindergarden;

public interface NoteService {

	ArrayList<noteKindergarden> selectUserList() throws NoteException;

}