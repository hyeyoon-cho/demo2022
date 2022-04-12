package com.sample.demo.search.vo;

import java.util.List;
public class ResultVO {

	private String status;
	private List<?> resultList;
    private int totalCount;
    private int pageSize;
    private int pageIndex;
    
	/**
	 * 
	 */
	public ResultVO() {
		super();
		this.pageSize = 10; //기본사이즈 10
		this.pageIndex = 1;
	}
	/**
	 * @param status
	 * @param resultList
	 * @param totalCount
	 */
	public ResultVO(String status, List<?> resultList, int totalCount) {
		super();
		this.status = status;
		this.resultList = resultList;
		this.totalCount = totalCount;
		this.pageSize = 5;
		this.pageIndex = 1;
	}
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}
	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the resultList
	 */
	public List<?> getResultList() {
		return resultList;
	}
	/**
	 * @param resultList the resultList to set
	 */
	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public void handleResultList(List<?> resultList, int totalCount) {
		this.resultList = resultList;
		this.totalCount = totalCount;
	}
	
	public void handleResultList(String status, List<?> resultList, int totalCount) {
		this.status = status;
		this.resultList = resultList;
		this.totalCount = totalCount;
	}
    
}
