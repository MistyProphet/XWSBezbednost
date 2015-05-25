package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rs.ac.uns.ftn.informatika.ib.security.KeyStoreReader;
import rs.ac.uns.ftn.informatika.ib.security.KeyStoreWriter;

public class SetPasswordForm {



	private JFrame frame;
	private JTextField password;

	public SetPasswordForm(X509Certificate newCertificate, String alias, File saveAs, CertificateForm certificateForm, NewCertificateAction newCertificateAction) {
		frame = new JFrame();
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BoxLayout(frame.getContentPane(),  BoxLayout.Y_AXIS));
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		FlowLayout flow = new FlowLayout();
		frame.add(panel);
		panel.setLayout(flow);
		JLabel lbl = new JLabel("Enter Password");
		password = new JTextField();
		password.setPreferredSize(new Dimension(150,30));

		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.setBackground(Color.WHITE);
		FlowLayout flow2 = new FlowLayout();
		frame.add(panel2);
		panel2.setLayout(flow2);
		JLabel lbl2 = new JLabel("Repeat Password");
		JTextField password2 = new JTextField();
		password2.setPreferredSize(new Dimension(150,30));
		
		JButton ok = new JButton();
		ok.setText("OK");
		if(certificateForm.getOpen().getText().equals("Select"))
			ok.addActionListener(new WriteInKeystoreAction(this,newCertificate,alias,saveAs,certificateForm.getKeypass().getPass().getText(),newCertificateAction));
		else
			ok.addActionListener(new NewKeystoreAction(this,newCertificate,alias,saveAs,certificateForm.getKeypass().getPass().getText(),newCertificateAction));
		
		panel.add(lbl);
		panel.add(password);
		panel2.add(lbl2);
		panel2.add(password2);
		JPanel panel3 = new JPanel(new BorderLayout());
		panel3.setBackground(Color.WHITE);
		FlowLayout flow3 = new FlowLayout();
		frame.add(panel3);
		panel3.setLayout(flow3);
		panel3.add(ok);
		frame.setVisible(true);

	}
	public JTextField getPassword() {
		return password;
	}
	public KeyPair generateKeyPair() {
		try {
			//generator para kljuceva
			KeyPairGenerator   keyGen = KeyPairGenerator.getInstance("RSA");
			//inicijalizacija generatora, 1024 bitni kljuc
			keyGen.initialize(1024);
			
			//generise par kljuceva
			KeyPair pair = keyGen.generateKeyPair();
			
			return pair;
			
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
