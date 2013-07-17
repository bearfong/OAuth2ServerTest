package org.oauth.servelet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
 










import oracle.net.aso.r;








import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuer;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.as.request.OAuthTokenRequest;
import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.core.util.MultivaluedMapImpl;












import constants.Constants;

@Path("/o")
public class AuthuzationServelet extends WapBase {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4533897307648495413L;



	 @GET
	 @Path("/add/{a}")
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getHelloWorld(@PathParam("a") double a) {
	   String response = "Hello World :)"+a;
	   return response;
	 }
	  
	 @POST
	 @Path("/auth")
	 public Response postAuth(@QueryParam(OAuth.OAUTH_CLIENT_ID) String clientId, @QueryParam(OAuth.OAUTH_SCOPE) String scope
			 , @QueryParam(OAuth.OAUTH_STATE) String state, @QueryParam(OAuth.OAUTH_REDIRECT_URI) String  redirect_uri, @QueryParam(OAuth.OAUTH_RESPONSE_TYPE) String response_type  
			 , @QueryParam(OAuth.OAUTH_GRANT_TYPE) String grant_type,@Context HttpServletRequest request ,@Context HttpServletResponse response) throws Exception {
		 //redirect uri
		  
		 
		
//	   getForwardNews(request, response);
		 System.out.println(request.getRemoteAddr());
		 init();
		 JSONObject json = new JSONObject();
		 Map<String, Object> map = new HashMap<String, Object>();
	   try {
//		   json = convertRequestBODY2Json(requestBodyStream);
		   json = new JSONObject();
		   System.out.println(clientId);
		   System.out.println(json.toString());
		  
//	    json.put("access_token", this.convertRequestBODY2String(requestBodyStream));
	   } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
//	   ResponseBuilder resBuilder = Response.created(getBaseURI("/o/check",json)).status(201).type(MediaType.APPLICATION_JSON_TYPE).entity(JSONObject.class);
//	   Response res = resBuilder.build();
//	   res.ok();
//	   response.sendRedirect("http://localhost:8080/ApisRestAuthApp/oauth2/o/ckeck");
	   
//	   return ;sendRedirect("http://localhost:8443/ApisRestAuthApp/l/login");
	   
	   if(checkUserLoginOrNot()){
		   
		   return Response.ok().build();
	   }else{
		  //redirect to login page 

		   map.put(OAuth.OAUTH_CLIENT_ID, clientId);
		   Viewable view = new Viewable("/login.jsp", map);
//		   String queryParam = "clientId="+clientId+"&redirect_uri=http://localhost:8082/ApisRestAuthAppClient/webclient/back/callBack&responce_type=code";
//			 
//			 queryParam.replace(Constants.double_slash, Constants.double_slash_map);
//			 queryParam.replace(Constants.single_slash, Constants.single_slash_map);
//		   URI uri = new URI("http://localhost:8443/ApisRestAuthApp/oauth2/l/login?"+queryParam);
//		   response.setStatus(response.SC_MOVED_TEMPORARILY);
//		   response.sendRedirect("http://localhost:8443/ApisRestAuthApp/oauth2/l/login?"+queryParam);
//		   return Response.ok().location(uri).build();
		   return Response.ok().entity(view).build(); 
		   
	   }
	  
	   
	 }
	   
	

//	 @GET
//	 @Path("/{getparameter}")
//	 @Produces(MediaType.APPLICATION_JSON)
//	 public String getByPathParameterAndQueryPath(
//	   @PathParam(value="getparameter") String getparameter,
//	   @QueryParam(value="q") String q) throws JSONException {
//	   System.out.println(getparameter);
//	   System.out.println(q);
//	   JSONObject json = new JSONObject();
//	   json.put("Parameter", getparameter);
//	   json.put("q", q);
//	   return json.toString();
//	 }
	
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
	        
//	        System.out.println(scope);
//	        System.out.println(state);
//	        System.out.println(redirecutUri);
//	        System.out.println(responseType);
//	        System.out.println(clientId);
//	        
	     } catch (Exception e) {
	      // TODO Auto-generated catch block
	    	 e.printStackTrace();
	     }
	    }while(bufferContent > 0 );
		
		return jsonReturn;
	}
	
	 @POST
	 @Path("/check")
	 public String postCheckAccount(InputStream requestBodyStream,@Context HttpServletRequest request ,@Context HttpServletResponse response) throws Exception {
		 //redirect uri
//	   getForwardNews(request, response);
		 System.out.println("check");
		 System.out.println(request.getRemoteAddr());
		 init();
		 JSONObject json = new JSONObject();
	   try {
		   json = convertRequestBODY2Json(requestBodyStream);
		   
		   
//	    json.put("access_token", this.convertRequestBODY2String(requestBodyStream));
	   } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	   
	   
	   return json.toString();
	 }
	   
	 //convert request inputstream to string
//	 private String convertRequestBODY2String(InputStream requestBodyStream){
//	    StringBuffer buffer = new StringBuffer();
//	    int bufferContent = 0;
//	    do
//	    {
//	     try {
//	      bufferContent = requestBodyStream.read();
//	      System.out.println(bufferContent);
//	      if(bufferContent > 0)
//	       buffer.append((char) bufferContent);
//	        
//	     } catch (IOException e) {
//	      // TODO Auto-generated catch block
//	      e.printStackTrace();
//	     }
//	    }while(bufferContent > 0 );
//	    return buffer.toString();
//	 }
	 
	 private static URI getBaseURI(String path, JSONObject json) {
		    return UriBuilder.fromUri("http://localhost:8080/ApisRestAuthApp/oauth2").path(path).buildFromEncoded(json);
		    
		}
}
