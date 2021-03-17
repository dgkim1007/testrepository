package com.naver.dgkim1007.dao;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.naver.dgkim1007.entities.Buy;

public interface BuyDao {
	public Buy selectOne(int seq) throws Exception;

	public int insertRow(Buy buy) throws Exception;

	public int updateRow(Map json) throws Exception;

	public ArrayList<Buy> selectBuyRollup(Buy buy) throws Exception;

	public ArrayList<Buy> selectBuyFindRollup(Model model) throws Exception;

	public int deleteAjax(int seq) throws Exception;

	public int getMaxNo(Buy buy) throws Exception;

	public int getMaxHang(Buy buy) throws Exception;

	public int buyBalanceAdd(Buy buy) throws Exception;

	public int buyBalanceSub(Map json) throws Exception;

	public int buyBalanceAjaxAdd(Map json) throws Exception;
}