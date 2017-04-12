package cn.catecat.global.service.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.catecat.global.dao.GlobalDao;
import cn.catecat.global.dto.DataRequest;
import cn.catecat.global.dto.DataResponse;
import cn.catecat.global.service.dao.GlobalService;
import cn.catecat.global.util.Conversion;
@Service
public class GlobalServiceImpl implements GlobalService{
	@Autowired private GlobalDao global;
	@Override
	public <T> void save(T t) {
		global.save(t);
	}

	@Override
	public <T> void update(T t) {
		global.update(t);
	}

	@Override
	public <T> void delete(T t) {
		global.delete(t);
	}

	@Override
	public <T> T getById(Class<T> cls, Serializable id) {
		return global.getById(cls, id);
	}

	@Override
	public <T> DataResponse<T> list(Class<T> cls,DataRequest dataRequest) {
		DataResponse<T> response = new DataResponse<T>();  
        int count;//总记录数  
        int size = dataRequest.getRows() <= 0 ? 20 : dataRequest.getRows();//每页显示数量  
        int totalPages;//总页数  
        int page = dataRequest.getPage() <= 0 ? 1 : dataRequest.getPage();//当前显示页码  
        List<T> list;
        String criteria = Conversion.initSearchCondition(dataRequest);
        count = global.count(cls,criteria);  
        totalPages = count / size;  
        if (count % size != 0) {  
            totalPages++;  
        }  
        int currPage = Math.min(totalPages, page);  
        int start = currPage * size - size;  
        start = start < 0 ? 0 : start;  
        list = global.list(cls, criteria+Conversion.sortCriteria(dataRequest.getSidx(), dataRequest.getSord()), start, size);
        response.setRecords(count);  
        response.setTotal(totalPages);  
        response.setPage(currPage);  
        response.setRows(list);  
        return response;  
	}

	@Override
	public <T> int deleteManyByIds(Class<T> cls, String ids) {
		return global.deleteManyByIds(cls, ids.split(","));
	}

	@Override
	public <T> int deleteManyByIds(Class<T> cls, Object[] id) {
		return global.deleteManyByIds(cls, id);
	}

	@Override
	public <T> List<T> findManyByIds(Class<T> cls, Object[] ids, String... fetch) {
		return global.findManyByIds(cls, ids, fetch);
	}

	@Override
	public <T> int[] saveOrUpdateMany(List<T> list) {
		return global.saveOrUpdateMany(list);
	}

	@Override
	public <T> List<T> getAll(Class<T> cls) {
		return global.getAll(cls);
	}

	@Override
	public <T> T getByType(Class<T> cls, String type, Serializable value) {
		return global.getByType(cls, type, value);
	}

	@Override
	public <T> T getByType(Class<T> cls, String name, String value, String id) {
		return global.getByType(cls,name,value,id);
	}

	@Override
	public <T> void saveOrUpdate(T t) {
		global.saveOrUpdate(t);
	}

	@Override
	public <T> void updateMany(List<T> ts) {
		global.updateMany(ts);
	}
	@Override
	public <T> Integer updateManyByFields(Class<T> cls,String[] ids,String[] name, Object[] value) {
		StringBuffer criteria = new StringBuffer();
		for(int i=0;i<name.length;i++){
			criteria.append(name[i]+"=?"+(i!=name.length-1?",":""));
		}
		return global.updateManyByFields(cls, criteria.toString(), ids,value);
	}
}
