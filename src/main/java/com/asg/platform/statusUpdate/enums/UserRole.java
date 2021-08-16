package com.asg.platform.statusUpdate.enums;

public enum UserRole {

  ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");
	
	private String roleAssigned;
    private UserRole(String roleAssigned) {
        this.roleAssigned = roleAssigned;
    }
   
    @Override
    public String toString(){
        return roleAssigned;
    }
}
