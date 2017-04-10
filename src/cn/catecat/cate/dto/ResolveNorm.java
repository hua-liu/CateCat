package cn.catecat.cate.dto;
/**
 * @see dirLevel		文件夹层数
 * @see inforSuffix		信息后缀
 * @see imgSuffix		图片后缀
 * @see propertySplit	属性分割
 * @see categoryIndex	系列位置
 * @see nameIndex		名称位置
 * @see imgListType		图片命名类型(数字,字母)
 * @see imgAddStartSign	图片命名开始标记
 * @see imgAddStep		图片标记步进
 * @author 刘华
 *
 */
public class ResolveNorm {
	private int dirLevel;
	private String inforSuffix;
	private String imgSuffix;
	private String propertySplit;
	private int categoryIndex;
	private int nameIndex;
	private String imgListType;
	private String imgAddStartSign;
	private String imgAddStep;
	public int getDirLevel() {
		return dirLevel;
	}
	public void setDirLevel(int dirLevel) {
		this.dirLevel = dirLevel;
	}
	public String getInforSuffix() {
		return inforSuffix;
	}
	public void setInforSuffix(String inforSuffix) {
		this.inforSuffix = inforSuffix;
	}
	public String getImgSuffix() {
		return imgSuffix;
	}
	public void setImgSuffix(String imgSuffix) {
		this.imgSuffix = imgSuffix;
	}
	public String getPropertySplit() {
		return propertySplit;
	}
	public void setPropertySplit(String propertySplit) {
		this.propertySplit = propertySplit;
	}
	public int getCategoryIndex() {
		return categoryIndex;
	}
	public void setCategoryIndex(int categoryIndex) {
		this.categoryIndex = categoryIndex;
	}
	public int getNameIndex() {
		return nameIndex;
	}
	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}
	public String getImgListType() {
		return imgListType;
	}
	public void setImgListType(String imgListType) {
		this.imgListType = imgListType;
	}
	public String getImgAddStartSign() {
		return imgAddStartSign;
	}
	public void setImgAddStartSign(String imgAddStartSign) {
		this.imgAddStartSign = imgAddStartSign;
	}
	public String getImgAddStep() {
		return imgAddStep;
	}
	public void setImgAddStep(String imgAddStep) {
		this.imgAddStep = imgAddStep;
	}
	
}
