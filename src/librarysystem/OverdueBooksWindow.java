package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import business.*;

public class OverdueBooksWindow extends JFrame implements LibWindow {
    public static final OverdueBooksWindow INSTANCE = new OverdueBooksWindow();

    private boolean isInitialized = false;
    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel lowerHalf;
    private JTextField isbnField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private SystemController controller;

    private static final String[] COLUMN_NAMES = {"ISBN", "Title", "Library Member", "Due Date"};

    private OverdueBooksWindow() {}

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    public void init() {
        if(!isInitialized()) {
            controller = new SystemController();
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
    
            defineUpperHalf();
            defineLowerHalf();
            defineResultTable();
            
            mainPanel.add(upperHalf, BorderLayout.NORTH);
            mainPanel.add(new JScrollPane(resultTable), BorderLayout.CENTER);
            mainPanel.add(lowerHalf, BorderLayout.SOUTH);
    
            getContentPane().add(mainPanel);
            isInitialized(true);
            pack();
        }
    }

    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchBooks());
        
        upperHalf.add(isbnLabel);
        upperHalf.add(isbnField);
        upperHalf.add(searchButton);
    }

    private void defineLowerHalf() {
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("<= Back to Main");
        backButton.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });

        lowerHalf.add(backButton);
    }

//============================================================================//
//============================================================================//
//==================== Books : Method 3---> Overdue Book  ====================//
//============================================================================//
//============================================================================//

    private void searchBooks() {
        // Get the ISBN from the input field
        String isbn = isbnField.getText();

        // Clear previous search results from the table
        tableModel.setRowCount(0);

        // Check if the ISBN field is empty, and show an error message if it is
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ISBN field cannot be empty");
            return;
        }

        // Retrieve a list of library members who have checked out entries for the given ISBN
        List<LibraryMember> usrs = controller.getCheckoutEntriesByISBN(isbn);

        // Iterate over each library member
        for (LibraryMember usr : usrs) {
            // Get the checkout record entries for the current member
            List<CheckoutRecordEntry> cres = usr.getCheckoutRecord().getEntries();

            // Iterate over each checkout record entry
            for (CheckoutRecordEntry cr : cres) {
                // Check if the book copy's ISBN matches the provided ISBN and if the entry is overdue
                if (cr.getBookCopy().getBook().getIsbn().equals(isbn) && cr.isOverdue()) {
                    // Add a new row to the table model with the overdue book details
                    tableModel.addRow(new Object[]{
                            cr.getBookCopy().getBook().getIsbn(),
                            cr.getBookCopy().getBook().getTitle(),
                            usr.getFirstName() + " " + usr.getLastName(),
                            cr.getDueDate().toString()
                    });
                }
            }
        }

        // Show a message if no overdue copies were found
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "This book's ISBN does not exist, or there are no overdue copies for this book!");
        }
    }

    public void clear() {
        isbnField.setText("");
        tableModel.setRowCount(0);
    }


    private void defineResultTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
    }

	
}
