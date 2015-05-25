package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.security.cert.X509Certificate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import rs.ac.uns.ftn.informatika.ib.security.KeyStoreReader;
import rs.ac.uns.ftn.informatika.ib.security.KeyStoreWriter;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel namePanel112 = new JPanel(new BorderLayout());
		JButton ok = new JButton();
		ok.setText("OK");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				CertificateForm certificateForm = new CertificateForm(frame);
			}
		});
		namePanel112.setMaximumSize(new Dimension(140, 50));
		
		content.add(namePanel2);
		JButton cancel = new JButton();

		cancel.setText("Cancel");
		namePanel112.add(ok, BorderLayout.WEST);
		namePanel112.add(cancel, BorderLayout.CENTER);
		content.add(namePanel112);
		setVisible(true);
	}


}
