/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.client;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author Mathias
 */
public class testTLS {
    
    public static void main(String[] args) {

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

            @Override
            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        });
        
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(MultiPartFeature.class);

        SslConfigurator sslConfig = SslConfigurator.newInstance()
                .trustStoreFile(".\\nbproject\\private\\security\\client7")
                .trustStorePassword("123456")
                .keyStoreFile(".\\nbproject\\private\\security\\client7Trust")
                .keyPassword("123456");

        SSLContext sslContext = sslConfig.createSSLContext();
        Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
        Response response;

        WebTarget target = client.target("https://localhost:9998/api/app/helloword");
        response = target.request().get();

        System.out.println("STATUS: " + response.getStatus());
    }
}
