package cn.catecat.layout.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.global.service.dao.GlobalService;
import cn.catecat.global.util.CatProperty;
import cn.catecat.global.util.Conversion;
import cn.catecat.layout.bean.Layout;

public class LayoutAction extends ActionSupport implements ModelDriven<Layout>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Layout layout = new Layout();
	private List<Layout> list = new ArrayList<Layout>();
	private String result;
	private String context;
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Layout getLayout() {
		return layout;
	}
	public void setLayout(Layout layout) {
		this.layout = layout;
	}
	public List<Layout> getList() {
		return list;
	}
	public void setList(List<Layout> list) {
		this.list = list;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Autowired private GlobalService globalService;
	public String getLayouts(){
		this.list = globalService.getAll(Layout.class);
		return "all";
	}
	public String getLayoutById(){
		if(layout.getId()==null){
			return SUCCESS;
		}
		Layout lay = globalService.getById(Layout.class, layout.getId());
		if(lay==null){
			return SUCCESS;
		}
		BufferedReader fileReader = null;
		try {
			fileReader= new BufferedReader(new FileReader(CatProperty.getPropertyValue("path", "layout")+File.separator+lay.getId()));
			StringBuffer sb = new StringBuffer();
			String temp;
			while((temp=fileReader.readLine())!=null){
				sb.append(temp);
			}
			this.result =sb.toString();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	public String saveLayout(){
		if(context==null){
			this.result = Conversion.stringToJson("result,false,cause,内容不能空");
			return SUCCESS;
		}
		File dir = new File(CatProperty.getPropertyValue("path", "layout"));
		if(!dir.exists())dir.mkdirs();
		String id = layout.getId()==null?UUID.randomUUID().toString():layout.getId();
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(new File(dir.getAbsoluteFile()+File.separator+id));
			fileWriter.write(context);
				if(layout.getId()==null){
				layout.setId(id);
				layout.setUsed(0);
				globalService.save(layout);
			}else{
				 Layout lay = globalService.getById(Layout.class, layout.getId());
				if(lay==null)globalService.save(layout);
				else {
					if(!lay.getName().equals(layout.getName())){
						lay.setName(layout.getName());
						globalService.update(lay);
					}
				}
			}
			this.result = Conversion.stringToJson("result,true,id,"+id);
			return SUCCESS;
		} catch (IOException e) {
			this.result = Conversion.stringToJson("result,false,cause,未知原因");
			e.printStackTrace();
		}finally{
			if(fileWriter!=null){
				try {
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * 更新状态
	 * @return
	 */
	public String updateState(){
		try{
		if(layout.getId()!=null){
			List<Layout> layouts = globalService.getAll(Layout.class);
			for(Layout lay : layouts){
				if(lay.getId().equals(layout.getId())){
					lay.setUsed(1);
				}else{
					lay.setUsed(0);
				}
			}
			globalService.updateMany(layouts);
			this.result=Conversion.stringToJson("result,true");
		}
		}catch(Exception e){
			this.result=Conversion.stringToJson("result,false,cause,未知");
		}
		return SUCCESS;
	}
	/**
	 * 获取当前正在使用
	 * @return
	 */
	public String getCurrentUse(){
		Layout lay = globalService.getByType(Layout.class, "used", 1);
		if(lay==null)return null;
		BufferedReader fileReader = null;
		try {
			fileReader= new BufferedReader(new FileReader(CatProperty.getPropertyValue("path", "layout")+File.separator+lay.getId()));
			StringBuffer sb = new StringBuffer();
			String temp;
			while((temp=fileReader.readLine())!=null){
				sb.append(temp);
			}
			this.result =sb.toString();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	@Override
	public Layout getModel() {
		return layout;
	}
}
