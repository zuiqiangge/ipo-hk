package com.pengyue.ipo.tree;

import com.pengyue.ipo.bean.TbXtTaskResult;

public class Pie {
	private String value;
	
	private String name;

	public Pie() {
		super();
	}
	
	public Pie(TbXtTaskResult tbXtTaskResult) {
		super();
		this.value=tbXtTaskResult.getClickedCounts();
		this.name=tbXtTaskResult.getResultsource();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
