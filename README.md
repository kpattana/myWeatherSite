# myWeatherSite

# Introduction
Single page application to select and view weather details of city selected uses 'open weather map' to source details.
When deployed url for app - http://localhost:8080/myWeatherSite/ 
localhost and portnumber in url are default.
If context and query parameters are added to url it will result in  resource not available error.

# Features Implemented

+ getting current weather details for city provided.
+ display todays date.
+ display city selected and weather details fetched for.
+ display weather description.
+ display temperature in celsius & fahrenheit.
+ display sunrise & sunset times in a 12 hour format.
+ display any error description encountered during data fetch.

# Developers note

+ No security as not in scope.
+ if user/password is needed in config.properties there is no encryption its in plain text
+ Single page app works only on url provided, redirects to welcome.jsp for 404 requests.
+ No unit tests included as not mentioned in requirements.
+ No logging implemented.
+ View html/jsp page is basic.
+ Simple exception handling using a single exception call and all errors propogated to it.
+ App key needed by open weather map stored in config.properties file.
+ JSON keys and open weather map url are assumed to not change so are hard coded in source code.
+ All dependencies in pom file.
+ Test environment details provided below, please try to test in this environment if possible.

# Test environment

+ Eclipse Mars IDE, Apache tomcat 8.0, Java SDK 1.8.77 and firefox, IE browser

# Some errors during test
+ connection timeout when connecting to open weather map
	This was due to proxy, firewall setting at work which do not allow external website other than browser ports.
	Have included code to allow for proxy, update config.properties as needed.

+ org.JSON.JSONException not found in class path
	Got this error sometimes while POST calls, could fix it by rebuilding MAVEN project or copying the org.JSON jar to WEB-INF/lib

+ Property file not found
	 During initial setup - resources folder is sometimes missing and is outside java resources, move this or create package under java resources along with config.properties file

# Steps to download and test
1. Download as zip from https://github.com/kpattana/myWeatherSite
2. update config.properties in rousources folder if proxy, port, username, password is needed
3. open IDE and import Maven project(pom.xml), make sure project cleaned and there should be no compile errors.
4. install webserver (tomcat8, Jetty..)
5. add myWeatherSite to webserver
6. start webserver and run 'http://localhost:8080/myWeatherSite/' in browser.


# Developer contact
email - kamal_pattnaik@yahoo.com
call - 07801430445

