package com.naver.dgkim1007.entities;

import org.springframework.stereotype.Component;

@Component
public class Product {
	private String code; // 상품코드 13
	private String name; // 상품명 30
	private String capacity; // 규격 30
	private int buyprice; // 매입단가 9(11)
	private int saleprice;// 매출단가 9(11)
	private int preyystock;// 전년재고 9(7)
	private int premmstock;// 전월재고 9(7)
	private int preddstock;// 전일재고 9(7)
	private int buy01; // 1월 매입 9(7)
	private int buy02;
	private int buy03;
	private int buy04;
	private int buy05;
	private int buy06;
	private int buy07;
	private int buy08;
	private int buy09;
	private int buy10;
	private int buy11;
	private int buy12; // 12월 매입
	private int sale01; // 1월 매출
	private int sale02;
	private int sale03;
	private int sale04;
	private int sale05;
	private int sale06;
	private int sale07;
	private int sale08;
	private int sale09;
	private int sale10;
	private int sale11;
	private int sale12; // 12월 매출
	private int stock; // 현재재고
	private String explanation; // 상품설명 (50)

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

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public int getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(int buyprice) {
		this.buyprice = buyprice;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public int getPreyystock() {
		return preyystock;
	}

	public void setPreyystock(int preyystock) {
		this.preyystock = preyystock;
	}

	public int getPremmstock() {
		return premmstock;
	}

	public void setPremmstock(int premmstock) {
		this.premmstock = premmstock;
	}

	public int getPreddstock() {
		return preddstock;
	}

	public void setPreddstock(int preddstock) {
		this.preddstock = preddstock;
	}

	public int getBuy01() {
		return buy01;
	}

	public void setBuy01(int buy01) {
		this.buy01 = buy01;
	}

	public int getBuy02() {
		return buy02;
	}

	public void setBuy02(int buy02) {
		this.buy02 = buy02;
	}

	public int getBuy03() {
		return buy03;
	}

	public void setBuy03(int buy03) {
		this.buy03 = buy03;
	}

	public int getBuy04() {
		return buy04;
	}

	public void setBuy04(int buy04) {
		this.buy04 = buy04;
	}

	public int getBuy05() {
		return buy05;
	}

	public void setBuy05(int buy05) {
		this.buy05 = buy05;
	}

	public int getBuy06() {
		return buy06;
	}

	public void setBuy06(int buy06) {
		this.buy06 = buy06;
	}

	public int getBuy07() {
		return buy07;
	}

	public void setBuy07(int buy07) {
		this.buy07 = buy07;
	}

	public int getBuy08() {
		return buy08;
	}

	public void setBuy08(int buy08) {
		this.buy08 = buy08;
	}

	public int getBuy09() {
		return buy09;
	}

	public void setBuy09(int buy09) {
		this.buy09 = buy09;
	}

	public int getBuy10() {
		return buy10;
	}

	public void setBuy10(int buy10) {
		this.buy10 = buy10;
	}

	public int getBuy11() {
		return buy11;
	}

	public void setBuy11(int buy11) {
		this.buy11 = buy11;
	}

	public int getBuy12() {
		return buy12;
	}

	public void setBuy12(int buy12) {
		this.buy12 = buy12;
	}

	public int getSale01() {
		return sale01;
	}

	public void setSale01(int sale01) {
		this.sale01 = sale01;
	}

	public int getSale02() {
		return sale02;
	}

	public void setSale02(int sale02) {
		this.sale02 = sale02;
	}

	public int getSale03() {
		return sale03;
	}

	public void setSale03(int sale03) {
		this.sale03 = sale03;
	}

	public int getSale04() {
		return sale04;
	}

	public void setSale04(int sale04) {
		this.sale04 = sale04;
	}

	public int getSale05() {
		return sale05;
	}

	public void setSale05(int sale05) {
		this.sale05 = sale05;
	}

	public int getSale06() {
		return sale06;
	}

	public void setSale06(int sale06) {
		this.sale06 = sale06;
	}

	public int getSale07() {
		return sale07;
	}

	public void setSale07(int sale07) {
		this.sale07 = sale07;
	}

	public int getSale08() {
		return sale08;
	}

	public void setSale08(int sale08) {
		this.sale08 = sale08;
	}

	public int getSale09() {
		return sale09;
	}

	public void setSale09(int sale09) {
		this.sale09 = sale09;
	}

	public int getSale10() {
		return sale10;
	}

	public void setSale10(int sale10) {
		this.sale10 = sale10;
	}

	public int getSale11() {
		return sale11;
	}

	public void setSale11(int sale11) {
		this.sale11 = sale11;
	}

	public int getSale12() {
		return sale12;
	}

	public void setSale12(int sale12) {
		this.sale12 = sale12;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

}
