// 3/17/2014 by Sungwook Kwon
package gov.ohio.jfs.fn.cpi;

import java.util.Date;

public class CPILog {
	String action;
	String application;
	Date   dateAccessed;
	String personalId;
	String targetAccessed;
	String userAccessed;
	
	public CPILog() {
	}

	public CPILog(String action, String application, Date dateAccessed
			, String personalId, String targetAccessed, String userAccessed) {
		this.action = action;
		this.application = application;
		this.dateAccessed = dateAccessed;
		this.personalId = personalId;
		this.targetAccessed = targetAccessed;
		this.userAccessed = userAccessed;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public Date getDateAccessed() {
		return dateAccessed;
	}
	public void setDateAccessed(Date dateAccessed) {
		this.dateAccessed = dateAccessed;
	}
	public String getTargetAccessed() {
		return targetAccessed;
	}
	public void setTargetAccessed(String targetAccessed) {
		this.targetAccessed = targetAccessed;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getUserAccessed() {
		return userAccessed;
	}
	public void setUserAccessed(String userAccessed) {
		this.userAccessed = userAccessed;
	}
	
	@Override
	public String toString() {
		return "CPILog [application=" + application + ", action=" + action
				+ ", userAccessed=" + userAccessed + ", dateAccessed="
				+ dateAccessed + ", personalId=" + personalId
				+ ", documentAccessed="	+ targetAccessed + "]";
	}
	
}
