package cn.catecat.cate.service.dao.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.websocket.Session;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.catecat.cate.action.CateMissionAction;
import cn.catecat.cate.bean.Cate;
import cn.catecat.cate.bean.SpecialShowCate;
import cn.catecat.cate.dao.CateDao;
import cn.catecat.cate.dto.CateSearchRequest;
import cn.catecat.cate.dto.MissionBean;
import cn.catecat.cate.dto.ScanForm;
import cn.catecat.cate.log.bean.CateLog;
import cn.catecat.cate.service.dao.CateService;
import cn.catecat.category.bean.Category;
import cn.catecat.category.dao.CategoryDao;
import cn.catecat.global.dao.GlobalDao;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.global.socket.CommServlet;
import cn.catecat.global.util.CatProperty;
import cn.catecat.global.util.Conversion;
import cn.catecat.global.util.FileOperation;
import cn.catecat.image.bean.Image;
import cn.catecat.image.dao.ImageDao;
@Service
public class CateServiceImpl implements CateService {
	@Autowired private GlobalDao globalDao;
	@Autowired private ImageDao imageDao;
	@Autowired private CategoryDao categoryDao;
	@Autowired private CateDao cateDao;
	/**
	 * 添加或更新美食信息
	 */
	@Override
	public void addOrUpdateCate(Cate cate) {
		String[] cateID = cate.getCategory().split(",");
		List<Category> categorys = globalDao.findManyByIds(Category.class, cateID);
		Set<Category> set = new HashSet<Category>(categorys);
		cate.setCategorys(set);
		if(cate.getId()!=null){
			Cate existCate = getCate(cate.getId());
			if(existCate!=null){
				try {
					Set<Image> imgs = existCate.getImages();
					BeanUtils.copyProperties(existCate, cate);
					existCate.setImages(imgs);
					globalDao.update(existCate);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		else{
			globalDao.save(cate);
		}
	}
	/**
	 * 删除美食，支持删除多个,例（id,id,id）
	 */
	@Override
	public int deleteCate(String id) {
		List<Cate> list = globalDao.findManyByIds(Cate.class, id.split(","),"images");
		for(Cate cate : list){
			if(cate.getLog()!=null){
				CateLog log = cate.getLog();
				cate.setLog(null);
				globalDao.delete(log);
			}
			Set<Image> imgs = cate.getImages();
			cate.setImages(null);	//不解除关联关系会抛org.hibernate.ObjectDeletedException: deleted object would be re-saved by cascade (remove deleted object from asso导演
			for(Image img : imgs){	//这里会使用线程去删除文件
				deleteFileThread(img, true, img.getId());
			}
		}
		int num = globalDao.deleteManyByIds(Cate.class, id.split(","));
		if(num==list.size()){
			return num;
		}else{
			throw new RuntimeException("删除前后数据结果不一致");
		}
	}
	/**
	 * 删除特别美食，支持删除多个,例（id,id,id）
	 */
	@Override
	public int deleteSpecialCate(String id) {
		List<SpecialShowCate> list = globalDao.findManyByIds(SpecialShowCate.class, id.split(","));
		for(SpecialShowCate cate : list){
			if(cate.getImg()!=null){	//这里会使用线程去删除文件
				deleteFileThread(cate.getImg(), true, cate.getImg().getId());
			}
		}
		int num = globalDao.deleteManyByIds(SpecialShowCate.class, id.split(","));
		if(num==list.size()){
			return num;
		}else{
			throw new RuntimeException("删除前后数据结果不一致");
		}
	}

	@Override
	public List<SpecialShowCate> getSpecialCates() {
		return globalDao.getAll(SpecialShowCate.class);
	}
	@Override
	public Cate getCate(String id) {
		Cate cate = globalDao.getById(Cate.class, id);
		return cate;
	}
	/**
	 * 自动扫描
	 */
	@Override
	public List<Cate> scanCate(ScanForm scanForm,Session session) {
		List<Cate> list = new ArrayList<Cate>();
		boolean norm = scanForm.getNorm()==0?true:false;
		CommServlet.sendMessage(session,Conversion.stringToJson("log,"+(norm?"使用默认规则解析":"使用自定义解析规则")));
		if(scanForm.getNorm()==0){	//解析规则读取
			scanForm.setNorm_infor(CatProperty.getPropertyValue("scanRule", "infor"));
			scanForm.setNorm_img(CatProperty.getPropertyValue("scanRule", "img"));
			scanForm.setNorm_property(CatProperty.getPropertyValue("scanRule", "property"));
			scanForm.setNorm_category(CatProperty.getPropertyValue("scanRule", "category"));
			/*
			 * 获取默认打折信息
			 */
			if(scanForm.getPrice_discount()==null||(scanForm.getPrice_discount()!=null&&(scanForm.getPrice_discount()>10||scanForm.getPrice_discount()<0))){
				String price_dis =CatProperty.getPropertyValue("scanRule", "price_discount");
				if(price_dis==null)price_dis="10";
				scanForm.setPrice_discount(Float.parseFloat(price_dis));
			}
		}
		if("|".equals(scanForm.getNorm_category()))scanForm.setNorm_category("\\|");
		autoScan(new File(scanForm.getPath()),session,scanForm.getLevel(),list,scanForm,null);
		return list;
	}
	/**
	 * 自动扫描
	 * @param file
	 * @param session
	 * @param level
	 * @param list
	 * @param scanForm
	 * @param cate
	 */
	public void autoScan(File file, Session session,int level,List<Cate> list, ScanForm scanForm, Cate cate){
		if(file.exists()){
			if(file.isDirectory()){
				if(level>0){
					//判断当前美食是否已存在，存在则放弃
					cate = globalDao.getByType(Cate.class, "name", file.getName());
					if(cate!=null){
						CommServlet.sendMessage(session,Conversion.stringToJson("log,放弃已存在美食："+file.getName()));
						return;
					}
					cate = new Cate(UUID.randomUUID().toString());
					cate.setName(file.getName());
					String[] files = file.list();
					if(files==null)return;
					level--;
					for(String temp : files){
						autoScan(new File(file.getAbsoluteFile(),temp),session,level,list,scanForm,cate);
					}
				}
			}else{
				//判断是否为信息文件
				if(scanForm.getNorm_infor().equals("*.txt")&&file.getName().endsWith(".txt")
					||file.getName().equals(scanForm.getNorm_infor())
					||(scanForm.getNorm_infor().startsWith("*")&&scanForm.getNorm_infor().endsWith(imageDao.getSuffix(file.getName())))){
					CommServlet.sendMessage(session,Conversion.stringToJson("log,扫描到信息文件："+file.getName()));
					resolveInfor(cate,file,scanForm);
					if(cate==null){
						CommServlet.sendMessage(session,Conversion.stringToJson("log,信息文件解析失败："+file.getName()));
						return;
					}else{
						CommServlet.sendMessage(session,Conversion.stringToJson("log,信息文件已解析："+file.getName()));
						if(cate.getCategorys().size()>0){
							boolean isrepet=true;
							for(Cate t : list){//检查有没有相同名称
								if(t.getName().equals(cate.getName())){
									if(!t.getCategory().equals(cate.getCategory())){
										cate.setName(cate.getName()+"_"+System.currentTimeMillis());
										break;
									}else{
										isrepet=false;
									}
								}
							}
							if(isrepet) list.add(cate);
						}
					}
					//判断是否为图片文件
				}else if((scanForm.getNorm_img().equals("img")||file.getName().endsWith(scanForm.getNorm_img()))&&imageDao.checkImgSuffix(file.getName())){
					if(cate==null)return;
					CommServlet.sendMessage(session,Conversion.stringToJson("log,扫描到图片文件："+file.getName()));
					resolveImg(cate,file);
				}
			}
		}
	}
	/**
	 * 解析图片
	 * @param cate
	 * @param file
	 */
	private void resolveImg(Cate cate, File file) {
		String path = null;
		try {
			path = imageDao.getSavePath("cateimg", file.getName(),cate.getId());
			String md5 = imageDao.getMd5(new FileInputStream(file));
			Image img = imageDao.verifyMd5(Image.class, md5);
			if(img==null){//如果没有当前MD5文件则创建一个
				img = new Image("CATE",md5,path,0);
				if(imageDao.saveFile(new FileInputStream(file), path)){
					//globalDao.save(img);
					cate.getImages().add(img);
				}
			}else{
				if(!cate.getImages().contains(img)){
					cate.getImages().add(img);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			new FileOperation(path).start();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 处理
	 * @param cate
	 * @param file
	 * @param scanForm
	 */
	private void resolveInfor(Cate cate,File file,ScanForm scanForm){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),codeString(file)));
			String text=null;
			String categoryValue="";
			while((text= reader.readLine())!=null){
				String[] temp = text.split(scanForm.getNorm_property());
				if(temp.length!=2)continue;
				if(temp[0].equals(CatProperty.getPropertyValue("scanRule", "property_price"))){
					cate.setShopPrice(Float.parseFloat(temp[1]));
					cate.setMarketPrice(Float.parseFloat(temp[1])*(scanForm.getPrice_discount()/10));
				}else if(temp[0].equals(CatProperty.getPropertyValue("scanRule", "property_decript"))){
					if(temp[1].length()<20)cate.setAbout(temp[1].trim());
					else cate.setAbout(temp[1].trim().substring(0,20));
					cate.setIntroduce(temp[1].trim());
				}else{
					String[] categorys = temp[1].trim().split(scanForm.getNorm_category());
					boolean isHasCategory=false;
					for(int i=0;i<categorys.length;i++){
						if(categorys[i].trim().equals("其它"))break;
						Category category = categoryDao.getCategoryByName(categorys[i].trim());
						if(category!=null){
							isHasCategory=true;
							cate.getCategorys().add(category);
							categoryValue += category.getId()+",";
						}
					}
					if(!isHasCategory){
						Category category = categoryDao.getCategoryByName_other(temp[0]);
						if(category!=null){
							cate.getCategorys().add(category);
							categoryValue += category.getId()+",";
						}
					}
				}
			}
			if(categoryValue!=""){
				cate.setCategory(categoryValue.substring(0, categoryValue.length()-1));
			}
			if(cate.getName()==null||cate.getCategorys().size()<1)cate=null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	/** 
	 * 判断文件的编码格式 
	 * @param fileName :file 
	 * @return 文件编码格式 
	 * @throws Exception 
	 */  
	public static String codeString(File file){  
		BufferedInputStream bin=null;
		String code = null;  
		try{
		    bin = new BufferedInputStream(new FileInputStream(file));  
		    int p = (bin.read() << 8) + bin.read();  
		    switch (p) {  
	        case 0xefbb:  
	            code = "UTF-8";  
	            break;  
	        case 0xfffe:  
	            code = "Unicode";  
	            break;  
	        case 0xfeff:  
	            code = "UTF-16BE";  
	            break;  
	        default:  
	            code = "GBK";  
		    }
		    return code;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bin!=null)
				try {
					bin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	    return "UTF-8";  
	}  
	@Override
	public int[] saveList(List<Cate> cates) {
		return globalDao.saveOrUpdateMany(cates);
	}

	@Override
	public boolean checkname(String name,String id) {
		if(id!=null&&!id.equals("")){
			return globalDao.getByType(Cate.class, "name",name, id)==null?false:true;
		}else
			return globalDao.getByType(Cate.class, "name", name)==null?false:true;
	}

	@Override
	public DataResponse<Cate> list(DataRequest dataRequest) {
		DataResponse<Cate> response = new DataResponse<Cate>();  
        int count;//总记录数  
        int size = dataRequest.getRows() <= 0 ? 20 : dataRequest.getRows();//每页显示数量  
        int totalPages;//总页数  
        int page = dataRequest.getPage() <= 0 ? 1 : dataRequest.getPage();//当前显示页码  
        List<Cate> list;
        String criteria = Conversion.initSearchCondition(dataRequest);
        count = globalDao.count(Cate.class,criteria);  
        totalPages = count / size;  
        if (count % size != 0) {  
            totalPages++;  
        }  
        int currPage = Math.min(totalPages, page);  
        int start = currPage * size - size;  
        start = start < 0 ? 0 : start;  
        list = globalDao.list(Cate.class, criteria+Conversion.sortCriteria(dataRequest.getSidx(), dataRequest.getSord()), start, size);
        response.setRecords(count);  
        response.setTotal(totalPages);  
        response.setPage(currPage);  
        response.setRows(list);  
        return response;  
	}
	//查询后台美食分页
	@Override
	public DataResponse<Cate> list_bgm(DataRequest dataRequest) {
		DataResponse<Cate> cateResponse = list(dataRequest);
        if(cateResponse.getRows()==null)return cateResponse;
        for(Cate cate : cateResponse.getRows()){
        	cate.setCategorys(null);
        }
        return cateResponse;  
	}
	/**
	 * 查询文件是否被占用
	 * @param id
	 * @return true  占用
	 * @return false  不占用
	 */
	public boolean selectFileIsUse(String id,String ... imgId){
		int i= globalDao.count(Image.class, " where t.id='"+id+"'"+(imgId.length>0?" and t.id!='"+imgId[0]+"'":""));
		if(i>0)return true;
		else return false;
	}
	/**
	 * 删除文件线程分配
	 * @param image		图片文件
	 * @param isSelect	是否需要向数据库查询文件被占用
	 * @param id		查询是否占用时排除的ID
	 */
	public void deleteFileThread(Image image,boolean isSelect,String ... id){
		if(image!=null&&image.getId()!=null){
			if(!(isSelect&&selectFileIsUse(image.getId(),id))){
				globalDao.delete(image);	//删除图片对象
				new FileOperation(image.getPath()).start();	//启动线程删除本地文件
			}
		}
	}
	public String mission(String type){
		return null;
	}
	@Override
	public int onOrOffCate(String id, int type) {
		List<Cate> list = globalDao.findManyByIds(Cate.class, id.split(","));
		List<Cate> passed = new ArrayList<Cate>();
		for(Cate cate : list){
			if(cate.getIsOnline()!=type){
				if(type==1&&cate.getLog()==null){
					cate.setOnLineTime(new Date());
					cate.setLog(new CateLog(0,0,"0,0,0,0,0"));
				}
				cate.setIsOnline(type);
				passed.add(cate);
			}
		}
		int num=0;
		if(passed.size()>0){
			int[] result = saveList(passed);
			for(int i=0;i<result.length;i++)if(result[i]==1)num++;
		}
		return num;
		//return cateDao.updateCateByField(id.split(","), "isOnline", type);
	}
	@Override
	public List<Cate> getCateByOnOrOff(int type) {
		return cateDao.getCateByOnOrOff(type);
	}
	@Override
	public void autoOnline(MissionBean missionBean) throws InterruptedException {
			System.out.println("liuhua:自动上线正在执行");
			List<Cate> passed = new ArrayList<Cate>();
			List<Cate> list = cateDao.getCateByOnOrOff(0);
			List<Category> categoryRoot = categoryDao.getRootCategory();
			for(int i=0;list!=null&&i<list.size();i++){
				Cate cate = getCate(list.get(i).getId());
				if(cate.getCategory()==null)continue;
				boolean catePass = false;//分类检验，每个分类至少包含一个
				for(Category category : categoryRoot){
					catePass = false;
					List<Category> categoryChild = categoryDao.getChilds(category.getId());
					if(categoryChild!=null)
					/*for(Category temp : categoryChild){
						if(cate.getCategory().indexOf(temp.getId())!=-1){
							catePass=true;break;
						}
					}*/
						for(Category temp : categoryChild){
							for(Category myCategory : cate.getCategorys()){
								if(temp.getId().equals(myCategory.getId())){
									catePass=true;break;
								}
							}
							if(cate.getCategory().indexOf(temp.getId())!=-1){
								if(catePass){
									catePass=true;break;
								}
							}else{
								if(catePass){
									String categoryValue = "";
									for(Category myCategory : cate.getCategorys()){
										categoryValue += myCategory.getId()+",";
									}
									cate.setCategory(categoryValue.substring(0,categoryValue.length()-1));
								}
							}
						}
					if(!catePass)break;
				}
				if(!catePass)continue;
				//检查图片是否丢失
				Set<Image> imgs = cate.getImages();
				catePass=true;
				if(imgs==null||imgs!=null&&imgs.size()<1)continue;
				for(Image img : imgs){
					File video = new File(img.getPath());
					if(!video.exists()){
						catePass=false;
					}
				}
				if(catePass){
					if(cate.getLog()==null)cate.setLog(new CateLog(0,0,"0,0,0,0,0"));
					cate.setIsOnline(1);
					cate.setOnLineTime(new Date());
					passed.add(cate);
				}
				if(missionBean!=null){
					if(missionBean.getState()==2){
						CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()), Conversion.stringToJson("type,"+missionBean.getType()+",state,2"));
						CateMissionAction.ONLINELOCK.wait();
						CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()), Conversion.stringToJson("type,"+missionBean.getType()+",state,1"));
					}else if(missionBean.getState()==0){
						CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()), Conversion.stringToJson("type,"+missionBean.getType()+",state,0"));
						return;
					}
				}
			}
			int result=0;
			if(passed.size()>0){
				int num[]= new int[passed.size()];
				//对符合规范的进行更新
				num = saveList(passed);
				for(int i=0;i<num.length;i++){
					if(num[i]==1)result++;
				}
			}
			CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,10,handler,"+result));
			CateMissionAction.removeMission(missionBean.getType());
		
	}
	@Override
	public void autoOffline(MissionBean missionBean) throws InterruptedException {
		System.out.println("liuhua:自动下线正在执行");
		List<Cate> passed = new ArrayList<Cate>();
		List<Cate> list = cateDao.getCateByOnOrOff(1);
		List<Category> categoryRoot = categoryDao.getRootCategory();
		for(int i=0;list!=null&&i<list.size();i++){
			Cate cate = getCate(list.get(i).getId());
			if(cate.getCategory()==null)continue;
			boolean catePass = false;//分类检验，每个分类至少包含一个
			for(Category category : categoryRoot){
				catePass = false;
				List<Category> categoryChild = categoryDao.getChilds(category.getId());
				if(categoryChild!=null)
				for(Category temp : categoryChild){
					for(Category myCategory : cate.getCategorys()){
						if(temp.getId().equals(myCategory.getId())){
							catePass=true;break;
						}
					}
					if(cate.getCategory().indexOf(temp.getId())!=-1){
						if(!catePass){
							String categoryValue = "";
							for(Category myCategory : cate.getCategorys()){
								categoryValue += myCategory.getId()+",";
							}
							cate.setCategory(categoryValue.substring(0,categoryValue.length()-1));
							catePass=false;
						}
						catePass=true;break;
					}
				}
				if(!catePass)break;
			}
			if(!catePass){
				cate.setIsOnline(0);
				passed.add(cate);
				continue;
			}
			//检查图片是否丢失
			Set<Image> imgs = cate.getImages();
			catePass=true;
			if(imgs==null||imgs!=null&&imgs.size()<1)continue;
			for(Image img : imgs){
				File video = new File(img.getPath());
				if(!video.exists()){
					catePass=false;
				}
			}
			if(!catePass){
				cate.setIsOnline(0);
				passed.add(cate);
			}
			if(missionBean!=null){
				if(missionBean.getState()==2){
					CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()), Conversion.stringToJson("type,"+missionBean.getType()+",state,2"));
					CateMissionAction.ONLINELOCK.wait();
					CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()), Conversion.stringToJson("type,"+missionBean.getType()+",state,1"));
				}else if(missionBean.getState()==0){
					CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()), Conversion.stringToJson("type,"+missionBean.getType()+",state,0"));
					return;
				}
			}
		}
		int result=0;
		if(passed.size()>0){
			int num[]= new int[passed.size()];
			//对符合规范的进行更新
			num = saveList(passed);
			for(int i=0;i<num.length;i++){
				if(num[i]==1)result++;
			}
		}
		CommServlet.sendMessage(CommServlet.getSessionById(missionBean.getId()),Conversion.stringToJson("type,"+missionBean.getType()+",state,10,handler,"+result));
		CateMissionAction.removeMission(missionBean.getType());
	}
	@Override
	public void clearRubbish() {
		imageDao.deleteRubbishImage();
	}
	@Override
	public boolean findSourceByPath(String absPath) {
		try {
			InputStream input = new FileInputStream(absPath);
			String md5 = imageDao.getMd5(input);
			if(imageDao.verifyMd5(Image.class, md5)!=null)return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Map<String, String> categoryOfCateNum() {
		List<Object> list= cateDao.categoryOfCateNum();
		if(list==null)return null;
		Map<String,String> map = new HashMap<String,String>();
		for(Object obj : list){
			Object[] objArr = (Object[]) obj;
			map.put(objArr[0]+"", objArr[1]+"");
		}
		return map;
	}
	@Override
	public DataResponse<Cate> searchList(CateSearchRequest cateRequest, List<Category> kind) {
		DataResponse<Cate> response = new DataResponse<Cate>();  
        int count;//总记录数  
        int size = cateRequest.getRows() <= 0 ? 20 : cateRequest.getRows();//每页显示数量  
        int totalPages;//总页数  
        int page = cateRequest.getPage() <= 0 ? 1 : cateRequest.getPage();//当前显示页码  
        List<Cate> list;
        String criteria = cateSearchCondition(cateRequest,kind);
        count = globalDao.count(Cate.class,criteria);  
        totalPages = count / size;  
        if (count % size != 0) {  
            totalPages++;  
        }  
        int currPage = Math.min(totalPages, page);  
        int start = currPage * size - size;  
        start = start < 0 ? 0 : start;  
        list = globalDao.list(Cate.class, criteria+Conversion.sortCriteria(cateRequest.getSidx(), cateRequest.getSort()), start, size);
        response.setRecords(count);  
        response.setTotal(totalPages);  
        response.setPage(currPage);  
        response.setRows(list);  
        return response;  
	}
	public String cateSearchCondition(CateSearchRequest cateRequest,List<Category> kind){
		String category = cateRequest.getCategory();
		StringBuffer search = new StringBuffer("where isOnline=1 ");
		if(category!=null&&!category.equals("")){
			String[] categorys = category.split(",");
			/*进行类别分类区分start*/
			List<String> kindCategory = null;List<String> otherCategory=null;
			for(String temp :categorys){
				if(kind!=null&&kind.contains(new Category(temp))){
					if(kindCategory==null)kindCategory = new ArrayList<String>();
					kindCategory.add(temp);
				}else{
					if(otherCategory==null)otherCategory = new ArrayList<String>();
					otherCategory.add(temp);
				}
			}
			/*进行类别分类区分end*/
			/*分类组查询语句start*/
			if(kindCategory!=null){
				search.append(" and ((");
				for(int i=0;i<kindCategory.size();i++){
					search.append("category like '%"+kindCategory.get(i)+"%'"+(i!=kindCategory.size()-1?" or ":""));
				}
				search.append(" )");
				if(otherCategory!=null){
					search.append(" and ");
					for(int j=0;j<otherCategory.size();j++){
						search.append("category like '%"+otherCategory.get(j)+"%'"+(j!=otherCategory.size()-1?" and ":""));
					}
				}
				search.append(" )");
			}else{
				if(otherCategory!=null){//没类别有其它分类
					for(int i=0;i<otherCategory.size();i++){
						search.append(" and category like '%"+otherCategory.get(i)+"%' ");
					}
				}
			}
			/*分类组查询语句end*/
		}
		if(cateRequest.getPriceMin()>=0){
			search.append(" and marketPrice>="+cateRequest.getPriceMin());
		}
		if(cateRequest.getPriceMax()>0){
			search.append(" and marketPrice<="+cateRequest.getPriceMax());
		}
		if(cateRequest.getSearchString()!=null){
			search.append(" and name like '%"+cateRequest.getSearchString()+"%'");
		}
		return search.toString();
	}
	@Override
	public List<Cate> getManyByCategoryIds(String searchCategory, int size) {
		String[] ids = searchCategory.split(",");
		String sql = "from Cate ";
		for(int i=0;i<ids.length;i++){
			if(i==0)sql += " where isOnline=1 and ";
			if(i==ids.length-1)sql += "category like '%"+ids[i]+"%'";
			else sql += "category like '%"+ids[i]+"%' or ";
		}
		sql += " ORDER BY RAND()";
		return cateDao.getManyByHql(sql,size);
	}
	@Override
	public Map<String, String> findNameByKey(String key) {
		List<Object[]> list = cateDao.findNameByKey(key,7);
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0;list!=null&&i<list.size();i++){
			Object[] obj = list.get(i);
			map.put(obj[0]+"", obj[1]+"");
		}
		map.put("length", map.size()+"");
		return map;
	}
	//通过美食ID查询数据库
	@Override
	public Cate findCateById(String cateId) {
		return cateDao.findCateById(cateId);
	}
	@Override
	public List<Cate> getActiveCate(int i) {
		return cateDao.getManyByHql("from Cate order by log.buyCount", i);
	}
	@Override
	public List<Cate> getNewCate(int i) {
		List<Cate> list = cateDao.getManyByHql("from Cate order by onLineTime", i);
		for(Cate cate : list){
			cate.getImages().size();
		}
		return list;
	}
	@Override
	public List<Cate> getRandCate(int i) {
		return cateDao.getManyByHql("from Cate where isOnline=1 ORDER BY RAND()", i);
	}
	
}
