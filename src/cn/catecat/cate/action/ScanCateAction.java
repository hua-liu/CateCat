package cn.catecat.cate.action;

import java.util.List;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.annotation.Jurisdiction;
import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.dto.ScanForm;
import cn.catecat.cate.service.dao.CateService;
import cn.catecat.global.socket.CommServlet;
import cn.catecat.global.util.Conversion;

public class ScanCateAction extends ActionSupport implements ModelDriven<ScanForm>{
	/**
	 * 美食Action
	 */
	private static final long serialVersionUID = 1L;
	@Autowired private CateService cateService;
	private String result;
	private ScanForm scanForm;
	public void setScanForm(ScanForm scanForm) {
		this.scanForm = scanForm;
	}
	public String getResult() {
		return result;
	}
	/**
	 * 批量扫描美食信息
	 * @return
	 */
	@Jurisdiction({"BackgroundLogin","LocalScan"})
	public String scan(){
		try{
			if(scanForm==null){
				this.result = Conversion.stringToJson("message,false,cause,上传服务器未连接");return SUCCESS;
			}
			Session session = CommServlet.getSessionById(scanForm.getSocketid());
			if(session==null){	//保存通信服务器连接
				this.result = Conversion.stringToJson("message,false,cause,上传服务器未连接");return SUCCESS;
			}
			if(scanForm.getPath()==null){
				this.result = Conversion.stringToJson("message,false,cause,路径不存在");return SUCCESS;
			}
			if(scanForm.getLevel()<1){
				scanForm.setLevel(1);
			}
			CommServlet.sendMessage(session, Conversion.stringToJson("log,开始扫描路径："+scanForm.getPath().replaceAll("\\\\", "/")));
			 List<Cate> cates = cateService.scanCate(scanForm,session);
			if(cates.size()<1){
				CommServlet.sendMessage(session, Conversion.stringToJson("log,扫描结束，没有扫描到美食信息"));
				this.result = Conversion.stringToJson("message,false,cause,目录下没有文件或路径错误或规范定义错误");
				return SUCCESS;
			}
			for(Cate temp : cates)temp.setId(null);//置空所有ID，因之后调用的saveOrUpdate
			CommServlet.sendMessage(session,Conversion.stringToJson("log,扫描结束，扫描到美食："+cates.size()+"个"));
			CommServlet.sendMessage(session,Conversion.stringToJson("log,开始保存数据"));
			//设置文件日志
			int[] num = cateService.saveList(cates);
			int sum=0;
			for(int i=0;i<num.length;i++){
				String name = cates.get(i).getName();
				if(num[i]==1){
					sum++;
					CommServlet.sendMessage(session,Conversion.stringToJson("log,保存美食：["+name+"]成功"));
				}else{
					CommServlet.sendMessage(session,Conversion.stringToJson("log,保存美食：["+name+"]失败"));
				}
			}
			this.result = Conversion.stringToJson("message,true,log,保存结果：["+sum+"个美食成功，"+(num.length-sum)+"个美食失败]");
		}catch(Exception e){
			this.result = Conversion.stringToJson("message,false,cause,服务器错误");
		}
		return SUCCESS;
	}
	@Override
	public ScanForm getModel() {
		scanForm = new ScanForm();
		return scanForm;
	}

}
