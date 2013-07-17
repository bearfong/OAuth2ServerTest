package constants;

public class Constants {
	//key
	public static final String Security_Key = "HUEfwehfeiFHEw.TewjGei";
	//Hmac
	public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	//uri
	public static final String token_uri="localhost:8080/ApisRestAuthApp/oauth2/auth";
	public static final String access_uri="localhost:8080/ApisRestAuthApp/oauth2/token";
	
	//auth requert
	//使用Oauth物件取代
//	public static final String AuthRequest_Scope="scope";
//	public static final String AuthRequest_State="state";
//	public static final String AuthRequest_Redirecut_Uri="redirect_uri";
//	public static final String AuthRequest_Response_Type="response_type";
//	public static final String AuthRequest_Client_Id="client_id";
	
	//auth requert
	public static final String AuthResponse_Code="code";
	public static final String AuthResponse_State="state";
	
	//sign
	public static final String double_slash = "://";
	public static final String single_slash = "/"; 
	
	//changeSign
	public static final String double_slash_map = "%3A";
	public static final String single_slash_map = "%2F";
	
	
}
