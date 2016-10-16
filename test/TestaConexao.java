
import com.plataformax.configuration.Configuration;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidParameterSpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.DHParameterSpec;
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

    public static void main(String args[]) {
       // try {
            /*try {
            Configuration config = new Configuration ("localhost");
            config.doLogin();
            System.out.println("USUÁRIO CRIADO");
            } catch (SmackException | IOException | XMPPException | NoSuchAlgorithmException | KeyStoreException | CertificateException | KeyManagementException ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
            }*/

 /* Configuration config = new Configuration ("client7", "123456", "localhost");
            config.searchUser("client28");
        } catch (KeyManagementException ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SmackException ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMPPException ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            AlgorithmParameterGenerator apg;
            try {
                apg = AlgorithmParameterGenerator.getInstance("DH");
                apg.init(512);
                AlgorithmParameters ap = apg.generateParameters();
                DHParameterSpec dhps = (DHParameterSpec) ap.getParameterSpec(DHParameterSpec.class);
                BigInteger p = dhps.getP();
                BigInteger g = dhps.getG();
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                BigInteger x = new BigInteger(512, random);
                BigInteger ax = g.modPow(x, p);
                System.out.println("AX: " + ax.bitLength());
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidParameterSpecException ex) {
                Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
