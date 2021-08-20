package downloader;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ControlloErrori implements Runnable {

	private BufferedReader stdError;
	private String testoInput, formato;
	private JTextArea txtConsole;
	private JButton btnDownload;

	public ControlloErrori() {

	}

	public ControlloErrori(BufferedReader stdError, String testoInput, String formato, JTextArea txtConsole,
			JButton btnDownload) {
		this.stdError = stdError;
		this.testoInput = testoInput;
		this.formato = formato;
		this.txtConsole = txtConsole;
		this.btnDownload = btnDownload;
	}

	@Override
	public void run() {
		try {
			while ((testoInput = stdError.readLine()) != null) {
				if (testoInput.contains("is not a valid URL.")) {
					txtConsole.setForeground(Color.RED);
					txtConsole.setText("URL non valido!");
					testoInput = null;
					JOptionPane.showMessageDialog(new JFrame(), "URL non valido!", "Errore", JOptionPane.ERROR_MESSAGE);
					btnDownload.setEnabled(true);
					return;
				} else if (testoInput.contains("This playlist is private,")) {
					txtConsole.setForeground(Color.RED);
					txtConsole.setText("La playlist è privata");
					JOptionPane.showMessageDialog(new JFrame(), "La playlist è privata", "Errore",
							JOptionPane.ERROR_MESSAGE);
					testoInput = null;
					btnDownload.setEnabled(true);
					return;
				} else if (testoInput.contains("requested format not available") && formato != null) {
					txtConsole.setForeground(Color.RED);
					txtConsole.setText("Formato richiesto non disponibile");
					JOptionPane.showMessageDialog(new JFrame(), "Formato non disponibile", "Errore",
							JOptionPane.ERROR_MESSAGE);
					testoInput = null;
					btnDownload.setEnabled(true);
					return;
				} else {
					txtConsole.append(testoInput);
					testoInput = null;
					btnDownload.setEnabled(true);
					return;
				}
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}
}
