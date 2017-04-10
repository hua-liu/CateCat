package cn.catecat.image.service.dao.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.bean.SpecialShowCate;
import cn.catecat.global.dao.GlobalDao;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.global.util.Conversion;
import cn.catecat.global.util.FileOperation;
import cn.catecat.image.bean.Image;
import cn.catecat.image.dao.ImageDao;
import cn.catecat.image.dto.ImageWebUpload;
import cn.catecat.image.service.dao.ImageService;
import cn.catecat.user.bean.User;
@Service
public class ImageServiceImpl implements ImageService {
	@Autowired private ImageDao imageDao;
	@Autowired private GlobalDao globalDao;
	@Override
	public String saveFile(ImageWebUpload image) {
		if(!imageDao.checkImgSuffix(image.getFileFileName())||!image.getFileContentType().startsWith("image/")){
			throw new RuntimeException("格式不支持");
		}
		//获取当前美食
		Cate cate = globalDao.getById(Cate.class, image.getCateid());
		if(cate==null){
			throw new RuntimeException("美食不存在呀");
		}
		//获取文件保存路径
		String path = imageDao.getSavePath("cateimg", image.getFileFileName(),cate.getId());
		try {
			String md5 = imageDao.getMd5(new FileInputStream(image.getFile()));
			Image img = imageDao.verifyMd5(Image.class, md5);
			if(img==null){//如果没有当前MD5文件则创建一个
				img = new Image("CATE",md5,path,0);
				if(imageDao.saveFile(new FileInputStream(image.getFile()), path)){
					globalDao.save(img);
					cate.getImages().add(img);
					globalDao.update(cate);
					return Conversion.stringToJson("result,true");
				}
			}else{
				if(!cate.getImages().contains(img)){
					cate.getImages().add(img);
					globalDao.update(cate);
				}
				return Conversion.stringToJson("result,true");
			}
		} catch (Exception e) {
			new FileOperation(path).start();
			throw new RuntimeException(e);
		}
		throw new RuntimeException("unknow cause");
	}
	
	@Override
	public String saveFileForSpecial(ImageWebUpload image) {
		if(!imageDao.checkImgSuffix(image.getFileFileName())||!image.getFileContentType().startsWith("image/")){
			throw new RuntimeException("格式不支持");
		}
		//获取当前美食
		SpecialShowCate cate = globalDao.getById(SpecialShowCate.class, image.getCateid());
		if(cate==null){
			throw new RuntimeException("特别展示美食不存在呀");
		}
		//获取文件保存路径
		String path = imageDao.getSavePath("cateimg", image.getFileFileName(),cate.getId());
		try {
			String md5 = imageDao.getMd5(new FileInputStream(image.getFile()));
			Image img = imageDao.verifyMd5(Image.class, md5);
			if(img==null){//如果没有当前MD5文件则创建一个
				img = new Image("SPECIALCATE",md5,path,0);
				if(imageDao.saveFile(new FileInputStream(image.getFile()), path)){
					globalDao.save(img);
					cate.setImg(img);
					globalDao.update(cate);
					return Conversion.stringToJson("result,true");
				}
			}else{
					cate.setImg(img);
					globalDao.update(cate);
					return Conversion.stringToJson("result,true");
			}
		} catch (Exception e) {
			new FileOperation(path).start();
			throw new RuntimeException(e);
		}
		throw new RuntimeException("unknow cause");
	}

	@Override
	public InputStream requestImg(String path, String type) {
			File file = new File(path);
			if(!file.exists())return null;
			InputStream input=null;ImageInputStream iis=null;
			try {
				input = new FileInputStream(file);
					//对这类图片进行缩小处理
				java.awt.Image srcImg  = ImageIO.read(input);//取源图
				int width=srcImg.getWidth(null),height=srcImg.getHeight(null);
				int w=width,h=height;
				if("middle".equals(type)){
					w  =  460; //假设要缩小到200点像素
					h =  358;//srcImg.getHeight(null)*446/srcImg.getWidth(null);//按比例，将高度缩减
				}else if("avatar".equals(type)){
					w=40;h=40;
				}else if("cate".equals(type)){
					w=260;h=248;
				}else if("countdown".equals(type)){
					w=253;h=355;
					if((width*1.0)/w < (height*1.0)/h){
	                    if(width > w){
	                        h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
	                    }
	                } else {
	                    if(height > h){
	                        w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
	                    }
	                }
				}else if("detail".equals(type)){
					w=335;h=415;
					if((width*1.0)/w < (height*1.0)/h){
	                    if(width > w){
	                        h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
	                    }
	                } else {
	                    if(height > h){
	                        w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
	                    }
	                }
				}else if("min".equals(type)){
					w=56;h=56;
				}
				
				  // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
               /* if((width*1.0)/w < (height*1.0)/h){
                    if(width > w){
                        h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                    }
                } else {
                    if(height > h){
                        w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                    }
                }*/
				//java.awt.Image awt_image =srcImg.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);//缩小
		        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		        Graphics2D g2 = bufferedImage.createGraphics();
		        g2.drawImage(srcImg, 0, 0, w, h, Color.WHITE, null);
		        g2.dispose();
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
		        encoder.encode(bufferedImage);
		        return new ByteArrayInputStream(baos.toByteArray());
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(input!=null)
					try {
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(iis!=null)
					try {
						iis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		return null;
	}
	@Override
	public String deleteImg(String id, String cateid) {
		try{
			Image image = globalDao.getById(Image.class, id);
			if(image==null)return Conversion.stringToJson("result,true");
			int num = imageDao.deleteCate_image("delete from cate_images where cate_id=? and image_id=?",cateid,id);
			if(num>0&&deleteFileThread(image,true,image.getId())){
				return Conversion.stringToJson("result,true");
			}
		}catch(Exception e){
			e.printStackTrace();
			return Conversion.stringToJson("result,false,cause,出状况了");
		}
		return Conversion.stringToJson("result,false,cause,不知道");
	}
	/**
	 * 查询文件是否被占用
	 * @param id
	 * @return true  占用
	 * @return false  不占用
	 */
	private boolean selectFileIsUse(String id,String ... imgId){
		int i= globalDao.count(Image.class, " where id='"+id+"'"+(imgId.length>0?" and id!='"+imgId[0]+"'":""));
		if(i>0)return true;
		else return false;
	}
	/**
	 * 删除文件线程分配
	 * @param image		图片文件
	 * @param isSelect	是否需要向数据库查询文件被占用
	 * @param id		查询是否占用时排除的ID
	 */
	private boolean deleteFileThread(Image image,boolean isSelect,String ... id){
		if(image!=null&&image.getId()!=null){
			if(!(isSelect&&selectFileIsUse(image.getId(),id))){
				globalDao.delete(image);	//删除图片对象
				new FileOperation(image.getPath()).start();	//启动线程删除本地文件
				return true;
			}
		}
		return false;
	}
	@Override
	public DataResponse<Image> defaultList(DataRequest dataRequest) {
		DataResponse<Image> response = new DataResponse<Image>();  
        int count;//总记录数  
        int size = dataRequest.getRows() <= 0 ? 20 : dataRequest.getRows();//每页显示数量  
        int totalPages;//总页数  
        int page = dataRequest.getPage() <= 0 ? 1 : dataRequest.getPage();//当前显示页码  
        List<Image> list;
        String criteria = Conversion.initSearchCondition(dataRequest);
        count = globalDao.count(Image.class,criteria);  
        totalPages = count / size;  
        if (count % size != 0) {  
            totalPages++;  
        }  
        int currPage = Math.min(totalPages, page);  
        int start = currPage * size - size;  
        start = start < 0 ? 0 : start;  
        list = globalDao.list(Image.class, criteria+Conversion.sortCriteria(dataRequest.getSidx(), dataRequest.getSord()), start, size);
        response.setRecords(count);  
        response.setTotal(totalPages);  
        response.setPage(currPage);  
        response.setRows(list);  
        return response;  
	}
	@Override
	public String saveHeadPortrait(ImageWebUpload image) {
		if(!image.getFileContentType().startsWith("image/")){
			throw new RuntimeException("格式不支持");
		}
		//获取文件保存路径
		String path = imageDao.getSavePath("avatar_system", System.currentTimeMillis()+"");
		try {
			String md5 = imageDao.getMd5(new FileInputStream(image.getFile()));
			Image img = imageDao.verifyMd5(Image.class, md5);
			if(img==null){//如果没有当前MD5文件则创建一个
				img = new Image("AVATAR",md5,path,1);
				if(imageDao.saveFile(new FileInputStream(image.getFile()), path)){
					globalDao.save(img);
					return Conversion.stringToJson("result,true");
				}
			}else{
				img.setIsSystem(1);img.setId(null);img.setType("AVATAR");
				globalDao.save(img);
				return Conversion.stringToJson("result,true");
			}
		} catch (Exception e) {
			new FileOperation(path).start();
			throw new RuntimeException(e);
		}
		throw new RuntimeException("unknow cause");
	}
	@Override
	public String saveHeadPortrait(ImageWebUpload image,User user) {
		if(!image.getFileContentType().startsWith("image/")){
			throw new RuntimeException("格式不支持");
		}
		//获取文件保存路径
		String path = imageDao.getSavePath("avatar_user", System.currentTimeMillis()+"");
		try {
			String md5 = imageDao.getMd5(new FileInputStream(image.getFile()));
			Image img = imageDao.verifyMd5(Image.class, md5);
			if(img==null){//如果没有当前MD5文件则创建一个
				img = new Image("AVATAR",md5,path,0);
				if(imageDao.saveFile(new FileInputStream(image.getFile()), path)){
					globalDao.save(img);
				}
			}else{
				img.setIsSystem(0);img.setId(null);img.setType("AVATAR");
				globalDao.save(img);
			}
			Image oldImg = user.getAvatar();
			user.setAvatar(img);
			globalDao.update(user);
			if(oldImg!=null&&oldImg.getIsSystem()==0)
				deleteImage(oldImg);
			return Conversion.stringToJson("result,true,id,"+img.getId());
		} catch (Exception e) {
			new FileOperation(path).start();
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean deleteImage(Image image) {
		if(image==null)return true;
		try{
			deleteFileThread(image,true,image.getId());
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public List<Image> findManyByIds(Class<Image> class1, String[] split) {
		return globalDao.findManyByIds(class1, split);
	}
	@Override
	public Image getImage(String id) {
		if(id==null)return null;
		return globalDao.getById(Image.class, id);
	}
	@Override
	public String getPathForCateId(String id) {
		if(id==null)return null;
		return imageDao.getPathForCateId(id);
	}
	@Override
	public List<Image> getDefaultHeadPortrait() {
		return globalDao.list(Image.class, "where type='AVATAR' and isSystem=1", 1, 10);
	}

}
