package librarysystem;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class AddBookWindow extends JFrame implements LibWindow {

	// Singleton instance of AddBookWindow
	public static final AddBookWindow INSTANCE = new AddBookWindow();
	ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;



	// Panels for organizing the layout
	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel container;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;
	private JPanel addressPanel;

	private Author author;
	//Add New Member Form Items
	private JLabel label;
	private JLabel titleLabel;
	private JTextField isbn;
	private JTextField title;

	private JTextField maxCheckout;
	private JTextField numCopies;

	private JTextField firstName;
	private JTextField lastName;
	private JTextField phone;
	private JTextField address;

	private JButton addBookButton;
	private JButton cancelButton;
	private JButton addAddressButton;



	// Method to check if the window is initialized
	public boolean isInitialized() {
		return isInitialized;
	}

	// Method to set the initialization state of the window
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	// Message bar for displaying messages to the user
	private JTextField messageBar = new JTextField();
	public void clear() {
		messageBar.setText("");
	}

	/* This class is a singleton */
    private AddBookWindow () {}

    public void init() {
		if(!isInitialized())
		{
				mainPanel = new JPanel();
				defineUpperHalf();
				defineMiddleHalf();
				defineLowerHalf();
				BorderLayout bl = new BorderLayout();
				bl.setVgap(30);
				mainPanel.setLayout(bl);

				mainPanel.add(upperHalf, BorderLayout.NORTH);
				mainPanel.add(middleHalf, BorderLayout.CENTER);
				mainPanel.add(lowerHalf, BorderLayout.SOUTH);

				getContentPane().add(mainPanel);
				isInitialized(true);
				pack();
				//setSize(660, 500);
		}


    }

    private void defineUpperHalf() {

    		upperHalf = new JPanel();
    		upperHalf.setLayout(new BorderLayout());
    		defineTopPanel();
    		defineMiddlePanel();
    		defineLowerPanel();
    		upperHalf.add(topPanel, BorderLayout.NORTH);
    		upperHalf.add(middlePanel, BorderLayout.CENTER);
    		upperHalf.add(lowerPanel, BorderLayout.SOUTH);

    	}
	private void defineMiddleHalf() {
    		middleHalf = new JPanel();
    		middleHalf.setLayout(new BorderLayout());
    		JSeparator s = new JSeparator();
    		s.setOrientation(SwingConstants.HORIZONTAL);
    		//middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
    		middleHalf.add(s, BorderLayout.SOUTH);

    	}
	private void defineLowerHalf() {

    		lowerHalf = new JPanel();
    		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));

    		JButton backButton = new JButton("<= Back to Main");
    		addBackButtonListener(backButton);
    		lowerHalf.add(backButton);

    	}
	private void defineTopPanel() {
    		topPanel = new JPanel();
    		JPanel intPanel = new JPanel(new BorderLayout());
    		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
    		JLabel loginLabel = new JLabel("Add New Book");
    		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
    		intPanel.add(loginLabel, BorderLayout.CENTER);
    		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    		topPanel.add(intPanel);
    	}
	private void defineMiddlePanel() {
    		middlePanel=new JPanel();
    		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
    		defineLeftTextPanel();
    		//defineRightTextPanel();


    		middlePanel.add(leftTextPanel);
    		//middlePanel.add(rightTextPanel);
    	}
	private void defineLeftTextPanel() {
			leftTextPanel = new JPanel();
			leftTextPanel.setLayout(new BoxLayout(leftTextPanel, BoxLayout.Y_AXIS));

			// Combine left and right panels into one horizontal panel
			JPanel rowPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

			// ISBN
			JPanel isbnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			isbn = new JTextField(10);
			label = new JLabel("ISBN");
			label.setFont(Util.makeSmallFont(label.getFont()));
			label.setPreferredSize(new Dimension(140, label.getPreferredSize().height));
			isbnPanel.add(label);
			isbnPanel.add(isbn);
			rowPanel1.add(isbnPanel);

			// Combine left and right panels into one horizontal panel
			JPanel rowPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			title = new JTextField(30);
			label = new JLabel("Title");
			label.setFont(Util.makeSmallFont(label.getFont()));
			label.setPreferredSize(new Dimension(140, label.getPreferredSize().height));
			titlePanel.add(label);
			titlePanel.add(title);
			rowPanel2.add(titlePanel);

			// Combine left and right panels into one horizontal panel
			JPanel rowPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JPanel maxCheckoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			maxCheckout = new JTextField(10);
			label = new JLabel("Maximum Checkout Length");
			label.setFont(Util.makeSmallFont(label.getFont()));
			label.setPreferredSize(new Dimension(140, label.getPreferredSize().height));
			maxCheckoutPanel.add(label);
			maxCheckoutPanel.add(maxCheckout);
			rowPanel3.add(maxCheckoutPanel);

			JPanel copiesNumPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			numCopies = new JTextField(10);
			label = new JLabel("Copies Number");
			label.setFont(Util.makeSmallFont(label.getFont()));
			label.setPreferredSize(new Dimension(140, label.getPreferredSize().height));
			copiesNumPanel.add(label);
			copiesNumPanel.add(numCopies);
			rowPanel3.add(copiesNumPanel);


			leftTextPanel.add(rowPanel1);
			leftTextPanel.add(rowPanel2);
			leftTextPanel.add(rowPanel3);
    	}
	private void defineLowerPanel() {
    		lowerPanel = new JPanel();
    		addBookButton = new JButton("Add Book");
    		addBookButtonListener(addBookButton);
    		lowerPanel.add(addBookButton);

			cancelButton = new JButton("Cancel");
			cancelButtonListener(cancelButton);
			lowerPanel.add(cancelButton);
    	}


//=====================================================================================//
//=====================================================================================//
//======================== Books : Method 1  ---> Add New Book=========================//
//=====================================================================================//
//=====================================================================================//

	private void addBookButtonListener(JButton butn) {
		// Adding an ActionListener to the button
		butn.addActionListener(evt -> {
			// Creating a new instance of SystemController
			SystemController sc = new SystemController();
			try {
				// Retrieving text from input fields
				String isbnText = isbn.getText();
				String titleText = title.getText();
				String numCopiesText = numCopies.getText();
				String maxCheckoutText = maxCheckout.getText();

				// Checking if all required fields are filled
				if(!isbnText.isEmpty() && !titleText.isEmpty() && !maxCheckoutText.isEmpty() && !numCopiesText.isEmpty()){
					// All fields are filled
					List<Author> authors = new ArrayList<>();

					// Creating a new Book object
					Book book = new Book(isbnText, titleText, Integer.parseInt(maxCheckoutText), authors, Integer.parseInt(numCopiesText));

					// Adding the new book using the controller interface
					ci.addNewBook(book);

					// Displaying a success message
					JOptionPane.showMessageDialog(this, "Book added successfully...");

					// Hiding all windows and showing the main window
					LibrarySystem.hideAllWindows();
					LibrarySystem.INSTANCE.setVisible(true);
				} else {
					// Displaying an error message if any field is empty
					JOptionPane.showMessageDialog(this, "Some fields are empty!");
				}
			} catch (Exception e) {
				// Displaying an error message if an exception occurs
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
	}
	private void addBackButtonListener(JButton butn) {
    		butn.addActionListener(evt -> {
    			LibrarySystem.hideAllWindows();
    			LibrarySystem.INSTANCE.setVisible(true);
    		});
    	}
	private void cancelButtonListener(JButton butn) {
    		butn.addActionListener(evt -> {
    			LibrarySystem.hideAllWindows();
    			LibrarySystem.INSTANCE.setVisible(true);
    		});
    	}
}
