/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.client;

import com.plataformax.swingui.LoginFrame;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 *
 * @author Mathias
 */
public class ClientApp {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyStoreException
     * @throws java.io.IOException
     * @throws java.security.cert.CertificateException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, Exception {
        LoginFrame tl = new LoginFrame();
        tl.setVisible(true);
        tl.revalidate();   
    }
}
