package com.ssl.examp.demosslapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemosslappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemosslappApplication.class, args);
	}

	@Value("classpath:importedStore.p12")
	private Resource res;

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
			IOException, KeyManagementException, UnrecoverableKeyException {
		File file = null;
		file = res.getFile();
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(new FileInputStream(file), "changeit".toCharArray());
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
				new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
						.loadKeyMaterial(keyStore, "changeit".toCharArray()).build());
		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		return restTemplate;
	}
}
