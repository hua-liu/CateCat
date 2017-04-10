package cn.catecat.image.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.catecat.global.util.Conversion;
import cn.catecat.image.bean.Image;
import cn.catecat.image.dto.ImageWebUpload;
import cn.catecat.image.service.dao.ImageService;
import cn.catecat.user.bean.User;
/**
 * 图片处理Action
 * @author 刘华
 *
 */
public class ImageAction extends ActionSupport implements ModelDriven<ImageWebUpload>{
	@Autowired private ImageService service;
	private ImageWebUpload images;
	private String result;	//用于返回json语句
	private String type;	//用于设置请求图片大小
	private String id;		//用于请求图片ID
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setImages(ImageWebUpload images) {
		this.images = images;
	}
	/**
	 * 文件处理
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 上传美食图片处理
	 * @return
	 */
	public String uploadCateImg(){
		if(images==null){
			this.result = Conversion.stringToJson("result,false,cause,文件获取失败");
		}
		if(images.getCateid()==null||images.getFile()==null||images.getFileContentType()==null){
			this.result = Conversion.stringToJson("result,false,cause,ID获得失败");
		}
		if(images.getSize()<1||images.getSize()>20480000){
			this.result = Conversion.stringToJson("result,false,cause,文件大小不能超过2M");
		}
		try{
			this.result = service.saveFile(images);
		}catch(Exception e){
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	public String uploadSpecialImg(){
		if(images==null){
			this.result = Conversion.stringToJson("result,false,cause,文件获取失败");
			return SUCCESS;
		}
		if(images.getCateid()==null||images.getFile()==null||images.getFileContentType()==null){
			this.result = Conversion.stringToJson("result,false,cause,ID获得失败");return SUCCESS;
		}
		/*if(images.getSize()<1||images.getSize()>20480000){
			this.result = Conversion.stringToJson("result,false,cause,文件大小不能超过2M");return SUCCESS;
		}*/
		try{
			this.result = service.saveFileForSpecial(images);
		}catch(Exception e){
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 图片下载，
	 * @param session
	 * @return
	 * @throws IOException
	 */
	public String requestImg(){
		if(id==null)return NONE;
		Image image = service.getImage(id);
		if(image!=null)this.inputStream = service.requestImg(image.getPath(),type);
		return "stream";
	}
	/**
	 * 图片下载，
	 * @param session
	 * @return
	 * @throws IOException
	 */
	public String requestCateImg(){
		if(id==null)return NONE;
		String path = service.getPathForCateId(id);
		if(path!=null)this.inputStream = service.requestImg(path,type);
		return "stream";
	}
	/**
	 * 删除图像
	 * @return
	 */
	public String delete(){
		this.result = service.deleteImg(id,images.getCateid());
		return SUCCESS;
	}
	/**
	 * 删除图像
	 * @return
	 */
	public String deleteImg(){
		List<Image> list = service.findManyByIds(Image.class, id.split(","));
		int num=0;
		if(list!=null)
		for(Image image : list){
			if(service.deleteImage(image)){
				num++;
			}
		}
		int lose = list!=null?list.size()-num:0;
		this.result = Conversion.stringToJson("result,true,success,"+num+",failure,"+lose);
		return SUCCESS;
	}
	/**
	 * 上传默认头像
	 * @return
	 */
	public String uploadDefaultHeadPortrait(){
		if(images==null){
			this.result = Conversion.stringToJson("result,false,cause,文件获取失败");
		}
		try{
			this.result = service.saveHeadPortrait(images);
		}catch(Exception e){
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	/**
	 * 上传用户头像
	 * @return
	 */
	public String uploadUserHeadPortrait(){
		User existUser = (User) ActionContext.getContext().getSession().get("user");
		if(null==existUser)this.result = Conversion.stringToJson("result,false,cause,未登陆无法上传");
		if(images==null){
			this.result = Conversion.stringToJson("result,false,cause,文件获取失败");
		}
		try{
			this.result = service.saveHeadPortrait(images,existUser);
		}catch(Exception e){
			this.result = Conversion.stringToJson("result,false,cause,"+e);
		}
		return SUCCESS;
	}
	@Override
	public ImageWebUpload getModel() {
		images = new ImageWebUpload();
		return images;
	}
}
