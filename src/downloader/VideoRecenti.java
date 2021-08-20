package downloader;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;

public class VideoRecenti extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<String> listaRecenti;
	public DefaultListModel<String> dlm = new DefaultListModel<String>();
	public ArrayList linkVideo = new ArrayList();

	public static void main(String[] args) {
		FlatLightLaf.install();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
				} catch (Exception e) {
					e.printStackTrace();
				}
				new VideoRecenti().setVisible(true);
			}
		});
	}

	public VideoRecenti() {
		super("Video Scaricati");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 110, 298, 316);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 11, 258, 256);
		contentPane.add(scrollPane);
		listaRecenti = new JList(dlm);
		scrollPane.setViewportView(listaRecenti);
		listaRecenti.addMouseListener(this);
	}

	public void aggiungiTitolo(String titolo) {
		dlm.addElement(titolo);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JList list = (JList) e.getSource();
		if (e.getClickCount() == 2) {

			int index = list.locationToIndex(e.getPoint());
			String URL = (String) linkVideo.get(index);
			URL = URL.replaceAll("\"", "");
			try {
				System.out.println("URL: " + URL);
				Desktop.getDesktop().browse(new URL(URL).toURI());
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
