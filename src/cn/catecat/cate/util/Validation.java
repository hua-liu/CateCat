package cn.catecat.cate.util;

import cn.catecat.cate.bean.Cate;
import cn.catecat.global.util.Conversion;

public class Validation {
	public static String valid(Cate cate){
		if(cate==null){
			return "美食信息获取失败";
		}
		if(cate.getName()==null||cate.getName()!=null&&cate.getName().trim().equals("")){
			return "美食名称不能为空";
		}else if(cate.getName().length()>60){
			return "美食名称长度不能超过60位";
		}
		cate.setName(Conversion.filter(cate.getName()));
		if(cate.getCategory()==null||cate.getCategory()!=null&&cate.getCategory().length()<78){
			return "美食分类不能为空";
		}
		if(cate.getAbout()!=null&&cate.getAbout().length()>40){
			return "美食简介长度不能超过40位";
		}
		cate.setAbout(Conversion.filter(cate.getAbout()));
		if(cate.getIntroduce()!=null&&cate.getIntroduce().length()>250){
			return "美食描述长度不能超过250位";
		}
		cate.setIntroduce(Conversion.filter(cate.getIntroduce()));
		return null;
	}
}
