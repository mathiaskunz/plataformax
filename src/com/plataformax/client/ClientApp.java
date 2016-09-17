/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.client;

import com.plataformax.swingui.TelaLogin;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ExceptionCallback;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.iqrequest.IQRequestHandler;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.PlainStreamElement;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import static org.jivesoftware.smackx.filetransfer.FileTransfer.Error.connection;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.sasl.AuthenticationException;
import rocks.xmpp.core.session.TcpConnectionConfiguration;
import rocks.xmpp.core.session.XmppClient;
import org.jivesoftware.smack.packet.Message;
import rocks.xmpp.core.stanza.model.Presence;
import rocks.xmpp.im.roster.RosterManager;



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
        TelaLogin tl = new TelaLogin();
        tl.setVisible(true);
        tl.revalidate();
        
        /*TrustManager[] myTMs = new TrustManager[] { new MyX509TrustManager() };
        KeyManager[] myKMs = new KeyManager[] { new MyX509KeyManager() };
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(myKMs, myTMs, null);*/
        
        
       /* TcpConnectionConfiguration tcpConfiguration = TcpConnectionConfiguration.builder()
        .hostname("localhost")
         
        .sslContext(ctx)
        .secure(true)
        .port(5222)
        .build();
        
        XmppClient xmppClient = XmppClient.create("note-mathias", tcpConfiguration);
        
        // Listen for presence changes
        xmppClient.addInboundPresenceListener(e -> {
            Presence presence = e.getPresence();
            // Handle inbound presence.
        });
        // Listen for messages
        xmppClient.addInboundMessageListener(e -> {
            Message message = e.getMessage();
            // Handle inbound message.
        });
        // Listen for roster pushes
        xmppClient.getManager(RosterManager.class).addRosterListener(e -> {
            // Roster has changed
        });
        
        try {
            xmppClient.connect();
            
        } catch (XmppException e) {
            System.out.println(e.getMessage());
        }
        
        try {
            xmppClient.login("admin", "admin");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        } catch (XmppException e) {
            System.out.println(e.getMessage());
        }*/
        
        
        
        // Create a connection to the jabber.org server.
        //AbstractXMPPConnection conn1;
        //conn1 = new XMPPTCPConnection("username", "password") ;
        //conn1.connect();
        
        // Create a connection to the jabber.org server on a specific port.        

        /* XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
        .setServiceName("note-mathias")
        .setHost("localhost")
        .setPort(5222)
        .setCustomSSLContext(ctx)
                 .setDebuggerEnabled(true)
        .setSendPresence(true)
        .setSecurityMode(ConnectionConfiguration.SecurityMode.required)
                 .setCompressionEnabled(false)
        .build();
        
        AbstractXMPPConnection conn2 = new XMPPTCPConnection(config);
        
        try {
            conn2.connect();
            System.out.println("CONECTADO");
        } catch (SmackException | IOException | XMPPException ex) {
            System.out.println(ex.getMessage());
        }
        conn2.login("client4", "123456");
        
        TelaMensagem tp = new TelaMensagem();
        tp.setVisible(true);
        ChatManager chatmanager = ChatManager.getInstanceFor(conn2);
        
        Chat newChat = chatmanager.createChat("client4@note-mathias", new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {
                        System.out.println("Received message: " + message);
                        tp.setMensagensText(message.toString());
            }
        });
        try{
            newChat.sendMessage("Howdy!");
        } catch (Exception e ){
            System.out.println(e.getMessage());
        }
        
        ChatManager chatManager = ChatManager.getInstanceFor(conn2);
        chatManager.addChatListener(
	new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
		if (!createdLocally){
                    chat.addMessageListener(new MyNewMessageListener());
                }
            }
	});*/
    }

    /*private static class MyNewMessageListener implements ChatMessageListener {

        public MyNewMessageListener() {
        }

        @Override
        public void processMessage(Chat chat, Message message) {
            System.out.println("Received message: " + message);
        }
    }*/
    
}
