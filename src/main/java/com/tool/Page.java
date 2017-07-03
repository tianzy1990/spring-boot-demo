package com.tool;

import java.util.List;
import java.util.Map;

public class Page<T> {

	private long tatalRow = 0;
	private int pageIndex = 0;
	private int pageSize = 0;
	private List<T> data = null;

	public Page() {
		
	}

	public Page(long tatalRow, int pageIndex, int pageSize, List<T> data) {
		super();
		this.tatalRow = tatalRow;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.data = data;
	}

	public long getTatalRow() {
		return tatalRow;
	}

	public void setTatalRow(long tatalRow) {
		this.tatalRow = tatalRow;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
