package com.naver.dgkim1007.entities;

import org.springframework.stereotype.Component;

@Component
public class SalaryRollViewToBean {
	private String yyyy;
	private String mm;
	private String empno;
	private String depart;
	private String name;
	private int partner;
	private int dependent20;
	private int dependent60;
	private int disabled;
	private int womanpower;
	private int mmpay;
	private int insurance;
	private int tax;
	private int finalpay;
	private int lastamount;

	public String getYyyy() {
		return yyyy;
	}

	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

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

	public int getMmpay() {
		return mmpay;
	}

	public void setMmpay(int mmpay) {
		this.mmpay = mmpay;
	}

	public int getInsurance() {
		return insurance;
	}

	public void setInsurance(int insurance) {
		this.insurance = insurance;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getFinalpay() {
		return finalpay;
	}

	public void setFinalpay(int finalpay) {
		this.finalpay = finalpay;
	}

	public int getLastamount() {
		return lastamount;
	}

	public void setLastamount(int lastamount) {
		this.lastamount = lastamount;
	}

}
