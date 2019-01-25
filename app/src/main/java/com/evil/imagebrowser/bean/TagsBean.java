package com.evil.imagebrowser.bean;

import org.litepal.crud.LitePalSupport;

public class TagsBean extends LitePalSupport {
	
	/**
	 * id : 1
	 * type : 人物
	 * content :
	 * status : 1
	 */
	
	private String id;
	private String type;
	private String content;
	private String status;
	
	public String getId() { return id;}
	
	public void setId(String id) { this.id = id;}
	
	public String getType() { return type;}
	
	public void setType(String type) { this.type = type;}
	
	public String getContent() { return content;}
	
	public void setContent(String content) { this.content = content;}
	
	public String getStatus() { return status;}
	
	public void setStatus(String status) { this.status = status;}
}
