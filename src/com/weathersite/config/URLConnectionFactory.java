package com.weathersite.config;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.weathersite.exception.WeatherException;
import com.weathersite.util.PropertyFileUtil;

public class URLConnectionFactory implements HttpURLConnectionFactory {

    Proxy proxy;

    SSLContext sslContext;
    
    String userName;
    String password;
    
    public URLConnectionFactory() {
    }

//
//    public URLConnectionFactory(String proxyHost, int proxyPort) {
//        this.proxyHost = proxyHost;
//        this.proxyPort = proxyPort;
//    }

    private boolean initializeProxy() {
    	PropertyFileUtil propFile = new PropertyFileUtil();
        String proxyHost = ""; 
        boolean proxyFoundInitialized = false;
        
        int proxyPort = 0;
        try {
        	proxyHost = propFile.getPropertyValue("host");
        	
        	userName = propFile.getPropertyValue("user");
        	password = propFile.getPropertyValue("password");
        	
			if ( propFile.getPropertyValue("port")!= null 
					&& !propFile.getPropertyValue("port").trim().equals("") 
					&& propFile.getPropertyValue("port").trim().matches("\\d+")) {
				proxyPort = Integer.parseInt(propFile.getPropertyValue("port").trim());
			}
		} catch (WeatherException | IOException e) {
			// TODO proper handling of this error
			System.out.println("Error getting host and proxy from property file");
		}
    	
        if (proxyHost!= null && !proxyHost.trim().equals("") && (proxyPort > 0)) {
        	//if IP to be included update code and property file
        	proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        	proxyFoundInitialized = true;
        }
        
        return proxyFoundInitialized;
    }

    @Override
    public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        
    	HttpURLConnection con;
    	
    	if (initializeProxy()) {
	        //if username, password required uncomment below code
	    		/*
	    		if (userName != null && !userName.trim().equals("") && password != null && !password.trim().equals("")) {
			        Authenticator authenticator = new Authenticator() {
			
			            public PasswordAuthentication getPasswordAuthentication() {
			                return (new PasswordAuthentication("mydomain\\username",
			                        "password".toCharArray()));
			            }
			        };
			        Authenticator.setDefault(authenticator);
	    		} */
    		//////////////////////////////////////////////////
    		
	        con = (HttpURLConnection) url.openConnection(proxy);
    	} else {
    		con = (HttpURLConnection) url.openConnection();
    	}
//		Below code for https calls    	
//        if (con instanceof HttpsURLConnection) {
//        	//this part for https, not tested as open weather map is non-ssl
//            HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection(proxy);
//            httpsCon.setHostnameVerifier(getHostnameVerifier());
//            httpsCon.setSSLSocketFactory(getSslContext().getSocketFactory());
//            return httpsCon;
//        } else {
            return con;
//        }
    }

    public SSLContext getSslContext() {
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new SecureTrustManager()}, new SecureRandom());
        } catch (NoSuchAlgorithmException ex) {
           // Logger.getLogger(URLConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
           // Logger.getLogger(URLConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sslContext;
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        };
    }
}
