package com.naver.dgkim1007.entities;

import org.springframework.stereotype.Component;

@Component
public class Vender {
	private String code;
	private String name;
	private String busno1;
	private String busno2;
	private String busno3;
	private String ceoname;
	private String zipcode;
	private String newaddr;
	private String oldaddr;
	private String detailaddr;
	private String officeno1;
	private String officeno2;
	private String officeno3;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusno1() {
		return busno1;
	}

	public void setBusno1(String busno1) {
		this.busno1 = busno1;
	}

	public String getBusno2() {
		return busno2;
	}

	public void setBusno2(String busno2) {
		this.busno2 = busno2;
	}

	public String getBusno3() {
		return busno3;
	}

	public void setBusno3(String busno3) {
		this.busno3 = busno3;
	}

	public String getCeoname() {
		return ceoname;
	}

	public void setCeoname(String ceoname) {
		this.ceoname = ceoname;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getNewaddr() {
		return newaddr;
	}

	public void setNewaddr(String newaddr) {
		this.newaddr = newaddr;
	}

	public String getOldaddr() {
		return oldaddr;
	}

	public void setOldaddr(String oldaddr) {
		this.oldaddr = oldaddr;
	}

	public String getDetailaddr() {
		return detailaddr;
	}

	public void setDetailaddr(String detailaddr) {
		this.detailaddr = detailaddr;
	}

	public String getOfficeno1() {
		return officeno1;
	}

	public void setOfficeno1(String officeno1) {
		this.officeno1 = officeno1;
	}

	public String getOfficeno2() {
		return officeno2;
	}

	public void setOfficeno2(String officeno2) {
		this.officeno2 = officeno2;
	}

	public String getOfficeno3() {
		return officeno3;
	}

	public void setOfficeno3(String officeno3) {
		this.officeno3 = officeno3;
	}

}
