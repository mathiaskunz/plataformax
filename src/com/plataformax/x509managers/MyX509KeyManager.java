/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.x509managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509KeyManager;

/**
 *
 * @author Mathias
 */
public class MyX509KeyManager implements X509KeyManager{
     
    private final String SECURITY_FOLDER_PATH = ".\\nbproject\\private\\security\\";
    
     X509KeyManager pkixKeyManager;
     KeyStore ks;
     
     
     public MyX509KeyManager(String username, String password) throws KeyStoreException, IOException, 
             NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
         // create a "default" JSSE X509TrustManager.
         
         ks = KeyStore.getInstance("JKS");
         
         
         ks.load(new FileInputStream(SECURITY_FOLDER_PATH + username), password.toCharArray());
         

         KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
         
         kmf.init(ks, password.toCharArray());
         
         KeyManager kms [] = kmf.getKeyManagers();

         /*
          * Iterate over the returned trust managers, looking
          * for an instance of X509TrustManager.  If found,
          * use that as the default trust manager.
          */
         for (int i = 0; i < kms.length; i++) {
             
             if (kms[i] instanceof X509KeyManager) {
                 pkixKeyManager = (X509KeyManager) kms[i];
                 return;
             }
         }

         /*
          * Find some other way to initialize, or else the
          * constructor fails.
          */
     }
     

    @Override
    public String[] getClientAliases(String string, Principal[] prncpls) {
        return pkixKeyManager.getClientAliases(string, prncpls);
    }

    @Override
    public String chooseClientAlias(String[] strings, Principal[] prncpls, Socket socket) {
        return pkixKeyManager.chooseClientAlias(strings, prncpls, socket);
    }

    @Override
    public String[] getServerAliases(String string, Principal[] prncpls) {
        return pkixKeyManager.getServerAliases(string, prncpls);
    }

    @Override
    public String chooseServerAlias(String string, Principal[] prncpls, Socket socket) {
        return pkixKeyManager.chooseServerAlias(string, prncpls, socket);
    }

    @Override
    public X509Certificate[] getCertificateChain(String string) {
        return pkixKeyManager.getCertificateChain(string);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        System.out.println("STRING: " + alias);
        return pkixKeyManager.getPrivateKey(alias);
    }
    
    public Certificate getCertificate(String alias) throws KeyStoreException{
        return ks.getCertificate(alias);
    }
    
    public String getCertificateSerialNumber(String alias) throws KeyStoreException{
        X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
        return certificate.getSerialNumber().toString(16);
    }
    
    public boolean checkValidity(String alias) throws KeyStoreException{
        Date date = ((X509Certificate) ks.getCertificate(alias)).getNotAfter();
        return date.after(new Date());
    }
}
