package downloader;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class MetodiMenu {
	private JFileChooser chooser;
	private Main main;
	private File cartellaSalvataggio;
	private VideoRecenti videoRecenti;

	public MetodiMenu(Main main, JFileChooser chooser, File cartellaSalvataggio, String dirSelezionata,
			VideoRecenti videoRecenti) {
		this.main = main;
		this.chooser = chooser;
		this.cartellaSalvataggio = cartellaSalvataggio;
		this.videoRecenti = videoRecenti;
	}

	public void exit() {
		System.exit(0);
	}

	public void cambiaDirectory() {
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showSaveDialog(main);
		cartellaSalvataggio = chooser.getSelectedFile();
		try {
			Main.dirSelezionata = cartellaSalvataggio.getAbsolutePath().toString();
		} catch (Exception e2) {

		}
		System.out.println(Main.dirSelezionata);
	}

	public void apriDirectory() {
		try {
			Desktop.getDesktop().open(new File(Main.dirSelezionata));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void apriRecenti() {
		videoRecenti.setVisible(true);
	}
}
