package cn.catecat.cate.service.dao;

import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.bean.SpecialShowCate;
import cn.catecat.cate.dto.CateSearchRequest;
import cn.catecat.cate.dto.MissionBean;
import cn.catecat.cate.dto.ScanForm;
import cn.catecat.category.bean.Category;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;

public interface CateService {
	void addOrUpdateCate(Cate cate);
	int deleteCate(String ids);
	/**
	 * 获取美食对象信息
	 * @param id
	 * @return
	 */
	Cate getCate(String id);
	/**
	 * 从本地扫描美食
	 * @param scanForm
	 * @param session
	 * @return
	 */
	List<Cate> scanCate(ScanForm scanForm,Session session);
	/**
	 * 保存扫描后的美食列表
	 * @param cates
	 * @return
	 */
	int[] saveList(List<Cate> cates);
	/**
	 * 检查名称是否已存在
	 * @param name
	 * @param id	排除之外
	 * @return
	 */
	boolean checkname(String name, String id);
	/**
	 * 分页请求列表
	 * @param dataRequest
	 * @return
	 */
	DataResponse<Cate> list(DataRequest dataRequest);
	/**
	 * 后台管理美食请求列表
	 * @param dataRequest
	 * @return
	 */
	DataResponse<Cate> list_bgm(DataRequest dataRequest);
	/**
	 * 上线或下线美食
	 * @param id
	 * @param type
	 * @return
	 */
	int onOrOffCate(String id, int type);
	/**
	 * 查找所有上线或下线美食
	 * @param type  1上线  0下线
	 * @return
	 */
	List<Cate> getCateByOnOrOff(int type);
	/**
	 * 自动上线任务
	 * @param missionBean
	 * @throws InterruptedException
	 */
	void autoOnline(MissionBean missionBean) throws InterruptedException;
	/**
	 * 自动下线任务
	 * @param missionBean
	 * @throws InterruptedException
	 */
	void autoOffline(MissionBean missionBean) throws InterruptedException;
	/**
	 * 清理数据库无主文件任务
	 */
	void clearRubbish();
	/**
	 * 清除本地无主指向文件任务
	 * @param absPath
	 * @return
	 */
	boolean findSourceByPath(String absPath);
	/**
	 * 查询每个分类美食数量
	 * @return
	 */
	Map<String, String> categoryOfCateNum();
	/**
	 * 列表页专用搜索
	 * @param cateRequest
	 * @param kind 
	 * @return
	 */
	DataResponse<Cate> searchList(CateSearchRequest cateRequest, List<Category> kind);
	/**
	 * 根据多分类ID随机查询
	 * @param searchCategory
	 * @param i	查询数量
	 * @return
	 */
	List<Cate> getManyByCategoryIds(String searchCategory, int i);
	/**
	 * 根据关键字查名称
	 * @param key
	 * @return
	 */
	Map<String, String> findNameByKey(String key);
	/**
	 * 删除特殊展示
	 * @param id
	 * @return
	 */
	int deleteSpecialCate(String id);
	/**
	 * 通过美食ID查询数据库
	 * @param cateId
	 * @return
	 */
	Cate findCateById(String cateId);
	/**
	 * 获取主页特殊展示
	 * @return
	 */
	List<SpecialShowCate> getSpecialCates();
	/**
	 * 获取畅销商品
	 * @param i
	 * @return
	 */
	List<Cate> getActiveCate(int i);
	/**
	 * 获取最新上架的商品
	 * @param i
	 * @return
	 */
	List<Cate> getNewCate(int i);
	/**
	 * 获取随机商品
	 * @param i
	 * @return
	 */
	List<Cate> getRandCate(int i);
}
