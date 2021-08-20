package downloader;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Indietro implements MouseListener {

	LoginIniziale login;
	JLabel lblRegistrazione, lblFreccia;
	JButton btnLogin;
	JTextField tfUtente, tfPassword;

	public Indietro(LoginIniziale login, JLabel lblRegistrazione, JLabel lblFreccia, JButton btnLogin,
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
		login.setTitle("Login");
		lblRegistrazione.setVisible(true);
		lblFreccia.setVisible(false);
		btnLogin.setText("Login");
		tfUtente.setText("");
		tfPassword.setText("");
		tfUtente.putClientProperty("JComponent.outline", "null");
		tfPassword.putClientProperty("JComponent.outline", "null");
		tfPassword.addKeyListener(login);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		lblFreccia.setIcon(new ImageIcon(new ImageIcon(login.getDirUser() + "\\resources\\" + "Freccia_2.png")
				.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		lblFreccia.setIcon(new ImageIcon(new ImageIcon(login.getDirUser() + "\\resources\\" + "Freccia.png").getImage()
				.getScaledInstance(32, 32, Image.SCALE_SMOOTH)));

	}

}
