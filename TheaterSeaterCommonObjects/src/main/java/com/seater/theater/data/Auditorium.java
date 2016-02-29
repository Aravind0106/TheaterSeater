

package com.seater.theater.data;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Auditorium{
	
	private List<AuditLevel> auditLevels;

	public List<AuditLevel> getAuditLevels() {
		return auditLevels;
	}

	public void setAuditLevels(List<AuditLevel> levels) {
		this.auditLevels = levels;
	}
	
}