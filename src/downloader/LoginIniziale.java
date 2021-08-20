package downloader;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialOceanicIJTheme;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class LoginIniziale extends JFrame implements ActionListener, ItemListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String dirUser, line;
	private JTextField tfUtente;
	private JPasswordField tfPassword;
	private Registrazione registrazione;
	private BufferedReader in;
	private File filePassword;
	private JMenuBar MenuBar;
	private JMenu impostazioni;
	private JMenuItem uscita;
	private JCheckBoxMenuItem chkboxPassword;
	private JButton btnLogin;
	private JLabel lblRegistrazione, lblUtente, lblPassword, lblFreccia;
	private Main main;

	public String getDirUser() {
		return dirUser;
	}

	public static void main(String[] args) {
		FlatLightLaf.install();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatMaterialOceanicIJTheme());
					new LoginIniziale().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginIniziale() throws IOException {
		super("Login");
		dirUser = System.getProperty("user.dir");
		contentPane = new JPanel();

		chkboxPassword = new JCheckBoxMenuItem("Nascondere la Password?");
		impostazioni = new JMenu("Impostazioni");
		MenuBar = new JMenuBar();
		btnLogin = new JButton("Login");
		lblRegistrazione = new JLabel("Registrati");
		tfUtente = new JTextField();
		tfPassword = new JPasswordField();
		lblUtente = new JLabel("Utente");
		lblPassword = new JLabel("Password");
		lblFreccia = new JLabel(new ImageIcon(new ImageIcon(dirUser + "\\resources\\" + "Freccia.png").getImage()
				.getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
		uscita = new JMenuItem("Esci");

		uscita.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		uscita.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/extras/svg/actions/suspend.svg", 16, 16));

		btnLogin.setFont(new Font("Verdana", Font.PLAIN, 16));
		btnLogin.setDisplayedMnemonicIndex(0);

		lblRegistrazione.setForeground(new Color(0, 206, 209));
		lblRegistrazione.setFont(new Font("Verdana", Font.PLAIN, 12));
		tfUtente.setText(null);
		tfUtente.setColumns(10);
		tfUtente.putClientProperty("JComponent.outline", "error");
		tfPassword.setText(null);
		tfPassword.setColumns(10);
		tfPassword.setEchoChar((char) 0);
		tfPassword.putClientProperty("JComponent.outline", "error");

		lblUtente.setFont(new Font("Verdana", Font.PLAIN, 12));

		lblPassword.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblFreccia.setVisible(false);

		impostazioni.add(chkboxPassword);
		impostazioni.add(uscita);
		MenuBar.add(impostazioni);

		uscita.addActionListener(listener -> {
			System.exit(0);
		});
		lblFreccia.addMouseListener(new Indietro(this, lblRegistrazione, lblFreccia, btnLogin, tfUtente, tfPassword));
		registrazione = new Registrazione(this, lblRegistrazione, lblFreccia, btnLogin, tfUtente, tfPassword);
		lblRegistrazione.addMouseListener(registrazione);
		chkboxPassword.addItemListener(this);
		btnLogin.addActionListener(this);
		tfPassword.addKeyListener(this);
		tfUtente.addKeyListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 434, 276);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setJMenuBar(MenuBar);
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("32px"),
						ColumnSpec.decode("56px"), ColumnSpec.decode("222px"), },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("13px"), FormSpecs.LINE_GAP_ROWSPEC,
						RowSpec.decode("22px"), RowSpec.decode("27px"), RowSpec.decode("13px"), RowSpec.decode("18px"),
						RowSpec.decode("21px"), RowSpec.decode("24px"), RowSpec.decode("32px"),
						FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("17px"), }));
		contentPane.add(lblFreccia, "1, 1, 2, 3, fill, fill");
		contentPane.add(tfUtente, "4, 4, fill, fill");
		contentPane.add(lblUtente, "4, 2, center, fill");
		contentPane.add(lblPassword, "4, 6, center, fill");
		contentPane.add(tfPassword, "4, 8, fill, fill");
		contentPane.add(btnLogin, "4, 10, center, fill");
		contentPane.add(lblRegistrazione, "4, 12, center, fill");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Login")) {
			login();
		} else {
			registrazione();
		}
	}

	public void login() {
		if (controlloSeVuoto()) {
			try {
				in = new BufferedReader(new FileReader(dirUser + "\\bin\\" + "pass.txt"));
				while ((line = in.readLine()) != null) {
					if (line.equals(tfUtente.getText() + ";" + new String(this.tfPassword.getPassword()))) {
						apriDownloader();
						return;
					}
				}
				JOptionPane.showMessageDialog(new JFrame(), "Utente o Password Errati!", "Errore",
						JOptionPane.ERROR_MESSAGE);
				in.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(new JFrame(), "Utente o Password Errati!", "Errore",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Il nome utente o password è vuoto", "Errore",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void registrazione() {
		filePassword = new File(dirUser + "\\bin\\" + "pass.txt");
		controlloSeFileEsistente();
		if (controlloSeVuoto()) {
			try {
				in = new BufferedReader(new FileReader(dirUser + "\\bin\\" + "pass.txt"));
				String[] arr = null;
				while ((line = in.readLine()) != null) {
					arr = line.split(";");
					for (int i = 0; i < arr.length; i += 2) {
						if (arr[i].equals(tfUtente.getText())) {
							JOptionPane.showMessageDialog(new JFrame(), "Nome Utente già Utilizzato", "Errore",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				salvaDati();
				JOptionPane.showMessageDialog(new JFrame(), "Registrazione Effettuata", "Attenzione",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Il nome utente o password è vuoto", "Errore",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean controlloSeVuoto() {
		if (!tfUtente.getText().equals("") && !new String(this.tfPassword.getPassword()).equals("")) {
			return true;
		}
		return false;
	}

	public boolean controlloSeFileEsistente() {
		if (!filePassword.exists()) {
			System.out.println("Creazione nuovo file");
			try {
				filePassword.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return true;
		}
		return false;
	}

	public void salvaDati() throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filePassword, true));
		out.append(tfUtente.getText() + ";" + new String(this.tfPassword.getPassword()) + "\n");
		out.close();
	}

	public void apriDownloader() {
		main = new Main();
		main.setVisible(true);
		this.setVisible(false);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == 1)
			tfPassword.setEchoChar('*');
		else
			tfPassword.setEchoChar((char) 0);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (tfPassword.getDocument().getLength() < 1) {
			tfPassword.putClientProperty("JComponent.outline", "error");
		} else {
			tfPassword.putClientProperty("JComponent.outline", "null");
		}
		if (tfUtente.getDocument().getLength() < 1) {
			tfUtente.putClientProperty("JComponent.outline", "error");
		} else {
			tfUtente.putClientProperty("JComponent.outline", "null");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
