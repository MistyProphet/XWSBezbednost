package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import rs.ac.uns.ftn.informatika.ib.security.KeyStoreReader;

public class SelectCAForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	SelectCAForm frame = this;


public DefaultTableModel getModel() {
	return model;
}

private DefaultTableModel model;


public JTable getTable() {
	return table;
}
KeyStore ks;


public KeyStore getKs() {
	return ks;
}

private JTable table;
File file;

String pass;

	public File getFile() {
	return file;
}

public String getPass() {
	return pass;
}

	public SelectCAForm(File file, String pass) {
		super();
		this.file = file;
		this.pass = pass;
		setSize(300, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		Container content = this.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		JPanel namePanel2 = new JPanel(new BorderLayout());
		 table = new JTable();
		namePanel2.add(table);
		 model = new DefaultTableModel();
		table.setModel(model);
		model.addColumn("Alias");
		model.addColumn("Details");
		KeyStoreReader keyread = new KeyStoreReader();
		try {
			ks = KeyStore.getInstance("JKS", "SUN");
		
		//ucitavamo podatke
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		ks.load(in, pass.toCharArray());
		Enumeration<String> aliases = ks.aliases();
		while(aliases.hasMoreElements()){
			String ali = aliases.nextElement();
			 X509Certificate certifikat = (X509Certificate) ks.getCertificate(ali);
		
			model.addRow(new Object[] {ali,((X509Certificate)certifikat).getSubjectX500Principal().getName()});
		}
		

		} catch (KeyStoreException | NoSuchProviderException e) {
			JOptionPane.showMessageDialog(frame, "Wrong Keystore password");
		
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(frame, "File not found");
			System.exit(0);
			
		} catch (NoSuchAlgorithmException e1) {
			JOptionPane.showMessageDialog(frame, "Wrong Keystore password");
			System.exit(0);
			
		} catch (CertificateException e1) {
			JOptionPane.showMessageDialog(frame, "Wrong Keystore password");
			System.exit(0);
		
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(frame, "Wrong Keystore password");
			System.exit(0);
		} 
		JPanel namePanel112 = new JPanel(new BorderLayout());
		JButton ok = new JButton();
		ok.setText("OK");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(frame.getTable().getSelectedRow()!=-1)
				{
				frame.setVisible(false);
				CertificateForm certificateForm = new CertificateForm(frame);
			}}
		});
		namePanel112.setMaximumSize(new Dimension(140, 50));
		
		content.add(namePanel2);
		JButton cancel = new JButton();
cancel.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		System.exit(0);
		
	}
});
		cancel.setText("Cancel");
		namePanel112.add(ok, BorderLayout.WEST);
		namePanel112.add(cancel, BorderLayout.CENTER);
		content.add(namePanel112);
		setVisible(true);
	}


}
