package org.oauth.servelet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuer;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.as.request.OAuthRequest;
import org.apache.amber.oauth2.as.request.OAuthTokenRequest;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.validators.OAuthValidator;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.view.Viewable;

import constants.Constants;

@Path("/l")
public class CustomerCheck extends WapBase{
	 private String loginId;
	 private String loginPwd;
	 
	 /**
	 * 
	 */
	private static final long serialVersionUID = -4304873808241099238L;

	 public String getLoginId() {
		return loginId;
	}



	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}



	public String getLoginPwd() {
		return loginPwd;
	}



	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}



	 @POST
	 @Path("/login")
	 public Response postAuth(@QueryParam(OAuth.OAUTH_CLIENT_ID) String clientId, @QueryParam(OAuth.OAUTH_SCOPE) String scope
			 , @QueryParam(OAuth.OAUTH_STATE) String state, @QueryParam(OAuth.OAUTH_REDIRECT_URI) String  redirect_uri, @QueryParam(OAuth.OAUTH_RESPONSE_TYPE) String response_type 
			 , @QueryParam(OAuth.OAUTH_GRANT_TYPE) String grant_type, @Context HttpServletRequest request ,@Context HttpServletResponse response) throws Exception {
		 //redirect uri
//	   getForwardNews(request, response);
		 
//		 System.out.println(loginId);
//		 System.out.println(loginPwd);
		 System.out.println();
		 Map<String, Object> map = new HashMap<String, Object>();
		 JSONObject json = new JSONObject();
	   try {
//		   json = convertRequestBODY2Json(requestBodyStream);
		   System.out.println("check:"+loginId);
		   if(checkUserLoginOrNot()){
			  //login OK
			  //struture pass code 
//			   json = makeAuthCodeByJson();
			   
		   }else{
			  //redirect to ori page 
			   	
//			   	request.getParameterMap().put(OAuth.OAUTH_CLIENT_ID, clientId); 
//			   	request.getParameterMap().put(OAuth.OAUTH_SCOPE, scope); 
//			   	request.getParameterMap().put(OAuth.OAUTH_STATE, state); 
//			   	request.getParameterMap().put(OAuth.OAUTH_REDIRECT_URI, redirect_uri); 
//			   	request.getParameterMap().put(OAuth.OAUTH_RESPONSE_TYPE, response_type); 
			   	//oauthrequest物件取值是由   servlet request中的param中取得，所以必須在發送時寫入。
				OAuthTokenRequest oauthRequest = null;
				 
				OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				
				
				oauthRequest = new OAuthTokenRequest(request);
				
				 
	            String authzCode = oauthRequest.getCode();
				 
			   	URI uri = new URI(redirect_uri+"?authzCode="+authzCode+"&state="+state+"&grant_type="+grant_type);
				return Response.temporaryRedirect(uri).build();
			   
		   }
//		   HttpSession session = request.getSession();
//		   session.getId();
//		   
//		   session.setAttribute("clientId", clientId);
//	    json.put("access_token", this.convertRequestBODY2String(requestBodyStream));
	   } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
//	   ResponseBuilder resBuilder = Response.created(getBaseURI("/o/check",json)).status(201).type(MediaType.APPLICATION_JSON_TYPE).entity(JSONObject.class);
//	   Response res = resBuilder.build();
//	   res.ok();
//	   response.sendRedirect("http://localhost:8080/ApisRestAuthApp/oauth2/o/ckeck");
	   
	   return Response.ok().build();
	 }
	 
	

//	private JSONObject makeAuthCodeByJson() {
//		JSONObject json = new JSONObject();
//		
//		json.put(key, value);
//		json.put(key, value);
//		json.put(key, value);
//		json.put(key, value);
//		
//		return json;
//	}



	private JSONObject convertRequestBODY2Json(InputStream requestBodyStream) {
			JSONObject jsonGet = new JSONObject();
			JSONObject jsonReturn = new JSONObject();
			StringBuilder sbLines   = new StringBuilder("");
		    int bufferContent = 0;
		    do
		    {
		     try {
		    	 BufferedReader reader = new BufferedReader(new InputStreamReader(requestBodyStream,"utf-8"));
				 String strLine = "";
				 while((strLine=reader.readLine())!=null){
				  sbLines.append(strLine);
				 }
				
				jsonGet = new JSONObject(sbLines.toString());
		        String scope = (String) jsonGet.get(OAuth.OAUTH_SCOPE);
		        String state = (String) jsonGet.get(OAuth.OAUTH_STATE);
		        String redirecutUri = (String) jsonGet.get(OAuth.OAUTH_REDIRECT_URI);
		        String responseType = (String) jsonGet.get(OAuth.OAUTH_RESPONSE_TYPE);
		        String clientId = (String) jsonGet.get(OAuth.OAUTH_CLIENT_ID);
		        if(StringUtils.equals(responseType, "code")){
		        	
		        	
		        	String authResponseCode = makeAuthResponseCode(clientId);
		        	jsonReturn.put(Constants.AuthResponse_Code, authResponseCode);
		        	jsonReturn.put(Constants.AuthResponse_State, state);
		        	String authCode = wapDAO.addAuthCode(authResponseCode, scope);
		        }
		        
//		        System.out.println(scope);
//		        System.out.println(state);
//		        System.out.println(redirecutUri);
//		        System.out.println(responseType);
//		        System.out.println(clientId);
//		        
		     } catch (Exception e) {
		      // TODO Auto-generated catch block
		    	 e.printStackTrace();
		     }
		    }while(bufferContent > 0 );
			
			return jsonReturn;
		}

}
