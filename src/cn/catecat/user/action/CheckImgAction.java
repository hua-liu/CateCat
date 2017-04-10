package cn.catecat.user.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.catecat.user.userUtil.ValidateCode;



//验证码action类
public class CheckImgAction extends ActionSupport{
	public String execute() throws Exception {
		// 设置响应的类型格式为图片格式
		ServletActionContext.getResponse().setContentType("image/jpeg");
		//禁止图像缓存。
		ServletActionContext.getResponse().setHeader("Pragma", "no-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ValidateCode vCode = new ValidateCode(95,40,4,100);
//				BufferedImage image = vCode.getBuffImg();
		session.setAttribute("code", vCode.getCode());
		vCode.write(ServletActionContext.getResponse().getOutputStream());
		
		return NONE;
	}
}
