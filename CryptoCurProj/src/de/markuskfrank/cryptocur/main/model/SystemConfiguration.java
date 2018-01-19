package de.markuskfrank.cryptocur.main.model;

public class SystemConfiguration {
	
	private static SystemConfiguration system;
	private String lastuser;
	private int marketplace;
	
	private SystemConfiguration() {
		this.system = this;
	}
	
	public synchronized SystemConfiguration getSystem(){
		if(system == null){
			new SystemConfiguration();
		}
		return system;
	}

}
