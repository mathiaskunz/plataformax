/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.configuration;

import com.plataformax.x509managers.MyX509TrustManager;
import com.plataformax.x509managers.MyX509KeyManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

/**
 *
 * @author Mathias
 */
public final class Configuration {

    private static AbstractXMPPConnection connection;
    private final TrustManager[] myTMs;
    private final KeyManager[] myKMs;
    private final SSLContext ctx;
    private final MyX509KeyManager myKM;
    private final MyX509TrustManager myTM;
    private final String username;

    public Configuration(String username, String password) throws NoSuchAlgorithmException, 
            KeyManagementException, SmackException, IOException, XMPPException, Exception {
        this.ctx = SSLContext.getInstance("TLS");
        this.myKM = new MyX509KeyManager(username, password);
        this.myTM = new MyX509TrustManager(username, password);
        this.myKMs = new KeyManager[]{this.myKM};
        this.myTMs = new TrustManager[]{this.myTM};
        this.username = username;
        doConnection();
    }

    private void doConnection() throws KeyManagementException, SmackException, IOException, XMPPException {

        ctx.init(myKMs, myTMs, null);
        System.out.println("CHEGOU AQUI");
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setServiceName("note-mathias")
                .setHost("192.168.0.100")
                .setPort(5222)
                .setCustomSSLContext(ctx)
                .setSendPresence(true)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.required)
                .setCompressionEnabled(false)
                .allowEmptyOrNullUsernames()
                .setConnectTimeout(45000)
                .build();

        this.connection = new XMPPTCPConnection(config);
        this.connection.connect();
        System.out.println("CONECTADO");
        doLogin();
        System.out.println("LOGADO COM:" + this.connection.getUser());

    }

    private void doLogin() throws XMPPException, SmackException, IOException {
        this.connection.login();
    }

    public X509Certificate getContactCertificate(String contact) throws Exception {
        X509Certificate[] certs = myTM.getAcceptedIssuers();

        for (X509Certificate cert : certs) {
            String cn = cert.getSubjectX500Principal().getName()
                    .substring(3, cert.getSubjectX500Principal().getName().indexOf(","));
            if (cn.equals(contact)) {
                return cert;
            }
        }

        return null;
    }

    public Certificate getOwnCertificate() throws KeyStoreException {
        return this.myKM.getCertificate(this.username);
    }

    public void addTrustEntry(String alias, Certificate certificate) throws KeyStoreException,
            IOException, NoSuchAlgorithmException, CertificateException {
        this.myTM.AddTrustEntrie(alias, certificate);
    }

    public void deleteTrustEntry(String alias) throws KeyStoreException, IOException,
            NoSuchAlgorithmException, CertificateException {
        this.myTM.deleteTrustEntry(alias);
    }

   /* public List getContacts() {

        List<String> listaContatos = new ArrayList<>();

        X509Certificate[] certs = this.myTM.getAcceptedIssuers();

        for (X509Certificate cert : certs) {
            String dn = cert.getSubjectX500Principal().getName();
            String cn;

            if (dn.contains(",")) {
                cn = dn.substring(3, dn.indexOf(","));
            } else {
                cn = dn.substring(3);
            }

            listaContatos.add(cn);
        }

        return listaContatos;
    }*/

    public PrivateKey getPrivateKey() throws Exception {
        return this.myKM.getPrivateKey("1.0." + this.username);
    }

    public void registerUser(String username, String password) throws SmackException.NoResponseException,
            XMPPException.XMPPErrorException, SmackException.NotConnectedException {
        AccountManager am = AccountManager.getInstance(this.connection);
        am.createAccount(username, password);
    }

    public boolean checkOwnCertValidity() throws KeyStoreException {
        return this.myKM.checkValidity(this.username);
    }

    public boolean checkContactCertValidity(String contact) throws KeyStoreException {
        return this.myTM.checkValidity(contact);
    }

    public boolean containsAlias(String alias) throws KeyStoreException {
        return this.myTM.containsAlias(alias);
    }

    public AbstractXMPPConnection getConnection() {
        return Configuration.connection;
    }
    
    public String getUser(){
        return connection.getUser()
                .substring(0, connection.getUser().indexOf("@"));
    }
    
    public ChatManager getChatManager(){
        return ChatManager.getInstanceFor(connection);
    }

}
