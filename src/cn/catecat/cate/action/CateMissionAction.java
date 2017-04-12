package cn.catecat.cate.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.annotation.Jurisdiction;
import cn.catecat.cate.dto.MissionBean;
import cn.catecat.cate.service.dao.CateService;
import cn.catecat.global.socket.CommServlet;
import cn.catecat.global.util.CatProperty;
import cn.catecat.global.util.Conversion;
import cn.catecat.global.util.FileOperation;


public class CateMissionAction extends ActionSupport implements ModelDriven<MissionBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired private CateService service;
	private MissionBean mission;
	private String result;
	//线程锁常量
	public final static String ONLINELOCK="online";
	public final static String OFFLINELOCK="offline";
	public final static String CLEARLOCK="clearRubbish";
	//保存当前任务
	private static Map<String,MissionBean> missions = new HashMap<String,MissionBean>();
	public static Map<String,MissionBean> getMissions() {
		return missions;
	}
	@Jurisdiction({"BackgroundLogin","UtilsUsed"})
	public static void removeMission(String id){
		missions.remove(id);
	}
	public String getResult() {
		return result;
	}
	//获取当前已存在任务
	@Jurisdiction({"BackgroundLogin"})
	public String cateMission(){
		ServletActionContext.getRequest().setAttribute("mission", missions.values());
		return SUCCESS;
	}
	//添加任务
	@Jurisdiction({"BackgroundLogin","UtilsUsed"})
	public String mission(){
		if(mission!=null){
			if(mission.getCode()!=null&&mission.getCode()==1){
				//判断任务集体中是否已存在当前类型任务
				if(!missions.containsKey(mission.getType())){
					//获取当前用户通信Session
					if(CommServlet.getSessionById(mission.getId())!=null){
						mission.setTime(System.currentTimeMillis());
						missions.put(mission.getType(),mission);
						//启动任务分配线程分配执行任务
						new Mission(service,mission).start();
						this.result = Conversion.stringToJson("message,true");
					}else{
						this.result = Conversion.stringToJson("message,false,cause,通信服务器未连接");
					}
				}else{
					MissionBean mib = missions.get(mission.getType());
					//判断当前已存在任务是否处理暂停状态
					if(mib.getState()==2){
						/*mib.setState(1);//设置状态
						this.notifyAll();*/
						mib.setId(mission.getId());
						new Mission(service,mib).start();
						this.result = Conversion.stringToJson("message,true");
					}else{
						this.result = Conversion.stringToJson("message,false,cause,任务在服务器上未完成");
					}
				}
				//如果
			}else if(mission.getCode()!=null&&mission.getCode()==0){
				if(missions.get(mission.getType())!=null)
					missions.get(mission.getType()).setState(mission.getCode());
				this.result = Conversion.stringToJson("message,true");
			}else if(mission.getCode()!=null&&mission.getCode()==2){
				if(missions.get(mission.getType())!=null)
					missions.get(mission.getType()).setState(mission.getCode());
				this.result = Conversion.stringToJson("message,true");
			}
		}else
		this.result = Conversion.stringToJson("message,false,cause,未知错误");
		return SUCCESS;
	}
	@Override
	public MissionBean getModel() {
		mission = new MissionBean();
		return mission;
	}
}
//任务分配线程
class Mission extends Thread{
	private MissionBean mi;
	private CateService service;
	public Mission(CateService service,MissionBean mi){
		this.service = service;
		this.mi = mi;
	}
	@Override
	public void run() {
		//根据类型分配线程执行任务
			if("autoOnline".equals(mi.getType())){
				synchronized (CateMissionAction.ONLINELOCK) {
					if(mi.getState()==2){	//当状态为2，则唤醒暂停的任务
						mi.setState(1);
						CateMissionAction.ONLINELOCK.notifyAll();
						return;
					}
				}
				new AutoOnline(service).start();	//开启线程执行任务
			}else if("autoOffline".equals(mi.getType())){
				synchronized (CateMissionAction.OFFLINELOCK) {
					if(mi.getState()==2){
						mi.setState(1);
						CateMissionAction.OFFLINELOCK.notifyAll();
						return;
					}
				}
				new AutoOffline(service).start();
			}else if("clearRubbish".equals(mi.getType())){
				synchronized (CateMissionAction.CLEARLOCK) {
					if(mi.getState()==2){
						mi.setState(1);
						CateMissionAction.CLEARLOCK.notifyAll();
						return;
					}
				}
				new ClearRubbish(service).start();
			}
	}
	
}
/**
 * 自动上线线程处理
 * @author 甜橙六画
 *
 */
class AutoOnline extends Thread{
	private CateService service;
	public AutoOnline(CateService service) {
		this.service = service;
	}
	@Override
	public void run() {
		MissionBean missionBean = CateMissionAction.getMissions().get("autoOnline");
		try{
			synchronized (CateMissionAction.ONLINELOCK) {
				service.autoOnline(missionBean);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,8"));
			CateMissionAction.removeMission(missionBean.getType());
		}
	}
}
/**
 * 自动下线处理线程
 * @author 甜橙六画
 *
 */
class AutoOffline extends Thread{
	private CateService service;
	public AutoOffline(CateService service) {
		this.service = service;
	}
	@Override
	public void run() {
		MissionBean missionBean = CateMissionAction.getMissions().get("autoOffline");
		try{
			synchronized (CateMissionAction.ONLINELOCK) {
				service.autoOffline(missionBean);
			}
		}
		catch(Exception e){
			CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,8"));
			CateMissionAction.removeMission(missionBean.getType());
		}
	}
}
/**
 * 清理垃圾文件处理线程
 * @author 甜橙六画
 *
 */
class ClearRubbish extends Thread{
	private CateService service;
	private int dirOrFile=0;
	MissionBean missionBean = CateMissionAction.getMissions().get("clearRubbish");
	public ClearRubbish(CateService service) {
		this.service = service;
	}
	@Override
	public void run() {
		try{
			synchronized(CateMissionAction.CLEARLOCK){
				System.out.println("liuhua:自动清理正在执行");
				service.clearRubbish();
				traverse(new File(CatProperty.getPropertyValue("path", "base")));
				if(missionBean!=null){
					if(missionBean.getState()==2){
						CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,2"));
						CateMissionAction.CLEARLOCK.wait();
						CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,2"));
					}else if(missionBean.getState()==0){
						CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,0"));
						CateMissionAction.removeMission(missionBean.getType());
						return;
					}
				}
				CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,10,handler,"+dirOrFile));
				CateMissionAction.removeMission(missionBean.getType());
			}
		}catch(Exception e){
			CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,8"));
			CateMissionAction.removeMission(missionBean.getType());
		}
		
	}
	//递归遍历目录下所有文件或目录
	private void traverse(File file){
		if(file.exists()){
			if(file.isDirectory()){
				File[] childs = file.listFiles();
				if(childs.length>0){
					for(File temp : childs)traverse(temp);
				}else{
					file.delete();
					dirOrFile++;
				}
			}else{
				String absPath = file.getAbsolutePath().replaceAll("/", "\\\\");
				if(!service.findSourceByPath(absPath)){
					new FileOperation(file.getAbsolutePath()).start();
					dirOrFile++;
				}
			}
		}
	}
}
