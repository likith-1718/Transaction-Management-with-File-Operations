import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;
import java.util.List;


public class Bank extends JFrame implements ActionListener {
    private JButton adminLoginButton;
    private JButton userLoginButton;
    private JButton userSignupButton;
    private JButton exitButton;

    public Bank() {
        setTitle("BANKING MANAGEMENT SYSTEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLayout(new BorderLayout());

        // Set background image
        ImageIcon backgroundIcon = new ImageIcon("isha2.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        add(backgroundLabel);
        JLabel welcomeLabel = new JLabel("WELCOME TO BANKING MANAGEMENT SYSTEM");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(55f).deriveFont(Font.BOLD));
        welcomeLabel.setForeground(Color.RED);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(-160, 100, backgroundIcon.getIconWidth(), 100);
        backgroundLabel.add(welcomeLabel);
        JLabel abc = new JLabel("Please Login !!");
        abc.setFont(abc.getFont().deriveFont(40f));
        abc.setForeground(Color.BLACK);
        abc.setHorizontalAlignment(SwingConstants.CENTER);
        abc.setVerticalAlignment(SwingConstants.CENTER);
        abc.setBounds(-200, 300, backgroundIcon.getIconWidth(), 100);
        backgroundLabel.add(abc);

        setVisible(true);

        JPanel g = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Dimension buttonSize = new Dimension(200, 100);
        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setPreferredSize(buttonSize);
        adminLoginButton.setOpaque(false);
        adminLoginButton.setFont(adminLoginButton.getFont().deriveFont(25f)); // Increase font size
        userLoginButton = new JButton("User Login");
        userLoginButton.setPreferredSize(buttonSize);
        userLoginButton.setFont(userLoginButton.getFont().deriveFont(25f)); // Increase font size
        userLoginButton.setOpaque(false);
        userSignupButton = new JButton("User Signup");
        userSignupButton.setPreferredSize(buttonSize);
        userSignupButton.setFont(userSignupButton.getFont().deriveFont(25f)); // Increase font size
        userSignupButton.setOpaque(false);
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);
        exitButton.setFont(exitButton.getFont().deriveFont(25f));
        exitButton.setOpaque(false);

        adminLoginButton.addActionListener(this);
        userLoginButton.addActionListener(this);
        userSignupButton.addActionListener(this);
        exitButton.addActionListener(this);
        g.setOpaque(false);
        g.setBounds(500, 200, 600, 180);

        g.add(adminLoginButton);
        g.add(userLoginButton);
        g.add(userSignupButton);
        g.add(exitButton);
        g.setBounds(500, 400, 500, 200);
        backgroundLabel.add(g);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Bank();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminLoginButton) {
            JPasswordField passwordField = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(this, passwordField, "Enter admin password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (password.equals("Hello123")) {
                    MainWindow mainWindow = new MainWindow();
                    mainWindow.createAndShowGUI();
                } else {
                    JOptionPane.showMessageDialog(this, "password correct aagi haako");
                }
            }
        } else if (e.getSource() == userLoginButton) {
            String username = JOptionPane.showInputDialog(this, "Enter Account Number:");

            JPasswordField passwordField = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(this, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (validateUserLogin(username, password)) {
                    String g = getName(username, password);
                    MainWindow2 mainWindow2 = new MainWindow2();
                    mainWindow2.createAndShowGUI(username, g);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }
            }
        } else if (e.getSource() == userSignupButton) {
            String username = JOptionPane.showInputDialog(this, "Enter Account Number:");

            if (isAccountNumberExists(username)) {
                JOptionPane.showMessageDialog(this, "Account number already exists");
                return;
            }

            String password = JOptionPane.showInputDialog(this, "Enter password:");

            String accountHolderName = JOptionPane.showInputDialog(this, "Enter account holder name:");

            if (signupUser(username, password, accountHolderName)) {
                JOptionPane.showMessageDialog(this, "User signup successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Error in user signup");
            }
        }
        else if (e.getSource() == exitButton) {
            // Handle exit button click
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private boolean validateUserLogin(String username, String password) {
        try {
            String filePath = "users.txt";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] users = content.split(System.lineSeparator());
            for (String user : users) {
                String[] userDetails = user.split("\\|");
                if (userDetails.length == 3 && userDetails[0].equals(username) && userDetails[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getName(String username, String password) {
        try {
            String filePath = "users.txt";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] users = content.split(System.lineSeparator());
            for (String user : users) {
                String[] userDetails = user.split("\\|");
                if (userDetails.length == 3 && userDetails[0].equals(username) && userDetails[1].equals(password)) {
                    return userDetails[2].toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isAccountNumberExists(String accountNumber) {
        try {
            String filePath = "users.txt";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] users = content.split(System.lineSeparator());
            for (String user : users) {
                String[] userDetails = user.split("\\|");
                if (userDetails.length >= 1 && userDetails[0].equals(accountNumber)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean signupUser(String username, String password, String accountHolderName) {
            if (username.length() != 3) {
                // Display error message for invalid username length
                JOptionPane.showMessageDialog(null, "Username should be exactly 3 characters long.", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6}$")) {
                // Display error message for invalid password format
                JOptionPane.showMessageDialog(null, "Password should be exactly 6 characters long and should contain atleast one lowercase,uppercase and a number each", "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        try (PrintWriter printWriter = new PrintWriter(new FileWriter("users.txt", true))) {
            printWriter.println(username + "|" + password + "|" + accountHolderName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}


class MainWindow2 extends JFrame {

    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextField descriptionField;
    private JTable journalTable;
    private JRadioButton creditRadioButton;
    private JRadioButton debitRadioButton;

    public void createAndShowGUI(String h, String j) {
        setTitle("BANKING MANAGEMENT SYSTEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLayout(new BorderLayout());

        // Set background image
        ImageIcon backgroundIcon = new ImageIcon("isha3.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        add(backgroundLabel);
        String f = "WELCOME " + j + " !!";

        JLabel welcomeLabel = new JLabel(f);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(55f).deriveFont(Font.BOLD));
        welcomeLabel.setForeground(Color.pink);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(-160, 100, backgroundIcon.getIconWidth(), 100);
        backgroundLabel.add(welcomeLabel);

        JPanel inputPanel = new JPanel(new GridLayout(5, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setForeground(Color.BLACK);
        accountNumberLabel.setFont(accountNumberLabel.getFont().deriveFont(Font.BOLD, 24f));
        accountNumberField = new JTextField();
        accountNumberField.setFont(accountNumberField.getFont().deriveFont(Font.PLAIN, 24f));
        JLabel amountLabel = new JLabel("Transaction Amount:");
        amountLabel.setForeground(Color.BLACK);
        amountLabel.setFont(amountLabel.getFont().deriveFont(Font.BOLD, 24f));
        amountField = new JTextField();
        amountField.setFont(amountField.getFont().deriveFont(Font.PLAIN, 24f));
        JLabel descriptionLabel = new JLabel("Transaction Description:");
        descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(Font.BOLD, 24f));
        descriptionField = new JTextField();
        descriptionField.setFont(descriptionField.getFont().deriveFont(Font.PLAIN, 24f));

        JLabel transactionTypeLabel = new JLabel("Transaction Type:");
        transactionTypeLabel.setForeground(Color.BLACK);
        transactionTypeLabel.setFont(transactionTypeLabel.getFont().deriveFont(Font.BOLD, 24f));
        creditRadioButton = new JRadioButton("Credit");
        debitRadioButton = new JRadioButton("Debit");
        ButtonGroup transactionTypeGroup = new ButtonGroup();
        transactionTypeGroup.add(creditRadioButton);
        transactionTypeGroup.add(debitRadioButton);
        creditRadioButton.setFont(creditRadioButton.getFont().deriveFont(Font.BOLD, 24f));
        debitRadioButton.setFont(debitRadioButton.getFont().deriveFont(Font.BOLD, 24f));

        JButton recordButton = new JButton("Record Transaction");
        recordButton.setFont(recordButton.getFont().deriveFont(Font.BOLD, 24f));
        JButton journalButton = new JButton("Display Journal");
        journalButton.setFont(journalButton.getFont().deriveFont(Font.BOLD, 24f));
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(logoutButton.getFont().deriveFont(Font.BOLD, 24f));

        inputPanel.setOpaque(false);
        inputPanel.setBounds(500, 200, 600, 180);
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(300, 650, 1000, 80);

        accountNumberField.setText(h);
        accountNumberField.setEditable(false);

        inputPanel.add(accountNumberLabel);
        inputPanel.add(accountNumberField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(transactionTypeLabel);
        inputPanel.add(creditRadioButton);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(debitRadioButton);

        buttonPanel.add(recordButton);
        buttonPanel.add(journalButton);
        buttonPanel.add(logoutButton);

        backgroundLabel.add(inputPanel);
        backgroundLabel.add(buttonPanel);

        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(MainWindow2.this, "Are you sure you want to record the transaction?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    recordTransaction();
                }
            }
        });

        journalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayJournal();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform logout actions here
                dispose(); // Close the current window
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void recordTransaction() {
        String accountNumberText = accountNumberField.getText();
        String amountText = amountField.getText();
        String description = descriptionField.getText();

        if (accountNumberText.isEmpty() || amountText.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all the fields.", "Incomplete Fields", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int accountNumber = Integer.parseInt(accountNumberText);
        double amount = Double.parseDouble(amountText);

        if (debitRadioButton.isSelected()) {
            amount = -amount; // Make the transaction amount negative for debit
        }
        else if(creditRadioButton.isSelected()){}
        else {
            JOptionPane.showMessageDialog(this, "Please select the transaction type!", "Type not selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        String monthYear = currentTime.getMonthValue() + "-" + currentTime.getYear();

        Transaction transaction = new Transaction(accountNumber, amount, description, monthYear);
        writeTransactionToJournal(transaction);
        writeTransactionToLedger(transaction);

        JOptionPane.showMessageDialog(this, "Transaction recorded successfully.");

        // Clear the text fields
        amountField.setText("");
        descriptionField.setText("");
    }


    private void displayJournal() {
        int accountNumber = Integer.parseInt(accountNumberField.getText());
        String[] columnNames = {"Amount", "Description", "Account No.", "Month"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        String journalText = readFromFile("journal.txt");

        String[] lines = journalText.split("\n");
        for (String line : lines) {
            String[] data = line.split("\\|");
            int lineAccountNumber = Integer.parseInt(data[2]);
            if (lineAccountNumber == accountNumber) {
                model.addRow(data);
            }
        }

        if (model.getRowCount() > 0) {
            journalTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(journalTable);
            JOptionPane.showMessageDialog(this, scrollPane, "Journal", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No transactions recorded in the journal for the specified account number.", "Journal Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void writeTransactionToLedger(Transaction transaction) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");

            String creditAmount = (transaction.getAmount() > 0) ? df.format(transaction.getAmount()) : "0.00";
            String debitAmount = (transaction.getAmount() < 0) ? df.format(-transaction.getAmount()) : "0.00";

            double previousBalance = getPreviousBalance(transaction.getAccountNumber());
            double presentBalance = previousBalance + transaction.getAmount();

            List<String> ledgerRecords = new ArrayList<>();

            // Read all existing records from ledger.txt
            try (BufferedReader reader = new BufferedReader(new FileReader("ledger.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    ledgerRecords.add(line);
                }
            }

            // Add the new record to the ledger records
            ledgerRecords.add(transaction.getAccountNumber() + "|" +
                    transaction.getDescription() + "|" +
                    creditAmount + "|" +
                    debitAmount + "|" +
                    transaction.getMonth() + "|" +
                    df.format(previousBalance) + "|" +
                    df.format(presentBalance));

            // Sort the ledger records based on account number
            Collections.sort(ledgerRecords, new Comparator<String>() {
                @Override
                public int compare(String record1, String record2) {
                    String[] parts1 = record1.split("\\|");
                    String[] parts2 = record2.split("\\|");
                    String accountNumber1 = parts1[0];
                    String accountNumber2 = parts2[0];
                    return accountNumber1.compareTo(accountNumber2);
                }
            });

            // Write the sorted records back to ledger.txt
            try (PrintWriter ledgerFile = new PrintWriter(new FileWriter("ledger.txt"))) {
                for (String record : ledgerRecords) {
                    ledgerFile.println(record);
                }
                ledgerFile.flush(); // Flush the writer to ensure data is written to the file immediately
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unable to open ledger file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private double getPreviousBalance(int accountNumber) {
        double previousBalance = 10000.0; // Assuming initial previous balance is 10000

        try (BufferedReader reader = new BufferedReader(new FileReader("ledger.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                int currentAccountNumber = Integer.parseInt(data[0]);
                if (currentAccountNumber == accountNumber) {
                    previousBalance = Double.parseDouble(data[6]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to open ledger file.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return previousBalance;
    }

    private void writeTransactionToJournal(Transaction transaction) {
        try {
            FileWriter journalFile = new FileWriter("journal.txt", true);
            BufferedWriter writer = new BufferedWriter(journalFile);

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            writer.write(decimalFormat.format(transaction.getAmount()) + "|");
            writer.write(transaction.getDescription() + "|");
            writer.write(transaction.getAccountNumber() + "|");
            writer.write(transaction.getMonth());
            writer.newLine();

            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to open journal file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to open " + fileName + ".", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return content.toString();
    }
}
class Transaction2 {
    private int accountNumber;
    private double amount;
    private String description;
    private String month;

    public Transaction2(int accountNumber, double amount, String description, String month) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
        this.month = month;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getMonth() {
        return month;
    }
}

class Transaction {
    private int accountNumber;
    private double amount;
    private String description;
    private String month;

    public Transaction(int accountNumber, double amount, String description, String month) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
        this.month = month;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getMonth() {
        return month;
    }
}

class MainWindow extends JFrame {

    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextField descriptionField;
    private JTable ledgerTable;
    private JTable journalTable;

    public void createAndShowGUI() {
        setTitle("BANKING MANAGEMENT SYSTEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLayout(new BorderLayout());

        // Set background image
        ImageIcon backgroundIcon = new ImageIcon("isha4.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        add(backgroundLabel);

        JLabel welcomeLabel = new JLabel("WELCOME ADMIN!!");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(55f).deriveFont(Font.BOLD));
        welcomeLabel.setForeground(Color.RED);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(-160, 100, backgroundIcon.getIconWidth(), 100);
        backgroundLabel.add(welcomeLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Dimension buttonSize = new Dimension(400, 100);
        JButton ledgerButton = new JButton("Display Ledger");
        ledgerButton.setFont(ledgerButton.getFont().deriveFont(Font.BOLD, 30f));
        ledgerButton.setPreferredSize(buttonSize);
        JButton journalButton = new JButton("Display Journal");
        journalButton.setFont(journalButton.getFont().deriveFont(Font.BOLD, 30f));
        journalButton.setPreferredSize(buttonSize);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(logoutButton.getFont().deriveFont(Font.BOLD, 30f));
        logoutButton.setPreferredSize(buttonSize);
        JButton searchButton = new JButton("Search Acc No.");
        searchButton.setFont(searchButton.getFont().deriveFont(Font.BOLD, 30f));
        searchButton.setPreferredSize(buttonSize);

        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(300, 300, 1000, 300);

        buttonPanel.add(ledgerButton);
        buttonPanel.add(journalButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(logoutButton);

        backgroundLabel.add(buttonPanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAccountTransactions();
            }
        });

        ledgerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLedger();
            }
        });

        journalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayJournal();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform logout actions here
                dispose(); // Close the current window
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayLedger() {
        ArrayList<String[]> ledgerData = getLedgerData();
        if (!ledgerData.isEmpty()) {
            // Create a map to store account numbers as keys and corresponding ledger data as values
            Map<Integer, ArrayList<String[]>> accountLedgerDataMap = new HashMap<>();

            for (String[] data : ledgerData) {
                int accountNumber = Integer.parseInt(data[0]);

                if (accountLedgerDataMap.containsKey(accountNumber)) {
                    accountLedgerDataMap.get(accountNumber).add(data);
                } else {
                    ArrayList<String[]> accountLedgerData = new ArrayList<>();
                    accountLedgerData.add(data);
                    accountLedgerDataMap.put(accountNumber, accountLedgerData);
                }
            }

            // Create a tabbed pane to display multiple tables for different accounts
            JTabbedPane tabbedPane = new JTabbedPane();

            for (Map.Entry<Integer, ArrayList<String[]>> entry : accountLedgerDataMap.entrySet()) {
                int accountNumber = entry.getKey();
                ArrayList<String[]> accountLedgerData = entry.getValue();

                String[] columnNames = {"Account Number", "Description", "Credit", "Debit", "Month", "Previous Balance", "Present Balance"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                for (String[] data : accountLedgerData) {
                    model.addRow(data);
                }

                JTable accountTable = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(accountTable);

                tabbedPane.addTab(String.valueOf(accountNumber), scrollPane);
            }

            JOptionPane.showMessageDialog(this, tabbedPane, "Ledger", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No transactions recorded in the ledger.", "Ledger Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private ArrayList<String[]> getLedgerData() {
        ArrayList<String[]> ledgerData = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("ledger.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                ledgerData.add(data);
            }

            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to open ledger file.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Sort the ledger data based on the account number
        Collections.sort(ledgerData, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                int accountNumber1 = Integer.parseInt(o1[0]);
                int accountNumber2 = Integer.parseInt(o2[0]);
                return Integer.compare(accountNumber1, accountNumber2);
            }
        });

        return ledgerData;
    }

    private void displayJournal() {
        String[] columnNames = {"Amount", "Description", "Account No.", "Month"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        String journalText = readFromFile("journal.txt");

        String[] lines = journalText.split("\n");
        for (String line : lines) {
            String[] data = line.split("\\|");
            model.addRow(data);
        }

        journalTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(journalTable);
        JOptionPane.showMessageDialog(this, scrollPane, "Journal",JOptionPane.PLAIN_MESSAGE);
    }

    private String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to open " + fileName + ".", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return content.toString();
    }

    private void searchAccountTransactions() {
        String accountNumberText = JOptionPane.showInputDialog(this, "Enter Account Number:");
        if (accountNumberText != null && !accountNumberText.isEmpty()) {
            int accountNumber;
            try {
                accountNumber = Integer.parseInt(accountNumberText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid account number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isAccountExists(accountNumber)) {
                ArrayList<String[]> ledgerData = getLedgerData();
                ArrayList<String[]> accountTransactions = new ArrayList<>();

                for (String[] data : ledgerData) {
                    int transactionAccountNumber = Integer.parseInt(data[0]);
                    if (transactionAccountNumber == accountNumber) {
                        accountTransactions.add(data);
                    }
                }

                if (!accountTransactions.isEmpty()) {
                    String[] columnNames = {"Account Number", "Description", "Credit", "Debit", "Month", "Previous Balance", "Present Balance"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                    for (String[] data : accountTransactions) {
                        model.addRow(data);
                    }

                    JTable accountTable = new JTable(model);
                    JScrollPane scrollPane = new JScrollPane(accountTable);
                    JOptionPane.showMessageDialog(this, scrollPane, "Transactions for Account " + accountNumber, JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Account exists but no transactions found for the Account No.  " + accountNumber, "No Transactions", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "This account number doesn't exist.", "Account Not Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isAccountExists(int accountNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                int existingAccountNumber = Integer.parseInt(userData[0]);
                if (existingAccountNumber == accountNumber) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to open users file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
