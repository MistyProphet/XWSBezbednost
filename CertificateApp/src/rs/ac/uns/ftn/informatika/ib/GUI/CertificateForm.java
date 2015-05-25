package rs.ac.uns.ftn.informatika.ib.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyStoreException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rs.ac.uns.ftn.informatika.ib.security.KeyStoreWriter;

public class CertificateForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField validity;
	JTextField name;
	JTextField commonName;
	JTextField surname;
	JTextField organisationUnit;
	JTextField organisationName;
	JTextField localityName;
	JTextField stateName;
	JTextField country;
	JTextField email;
	private File file;
	private JComboBox<String> saveinCb;
	
	
	private KeyStoreWriter keyStoreWriter;
	
	public KeyStoreWriter getKeyStoreWriter() {
		return keyStoreWriter;
	}

	private JButton open;

	protected KeyStorePassForm keypass;

	public JComboBox<String> getSaveinCb() {
		return saveinCb;
	}

	SelectCAForm frame;
	public CertificateForm(SelectCAForm frame) {
		super();
		this.frame = frame;
		setSize(300, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		Container content = this.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		JPanel namePanel2 = new JPanel(new BorderLayout());
		JLabel validitylbl = new JLabel("Validity (days)");
		validity = new JTextField();

		namePanel2.setMaximumSize(new Dimension(230, 50));
		namePanel2.add(validitylbl, BorderLayout.WEST);
		namePanel2.add(validity, BorderLayout.CENTER);
		content.add(namePanel2);

		JPanel namePanel3 = new JPanel(new BorderLayout());
		JLabel commonNamelbl = new JLabel("Common Name (CN)");
		commonName = new JTextField();
		namePanel3.add(commonNamelbl, BorderLayout.WEST);
		namePanel3.add(commonName, BorderLayout.CENTER);
		content.add(namePanel3);

		JPanel namePanel4 = new JPanel(new BorderLayout());
		JLabel namelbl = new JLabel("Given Name");
		name = new JTextField();
		namePanel4.add(namelbl, BorderLayout.WEST);
		namePanel4.add(name, BorderLayout.CENTER);
		content.add(namePanel4);

		JPanel namePanel5 = new JPanel(new BorderLayout());
		JLabel surnamelbl = new JLabel("Surname");
		surname = new JTextField();
		namePanel5.add(surnamelbl, BorderLayout.WEST);
		namePanel5.add(surname, BorderLayout.CENTER);
		content.add(namePanel5);

		JPanel namePanel6 = new JPanel(new BorderLayout());
		JLabel organisationUnitlbl = new JLabel("Organisation Unit (OU)");
		organisationUnit = new JTextField();
		namePanel6.add(organisationUnitlbl, BorderLayout.WEST);
		namePanel6.add(organisationUnit, BorderLayout.CENTER);
		content.add(namePanel6);

		JPanel namePanel7 = new JPanel(new BorderLayout());
		JLabel organisationNamelbl = new JLabel("Organisation Name (ON)");
		organisationName = new JTextField();
		namePanel7.add(organisationNamelbl, BorderLayout.WEST);
		namePanel7.add(organisationName, BorderLayout.CENTER);
		content.add(namePanel7);

		JPanel namePanel8 = new JPanel(new BorderLayout());
		JLabel localityNamelbl = new JLabel("Locality Name (L)");
		localityName = new JTextField();
		namePanel8.add(localityNamelbl, BorderLayout.WEST);
		namePanel8.add(localityName, BorderLayout.CENTER);
		content.add(namePanel8);

		JPanel namePanel9 = new JPanel(new BorderLayout());
		JLabel stateNamelbl = new JLabel("State Name (ST)");
		stateName = new JTextField();
		namePanel9.add(stateNamelbl, BorderLayout.WEST);
		namePanel9.add(stateName, BorderLayout.CENTER);
		content.add(namePanel9);

		JPanel namePanel0 = new JPanel(new BorderLayout());
		JLabel countrylbl = new JLabel("Country (C)");
		country = new JTextField();
		namePanel0.add(countrylbl, BorderLayout.WEST);
		namePanel0.add(country, BorderLayout.CENTER);
		content.add(namePanel0);

		JPanel namePanel11 = new JPanel(new BorderLayout());
		JLabel emaillbl = new JLabel("E-mail (E)");
		email = new JTextField();
		namePanel11.add(emaillbl, BorderLayout.WEST);
		namePanel11.add(email, BorderLayout.CENTER);
		content.add(namePanel11);

		JPanel namePanel13 = new JPanel(new BorderLayout());
		JLabel saveIn = new JLabel("Save in ");
		String[] save = { "Existing Keystore", "New Keystore" };
		saveinCb = new JComboBox<String>(save);
		saveinCb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (saveinCb.getSelectedItem().toString()
						.equals("Existing Keystore"))
					open.setText("Select");
				else
					open.setText("Create");

			}
		});
		open = new JButton();
		open.setText("Select");
		open.addActionListener(new ActionListener() {

			

			@Override
			public void actionPerformed(ActionEvent e) {
				if (open.getText().equals("Select")) {
					final JFileChooser fc = new JFileChooser();

					// In response to a button click:
					int returnVal = fc.showOpenDialog(new FileChooserDemo());

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						file = fc.getSelectedFile();
						 keypass = new KeyStorePassForm(null,null);
						

					}
				} else {
					final JFileChooser fc = new JFileChooser();

					// In response to a button click:
					int returnVal = fc.showSaveDialog(new FileChooserDemo());

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						file = fc.getSelectedFile();
						keypass = new KeyStorePassForm(null,null);

						
						
					}
				}

			}
		});
		namePanel13.add(saveIn, BorderLayout.WEST);
		namePanel13.add(saveinCb, BorderLayout.CENTER);
		namePanel13.add(open, BorderLayout.EAST);
		content.add(namePanel13);

		namePanel3.setMaximumSize(new Dimension(230, 50));
		namePanel4.setMaximumSize(new Dimension(230, 50));
		namePanel5.setMaximumSize(new Dimension(230, 50));
		namePanel6.setMaximumSize(new Dimension(230, 50));
		namePanel7.setMaximumSize(new Dimension(230, 50));
		namePanel8.setMaximumSize(new Dimension(230, 50));
		namePanel9.setMaximumSize(new Dimension(230, 50));
		namePanel0.setMaximumSize(new Dimension(230, 50));
		namePanel11.setMaximumSize(new Dimension(230, 50));
		namePanel13.setMaximumSize(new Dimension(230, 50));
		JPanel namePanel112 = new JPanel(new BorderLayout());
		JButton ok = new JButton();
		ok.setText("OK");
		namePanel112.setMaximumSize(new Dimension(140, 50));

		if(frame!=null){
			ok.addActionListener(new SignedCertificateAction(this,frame));
		}else
			ok.addActionListener(new NewCertificateAction(this));
		JButton cancel = new JButton();

		cancel.setText("Cancel");
		namePanel112.add(ok, BorderLayout.WEST);
		namePanel112.add(cancel, BorderLayout.CENTER);
		content.add(namePanel112);
		setVisible(true);
	}

	public SelectCAForm getFrame() {
		return frame;
	}

	public JButton getOpen() {
		return open;
	}

	public KeyStorePassForm getKeypass() {
		return keypass;
	}

	public File getFile() {
		return file;
	}

	public JTextField getName2() {
		return name;
	}

	public void setName(JTextField name) {
		this.name = name;
	}

	public JTextField getCommonName() {
		return commonName;
	}

	public void setCommonName(JTextField commonName) {
		this.commonName = commonName;
	}

	public JTextField getSurname() {
		return surname;
	}

	public void setSurname(JTextField surname) {
		this.surname = surname;
	}

	public JTextField getOrganisationUnit() {
		return organisationUnit;
	}

	public void setOrganisationUnit(JTextField organisationUnit) {
		this.organisationUnit = organisationUnit;
	}

	public JTextField getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(JTextField organisationName) {
		this.organisationName = organisationName;
	}

	public JTextField getLocalityName() {
		return localityName;
	}

	public void setLocalityName(JTextField localityName) {
		this.localityName = localityName;
	}

	public JTextField getStateName() {
		return stateName;
	}

	public void setStateName(JTextField stateName) {
		this.stateName = stateName;
	}

	public JTextField getCountry() {
		return country;
	}

	public void setCountry(JTextField country) {
		this.country = country;
	}

	public JTextField getEmail() {
		return email;
	}

	public void setEmail(JTextField email) {
		this.email = email;
	}

	public JTextField getValidity() {
		return validity;
	}

	public void setValidity(JTextField validity) {
		this.validity = validity;
	}
}
