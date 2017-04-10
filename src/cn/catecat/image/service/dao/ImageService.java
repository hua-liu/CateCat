package cn.catecat.image.service.dao;

import java.io.InputStream;
import java.util.List;

import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.image.bean.Image;
import cn.catecat.image.dto.ImageWebUpload;
import cn.catecat.user.bean.User;

public interface ImageService {
	/**
	 * 保存图片
	 * @param images
	 * @return
	 */
	String saveFile(ImageWebUpload images);
	/**
	 * 读取图片
	 * @param id
	 * @param type
	 * @param response
	 * @return 
	 */
	InputStream requestImg(String id, String type);
	/**
	 * 删除美食图片
	 * @param id
	 * @param cateid
	 * @return
	 */
	String deleteImg(String id, String cateid);
	/**
	 * 获取默认头像列表
	 * @param dataRequest
	 * @return
	 */
	DataResponse<Image> defaultList(DataRequest dataRequest);
	/**
	 * 头像上传保存
	 * @param images
	 * @return
	 */
	String saveHeadPortrait(ImageWebUpload images);
	/**
	 * 头像上传保存
	 * @param images
	 * @param user 
	 * @return
	 */
	String saveHeadPortrait(ImageWebUpload images, User user);
	/**
	 * 删除图像
	 * @param id
	 * @return
	 */
	boolean deleteImage(Image image);
	/**
	 * 查询多
	 * @param class1
	 * @param split
	 * @return
	 */
	List<Image> findManyByIds(Class<Image> class1, String[] split);
	/**
	 * 根据ID获取IMAGE
	 * @param id
	 * @return
	 */
	public Image getImage(String id);
	/**
	 * 根据美食ID获取一张图片
	 * @param id
	 * @return
	 */
	String getPathForCateId(String id);
	/**
	 * 保存特殊展示图片
	 * @param images
	 * @return
	 */
	String saveFileForSpecial(ImageWebUpload images);
	/**
	 * 获取默认头像
	 * @return
	 */
	List<Image> getDefaultHeadPortrait();
	
}
