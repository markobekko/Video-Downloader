package downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

class TextAppendWorker extends SwingWorker<String, String> {
	private final String MB = "MiB";
	private JTextArea txtConsole;
	private String testoInput;
	private BufferedReader stdInput;
	private ArrayList<String> arrayList;
	private JButton btnDownload;
	private boolean isPlaylistOn;

	public TextAppendWorker() {
	}

	public TextAppendWorker(JTextArea txtConsole, String testoInput, BufferedReader stdInput, JButton btnDownload,
			boolean isPlaylistOn) {
		this.txtConsole = txtConsole;
		this.testoInput = testoInput;
		this.stdInput = stdInput;
		this.btnDownload = btnDownload;
		this.isPlaylistOn = isPlaylistOn;
	}

	@Override
	protected String doInBackground() throws Exception {
		try {
			while ((testoInput = stdInput.readLine()) != null) {
				System.out.println("Testo: " + testoInput);
				if (testoInput.contains(MB)) {
					publish(testoInput + "\n");
					System.out.println(testoInput + "\n");
				}
				arrayList = new ArrayList<String>();
				arrayList.add(testoInput);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void process(List<String> chunks) {
		for (String s : chunks) {
			txtConsole.append(s);
		}
	}

	@Override
	protected void done() {
		System.out.println("finito");
		for (String s : arrayList) {
			if ((s.contains("Correcting container") || s.contains("100%")) && !isPlaylistOn) {
				txtConsole.append("Download Completato");
				txtConsole.append("\n");
				JOptionPane.showMessageDialog(new JFrame(), "Download Completato", "Attenzione",
						JOptionPane.INFORMATION_MESSAGE);
				testoInput = null;
			}
			if (s.contains("Finished downloading playlist") && isPlaylistOn) {
				txtConsole.append("Playlist Scaricata");
				txtConsole.append("\n");
				JOptionPane.showMessageDialog(new JFrame(), "Playlist Scaricata", "Attenzione",
						JOptionPane.INFORMATION_MESSAGE);
				testoInput = null;
			}
		}
		btnDownload.setEnabled(true);
	}
}
