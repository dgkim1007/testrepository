package com.naver.dgkim1007.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.naver.dgkim1007.entities.Salary;
import com.naver.dgkim1007.entities.SalaryRoll;
import com.naver.dgkim1007.entities.SalaryRollViewToBean;

public interface SalaryDao {
	public Salary selectOne(String empno) throws Exception;

	public int insertRow(Salary salary) throws Exception;

	public int updateRow(Salary salary) throws Exception;

	public ArrayList<Salary> selectAll() throws Exception;

	public ArrayList<Salary> selectTaxYes() throws Exception;

	public int updateAjax(Salary salary) throws Exception;

	public int deleteAjax(String empno) throws Exception;

	public int deleteSalaryRollbefore(HashMap yyyymm) throws Exception;

	public int insertSalaryRoll(SalaryRoll salaryroll) throws Exception;

	public ArrayList<SalaryRollViewToBean> selectSalaryRollView(HashMap yyyymm) throws Exception;
}