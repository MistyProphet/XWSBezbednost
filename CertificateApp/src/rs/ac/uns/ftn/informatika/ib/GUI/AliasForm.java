package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.cert.X509Certificate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AliasForm {

	private JFrame frame;
	X509Certificate newCertificate;
	private JTextField alias;
	 NewCertificateAction newCertificateAction;
	

	public NewCertificateAction getNewCertificateAction() {
		return newCertificateAction;
	}
	public AliasForm(final NewCertificateAction newCertificateAction, final File saveAs, final CertificateForm certificateForm){
		this.newCertificate = newCertificateAction.getCert();
		this.newCertificateAction = newCertificateAction;
		 frame = new JFrame();
		frame.setSize(300, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		FlowLayout flow = new FlowLayout();
		frame.add(panel);
		panel.setLayout(flow);
		JLabel lbl = new JLabel("Enter Alias");
		 alias = new JTextField();
		alias.setPreferredSize(new Dimension(150,30));
		JButton ok = new JButton();
		ok.setText("OK");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				SetPasswordForm password = new SetPasswordForm(newCertificate,alias.getText(),saveAs,certificateForm, newCertificateAction);
			}
		});
		panel.add(lbl);
		panel.add(alias);
		panel.add(ok);
		frame.setVisible(true);

	}
	public JTextField getAlias() {
		return alias;
	}
	public void setAlias(JTextField alias) {
		this.alias = alias;
	}
}
