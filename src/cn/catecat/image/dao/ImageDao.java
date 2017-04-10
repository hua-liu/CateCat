package cn.catecat.image.dao;

import java.io.InputStream;

public interface ImageDao {
	/**
	 * 检查图片后缀是否合法
	 * @param name
	 * @return
	 */
	boolean checkImgSuffix(String name);
	/**
	 * 通过流计算文件md5,返回结果
	 * @param input
	 * @return
	 */
	String getMd5(InputStream input);
	/**
	 * 获取文件后缀
	 * @param name
	 * @return
	 */
	String getSuffix(String name);
	/**
	 * 通过传入类型获取配置路径
	 * @param type
	 * @return
	 */
	String getDir(String type,String ... uuid);
	/**
	 * 通过输入流保存文件
	 * @param input
	 * @param path
	 * @return
	 */
	boolean saveFile(InputStream input,String path);
	/**
	 * 通过字节保存文件
	 * @param input
	 * @param path
	 * @return
	 */
	boolean saveFile(byte[] b,String path);
	/**
	 * 检查当前md5文件是否已经存在
	 * @param cls
	 * @param md5
	 * @return  存在返回true,反之返回false
	 */
	<T> T verifyMd5(Class<T> cls,String md5);
	/**
	 * 获取保存文件目录
	 * @param fileName
	 * @param id 
	 * @return
	 */
	String getSavePath(String type,String fileName, String ... id);
	/**
	 * 删除美食与图片的关系
	 * @param sql
	 * @param cateid
	 * @param id
	 * @return
	 */
	int deleteCate_image(String sql, String cateid, String id);
	/**
	 * 清除没有指向的图片
	 */
	int deleteRubbishImage();
	/**
	 * 通过美食ID获取美食一张图片地址 
	 * @param id
	 * @return
	 */
	String getPathForCateId(String id);
}
