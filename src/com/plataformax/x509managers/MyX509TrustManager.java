/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.x509managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Mathias
 */
public class MyX509TrustManager implements X509TrustManager {
    
    private X509TrustManager pkixTrustManager;
    private final KeyStore ks;
    private final String username;
    private final String password;
    
    private final String SECURITY_FOLDER_PATH = "security/";

    public MyX509TrustManager(String username, String password) throws KeyStoreException, IOException, 
            NoSuchAlgorithmException, CertificateException{

        this.username = username;
        this.password = password;

        this.ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(SECURITY_FOLDER_PATH + username + "Trust"),
                password.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
        tmf.init(ks);

        TrustManager tms[] = tmf.getTrustManagers();

        /*
          * Iterate over the returned trust managers, looking
          * for an instance of X509TrustManager.  If found,
          * use that as the default trust manager.
         */
        for (int i = 0; i < tms.length; i++) {

            if (tms[i] instanceof X509TrustManager) {
                pkixTrustManager = (X509TrustManager) tms[i];
                return;
            }
        }
    }

    /*
      * Delegate to the default trust manager.
     */
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) 
            throws CertificateException{
            pkixTrustManager.checkClientTrusted(chain, authType);
    }

    /*
      * Delegate to the default trust manager.
     */
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
            pkixTrustManager.checkServerTrusted(chain, authType);
    }

    /*
      * Merely pass this through.
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return pkixTrustManager.getAcceptedIssuers();
    }

    public void AddTrustEntrie(String alias, Certificate certificate) throws KeyStoreException,
            IOException, NoSuchAlgorithmException, CertificateException {

        ks.setCertificateEntry(alias, certificate);
        ks.store(new FileOutputStream(SECURITY_FOLDER_PATH + username + "Trust"),
                password.toCharArray());
    }

    public void deleteTrustEntry(String alias) throws KeyStoreException,
            IOException, NoSuchAlgorithmException, CertificateException {
        ks.deleteEntry(alias);
        ks.store(new FileOutputStream(SECURITY_FOLDER_PATH + username + "Trust"),
                password.toCharArray());
    }

    public boolean checkValidity(String alias) throws KeyStoreException {
        Date date = ((X509Certificate) ks.getCertificate(alias)).getNotAfter();
        return date.after(new Date());
    }

    public boolean containsAlias(String alias) throws KeyStoreException {
        return ks.containsAlias(alias);
    }
}
