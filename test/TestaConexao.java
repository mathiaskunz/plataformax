
import com.plataformax.configuration.Configuration;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mathias
 */
public class TestaConexao {
    
    public static void main (String args[]){
        try {
            Configuration config = new Configuration ("localhost");
            config.doLogin();
            System.out.println("USUÁRIO CRIADO");
        } catch (SmackException | IOException | XMPPException | NoSuchAlgorithmException | KeyStoreException | CertificateException | KeyManagementException ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
