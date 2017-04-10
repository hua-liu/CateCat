package cn.catecat.global.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import cn.catecat.global.util.Conversion;
/*
 * 	通信Socket服务器   
 */
@ServerEndpoint("/commServer.server")
public class CommServlet {
	private final static Map<String,Session> missionUser = new HashMap<String,Session>();
	@OnOpen
	public void onOpen(Session session){
		missionUser.put(session.getId(), session);
		sendMessage(session, Conversion.stringToJson("state,100,id,"+session.getId()));
		System.out.println("新用户连接到websocket,当前用户个数："+missionUser.size());
	}
	 /**  
     * 收到客户端消息时触发  
     * @param relationId  
     * @param userCode  
     * @param message  
     * @return  
    */  
	@OnMessage
	public String onMessage(Session session,String message){
		return null;
	}
	 /**  
     * 异常时触发  
     * @param relationId  
     * @param userCode  
     * @param session  
    */  
    @OnError  
     public void onError(Throwable throwable,Session session) {  
    	missionUser.remove(session.getId());
		System.out.println("用户"+session.getId()+"断开连接");
    	try {
			session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
  
    /**  
     * 关闭连接时触发  
     * @param relationId  
     * @param userCode  
     * @param session  
    */  
    @OnClose  
     public void onClose(Session session) {  
    	//等待关闭
    	missionUser.remove(session.getId());
		System.out.println("用户"+session.getId()+"断开连接");
    } 
  //发送消息
    public static void sendMessage(Session session,String message){
    	if(session==null)return;
    	final RemoteEndpoint.Basic basic = session.getBasicRemote();
    	try {
			basic.sendText(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static Session getSessionById(String id){
		return missionUser.get(id);
	}
}
