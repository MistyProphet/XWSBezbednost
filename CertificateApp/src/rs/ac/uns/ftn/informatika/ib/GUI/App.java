package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import rs.ac.uns.ftn.informatika.ib.security.CertificateGenerator;

public class App {

	private JFrame frame;
	private JComboBox<String> issuer;
	private File file;
	private App app = this;
	
	public JComboBox<String> getIssuer() {
		return issuer;
	}

	public App() {
		 frame = new JFrame();
		frame.setSize(200	, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		FlowLayout flow = new FlowLayout();
		frame.add(panel);
		panel.setLayout(flow);
		String[] values = { "Self-signed", "Signed" };
		 issuer = new JComboBox<String>(values);
		panel.add(issuer);
		JButton ok = new JButton();
		ok.setText("OK");
		panel.add(ok);
		ok.addActionListener(new ActionListener() {
			
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				if(issuer.getSelectedItem().toString().equals("Self-signed")){
					CertificateForm certificateForm = new CertificateForm(null); 
				}
				else{
					final JFileChooser fc = new JFileChooser();

					// In response to a button click:
					int returnVal = fc.showOpenDialog(new FileChooserDemo());

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						file = fc.getSelectedFile();
						KeyStorePassForm ksPassForm =  new KeyStorePassForm(app,file);
						

					}}	
			}
		});
		/*	JToolBar toolbar = new JToolBar();
		JButton newKeystore = new JButton();
		newKeystore.setText("New Keystore");
		newKeystore.addActionListener(new NewKeystoreAction());
		JButton newCertificate = new JButton();
		newCertificate.setText("New Certificate");
		toolbar.add(newKeystore);
		toolbar.add(newCertificate);

	//	frame.add(toolbar);
		panel.add(toolbar, BorderLayout.NORTH);
	*/
		frame.setVisible(true);

	}

	public static void main(String[] args) {
	//	CertificateGenerator gen = new CertificateGenerator();
	//	gen.testIt();
		new App();
	}
}