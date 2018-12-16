package com.ssl.examp.demosslapp.conf;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class ConnectorConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>
{

	@Value("classpath:certificate.p12")
    private Resource res;
	
	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		 
		   
		  
	     factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {

	 		@Override
	 		public void customize(Connector connector) {
	 				
	 				 connector.setPort(9191);
	                 connector.setSecure(true);
	                 connector.setScheme("https");
	                 connector.setAttribute("keyAlias", "localhost");
	                 connector.setAttribute("keystorePass", "changeit");
	                 

	                 File file= null;
					try {
						//file = new ClassPathResource("certificate.p12").getFile();
						file =res.getFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               
	                 String absoluteKeystoreFile = file.getAbsolutePath();
	                 System.out.println(absoluteKeystoreFile);
	                 connector.setAttribute("keystoreFile", absoluteKeystoreFile);
	                 connector.setAttribute("clientAuth", "false");
	                 connector.setAttribute("sslProtocol", "TLS");
	                 connector.setAttribute("SSLEnabled", true);

	                 Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();

	                 proto.setSSLEnabled(true);
	                 proto.setKeystoreFile(absoluteKeystoreFile);
	                 proto.setKeystorePass("changeit");
	                 proto.setKeystoreType("PKCS12");
	                 proto.setKeyAlias("localhost");
	 		}
	 		});
	}
	
}