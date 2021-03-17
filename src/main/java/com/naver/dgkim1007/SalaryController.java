package com.naver.dgkim1007;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naver.dgkim1007.dao.SalaryDao;
import com.naver.dgkim1007.entities.Salary;
import com.naver.dgkim1007.entities.SalaryRoll;
import com.naver.dgkim1007.entities.SalaryRollViewToBean;

@Controller
public class SalaryController {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	Salary salary;
	@Autowired
	SalaryRoll salaryroll;

	@RequestMapping(value = "/salaryConfirmAjax", method = RequestMethod.POST)
	@ResponseBody
	public String emailConfirmAjax(@RequestParam String empno) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		Salary data = dao.selectOne(empno);
		String row = "y";
		if (data == null) {
			row = "n";
		}
		return row;
	}

	@RequestMapping(value = "/salaryInsert", method = RequestMethod.GET)
	public String salaryInsert(Locale locale, Model model) {
		return "salary/salary_insert";
	}

	@RequestMapping(value = "/salary_insertSave", method = RequestMethod.POST)
	public String salary_insertSave(Model model, @ModelAttribute Salary salary) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		dao.insertRow(salary);
		return "index";
	}

	@RequestMapping(value = "/salaryUpdate", method = RequestMethod.GET)
	public String salaryUpdate(Locale locale, Model model, @RequestParam String empno) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		salary = dao.selectOne(empno);
		model.addAttribute("salary", salary);
		return "salary/salary_update";
	}

	@RequestMapping(value = "/salaryUpdateSave", method = RequestMethod.POST)
	public String salaryUpdateSave(Model model, @ModelAttribute Salary salary) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		dao.updateRow(salary);
		return "index";
	}

	@RequestMapping(value = "/salaryList", method = RequestMethod.GET)
	public String salaryList(Locale locale, Model model) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		ArrayList<Salary> salarys = dao.selectAll();
		model.addAttribute("salarys", salarys);
		String arryns[] = new String[2];
		arryns[0] = "y";
		arryns[1] = "n";
		model.addAttribute("arryns", arryns);
		return "salary/salary_list";
	}

	@RequestMapping(value = "/salaryUpdateAjax", method = RequestMethod.POST)
	@ResponseBody
	public String salaryUpdateAjax(@RequestParam String empno, @RequestParam String yn) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		salary.setEmpno(empno);
		salary.setYn(yn);
		int result = dao.updateAjax(salary);
		if (result > 0) {
			return "y";
		} else {
			return "n";
		}
	}

	@RequestMapping(value = "/salaryDeleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public String salaryDeleteAjax(@RequestParam String empno) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		int result = dao.deleteAjax(empno);
		if (result > 0) {
			return "y";
		} else {
			return "n.";
		}
	}

	@RequestMapping(value = "/salaryTax", method = RequestMethod.GET)
	public String salaryTax(Model model) throws Exception {
		String yyyys[] = new String[5];
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		int start = year - 2;
		for (int i = 0; i < yyyys.length; i++) {
			yyyys[i] = start++ + "";
		}

		String mms[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		model.addAttribute("yyyys", yyyys);
		model.addAttribute("mms", mms);
		String thisyyyy = year + "";

		String thismm = String.format("%02d", cal.get(Calendar.MONTH) + 1);
		model.addAttribute("thisyyyy", thisyyyy);
		model.addAttribute("thismm", thismm);

		return "salary/salary_tax";
	}

	@RequestMapping(value = "/salaryTaxSubmit", method = RequestMethod.POST)
	public String salaryTaxSubmit(Model model, @RequestParam String yyyy, @RequestParam String mm) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		HashMap yyyymm = new HashMap();
		yyyymm.putIfAbsent("yyyy", yyyy);
		yyyymm.putIfAbsent("mm", mm);
		dao.deleteSalaryRollbefore(yyyymm);
		ArrayList<Salary> salarys = dao.selectTaxYes();

		for (Salary salary : salarys) {
			salaryroll.setYyyy(yyyy);
			salaryroll.setMm(mm);
			salaryroll.setEmpno(salary.getEmpno());
			salaryroll.setDepart(salary.getDepart());
			salaryroll.setName(salary.getName());
			salaryroll.setPartner(salary.getPartner());
			salaryroll.setDependent20(salary.getDependent20() * 1500000);
			salaryroll.setDependent60(salary.getDependent60() * 1500000);
			salaryroll.setDisabled(salary.getDisabled() * 1500000);
			salaryroll.setPay12(salary.getPay() * 12);
			int family = 1 + salary.getPartner() + salary.getDependent20() + salary.getDependent60()
					+ salary.getDisabled();
			int pay12 = (salary.getPay() + salary.getExtra()) * 12;
			int incomededuction = 0;
			if (family == 1) {
				if (pay12 < 30000001) {
					incomededuction = (int) (3100000 + (pay12 * 0.04));
				} else if (pay12 > 30000000 && pay12 < 45000001) {
					incomededuction = (int) (3100000 + ((pay12 * 0.04) - ((pay12 - 30000000) * 0.05)));
				} else if (pay12 > 45000000 && pay12 < 70000001) {
					incomededuction = (int) (3100000 + (pay12 * 0.015));
				} else if (pay12 > 70000000 && pay12 < 120000001) {
					incomededuction = (int) (3100000 + (pay12 * 0.005));
				}
			} else if (family == 2) {
				if (pay12 < 30000001) {
					incomededuction = (int) (3600000 + (pay12 * 0.04));
				} else if (pay12 > 30000000 && pay12 < 45000001) {
					incomededuction = (int) (3600000 + ((pay12 * 0.04) - ((pay12 - 30000000) * 0.05)));
				} else if (pay12 > 45000000 && pay12 < 70000001) {
					incomededuction = (int) (3600000 + (pay12 * 0.02));
				} else if (pay12 > 70000000 && pay12 < 120000001) {
					incomededuction = (int) (3600000 + (pay12 * 0.01));
				}
			} else {
				if (pay12 < 30000001) {
					incomededuction = (int) (5000000 + (pay12 * 0.07));
					if (((pay12 - 40000000) * 0.04) > 0) {
						incomededuction += ((pay12 - 40000000) * 0.04);
					}
				} else if (pay12 > 30000000 && pay12 < 45000001) {
					incomededuction = (int) (5000000 + (pay12 * 0.07) - ((pay12 - 30000000) * 0.05));
					if (((pay12 - 40000000) * 0.04) > 0) {
						incomededuction += ((pay12 - 40000000) * 0.04);
					}
				} else if (pay12 > 45000000 && pay12 < 70000001) {
					incomededuction = (int) (5000000 + (pay12 * 0.05) + ((pay12 - 40000000) * 0.04));
				} else if (pay12 > 70000000 && pay12 < 120000001) {
					incomededuction = (int) (5000000 + (pay12 * 0.03) + ((pay12 - 40000000) * 0.04));
				}
			}
			salaryroll.setIncomededuction(incomededuction);
			salaryroll.setIncomeamount(pay12 - incomededuction);
			salaryroll.setPersonaldeduction(family * 1500000);
			int annuityinsurance = (int) (pay12 / 12 * 0.045) * 12;
			salaryroll.setAnnuityinsurance(annuityinsurance);
			int standardamount = pay12 - (incomededuction + annuityinsurance + family * 1500000);
			salaryroll.setStandardamount(standardamount);
			int calculatedtax = 0;
			if (standardamount < 12000001) {
				calculatedtax = (int) (standardamount * 0.06);
			} else if (standardamount > 12000000 && standardamount < 46000001) {
				calculatedtax = (int) (720000 + (standardamount - 12000000) * 0.15);
			} else if (standardamount > 46000000 && standardamount < 88000001) {
				calculatedtax = (int) (5820000 + (standardamount - 46000000) * 0.24);
			} else if (standardamount > 88000000 && standardamount < 150000001) {
				calculatedtax = (int) (15900000 + (standardamount - 88000000) * 0.35);
			} else if (standardamount > 150000000 && standardamount < 300000001) {
				calculatedtax = (int) (37600000 + (standardamount - 150000000) * 0.38);
			} else if (standardamount > 300000000 && standardamount < 500000001) {
				calculatedtax = (int) (94600000 + (standardamount - 300000000) * 0.40);
			} else if (standardamount > 500000000) {
				calculatedtax = (int) (174600000 + (standardamount - 500000000) * 0.42);
			}
			salaryroll.setCalculatedtax(calculatedtax);
			int incometaxdeduction = 0;
			if (calculatedtax < 1300001) {
				incometaxdeduction = (int) (calculatedtax * 0.55);
			} else {
				incometaxdeduction = (int) (715000 + (calculatedtax - 1300000) * 0.30);
			}

			int decidedtax = calculatedtax - incometaxdeduction;
			salaryroll.setDecidedtax(decidedtax);
			int simpletax = (int) ((decidedtax / 12) * 0.01);
			simpletax = simpletax * 100;
			salaryroll.setSimpletax(simpletax);
			int finalpay = (salary.getPay() + salary.getExtra()) - (annuityinsurance + simpletax);
			salaryroll.setFinalpay(finalpay);
			dao.insertSalaryRoll(salaryroll);
			System.out.println("final pay:" + finalpay);
		}
		return "redirect:index";
	}

	@RequestMapping(value = "/salaryDeleteSubmit", method = RequestMethod.POST)
	public String salaryDeleteSubmit(Model model, @RequestParam String yyyy, @RequestParam String mm) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		HashMap yyyymm = new HashMap();
		yyyymm.putIfAbsent("yyyy", yyyy);
		yyyymm.putIfAbsent("mm", mm);
		dao.deleteSalaryRollbefore(yyyymm);
		return "redirect:index";
	}

	@RequestMapping(value = "/salaryRollChoice", method = RequestMethod.GET)
	public String salaryRollChoice(Model model) throws Exception {
		String yyyys[] = new String[5];
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		int start = year - 2;
		for (int i = 0; i < yyyys.length; i++) {
			yyyys[i] = start++ + "";
		}

		String mms[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		model.addAttribute("yyyys", yyyys);
		model.addAttribute("mms", mms);
		String thisyyyy = year + "";

		String thismm = String.format("%02d", cal.get(Calendar.MONTH) + 1);
		model.addAttribute("thisyyyy", thisyyyy);
		model.addAttribute("thismm", thismm);
		return "salary/salaryroll_list_choice";
	}

	@RequestMapping(value = "/salaryRollList", method = RequestMethod.POST)
	public String salaryRollList(Model model, @RequestParam String yyyy, @RequestParam String mm) throws Exception {
		SalaryDao dao = sqlSession.getMapper(SalaryDao.class);
		HashMap yyyymm = new HashMap();
		yyyymm.putIfAbsent("yyyy", yyyy);
		yyyymm.putIfAbsent("mm", mm);
		ArrayList<SalaryRollViewToBean> salaryrolls = dao.selectSalaryRollView(yyyymm);
		model.addAttribute("salaryrolls", salaryrolls);
		return "salary/salaryroll_list";
	}
}
