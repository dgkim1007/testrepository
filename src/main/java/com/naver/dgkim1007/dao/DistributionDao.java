package com.naver.dgkim1007.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.naver.dgkim1007.entities.Balance;
import com.naver.dgkim1007.entities.Vender;

public interface DistributionDao {

	public ArrayList<Balance> balanceList(String yyyy) throws Exception;

	public Vender venderSelectOne(String code) throws Exception;

	public Balance balanceSelectOne(HashMap hash) throws Exception;

	public int balanceUpdateRow(Balance balance) throws Exception;

	public int balanceUpdateAjax(HashMap hash) throws Exception;

	public int balanceDeleteAjax(HashMap hash) throws Exception;

	public void balanceYYFinalBeforeDelete(String nextyyyy) throws Exception;

	public void balanceYYFinal(ArrayList<Balance> balances) throws Exception;

	public void balanceMMFinal(HashMap hash) throws Exception;

}