package downloader;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Registrazione implements MouseListener, KeyListener {

	LoginIniziale login;
	JLabel lblRegistrazione, lblFreccia;
	JButton btnLogin;
	JTextField tfUtente, tfPassword;

	public Registrazione(LoginIniziale login, JLabel lblRegistrazione, JLabel lblFreccia, JButton btnLogin,
			JTextField tfUtente, JTextField tfPassword) {
		this.login = login;
		this.lblRegistrazione = lblRegistrazione;
		this.lblFreccia = lblFreccia;
		this.btnLogin = btnLogin;
		this.tfUtente = tfUtente;
		this.tfPassword = tfPassword;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		login.validate();
		login.repaint();
		tfUtente.setText("");
		tfPassword.setText("");
		tfUtente.putClientProperty("JComponent.outline", "error");
		tfPassword.putClientProperty("JComponent.outline", "error");
		login.setTitle("Registrazione");
		lblRegistrazione.setVisible(false);
		lblFreccia.setVisible(true);
		btnLogin.setText("Registrati");
		tfPassword.addKeyListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		lblRegistrazione.setText("<HTML><U>Registrati</U></HTML>");

	}

	@Override
	public void mouseExited(MouseEvent e) {
		lblRegistrazione.setText("<HTML>Registrati</HTML>");

	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(tfPassword.getDocument().getLength());
		if (tfPassword.getDocument().getLength() < 3) {
			tfPassword.putClientProperty("JComponent.outline", "error");
		} else if (tfPassword.getDocument().getLength() >= 3 && tfPassword.getDocument().getLength() < 8) {
			tfPassword.putClientProperty("JComponent.outline", "warning");
		} else if (tfPassword.getDocument().getLength() >= 8) {
			tfPassword.putClientProperty("JComponent.outline", "null");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
