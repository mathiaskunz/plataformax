/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.configuration;

import com.plataformax.x509managers.MyX509TrustManager;
import com.plataformax.x509managers.MyX509KeyManager;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.ReportedData.Row;
import org.jivesoftware.smackx.search.UserSearch;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;

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
    private String username;
    private final String ip;

    public Configuration(String ip) throws SmackException, IOException, XMPPException, NoSuchAlgorithmException, KeyStoreException, CertificateException, KeyManagementException, UnrecoverableKeyException {

        this.ctx = SSLContext.getInstance("TLS");
        this.myTM = new MyX509TrustManager("client7", "123456");
        this.myKM = null;
        this.myKMs = null;
        this.myTMs = new TrustManager[]{this.myTM};
        this.ip = ip;
        doConnection();

        //System.out.println("CHEGOU AQUI");
        /*ctx.init(myKMs, myTMs, null);
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setServiceName("note-mathias")
                .setHost(ip)
                .setPort(5222)
                .setSendPresence(true)
                .setCustomSSLContext(ctx)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.required)
                .setCompressionEnabled(false)
                .allowEmptyOrNullUsernames()
                .setConnectTimeout(45000)
                .build();

        this.connection = new XMPPTCPConnection(config);
        this.connection.connect();
        System.out.println("CONECTADO");*/
    }

    public Configuration(String username, String password, String ip) throws NoSuchAlgorithmException,
            KeyManagementException, SmackException, IOException, XMPPException, Exception {
        this.ctx = SSLContext.getInstance("TLS");
        this.myKM = new MyX509KeyManager(username, password);
        this.myTM = new MyX509TrustManager(username, password);
        this.myKMs = new KeyManager[]{this.myKM};
        this.myTMs = new TrustManager[]{this.myTM};
        this.username = username;
        this.ip = ip;
        doConnection();
    }

    private void doConnection() throws KeyManagementException, SmackException, IOException, XMPPException {

        ctx.init(myKMs, myTMs, null);
        System.out.println("CHEGOU AQUI");
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setServiceName("note-mathias")
                .setHost(ip)
                .setPort(5222)
                .setCustomSSLContext(ctx)
                .setSendPresence(true)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.required)
                .setCompressionEnabled(false)
                .allowEmptyOrNullUsernames()
                .setConnectTimeout(60000)
                .build();

        this.connection = new XMPPTCPConnection(config);
        this.connection.connect();
        System.out.println("CONECTADO");

        if (myKMs != null) {
            doLogin();
            System.out.println("LOGADO COM:" + this.connection.getUser());
        }
    }

    public void doLogin() throws XMPPException, SmackException, IOException {
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

    public PrivateKey getPrivateKey() throws Exception {
        return this.myKM.getPrivateKey("1.0." + this.username);
    }

    public void registerUser(String username, String password) throws SmackException.NoResponseException,
            XMPPException.XMPPErrorException, SmackException.NotConnectedException, NoSuchAlgorithmException {
        AccountManager am = AccountManager.getInstance(this.connection);
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        am.createAccount(username, new BigInteger(64, sr).toString());
    }

    public List<String> searchUser(String description) {
        UserSearchManager manager = new UserSearchManager(connection);

        Form searchForm;
        List<String> clientsList = null;
        try {
            System.out.println("PROCURANDO");
            String searchFormString = "search.note-mathias";
            searchForm = manager.getSearchForm(searchFormString);

            Form answerForm = searchForm.createAnswerForm();

            UserSearch userSearch = new UserSearch();
            answerForm.setAnswer("Username", true);
            answerForm.setAnswer("Name", true);
            answerForm.setAnswer("search", description);

            ReportedData results = userSearch.sendSearchForm(connection, answerForm, searchFormString);
            clientsList = new ArrayList<String>();
            
            if (results != null) {
                List<Row> rows = results.getRows();
                System.out.println(rows.isEmpty());
                for (Row row : rows) {
                    List<String> valueUser = row.getValues("Usuário");
                    List<String> valueName = row.getValues("Nome");
                    
                    for (String user : valueUser) {
                        for (String name : valueName) {
                            clientsList.add(user + " | " + name);
                        }
                    }
                }
            } else {
                System.out.println("No result found");
            }

        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException 
                | SmackException.NotConnectedException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientsList;
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

    public String getUser() {
        return connection.getUser()
                .substring(0, connection.getUser().indexOf("@"));
    }

    public ChatManager getChatManager() {
        return ChatManager.getInstanceFor(connection);
    }

}
