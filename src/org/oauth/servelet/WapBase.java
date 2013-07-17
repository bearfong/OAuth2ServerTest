package org.oauth.servelet;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.jersey.api.core.InjectParam;

import constants.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


import dao.WapDAOInterface;

@Component
public class WapBase extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3122384878919797621L;
	
	
	protected WapDAOInterface wapDAO;
	
	public void init(){
		try{
			ApplicationContext context = 
	    	   new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
	 
			wapDAO = (WapDAOInterface)context.getBean("WapDAO");
	    	System.out.println(wapDAO);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public WapDAOInterface getWapDAO() {
		return wapDAO;
	}

	public void setWapDAO(WapDAOInterface wapDAO) {
		this.wapDAO = wapDAO;
	}

	public String getForwardNews(

		@Context final HttpServletRequest request,
	
		@Context final HttpServletResponse response,String url) throws Exception
	
		{
	
		System.out.println("CMSredirecting... ");
		
		response.sendRedirect(url);
	
		return "";

	}
	
	public String makeAuthResponseCode(String clientId){
		clientId = clientId + String.valueOf(new Date());
		String result = null;
		Key signingKey = new SecretKeySpec(Constants.Security_Key.getBytes(), Constants.HMAC_SHA1_ALGORITHM);
        Mac mac;
		try {
			mac = Mac.getInstance(Constants.HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(clientId.getBytes());
        result = Base64.encodeBase64String(rawHmac); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
//		String authCode ="HUEfwehfeiFHEw.TewjGei";
//		String date = String.valueOf(new Date());
//		String b64Code = DatatypeConverter.printBase64Binary(clientId.getBytes());
//		String b64Date = DatatypeConverter.printBase64Binary(date.getBytes());
//		System.out.println(b64Code+"."+b64Date);
		
		
		return result;
	}
	
	public String makeAuthToken(){
		String authToken ="";
		
		return authToken;
	}
	
	public String makeAuthAccessToken(){
		String authAccessToken ="";
		
		return authAccessToken;
	}
	
	public boolean checkUserLoginOrNot() {
		
		return false;
}
	
}
