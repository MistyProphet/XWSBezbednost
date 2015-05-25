package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.cert.X509Certificate;
import rs.ac.uns.ftn.informatika.ib.GUI.SelectCAForm;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeyStorePassForm {

	private JFrame frame;
	
	private JTextField pass;

	private App app;

	private File file;
	public KeyStorePassForm(final App app, final File file){
		this.file = file;
		this.app = app;
		 frame = new JFrame();
		frame.setSize(300, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		FlowLayout flow = new FlowLayout();
		frame.add(panel);
		panel.setLayout(flow);
		JLabel lbl = new JLabel("Enter KeyStore Password");
		pass = new JTextField();
		pass.setPreferredSize(new Dimension(150,30));
		JButton ok = new JButton();
		ok.setText("OK");
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				if(app!=null &&app.getIssuer().getSelectedItem().toString().equals("Signed")){
					 SelectCAForm selectform = new SelectCAForm(file,pass.getText());
				}
				
					
			}
		});
		panel.add(lbl);
		panel.add(pass);
		panel.add(ok);
		frame.setVisible(true);

	}
	public JTextField getPass() {
		return pass;
	}
	public void setPass(JTextField alias) {
		this.pass = alias;
	}
}
