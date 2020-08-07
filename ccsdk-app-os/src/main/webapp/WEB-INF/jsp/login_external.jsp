<%--
  ================================================================================
  ECOMP Portal SDK
  ================================================================================
  Copyright (C) 2017 AT&T Intellectual Property
  ================================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ================================================================================
--%>
<%@ page import="org.onap.portalsdk.core.util.SystemProperties"%>
<!DOCTYPE html>
<%
	// Name is defined by app; do not throw if missing
	final String appDisplayName = SystemProperties.containsProperty(SystemProperties.APP_DISPLAY_NAME)
			? SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME)
			: SystemProperties.APP_DISPLAY_NAME;
    String signUpPage = "signup.htm";
	String signupUrl = (request.isSecure() ? "https://" : "http://") + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/" + signUpPage;
%>

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Login</title>
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
            <form action="login_external" method="POST"> 
                <h3> Login </h3>
				<input class="formIn" id="loginId" name="loginId" type="text" maxlength="30" placeholder="User ID">
				<br>
				<br>
				<input class="formIn" id="password" name="password" type="password" maxlength="30" placeholder="Password">
				<br>
				<br>
				<input id="loginBtn" type="submit" alt="Login" value="LOG IN" style="width: 200px; font-weight: bolder;">
			</form> 
            <br>   
            <p>Not registered yet? <a id="regLink" href="<%=signupUrl%>">Sign up</a></p>
            </div>
		</div>
		<br />
		<br />
		<div class="loginError">${model.error}</div>
		<br />
		<br />
		<br />
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
