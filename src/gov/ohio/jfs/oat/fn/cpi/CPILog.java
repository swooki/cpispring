// 3/17/2014 by Sungwook Kwon
package gov.ohio.jfs.oat.fn.cpi;

import java.util.Date;

public class CPILog {
	String application;
	String action;
	String userAccessed;
	Date dateAccessed;
	String personalId;
	Date dateCreated;
	String documentAccessed;
	
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
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDocumentAccessed() {
		return documentAccessed;
	}
	public void setDocumentAccessed(String documentAccessed) {
		this.documentAccessed = documentAccessed;
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
				+ ", dateCreated=" + dateCreated + ", documentAccessed="
				+ documentAccessed + "]";
	}
	
}
