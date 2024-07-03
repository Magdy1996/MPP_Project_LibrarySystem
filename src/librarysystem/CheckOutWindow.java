package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class CheckOutWindow extends JFrame implements LibWindow {
    public static final CheckOutWindow INSTANCE = new CheckOutWindow();

    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JTextField memberIdField;
    private JTextField isbnField;
    private JTable checkoutTable;
    private DefaultTableModel tableModel;
    private JLabel label;
    private JButton checkoutButton;

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    private JTextField messageBar = new JTextField();

    public void clear() {
        messageBar.setText("");
        memberIdField.setText("");
        isbnField.setText("");
    }

    /* This class is a singleton */
    private CheckOutWindow() {
    }

    public void init() {
        if (isInitialized) {
            clear();
            return;
        }
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
        middleHalf.add(s, BorderLayout.SOUTH);
        DataAccess da = new DataAccessFacade();
        List<LibraryMember> memberList = new ArrayList<>(da.readMemberMap().values());
        tableModel = new DefaultTableModel(
                new Object[] { "Member ID", "ISBN", "Book Title", "Checkout Date", "Due Date" }, 0);
        for (int i = 0; i < memberList.size(); i++) {
            final LibraryMember mem = memberList.get(i);
            String memberId = mem.getMemberId();
            final CheckoutRecord record = mem.getCheckoutRecord();
            for (CheckoutRecordEntry entry : record.getEntries()) {
                String checkoutDate = entry.getCheckoutDate().toString();
                String dueDate = entry.getDueDate().toString();
                String bookTitle = entry.getBookCopy().getBook().getTitle();
                String isbn = entry.getBookCopy().getBook().getIsbn();
                tableModel.addRow(new Object[] { memberId, isbn, bookTitle, checkoutDate, dueDate });
            }
        }
        checkoutTable = new JTable(tableModel);
        checkoutTable.setEnabled(false); // Make table read-only
        middleHalf.add(new JScrollPane(checkoutTable), BorderLayout.CENTER);
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
        intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        JLabel checkoutLabel = new JLabel("Checkout");
        Util.adjustLabelFont(checkoutLabel, Color.BLUE.darker(), true);
        intPanel.add(checkoutLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);
    }
    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }
    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        checkoutButton = new JButton("Checkout");
        addCheckoutButtonListener(checkoutButton);
        lowerPanel.add(checkoutButton);
    }
    private void defineLeftTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        memberIdField = new JTextField(10);
        label = new JLabel("Member ID");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(memberIdField);
        bottomText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText, BorderLayout.NORTH);
        leftTextPanel.add(bottomText, BorderLayout.CENTER);
    }
    private void defineRightTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        isbnField = new JTextField(10);
        label = new JLabel("ISBN");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(isbnField);
        bottomText.add(label);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText, BorderLayout.NORTH);
        rightTextPanel.add(bottomText, BorderLayout.CENTER);
    }



//=====================================================================================//
//=====================================================================================//
//========================== Books : Method 2---> Check Out Book=======================//
//=====================================================================================//
//=====================================================================================//

    private void addCheckoutButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            // Retrieve and trim the member ID and ISBN from the input fields
            String memberId = memberIdField.getText().trim();
            String isbn = isbnField.getText().trim();

            // Print the member ID to the console for debugging
            System.out.println("memberId :" + memberId);

            // Check if either the member ID or ISBN is empty, and show an error message if so
            if (memberId.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(CheckOutWindow.this, "Please enter both Member ID and ISBN.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a DataAccess object to interact with the data layer
            DataAccess da = new DataAccessFacade();

            // Search for the library member using the provided member ID
            final LibraryMember member = da.searchMember(memberId);
            // Show an error message if the member ID is invalid
            if (member == null) {
                Util.showErrorMessage(CheckOutWindow.this, "Invalid Member ID.");
                return;
            }

            // Search for the book using the provided ISBN
            final Book book = da.searchBook(isbn);
            // Show an error message if the ISBN is invalid
            if (book == null) {
                Util.showErrorMessage(CheckOutWindow.this, "Invalid ISBN.");
                return;
            }

            // Check if the book is available for checkout, and show an error message if not
            if (!book.isAvailable()) {
                Util.showErrorMessage(CheckOutWindow.this, "No Copies Available.");
                return;
            }

            // Get the next available copy of the book
            final BookCopy bookCopy = book.getNextAvailableCopy();
            // Get the maximum checkout length for the book
            final int maxCheckoutLength = book.getMaxCheckoutLength();

            // Retrieve the book title, and set the checkout and due dates
            String bookTitle = book.getTitle();
            LocalDate checkoutDate = LocalDate.now();
            LocalDate dueDate = checkoutDate.plusDays(maxCheckoutLength);

            // Perform the checkout operation for the member with the book copy and dates
            member.checkOut(bookCopy, checkoutDate, dueDate);
            // Update the book's copies to reflect the checkout
            book.updateCopies(bookCopy);
            // Save the updated member and book information
            da.saveNewMember(member);
            da.saveBook(book);

            // Add the checkout details to the table model
            tableModel.addRow(new Object[] { memberId, isbn, bookTitle, checkoutDate, dueDate });
            // Show a success message indicating the book was checked out successfully
            JOptionPane.showMessageDialog(CheckOutWindow.this, "Book checked out successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }
}

