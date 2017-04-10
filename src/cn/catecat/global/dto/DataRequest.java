package cn.catecat.global.dto;
/**
 * 分页请求数据封装<br/>
 * page		当前页<br/>
 * rows		显示行数<br/>
 * sidx		排序的字段<br/>
 * sord		排序的方式desc/asc<br/>
 * search	是否是搜索请求<br/>
 * searchField 	搜索的字段<br/>
 * searchOper   搜索的操作符(eq:等于,ne:不等于,lt:小于,le:小于等于,gt:大于,ge:大于等于,cn:相似,nc:不相似 )<br/>
 * searchString 搜索的内容<br/>
 * @author 刘华
 *
 */
public class DataRequest implements java.io.Serializable {  
    
    private static final long serialVersionUID = 1L;  
      
    //当前页码  
    private int page=1;  
    //页面可显示行数  
    private int rows=15;  
    //用于排序的列名  
    private String sidx;  
    //排序的方式desc/asc  
    private String sord;  
    //是否是搜索请求  
    private boolean search;  
    //已经发送的请求的次数  
    private String[] searchField;
    private String[] searchOper;
    private String[] searchString;
    private String specialSearch;
	public String getSpecialSearch() {
		return specialSearch;
	}
	public void setSpecialSearch(String specialSearch) {
		this.specialSearch = specialSearch;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public boolean isSearch() {
		return search;
	}
	public void setSearch(boolean search) {
		this.search = search;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String[] getSearchField() {
		return searchField;
	}
	public void setSearchField(String[] searchField) {
		this.searchField = searchField;
	}
	public String[] getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String[] searchOper) {
		this.searchOper = searchOper;
	}
	public String[] getSearchString() {
		return searchString;
	}
	public void setSearchString(String[] searchString) {
		this.searchString = searchString;
	}  
    
}  