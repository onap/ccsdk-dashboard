package org.onap.ccsdk.dashboard.core;

import java.util.Date;

import org.onap.portalsdk.core.domain.User;

public class MockUser {

	public User mockUser() {

		User ePUser = new User();
		ePUser.setOrgId(null);
		ePUser.setManagerId(null);
		ePUser.setFirstName("test");
		ePUser.setLastName("test");
		ePUser.setMiddleInitial(null);
		ePUser.setPhone(null);
		ePUser.setFax(null);
		ePUser.setCellular(null);
		ePUser.setEmail(null);
		ePUser.setAddressId(null);
		ePUser.setAlertMethodCd(null);
		ePUser.setHrid(null);
		ePUser.setOrgUserId("guestT");
		ePUser.setOrgCode(null);
		ePUser.setAddress1(null);
		ePUser.setAddress2(null);
		ePUser.setCity(null);
		ePUser.setState(null);
		ePUser.setZipCode(null);
		ePUser.setCountry(null);
		ePUser.setOrgManagerUserId(null);
		ePUser.setLocationClli(null);
		ePUser.setBusinessCountryCode(null);
		ePUser.setBusinessCountryName(null);
		ePUser.setBusinessUnit(null);
		ePUser.setBusinessUnitName(null);
		ePUser.setDepartment(null);
		ePUser.setDepartmentName(null);
		ePUser.setCompanyCode(null);
		ePUser.setCompany(null);
		ePUser.setZipCodeSuffix(null);
		ePUser.setJobTitle(null);
		ePUser.setCommandChain(null);
		ePUser.setSiloStatus(null);
		ePUser.setCostCenter(null);
		ePUser.setFinancialLocCode(null);

		ePUser.setLoginId(null);
		ePUser.setLoginPwd(null);
		Date date = new Date();
		ePUser.setLastLoginDate(date);
		ePUser.setActive(true);
		ePUser.setInternal(false);
		ePUser.setSelectedProfileId(null);
		ePUser.setTimeZoneId(null);
		ePUser.setOnline(true);
		ePUser.setChatId(null);
		ePUser.setUserApps(null);
		ePUser.setPseudoRoles(null);

		ePUser.setId((long) -1);
		return ePUser;
	}
}
