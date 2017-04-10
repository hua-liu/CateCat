package cn.catecat.cate.dto;

/**
 * 用于计划任务
 * @author 刘华
 *
 */
public class MissionBean {
	private String type;	//任务类型
	private Long time;		//任务开始时间
	private Integer state;	//任务当前状态
	private String name;	//任务名称
	private Integer code;	//任务code
	private String id;		//通信ID
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsedtime() {
		return (System.currentTimeMillis()-time)/1000+"";
	}
}
