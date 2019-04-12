package com.ibm.wolfs.tracker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * This is a simple Entity Class, just to handle with links. This class is used
 * to set a table on H2 DB.
 *
 * @author Wolfshorndl, D. A.
 * 
 */
@Entity(name = "link")
public class Url {

	@Id
	@GeneratedValue
	private Long id;
	private String father;
	private String link;

	public Url() {

	}

	public Url(int code, String father, String link) {
		super();
		this.father = father;
		this.link = link;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
