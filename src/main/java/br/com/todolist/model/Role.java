package br.com.todolist.model;

public enum  Role {
	ADMIN("admin"),
	USER("user");
	
	private String role;
	
	Role(String role) {
		this.role = role;
	}
	
	public String getRoleName() {
		return role;
	}
}
