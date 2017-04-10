package cn.catecat.global.util;

import cn.catecat.global.dto.DataRequest;

public class Conversion{
	/*public T  beanToBean(T target,K source){
		BeanUtils.copyProperties(source, target);
		return target;
	}*/
	/**
	 * 普通字符串转json字符串，注意：传入字符串需键值对应
	 * @param 接收一个字符串，例："abc,true,abc,ead"
	 * @return {"abc":true,"abc":"ead"}
	 */
	public static String stringToJson(String str){
		String[] newArray = str.split(",");
		StringBuffer json = new StringBuffer("{");
		for(int i=0;i<newArray.length;i++){
			if(i%2==0){
				json.append("'"+newArray[i]+"':");
			}else{
				if(newArray[i].equals("true")||newArray[i].equals("false")||newArray[i].equals("null")){
					json.append(newArray[i]+",");
				}else{
					json.append("'"+newArray[i]+"',");
				}
			}
		}
		System.out.println("StringToJson:"+json.deleteCharAt(json.length()-1).append("}").toString());
		return json.deleteCharAt(json.length()-1).append("}").toString();
	}
	/**
	 * 字符转义
	 * @param message
	 * @return
	 */
	public static String filter(String message){
		if(message == null){
			return null;
		}
		char content[] = new char[message.length()];
		message.getChars(0, message.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length);
		for(int i=0;i<content.length;i++){
			switch(content[i]){
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			default:
				result.append(content[i]);
			}
		}
		return result.toString();
	}
	//获取搜索条件
		public static String initSearchCondition(DataRequest dataRequest) {
			StringBuffer sb = new StringBuffer(" where(");
			if(dataRequest.isSearch()){
				//如果搜索的字段不一致则不搜索
				if(dataRequest.getSearchField()==null||dataRequest.getSearchOper()==null||dataRequest.getSearchString()==null)return "";
				if(dataRequest.getSearchField().length!=dataRequest.getSearchOper().length&&dataRequest.getSearchField().length!=dataRequest.getSearchString().length)return "";
				//循环进行条件语句拼接
				for(int i=0;i<dataRequest.getSearchField().length;i++){
					if(dataRequest.getSearchString()[i]==null)continue;
					String searchString = dataRequest.getSearchString()[i];
					String searchOper = dataRequest.getSearchOper()[i];
					String searchField = dataRequest.getSearchField()[i];
					//这里目前只兼容like搜索的或条件
					if(i!=0&&i<dataRequest.getSearchField().length)sb.append(" or ");
					sb.append("t."+searchField);
					//这里加入管理员条件
					if("eq".equals(searchOper)){
						sb.append(" = '" +searchString+"'");
					}else if("ne".equals(searchOper)){
						sb.append(" != '" +searchString+"'");
					}else if("lt".equals(searchOper)){
						sb.append(" < '" +searchString+"'");
					}else if("le".equals(searchOper)){
						sb.append(" <= '" +searchString+"'");
					}else if("gt".equals(searchOper)){
						sb.append(" > '" +searchString+"'");
					}else if("ge".equals(searchOper)){
						sb.append(" >= '" +searchString+"'");
					}else if("nu".equals(searchOper)||"nc".equals(searchOper)||"ni".equals(searchString)){
						sb.append(" not like '%" +searchString+"%'");
					}else if("nn".equals(searchOper)||"cn".equals(searchOper)||"in".equals(searchOper)){
						sb.append(" like '%" +searchString+"%'");
					}else if("bw".equals(searchOper)){
						sb.append(" like '" +searchString+"%'");
					}else if("bn".equals(searchOper)){
						sb.append("not like '" +searchString+"%'");
					}else if("ew".equals(searchOper)){
						sb.append("like '%" +searchString+"'");
					}else if("en".equals(searchOper)){
						sb.append("not like '%" +searchString+"'");
					}
				}
				sb.append(")");
				return sb.toString();
			}
			return "";
		}
		//获取排序条件
		public static String sortCriteria(String sidx,String sord){
			if(sidx!=null&&!sidx.trim().equals("")){
				return " order by "+sidx+" "+sord;
			}
			return "";
		}
}
