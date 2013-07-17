<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Mitac provider login page</title>

<script type="text/javascript">
	
		function submit(action){
			var queryParam = location.search;
			alert(queryParam);
			alert(action);
			document.form1.action = action+queryParam;
			alert(document.form1.action);
			document.form1.submit();
		
		}	
	
	</script>
</head>
<body>

你現在在帳號認證頁面
	<div id="login">    
<form action="http://localhost:8443/ApisRestAuthApp/oauth2/l/login" method="post" name="form1" >

<input type="hidden" name="loginType" id="loginType" size="30" maxlength="30" value="PER">
                    <table width="250" align="center"" border="1" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC">
                      
                      <tr> 
                        <td height="30" valign="middle" bordercolor="98B7FD" bgcolor="98B7FD" class="left-area-member-text">使用者驗證</td>
                      </tr>
                      <tr>
                        <td height="44" valign="bottom" bordercolor="#FFFFFF" class="left-area-member-text">帳號： 
                          <input type="text" name="loginId" id="loginId" size="18" maxlength="18"/></td>
                      </tr>
                      <tr> 
                        <td height="44" valign="top" bordercolor="#FFFFFF" class="left-area-member-text">密碼： 
                          <input type="text" name="loginPwd" id="loginPwd" size="10" maxlength="15" /></td>
                      </tr>                     
                      <tr> 
                        <td height="30" align="center" valign="middle" bordercolor="efefef" bgcolor="efefef" class="left-area-member-text"> 
                           <a href="javascript:submit('http://localhost:8443/ApisRestAuthApp/oauth2/l/login');" style="cursor:hand;">
                          <input type="button"  class="comfirm" id="loginById" value="登入" >
                        </a>
                        </td>
                      </tr>
                    </table>
</form>
</div>
</body>
</html>