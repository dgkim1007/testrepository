package com.naver.dgkim1007.entities;

import org.springframework.stereotype.Component;

@Component
public class Salary {
	private String empno;
	private String depart;
	private String name;
	private int partner;
	private int dependent20;
	private int dependent60;
	private int disabled;
	private int womanpower;
	private int pay;
	private int extra;
	private String yn;

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPartner() {
		return partner;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public int getDependent20() {
		return dependent20;
	}

	public void setDependent20(int dependent20) {
		this.dependent20 = dependent20;
	}

	public int getDependent60() {
		return dependent60;
	}

	public void setDependent60(int dependent60) {
		this.dependent60 = dependent60;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public int getWomanpower() {
		return womanpower;
	}

	public void setWomanpower(int womanpower) {
		this.womanpower = womanpower;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public int getExtra() {
		return extra;
	}

	public void setExtra(int extra) {
		this.extra = extra;
	}

	public String getYn() {
		return yn;
	}

	public void setYn(String yn) {
		this.yn = yn;
	}

}
