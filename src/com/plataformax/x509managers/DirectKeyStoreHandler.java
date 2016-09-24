/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.x509managers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
//import sun.security.pkcs10.PKCS10;
//import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

/**
 *
 * @author Mathias
 */
public class DirectKeyStoreHandler {
    
    private static final String SECURITY_DIRECTORY_PATH = "security/";
    
    private static KeyStore ks;
    

    /*public static void main(String args[]) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, InvalidKeyException, SignatureException {
        ks = KeyStore.getInstance("JKS");
        ks.load(null, "123456".toCharArray());

        CertAndKeyGen keypair = new CertAndKeyGen("RSA", "SHA1withRSA", null);
        X500Name x500Name = new X500Name("testeGen", "univali", "Univali", "Porto belo", "SC", "BR");
        keypair.generate(2048);

        PrivateKey pk = keypair.getPrivateKey();

        X509Certificate[] chain = new X509Certificate[1];

        chain[0] = keypair.getSelfCertificate(x500Name, new Date(), (long) 364 * 24 * 60 * 60);

        ks.setKeyEntry("testeGen", pk, "123456".toCharArray(), chain);

        // store away the keystore
        java.io.FileOutputStream fos
                = new java.io.FileOutputStream(".\\nbproject\\private\\security\\newKeyStoreName2");
        ks.store(fos, "123456".toCharArray());
        fos.close();
    }

    public static void main(String args[]) {

        try {
            PKCS10 req = new PKCS10(new MyX509KeyManager("newKeyStoreName", "123456").getCertificate("testegen").getPublicKey());

            Signature signature = Signature.getInstance("SHA256withRSA");
            PrivateKey pk = new MyX509KeyManager("newKeyStoreName", "123456").getPrivateKey("1.0.testegen");
            System.out.println(pk);
            signature.initSign(pk);
            req.encodeAndSign(new X500Name("testeGen", "univali", "Univali", "Porto belo", "SC", "BR"), signature);

            FileOutputStream fos = new FileOutputStream(".\\nbproject\\private\\security\\testegen.csr");
            PrintStream ps = new PrintStream(fos);
            req.print(ps);
            fos.close();
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(DirectKeyStoreHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(DirectKeyStoreHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DirectKeyStoreHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(DirectKeyStoreHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DirectKeyStoreHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DirectKeyStoreHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(DirectKeyStoreHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    public void generateKeyPair(String username, String password) {
        String command = " -genkeypair "
                + " -alias " + username + " "
                + " -keyalg RSA "
                + " -dname CN=" + username + " "
                + " -storetype JKS "
                + " -keypass " + password + " "
                + " -keystore " + SECURITY_DIRECTORY_PATH + username + " "
                + " -storepass " + password;
        execute(command);
        genCertReq(username, password);
    }

    public void genCertReq(String username, String password) {
        String command = "-keystore " + SECURITY_DIRECTORY_PATH + username + " "
                + "-certreq "
                + "-alias " + username + " "
                + "-keyalg rsa "
                + "-file " + SECURITY_DIRECTORY_PATH + username + ".csr "
                + "-storepass " + password;
        execute(command);
    }

    public void importCACert(String username, String password) {
        String command = "-keystore " + SECURITY_DIRECTORY_PATH + username + " "
                + "-import "
                + "-file " + SECURITY_DIRECTORY_PATH + "ca-certificate.pem "
                + "-alias thecaroot "
                + "-storepass " + password + " "
                + "-noprompt";
        execute(command);
    }

    public void importCert(String username, String password) {

        String command = "-keystore " + SECURITY_DIRECTORY_PATH + username + " "
                + "-import "
                + "-file " + SECURITY_DIRECTORY_PATH + username + ".cer "
                + "-alias " + username + " "
                + "-storepass " + password + " "
                + "-noprompt";
        execute(command);
    }

    public void createTrustStore(String username, String password) {
        String command = "-keystore " + SECURITY_DIRECTORY_PATH + username + "Trust "
                + "-import "
                + "-file " + SECURITY_DIRECTORY_PATH + "ca-certificate.pem "
                + "-alias thecaroot "
                + "-storepass " + password + " "
                + "-noprompt";

        execute(command);
    }

    public void addTrustEntry(String username, String alias, String password) {
        String command = "-keystore " + SECURITY_DIRECTORY_PATH + username + "Trust "
                + "-import "
                + "-file " + SECURITY_DIRECTORY_PATH + alias + ".cer "
                + "-alias " + alias + " "
                + "-storepass " + password + " "
                + "-noprompt";

        execute(command);
    }

    // Execute the commands
    public void execute(String command) {
        try {
            printCommand(command);
            sun.security.tools.keytool.Main.main(parse(command));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Parse command
    private static String[] parse(String command) {
        String[] options = command.trim().split("\\s+");
        return options;
    }

    // Print the command
    private static void printCommand(String command) {
        System.out.println(command);
    }

}
