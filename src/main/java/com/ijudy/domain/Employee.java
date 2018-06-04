/**
 *    Copyright 2010-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.ijudy.domain;

import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A simple bean that holds Employee info.
 */
public class Employee {

	private String empNo; 		// "EMPNO" CHAR(6 OCTETS) NOT NULL,
	private String firsName; 	// "FIRSTNME" VARCHAR(12 OCTETS) NOT NULL,
	private String middle; 		//	"MIDINIT" CHAR(1 OCTETS),
	private String lastName; 	// "LASTNAME" VARCHAR(15 OCTETS) NOT NULL,
	private String workDept; 	// "WORKDEPT" CHAR(3 OCTETS),
	private String phone; 		// "PHONENO" CHAR(4 OCTETS),
	private Date hireDate; 		// "HIREDATE" DATE,
	private String job; 		// "JOB" CHAR(8 OCTETS),
	private String eduLevel; 	// "EDLEVEL" SMALLINT NOT NULL,
	private String sex; 		// "SEX" CHAR(1 OCTETS),
	private Date birth; 		// "BIRTHDATE" DATE,
	private float salary; 		// "SALARY" DECIMAL(9 , 2),
	private float bonus; 		// "BONUS" DECIMAL(9 , 2),
	private float commission; 	// "COMM" DECIMAL(9 , 2)

	@Override
	public String toString() {
		return new ToStringBuilder(this).
				append("empNo", empNo).
				append("firsName", firsName).
				append("middle", middle).
				append("lastName", lastName).
				append("workDept", workDept).
				append("phone", phone).
				append("hireDate", hireDate).
				append("job", job).
				append("eduLevel", eduLevel).
				append("sex", sex).
				append("birth", birth).
				append("salary", salary).
				append("bonus", bonus).
				append("commission", commission).
				toString();
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getFirsName() {
		return firsName;
	}

	public void setFirsName(String firsName) {
		this.firsName = firsName;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWorkDept() {
		return workDept;
	}

	public void setWorkDept(String workDept) {
		this.workDept = workDept;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public float getBonus() {
		return bonus;
	}

	public void setBonus(float bonus) {
		this.bonus = bonus;
	}

	public float getCommission() {
		return commission;
	}

	public void setCommission(float commission) {
		this.commission = commission;
	}
}
