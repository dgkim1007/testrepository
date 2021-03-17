package com.naver.dgkim1007.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.naver.dgkim1007.entities.Buy;
import com.naver.dgkim1007.entities.Product;

public interface ProductDao {
	public Product selectOne(String code) throws Exception;

	public int insertRow(Product product) throws Exception;

	public int updateRow(Product product) throws Exception;

	public ArrayList<Product> selectAll() throws Exception;

	public int updateAjax(HashMap hash) throws Exception;

	public int deleteAjax(String code) throws Exception;

	public int buyProductSub(Map json) throws Exception;

	public int buyProductAddJson(Map json) throws Exception;

	public int buyProductAdd(Buy buy) throws Exception;

	public void ddProductFinal() throws Exception;

	public void mmProductFinal() throws Exception;

	public void yyProductFinal() throws Exception;

}