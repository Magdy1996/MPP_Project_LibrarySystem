package librarysystem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import business.ControllerInterface;
import business.SystemController;

public class AllMemberIdsWindow extends JFrame implements LibWindow {
	public static final AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
	private final ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;

	private AllMemberIdsWindow() {}

	public void init() {
		mainPanel = new JPanel(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}

	private void defineTopPanel() {
		topPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		JLabel allIDsLabel = new JLabel("All Member IDs");
		Util.adjustLabelFont(allIDsLabel, Util.DARK_BLUE, true);
		topPanel.add(allIDsLabel, gbc);
	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		textArea = new TextArea(8, 20);
		middlePanel.add(textArea, gbc);
	}

	private void defineLowerPanel() {
		lowerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		JButton backButton = new JButton("<== Back to Main");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton, gbc);
	}

	public void setData(String data) {
		textArea.setText(data);
	}

	private void addBackButtonListener(JButton button) {
		button.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
}
