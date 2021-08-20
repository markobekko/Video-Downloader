package downloader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class Main extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtConsole;
	private JToggleButton btnMp3, btnMp4, btnWav, btnM4a;
	private Process proc;
	private String testoInput, formato;
	private ControlloErrori controlloErrori;
	private Thread thread1;
	private JTextField tfLink;
	private JCheckBox CheckBoxPlaylist;
	private JButton btnDownload;
	private JScrollPane scrollPane;
	private JLabel label1, lblLink, lblPlaylist, lblConsole;
	private JMenuBar MenuBar;
	private JMenu file, view;
	private JMenuItem uscita, cambiaDirectory, apriDirectory; // File
	private JMenuItem recenti; // View
	private JFileChooser chooser;
	private File cartellaSalvataggio;
	private VideoRecenti videoRecenti;
	public static String dirUser, dirSelezionata;

	public static void main(String[] args) {
		FlatLightLaf.install();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					dirUser = System.getProperty("user.dir");
					dirSelezionata = dirUser;
					UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				new Main().setVisible(true);
			}
		});
	}

	public Main() {
		super("Video Downloader");
		dirUser = System.getProperty("user.dir");
		dirSelezionata = dirUser;
		contentPane = new JPanel();
		BufferedImage buttonIcon = null;
		try {
			buttonIcon = ImageIO.read(new File(dirUser + "\\resources\\" + "Download.png"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		MenuBar = new JMenuBar();
		file = new JMenu("File");
		view = new JMenu("View");
		cambiaDirectory = new JMenuItem(UIManager.getIcon("Tree.closedIcon"));
		apriDirectory = new JMenuItem(UIManager.getIcon("Tree.openIcon"));
		uscita = new JMenuItem("Esci");
		recenti = new JMenuItem("Recenti");
		btnDownload = new JButton(new ImageIcon(buttonIcon));
		scrollPane = new JScrollPane();
		txtConsole = new JTextArea();
		label1 = new JLabel("Tipo di File");
		btnMp3 = new JToggleButton("MP3");
		btnMp4 = new JToggleButton("MP4");
		btnWav = new JToggleButton("WAV");
		btnM4a = new JToggleButton("M4A");
		tfLink = new JTextField();
		lblLink = new JLabel("Link del Video");
		lblPlaylist = new JLabel("Scaricare l'intera Playlist?");
		lblConsole = new JLabel("Console");

		cambiaDirectory
				.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		cambiaDirectory.setText("Salva in..");

		apriDirectory
				.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		apriDirectory.setText("Apri Directory");

		uscita.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		recenti.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		recenti.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/RecentlyUsed.svg", 16, 16));
		uscita.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/extras/svg/actions/suspend.svg", 16, 16));

		btnDownload.setFont(new Font("Stencil", Font.PLAIN, 16));
		btnDownload.setForeground(Color.BLACK);

		scrollPane.setViewportView(txtConsole);

		txtConsole.setFont(new Font("Serif", Font.ITALIC, 16));
		txtConsole.setLineWrap(true);
		txtConsole.setForeground(Color.LIGHT_GRAY);
		txtConsole.setWrapStyleWord(true);
		txtConsole.setEditable(false);
		txtConsole.setText("Benvenuto!");

		label1.setFont(new Font("Verdana", Font.PLAIN, 16));
		tfLink.setColumns(10);

		lblLink.setFont(new Font("Verdana", Font.PLAIN, 16));

		CheckBoxPlaylist = new JCheckBox("");

		lblConsole.setFont(new Font("Verdana", Font.PLAIN, 15));

		file.add(cambiaDirectory);
		file.add(apriDirectory);
		file.add(uscita);
		view.add(recenti);
		MenuBar.add(file);
		MenuBar.add(view);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("212px"),
						ColumnSpec.decode("29px"), ColumnSpec.decode("90px"), ColumnSpec.decode("39px"),
						ColumnSpec.decode("85px"), ColumnSpec.decode("30px"), ColumnSpec.decode("60px"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("166px"), },
				new RowSpec[] { FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("17px"),
						FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("22px"), FormSpecs.PARAGRAPH_GAP_ROWSPEC,
						RowSpec.decode("22px"), FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("30px"),
						FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("30px"), RowSpec.decode("19px"),
						RowSpec.decode("83px"), RowSpec.decode("37px"), RowSpec.decode("30px"), }));
		contentPane.add(btnDownload, "4, 14, 3, 1, fill, fill");
		contentPane.add(scrollPane, "2, 4, 3, 9, fill, fill");
		contentPane.add(label1, "8, 6, 3, 1, left, fill");
		contentPane.add(btnMp3, "6, 8, 3, 1, fill, fill");
		contentPane.add(btnMp4, "10, 8, fill, fill");
		contentPane.add(btnWav, "6, 10, 3, 1, fill, fill");
		contentPane.add(btnM4a, "10, 10, fill, fill");
		contentPane.add(tfLink, "6, 4, 5, 1, fill, fill");
		contentPane.add(lblLink, "8, 2, 3, 1, left, fill");
		contentPane.add(lblPlaylist, "6, 12, 3, 1, fill, top");
		contentPane.add(CheckBoxPlaylist, "9, 12, 2, 1, left, top");
		contentPane.add(lblConsole, "2, 2, right, fill");

		btnMp3.addActionListener(actionListener -> {
			formato = "mp3";
			disabilitaBottoni(actionListener);
		});
		btnMp4.addActionListener(actionListener -> {
			formato = "mp4";
			disabilitaBottoni(actionListener);
		});
		btnWav.addActionListener(actionListener -> {
			formato = "wav";
			disabilitaBottoni(actionListener);
		});
		btnM4a.addActionListener(actionListener -> {
			formato = "m4a";
			disabilitaBottoni(actionListener);
		});

		videoRecenti = new VideoRecenti();

		MetodiMenu m = new MetodiMenu(this, chooser, cartellaSalvataggio, dirSelezionata, videoRecenti);
		btnDownload.addActionListener(this);
		uscita.addActionListener(e -> m.exit());
		cambiaDirectory.addActionListener(e -> m.cambiaDirectory());
		apriDirectory.addActionListener(e -> m.apriDirectory());
		recenti.addActionListener(e -> m.apriRecenti());

		setSize(800, 423);
		setIconImage(Toolkit.getDefaultToolkit().getImage(dirUser + "\\resources\\" + "Icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setJMenuBar(MenuBar);
		setContentPane(contentPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean isPlaylistOn = false;
		btnDownload.setEnabled(false);
		txtConsole.setForeground(Color.LIGHT_GRAY);
		txtConsole.setText("Downloading..\n");
		Runtime rt = Runtime.getRuntime();
		String video = "\"" + tfLink.getText() + "\"";

		if (formato == null) {
			txtConsole.setText("Inserisci un Formato!\n");
			JOptionPane.showMessageDialog(new JFrame(), "Seleziona un Formato", "Errore", JOptionPane.ERROR_MESSAGE);
			testoInput = null;
			btnDownload.setEnabled(true);
			return;
		}

		String commands;
		commands = "cmd /c cd " + dirUser + " && exec " + video + " -title" + " --no-playlist";
		try {
			System.out.println(commands);
			proc = rt.exec(commands);
			String titoloIntero;
			BufferedReader titoloInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((titoloIntero = titoloInput.readLine()) != null) {
				String[] a = titoloIntero.split("&list");
				for (String titolo : a) {
					videoRecenti.aggiungiTitolo(titolo);
				}

			}
			videoRecenti.linkVideo.add(video);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		if (CheckBoxPlaylist.isSelected()) {
			commands = "cmd /c cd " + dirUser + " && exec " + "-o " + "\"" + dirSelezionata + "\""
					+ "\\%(title)s.%(ext)s\\ " + video + " -f " + formato + " --yes-playlist " + " --geo-bypass";
			isPlaylistOn = true;
		} else {
			commands = "cmd /c cd " + dirUser + " && exec " + "-o " + "\"" + dirSelezionata + "\""
					+ "\\%(title)s.%(ext)s\\ " + video + " -f " + formato + " --no-playlist " + " --geo-bypass";
		}
		try {
			System.out.println(commands);
			proc = rt.exec(commands);
			testoInput = "";
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			new TextAppendWorker(txtConsole, testoInput, stdInput, btnDownload, isPlaylistOn).execute();

			controlloErrori = new ControlloErrori(stdError, testoInput, formato, txtConsole, btnDownload);
			thread1 = new Thread(controlloErrori);
			thread1.start();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void disabilitaBottoni(ActionEvent actionListener) {
		btnMp3.setSelected(false);
		btnMp4.setSelected(false);
		btnWav.setSelected(false);
		btnM4a.setSelected(false);
		switch (actionListener.getActionCommand()) {
		case "MP3":
			btnMp3.setSelected(true);
			break;
		case "MP4":
			btnMp4.setSelected(true);
			break;
		case "WAV":
			btnWav.setSelected(true);
			break;
		case "M4A":
			btnM4a.setSelected(true);
			break;
		}
	}
}
