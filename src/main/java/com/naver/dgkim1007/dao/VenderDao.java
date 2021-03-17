package com.naver.dgkim1007.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.naver.dgkim1007.entities.Vender;

public interface VenderDao {
	public Vender selectOne(String code) throws Exception;

	public int insertRow(Vender vender) throws Exception;

	public int updateRow(Vender vender) throws Exception;

	public ArrayList<Vender> selectAll() throws Exception;

	public int updateAjax(HashMap hash) throws Exception;

	public int deleteAjax(String code) throws Exception;
}