<%@ page import="org.onap.portalsdk.core.util.SystemProperties"%>
<!DOCTYPE html>
<%
	// Name is defined by app; do not throw if missing
	final String appDisplayName = SystemProperties.containsProperty(SystemProperties.APP_DISPLAY_NAME)
			? SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME)
			: SystemProperties.APP_DISPLAY_NAME;
    String loginPage = "login_external.htm";
	String loginUrl = (request.isSecure() ? "https://" : "http://") + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/" + loginPage;
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign up / Register</title>
<style>
    html {
      font-family: Verdana, Arial, Helvetica, sans-serif;
    }
    body {
      padding-top: 15px;
    }
    .logo {
      position: fixed;
      left: 15px;
      top: 15px;
      z-index: -1;
    }
    .loginError {
      font-size: 18px;
      color: red;
      text-align: center;
    }
    .login {
      font-size: 16px;
      display: block;
      margin-left: auto;
      margin-right: auto;
      text-align: center;
      width: 100%;
    }
    .login input[type=submit] {
      font-size: 16px;
    }
    .terms {
      font-size: 10px;
      text-align: center;
      margin-left: auto;
      margin-right: auto;
    }
    .terms a {
      font-size: 10px;
      text-align: center;
      margin-left: auto;
      margin-right: auto;
    }
    .formIn {
        width: 200px;
        height:25px;
        border-radius: 4px;
        font-size:18px;
        padding-left:5px;
        background-color: #f1ecec;
    }
    #regLink {
    background: #0081a4;
    border-radius: 3px;
    padding: 4px;
    font-size: large;
    font-weight: bold;
    text-decoration: none;
    color: black;
    }
    #formDiv {
    background-color: #0081a4b0;
    width: 300px;
    margin-left: auto;
    margin-right: auto;
    padding-bottom: 10px;
    }
    </style>

</head>
<body>
    <div class="login">
      <img src="app/ccsdk/images/onap_logo_2257x496.png" style="height: 150px;" />
      <h2>
        <%=appDisplayName%>
      </h2>
      <div id="formDiv">
      <form action="signup" method="POST"> 
        <h3>Sign Up</h3>
        <input id="first" class="formIn" name="first" type="text" maxlength="30" placeholder="First Name">
        <br/>
        <br/>
        <input id="last" class="formIn" name="last" type="text" maxlength="30" placeholder="Last Name">
        <br/>
        <br/>
        <input id="loginId" class="formIn" name="loginId" type="text" maxlength="30" placeholder="User ID">
        <br/>
        <br/>
        <input id="password" class="formIn" name="password" type="password" maxlength="30" placeholder="Password">
        <br />
        <br />
        <input id="email" class="formIn" name="email" type="text" maxlength="30" placeholder="Email">
        <br />
        <br />
        <input id="loginBtn" type="submit" alt="Login" value="SIGN UP" style="width: 200px; font-weight: bolder;">
      </form>  
      <br>
              <p>Already registered? <a id="regLink" href="<%=loginUrl%>"> Login</a></p> 
       </div>  
    </div>
    <br />
    <br />
    <div class="loginError">${model.error}</div>
    <br />
    <br />
    <br />
    <div id="footer">
      <div class="terms">
           <p class="copyright-text">
            &copy; 2020 ONAP. The Linux Foundation.         
           </p> 
      </div>
    </div>
</body>
</html>