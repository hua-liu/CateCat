package cn.catecat.image.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

import javax.persistence.Table;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.catecat.global.util.CatProperty;
import cn.catecat.global.util.FileOperation;
import cn.catecat.image.bean.Image;
import cn.catecat.image.dao.ImageDao;
@Repository
public class ImageImpl implements ImageDao{
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public boolean checkImgSuffix(String name) {
		if(name==null||name!=null&&name.trim().equals("")){
			return false;
		}
		String suff = CatProperty.getPropertyValue("scanRule", "imgsuffix");
		String[] fileSuffix;
		if(suff!=null)fileSuffix = suff.split(",");
		else fileSuffix = new String[]{".jpg",".gif",".png",".jpge"};
		for (String fileSuffixName : fileSuffix) {
			if (name.toLowerCase().endsWith(fileSuffixName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getMd5(InputStream input) {
		// 缓冲区大小（这个可以抽出一个参数）
	      int bufferSize = 1024*10000;
	      DigestInputStream digestInputStream = null;
	      try {
	         // 拿到一个MD5转换器（同样，这里可以换成SHA1）
	         MessageDigest messageDigest =MessageDigest.getInstance("MD5");
	         // 使用DigestInputStream
	         digestInputStream = new DigestInputStream(input,messageDigest);
	         // read的过程中进行MD5处理，直到读完文件
	         byte[] buffer =new byte[bufferSize];
	         while (digestInputStream.read(buffer) > 0);
	         // 获取最终的MessageDigest
	         messageDigest= digestInputStream.getMessageDigest();
	         // 拿到结果，也是字节数组，包含16个元素
	         byte[] resultByteArray = messageDigest.digest();
	         // 同样，把字节数组转换成字符串
	         return byteArrayToHex(resultByteArray);
	      } catch (Exception e) {
	         return null;
	      }finally{
	    	  if(digestInputStream!=null)
				try {
					digestInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	}
	//下面这个函数用于将字节数组换成成16进制的字符串
	   public static String byteArrayToHex(byte[] b) {
	        String hs = "";   
	        String stmp = "";   
	        for (int n = 0; n < b.length; n++) {   
	            stmp = (Integer.toHexString(b[n] & 0XFF));   
	            if (stmp.length() == 1) {   
	                hs = hs + "0" + stmp;   
	            } else {   
	                hs = hs + stmp;   
	            }   
	            if (n < b.length - 1) {   
	                hs = hs + "";   
	            }   
	        }   
	        return hs;
	}
	@Override
	public String getSuffix(String name) {
		if(name==null||name!=null&&name.lastIndexOf(".")==-1)return "";
		return name.substring(name.lastIndexOf("."));
	}

	@Override
	public String getDir(String type,String ... uuid) {
		String dir = CatProperty.getPropertyValue("path", type);
		if(uuid!=null&&uuid.length>0){
			int dir1 = uuid[0].hashCode() & 0xf;
			int dir2 = (uuid[0].hashCode() >> 4) & 0xf;
			dir += File.separator+dir1+File.separator+dir2;
		}
		File file = new File(dir);
		if(!file.exists())file.mkdirs();
		return dir;
	}

	@Override
	public boolean saveFile(InputStream input, String path) {
		FileOutputStream outputStream=null;
		try {
			outputStream = new FileOutputStream(path);
			byte[] buf = new byte[10240];
			int length = 0;
			while ((length = input.read(buf)) != -1) {
				outputStream.write(buf, 0, length);
			}
			outputStream.flush();
			return true;
		} catch (Exception e) {
			new FileOperation(path).start(); // 启用线程删除文件
			throw new RuntimeException(e);
		}finally{
				try {
					if(outputStream!=null) outputStream.close();
					if(input!=null) input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public boolean saveFile(byte[] b, String path) {
		FileOutputStream output=null;
		try {
			output = new FileOutputStream(path);
			output.write(b);
			output.flush();
			return true;
		} catch (Exception e) {
			new FileOperation(path).start(); // 启用线程删除文件
			throw new RuntimeException(e);
		}finally{
			try {
				if(output!=null)
				output.close();
			} catch (IOException e) {
				output=null;
			}
		}
	}

	@Override
	public <T> T verifyMd5(Class<T> cls, String md5) {
		List<T> list = sessionFactory.getCurrentSession().createQuery("from "+cls.getName()+" where md5=:md",cls).setParameter("md", md5).getResultList();
		if(list!=null&&list.size()>0)return list.get(0);
		else return null;
	}

	@Override
	public String getSavePath(String type, String fileName,String ... id) {
		String uuid;
		if(!(id!=null&&id.length>0))uuid = UUID.randomUUID().toString();
		else uuid = id[0];
		return getDir(type,uuid)+File.separator+uuid+"_"+fileName;
	}

	@Override
	public int deleteCate_image(String sql, String cateid, String id) {
		return sessionFactory.getCurrentSession().createNativeQuery(sql).setParameter(1, cateid).setParameter(2, id).executeUpdate();
	}

	@Override
	public int deleteRubbishImage() {
		Table table = Image.class.getAnnotation(Table.class);
		return sessionFactory.getCurrentSession().createNativeQuery("delete from "+table.name()+" where id not in(select image_id from cate_images)").executeUpdate();
	}

	@Override
	public String getPathForCateId(String id) {
		List<?> list = sessionFactory.getCurrentSession().createNativeQuery("select path from image where id=(select max(image_id) from cate_images where cate_id=:id)").setParameter("id", id).getResultList();
		if(list!=null&&list.size()>0)return list.get(0)+"";
		return null;
	}
	
}
