package com.naver.dgkim1007.dao;

import java.util.ArrayList;

import com.naver.dgkim1007.entities.Board;
import com.naver.dgkim1007.entities.BoardPaging;

public interface BoardDao {
	public Board selectOne(int b_seq) throws Exception;

	public int insertRow(Board Board) throws Exception;

	public int updateRow(Board Board) throws Exception;

	public ArrayList<Board> selectAll() throws Exception;

	public int updateAjax(Board Board) throws Exception;

	public int deleteRow(int b_seq) throws Exception;

	public ArrayList<Board> selectPageList(BoardPaging boardpaging) throws Exception;

	public ArrayList<Board> findListBoard(BoardPaging boardpaging) throws Exception;

	public int selectCountFirst() throws Exception;

	public int selectCount(BoardPaging boardpaging) throws Exception;

	public void addHit(int b_seq) throws Exception;

	public int insertReplyRow(Board Board) throws Exception;

	public int selectMaxStep(int b_ref) throws Exception;

}