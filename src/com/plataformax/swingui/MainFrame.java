/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.swingui;

import com.plataformax.configuration.Configuration;
import com.plataformax.x509managers.DirectKeyStoreHandler;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smackx.jiveproperties.JivePropertiesManager;

/**
 *
 * @author Mathias
 */
public final class MainFrame extends javax.swing.JFrame {

    private Configuration config;
    private List<ConversationFrame> listTm;
    private Roster roster;
    private String username;
    private String password;

    /**
     * Creates new form TelaPrincipal
     *
     * @param username
     * @param password
     * @param config
     * @throws java.lang.Exception
     */
    public MainFrame(String username, String password, Configuration config) {
        JivePropertiesManager.setJavaObjectEnabled(true);
        setTitle(username);
        this.config = config;
        this.username = username;
        this.password = password;
        initComponents();
        listenStanza();
        listTm = new ArrayList<>();
        receiveMessage();
    }

    private MainFrame() {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        openButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaContato = new javax.swing.JList<>();
        campoAddUser = new javax.swing.JTextField();
        addUserButton = new javax.swing.JButton();
        removeContactButton = new javax.swing.JButton();

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        openButton.setText("Abrir");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        try{
            doRoster();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        jScrollPane3.setViewportView(listaContato);

        addUserButton.setText("Adicionar contato");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        removeContactButton.setText("Remover contato");
        removeContactButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeContactButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(openButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoAddUser)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(removeContactButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addUserButton)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(campoAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addUserButton)
                    .addComponent(removeContactButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(openButton))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        String contato = listaContato.getSelectedValue();

        if (contato != null) {
            System.out.println("CONTATO: " + contato);

            //VERIFICA SE TEM UMA CONVERSA EM ANDAMENTO COM O CONTATO
            //SE NÃO, CRIAR UMA NOVA TELA DE MENSAGEM E ADICIONA NA LISTA DE 
            //MENSAGENS EM ABERTO
            if (!containContact(contato)) {
                ConversationFrame tm = new ConversationFrame(config, contato + "@note-mathias", true);
                listTm.add(tm);
            }

            listTm.get(indexOfContact(contato)).setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Escolha um contato");
        }

    }//GEN-LAST:event_openButtonActionPerformed


    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        List<String> clientList = config.searchUser(campoAddUser.getText());
        
        JLabel clientListLabel = new JLabel();
        clientListLabel.setText("Usuário | Nome");
        clientListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel userSelectionPanel = new JPanel();
        userSelectionPanel.setLayout(new BoxLayout(userSelectionPanel, BoxLayout.Y_AXIS));
        
        JList clientJList = new JList();
        DefaultListModel lista = new DefaultListModel();
        
        for (String client : clientList) {
            lista.addElement(client);
        }
        
        clientJList.setModel(lista);
        clientJList.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userSelectionPanel.add(clientListLabel);
        userSelectionPanel.add(clientJList);
        
        JOptionPane.showMessageDialog(null, userSelectionPanel, "Clientes Encontrados", 
                JOptionPane.PLAIN_MESSAGE);
        
        System.out.println("SELECTED USER: " + clientJList.getSelectedValue());
        String selectClient = clientJList.getSelectedValue().toString();
                
        System.out.println(selectClient.substring(0, selectClient.indexOf(" |"))+ "@note-mathias");
        addFriend(selectClient.substring(0, selectClient.indexOf("|")-1)+ "@note-mathias");
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void removeContactButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeContactButtonActionPerformed
        if (listaContato.getSelectedValue() != null) {
            unsubscribe(listaContato.getSelectedValue() + "@note-mathias");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Escolha um contato");
        }
    }//GEN-LAST:event_removeContactButtonActionPerformed

    private void listenStanza() {

        StanzaFilter sf = new AndFilter(new StanzaTypeFilter(Presence.class));
        StanzaListener sl = new StanzaListener() {

            @Override
            public void processPacket(Stanza stanza) throws NotConnectedException {
                String alias = stanza.getFrom().substring(0, stanza.getFrom().indexOf("@"));
                switch (((Presence) stanza).getType()) {
                    case subscribe:
                        if (roster.contains(stanza.getFrom())) {
                            Presence newp = new Presence(Presence.Type.subscribed);
                            newp.setMode(Presence.Mode.available);
                            newp.setPriority(24);
                            newp.setTo(stanza.getFrom());
                            config.getConnection().sendStanza(newp);
                        } else {
                            int aceita = JOptionPane.showConfirmDialog(rootPane, stanza.getFrom() + "Quer lhe adicionar, "
                                    + "aceitar?");
                            if (aceita == JOptionPane.YES_OPTION) {

                                //NÃO ESQUECER DE FAZER O IF PARA VER SE O CERTIFICADO JÁ
                                //ESTÁ INSTALADO NA TRUSTSTORE!!
                                Certificate certificate = (Certificate) JivePropertiesManager
                                        .getProperty(stanza, "certificate");

                                writeRemoteClientCert(certificate, alias);
                                new DirectKeyStoreHandler().addTrustEntry(username, alias, password);

                                //INFORMA QUE ACEITA A INSCRICAO/PEDIDO DE AMIZADE
                                Presence newp = new Presence(Presence.Type.subscribed);
                                newp.setMode(Presence.Mode.available);
                                newp.setPriority(24);
                                newp.setTo(stanza.getFrom());
                                config.getConnection().sendStanza(newp);

                                //ENVIA PEDIDO DE AMIZADE, PARA PODER RECEBER OS STATUS TAMBÉM
                                Presence subscription = new Presence(Presence.Type.subscribe);
                                subscription.setTo(stanza.getFrom());

                                try {
                                    certificate = config.getOwnCertificate();
                                } catch (KeyStoreException ex) {
                                    JOptionPane.showMessageDialog(rootPane, "Não foi possível recuperar seu "
                                            + "certificado da sua KeyStore. Seu pedido de amizade não será aceito"
                                            + "pelo cliente: " + stanza.getFrom() + ". Você pode verificar se há "
                                            + "algum problema com sua KeyStore e tentar enviar um pedido "
                                            + "de amizade novamente.");
                                }

                                JivePropertiesManager.addProperty(subscription, "certificate", certificate);

                                config.getConnection().sendStanza(subscription);

                                listContacts();

                            } else if (aceita == JOptionPane.NO_OPTION) {
                                Presence newp = new Presence(Presence.Type.unsubscribe);
                                newp.setTo(stanza.getFrom());
                                config.getConnection().sendStanza(newp);
                            }
                        }
                        break;
                    case unsubscribe:
                        System.out.println("PEDIDO PARA UNSUBSCRIBE");
                        Presence newp = new Presence(Presence.Type.unsubscribed);
                        newp.setTo(stanza.getFrom());
                        config.getConnection().sendStanza(newp);
                        break;
                    case unsubscribed:
                        System.out.println("PEDIDO DE UNSUBSCRIBE ACEITO");

                        try {
                            config.deleteTrustEntry(alias);
                        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
                            JOptionPane.showMessageDialog(rootPane, "Não foi possível remover o certificado"
                                    + "digital do cliente remoto da sua TrustStore. Isso não implica em "
                                    + "nenhuma brecha de segurança para você, o certificado somente irá "
                                    + "ocupar espaço na sua TrustStore. É possível que seu arquivo de "
                                    + "TrustStore tenha sido removido e não foi recriado. Verifique o mesmo.");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(rootPane, "Seu arquivo de TrustStore foi removido"
                                    + "e não foi recriado. Você pode tentar refazê-lo.");
                        }
                        break;
                    case subscribed:
                        try {
                            roster.reload();
                        } catch (SmackException.NotLoggedInException ex) {
                            JOptionPane.showMessageDialog(rootPane, "Erro ao recarregar a sua lista de "
                                    + "contatos. É possível que você não esteja mais logado no servidor."
                                    + " Tente fechar e abrir o aplicativo. E entre novamente com seu "
                                    + "usuário.");
                        }
                        listContacts();
                        break;
                    case available:
                        listContacts();
                        break;
                    default:
                        break;
                }
                listContacts();
            }
        };
        config.getConnection()
                .addAsyncStanzaListener(sl, sf);
    }

    private void addFriend(String address) {

        //CRIA UM PEDIDO DE SUBSCRIBE
        //RECUPERA O PRÓPRIO CERTIFICADO E O COLOCA EM UMA PROPRIEDADE PARA O ENVIAR
        //AO CLIENTE AO QUAL SE DESEJA ADICIONAR.
        try {
            Presence response = new Presence(Presence.Type.subscribe);
            response.setTo(address);

            Certificate certificate = config.getOwnCertificate();

            JivePropertiesManager.addProperty(response, "certificate", certificate);

            config.getConnection().sendStanza(response);

            /*if (!roster.contains(address)) {
                roster.createEntry(address, address.substring(0, address.indexOf("@")), null);
            }*/
        } catch (NotConnectedException /*| SmackException.NotLoggedInException | SmackException.NoResponseException | XMPPException.XMPPErrorException*/ ex) {
            JOptionPane.showMessageDialog(rootPane, "Não foi possível enviar seu pedido de amizade."
                    + "As possíveis causas para esse problema são:"
                    + "\n - Não há conexão com o servidor de comunicação. "
                    + "\n - Você não está logado no servidor (improvável se fosse já está onde está)."
                    + "\n - Não houve resposta do servidor, isso pode significar que há uma lentidão na comunicação."
                    + "\n - Outro problema ainda não tratado (improvável)");
            ex.printStackTrace();
        } catch (KeyStoreException ex) {
            JOptionPane.showMessageDialog(rootPane, "Não foi possível recuperar seu certificado da sua "
                    + "KeyStore. Seu pedido de amizade será enviado, mas será recusado. Favor verificar "
                    + "a integridade da sua KeyStore.");
            ex.printStackTrace();
        }

    }

    private void unsubscribe(String address) {
        try {
            Presence response = new Presence(Presence.Type.unsubscribe);
            response.setTo(address);

            config.getConnection().sendStanza(response);
        } catch (NotConnectedException ex) {
            JOptionPane.showMessageDialog(rootPane, "Não foi possível enviar seu pedido para se "
                    + "desvincular do cliente: " + address + ". Não há uma conexão aberta com o "
                    + "servidor de comunicação.");
        }
    }

    private void doRoster() throws Exception {
        roster = Roster.getInstanceFor(config.getConnection());
        roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
        roster.addRosterListener(new RosterListener() {

            @Override
            public void entriesAdded(Collection<String> addresses) {
                listContacts();
            }

            @Override
            public void entriesDeleted(Collection<String> addresses) {
                listContacts();
            }

            @Override
            public void entriesUpdated(Collection<String> addresses) {
                listContacts();
            }

            @Override
            public void presenceChanged(Presence presence) {
                String contato = presence.getFrom()
                        .substring(0, presence.getFrom().indexOf("@"));
                if (containContact(contato) && !presence.isAvailable()) {
                    listTm.get(indexOfContact(contato)).setFirstMessage();
                }

                System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
                listContacts();
            }
        });

        listContacts();
    }

    public void listContacts() {
        Set<RosterEntry> set = roster.getEntries();
        DefaultListModel lista = new DefaultListModel();
        Map<String, Integer> presenca = new HashMap<String, Integer>();

        for (RosterEntry rosterEntry : set) {
            String contato = rosterEntry.getName();
            if (contato != null) {
                System.out.println("CONTATO: " + contato);
                lista.addElement(contato);

                if (roster.getPresence(contato + "@note-mathias").isAvailable()) {
                    presenca.put(contato, 1);
                } else {
                    presenca.put(contato, 0);
                }
            }
        }

        listaContato.setModel(lista);
        listaContato.setCellRenderer(new MyListCellRenderer((HashMap<String, Integer>) presenca));
        listaContato.revalidate();
    }

    private void receiveMessage() {
        ChatManager chatManager = ChatManager.getInstanceFor(config.getConnection());
        chatManager.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                if (!createdLocally) {
                    chat.addMessageListener(new ChatMessageListener() {
                        @Override
                        public void processMessage(Chat chat, Message message) {
                            String contato = message.getFrom()
                                    .substring(0, message.getFrom().indexOf("@"));

                            //VERIFICA SE ESTÁ EM CONVERSA COM O OUTR USUÁRIO
                            //SE NÃO ESTIVER ENTRA NO IF
                            if (!containContact(contato)) {
                                ConversationFrame tm = new ConversationFrame(config, contato + "@note-mathias", false);
                                listTm.add(tm);
                            }

                            listTm.get(indexOfContact(contato)).receiveMessage(message);
                            listTm.get(indexOfContact(contato)).setVisible(true);
                        }
                    });
                }
            }
        });
    }

    private void writeRemoteClientCert(Certificate cert, String username) {
        String certFilePath = ".\\nbproject\\private\\security\\" + username + ".cer";

        try {
            byte[] certBytes = cert.getEncoded();
            FileUtils.writeByteArrayToFile(new File(certFilePath), certBytes);
            System.out.println("CERTIFICADO DO USUÁRIO BAIXADO COM SUCESSO");
        } catch (IOException e) {
            System.out.println("CERTIFICADO DO USUÁRIO NÃO ESCRITO NA PASTA");
            e.printStackTrace();
        } catch (CertificateEncodingException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int indexOfContact(String contato) {
        for (ConversationFrame telaMensagem : listTm) {
            System.out.println("CONTATO: " + telaMensagem.getContactName());
            if (telaMensagem.getContactName().equals(contato)) {
                return listTm.indexOf(telaMensagem);
            }
        }

        return -1;
    }

    public boolean containContact(String contato) {

        for (ConversationFrame telaMensagem : listTm) {
            if (telaMensagem.getContactName().equals(contato)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUserButton;
    private javax.swing.JTextField campoAddUser;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listaContato;
    private javax.swing.JButton openButton;
    private javax.swing.JButton removeContactButton;
    // End of variables declaration//GEN-END:variables
}
