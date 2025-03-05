import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;


class EventManagementLoginPage extends JFrame implements ActionListener {

    private Container container;
    private JLabel titleLabel;
    private JLabel userLabel;
    private JTextField userTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel roleLabel;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JButton signupButton;
    private JButton resetButton;
    private JLabel messageLabel;

    public EventManagementLoginPage() {
        setTitle("USER AUTHENTICATION");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(230, 230, 250));

        titleLabel = new JLabel("Login / Signup");
        titleLabel.setFont(new Font("Cooper Black", Font.BOLD, 32));
        titleLabel.setSize(400, 50);
        titleLabel.setLocation(250, 30);
        titleLabel.setForeground(new Color(75, 0, 130));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(titleLabel);

        userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Cooper Black", Font.PLAIN, 20));
        userLabel.setSize(120, 30);
        userLabel.setLocation(250, 100);
        userLabel.setForeground(new Color(138, 43, 226));
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        container.add(userLabel);

        userTextField = new JTextField();
        userTextField.setFont(new Font("Cooper Black", Font.PLAIN, 15));
        userTextField.setSize(200, 30);
        userTextField.setLocation(400, 100);
        userTextField.setBackground(new Color(240, 230, 255));
        userTextField.setForeground(new Color(75, 0, 130));
        container.add(userTextField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Cooper Black", Font.PLAIN, 20));
        passwordLabel.setSize(120, 30);
        passwordLabel.setLocation(250, 150);
        passwordLabel.setForeground(new Color(138, 43, 226));
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        container.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Cooper Black", Font.PLAIN, 15));
        passwordField.setSize(200, 30);
        passwordField.setLocation(400, 150);
        passwordField.setBackground(new Color(240, 230, 255));
        passwordField.setForeground(new Color(75, 0, 130));
        container.add(passwordField);

        roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Cooper Black", Font.PLAIN, 20));
        roleLabel.setSize(120, 30);
        roleLabel.setLocation(250, 200);
        roleLabel.setForeground(new Color(138, 43, 226));
        roleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        container.add(roleLabel);

        String[] roles = {"User", "Admin"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Cooper Black", Font.PLAIN, 15));
        roleComboBox.setSize(200, 30);
        roleComboBox.setLocation(400, 200);
        roleComboBox.setBackground(new Color(221, 160, 221));
        roleComboBox.setForeground(Color.WHITE);
        container.add(roleComboBox);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Cooper Black", Font.BOLD, 18));
        loginButton.setSize(120, 40);
        loginButton.setLocation(180, 300);
        loginButton.setBackground(new Color(75, 0, 130));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        container.add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Cooper Black", Font.BOLD, 18));
        signupButton.setSize(120, 40);
        signupButton.setLocation(380, 300);
        signupButton.setBackground(new Color(138, 43, 226));
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(this);
        container.add(signupButton);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Cooper Black", Font.BOLD, 18));
        resetButton.setSize(120, 40);
        resetButton.setLocation(580, 300);
        resetButton.setBackground(new Color(186, 85, 211));
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(this);
        container.add(resetButton);

        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Cooper Black", Font.PLAIN, 18));
        messageLabel.setSize(700, 30);
        messageLabel.setLocation(100, 380);
        messageLabel.setForeground(new Color(148, 0, 211));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(messageLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());
        String role = roleComboBox.getSelectedItem().toString();

        if (e.getSource() == loginButton) {
            if (!isUsernameValid(username)) {
                messageLabel.setText("Invalid username! Must be at least 6 alphanumeric characters.");
                messageLabel.setForeground(Color.RED);
            } else if (!isPasswordValid(password)) {
                messageLabel.setText("Invalid password! Must be at least 6 characters, include letters and digits.");
                messageLabel.setForeground(Color.RED);
            } else if (validateLogin(username, password, role)) {
                messageLabel.setText("Login successful!");
                messageLabel.setForeground(new Color(34, 139, 34));
                new HomePage(role); 
                dispose();
            } else {
                messageLabel.setText("Invalid username, password, or role.");
                messageLabel.setForeground(Color.RED);
            }
        } else if (e.getSource() == signupButton) {
            if (!isUsernameValid(username)) {
                messageLabel.setText("Invalid username! Must be at least 6 alphanumeric characters.");
                messageLabel.setForeground(Color.RED);
            } else if (!isPasswordValid(password)) {
                messageLabel.setText("Invalid password! Must be at least 6 characters, include letters and digits.");
                messageLabel.setForeground(Color.RED);
            } 
			else if (signupUser(username, password, role)) {
				// else if (validateLogin(username, password, role)) {
				
                messageLabel.setText("Signup successful! Please login.");
                messageLabel.setForeground(new Color(34, 139, 34));
            } else {
                messageLabel.setText("Signup failed. Username already exists.");
                messageLabel.setForeground(Color.RED);
            }
        } else if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
        }
    }

    private boolean validateLogin(String username, String password, String role) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
        
        String DB_URL = "jdbc:mysql://localhost:3306/eventdb";
        String DB_USER = "root";
        String DB_PASSWORD = "trisha16veer10*";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {
    
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
    
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a row exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Login failed due to SQL error
        }
    }
    

    private boolean signupUser(String username, String password, String role) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }

    private boolean isUsernameValid(String username) {
        String usernameRegex = "^[a-zA-Z0-9]{6,}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static void main(String[] args) {
        new EventManagementLoginPage();
    }
}




// HOMEPAGE

class HomePage extends JFrame implements ActionListener {

    private Container container;
    private JLabel welcomeLabel;
    private JButton aboutUsButton;
    private JButton servicesButton;
    private JButton enquiryButton;
    private JButton budgetButton;
    private JButton entertainmentButton;
    private JButton feedbackButton;
    private JButton contactUsButton;
    private JLabel imageLabel;

    public HomePage(String role) {
        setTitle("Home Page - Planपल");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(230, 230, 250));

        welcomeLabel = new JLabel("Welcome, " + role + "!");
        welcomeLabel.setFont(new Font("Cooper Black", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(75, 0, 130));
        welcomeLabel.setSize(400, 40);
        welcomeLabel.setLocation(50, 20);
        container.add(welcomeLabel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 7, 10, 10));
        topPanel.setBounds(50, 80, 800, 50);
        topPanel.setBackground(new Color(138, 43, 226));

        aboutUsButton = createButton("About Us", new Color(138, 43, 226));
        servicesButton = createButton("Services", new Color(138, 43, 226));
        enquiryButton = createButton("Enquiry Form", new Color(138, 43, 226));
        budgetButton = createButton("Budget Tracker", new Color(138, 43, 226));
        entertainmentButton = createButton("Entertainment", new Color(138, 43, 226));
        feedbackButton = createButton("Feedback", new Color(138, 43, 226));
        contactUsButton = createButton("Contact Us", new Color(138, 43, 226));

        topPanel.add(aboutUsButton);
        topPanel.add(servicesButton);
        topPanel.add(enquiryButton);
        topPanel.add(budgetButton);
        topPanel.add(entertainmentButton);
        topPanel.add(feedbackButton);
        topPanel.add(contactUsButton);

        container.add(topPanel);

        imageLabel = new JLabel();
        imageLabel.setBounds(50, 150, 800, 350);
        ImageIcon imgIcon = new ImageIcon("D:/mpr pic/planpal.jpg");
        Image img = imgIcon.getImage();
        Image scaledImg = img.getScaledInstance(800, 350, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImg));
        container.add(imageLabel);

        aboutUsButton.addActionListener(this);
        servicesButton.addActionListener(this);
        enquiryButton.addActionListener(this);
        budgetButton.addActionListener(this);
        entertainmentButton.addActionListener(this);
        feedbackButton.addActionListener(this);
        contactUsButton.addActionListener(this);

        setVisible(true);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Cooper Black", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(147, 112, 219), 1));
        button.setPreferredSize(new Dimension(500, 100));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aboutUsButton) {
            openAboutUs();
        } else if (e.getSource() == servicesButton) {
            new ServicesFeature();
        } else if (e.getSource() == enquiryButton) {
            new EnquiryForm();
        } else if (e.getSource() == budgetButton) {
            new BudgetTracker();
        } else if (e.getSource() == feedbackButton) {
            openFeedbackSystem();
        } else if (e.getSource() == contactUsButton) {
            new ContactUsForm(this);
        } else if (e.getSource() == entertainmentButton) {
            new WeddingQuizGame(this);
        }
    }

    private void openAboutUs() {
        new AboutUs();
    }

    private void openFeedbackSystem() {
        new FeedbackSystem();
    }

    public static void main(String[] args) {
        new HomePage("User");
    }
}



//GAME

class WeddingQuizGame extends JFrame implements ActionListener {

    private String[] questions = {
        "1. Why are engagement and wedding rings traditionally worn on the fourth finger?",
        "2. What is the traditional color of a wedding dress?",
        "3. Which flower is often associated with weddings?",
        "4. What is the name of the event held before a wedding to celebrate with friends?",
        "5. What is the most common wedding cake flavor?"
    };

    private String[][] options = {
        {"A. to bring good luck", "B. a vein in that finger led directly to the heart", "C. It's easier", "D. has a better fit"},
        {"A. Red", "B. Blue", "C. White", "D. Pink"},
        {"A. Lily", "B. Sunflower", "C. Rose", "D. Tulip"},
        {"A. Engagement Party", "B. Reception", "C. Bridal Shower", "D. Bachelor Party"},
        {"A. Vanilla", "B. Chocolate", "C. Strawberry", "D. Red Velvet"}
    };

    private char[] answers = {'B', 'C', 'C', 'C', 'A'};
    private char guess;
    private int index;
    private int correctGuesses = 0;
    private int totalQuestions = questions.length;

    private JTextArea questionArea;
    private JButton optionAButton, optionBButton, optionCButton, optionDButton;
    private JLabel scoreLabel;
    private JButton startButton;
    private HomePage homePage;

    public WeddingQuizGame(HomePage homePage) {
        this.homePage = homePage;
        setTitle("Wedding Quiz Game");
        setBounds(300, 90, 900, 600); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(230, 230, 250));

        JTextArea welcomeText = new JTextArea("Welcome to the Wedding Quiz Game!\n\nTest your knowledge about wedding traditions!");
        welcomeText.setFont(new Font("Cooper Black", Font.BOLD, 24));
        welcomeText.setBackground(new Color(230, 230, 250));
        welcomeText.setEditable(false);
        welcomeText.setWrapStyleWord(true);
        welcomeText.setLineWrap(true);
        welcomeText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        welcomeText.setOpaque(false);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(230, 230, 250));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(welcomeText);
        centerPanel.add(Box.createVerticalGlue());

        startButton = new JButton("Start Quiz");
        startButton.setFont(new Font("Cooper Black", Font.BOLD, 18));
        startButton.setBackground(new Color(138, 43, 226));
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(e -> startQuiz());
        welcomePanel.add(centerPanel, BorderLayout.CENTER);
        welcomePanel.add(startButton, BorderLayout.SOUTH);

        add(welcomePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void startQuiz() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        questionArea = new JTextArea();
        questionArea.setWrapStyleWord(true);
        questionArea.setLineWrap(true);
        questionArea.setEditable(false);
        questionArea.setFont(new Font("Arial Black", Font.BOLD, 18));
        questionArea.setBackground(new Color(240, 248, 255));
        questionArea.setText(questions[index]);
        questionArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        questionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(questionArea, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBackground(new Color(230, 230, 250));

        optionAButton = createOptionButton("A");
        optionBButton = createOptionButton("B");
        optionCButton = createOptionButton("C");
        optionDButton = createOptionButton("D");

        optionsPanel.add(optionAButton);
        optionsPanel.add(optionBButton);
        optionsPanel.add(optionCButton);
        optionsPanel.add(optionDButton);

        add(optionsPanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score: 0/" + totalQuestions, JLabel.CENTER);
        scoreLabel.setFont(new Font("Cooper Black", Font.BOLD, 20));
        scoreLabel.setBackground(new Color(186, 85, 211));
        scoreLabel.setOpaque(true);
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel, BorderLayout.SOUTH);

        updateQuestion();
        revalidate();
        repaint();
    }

    private JButton createOptionButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial Black", Font.BOLD, 16));
        button.setBackground(new Color(147, 112, 219));
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == optionAButton) guess = 'A';
        else if (e.getSource() == optionBButton) guess = 'B';
        else if (e.getSource() == optionCButton) guess = 'C';
        else if (e.getSource() == optionDButton) guess = 'D';

        checkAnswer();
    }

    private void checkAnswer() {
        if (guess == answers[index]) correctGuesses++;
        index++;
        if (index < totalQuestions) updateQuestion();
        else displayResults();
    }

    private void updateQuestion() {
        questionArea.setText(questions[index]);
        optionAButton.setText(options[index][0]);
        optionBButton.setText(options[index][1]);
        optionCButton.setText(options[index][2]);
        optionDButton.setText(options[index][3]);
        scoreLabel.setText("Score: " + correctGuesses + "/" + totalQuestions);
    }

 private void displayResults() {
    getContentPane().removeAll();
    setLayout(new BorderLayout()); 

    
    JPanel resultsPanel = new JPanel(new GridBagLayout());
    resultsPanel.setBackground(new Color(230, 230, 250));

    
    JLabel resultText = new JLabel("Congratulations! You scored " + correctGuesses + " out of " + totalQuestions + "!");
    resultText.setFont(new Font("Cooper Black", Font.BOLD, 24));
    resultText.setForeground(Color.BLACK); 
    resultText.setBackground(new Color(230, 230, 250));
    resultText.setOpaque(true);
    resultText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    resultText.setHorizontalAlignment(SwingConstants.CENTER); 

   
    ImageIcon originalIcon = new ImageIcon("D:/mpr pic/congrats.jpg"); 
    Image scaledImage = originalIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage)); 
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(20, 20, 20, 20); 

   
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 0.1; 
    resultsPanel.add(resultText, gbc);

    
    gbc.gridy = 1;
    gbc.weighty = 0.9; 
    resultsPanel.add(imageLabel, gbc);

    
    add(resultsPanel, BorderLayout.CENTER);

    
    JLabel finalScoreLabel = new JLabel("Final Score: " + correctGuesses + "/" + totalQuestions, JLabel.CENTER);
    finalScoreLabel.setFont(new Font("Cooper Black", Font.BOLD, 20));
    finalScoreLabel.setBackground(new Color(138, 43, 226));
    finalScoreLabel.setForeground(Color.WHITE);
    finalScoreLabel.setOpaque(true);
    add(finalScoreLabel, BorderLayout.SOUTH);

    revalidate();
    repaint();
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WeddingQuizGame(new HomePage("User")));
    }
}




// Enquiry Form class


 class EnquiryForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField contactField;
    private JTextField subjectField;
    private JComboBox<String> eventTypeComboBox;
    private JTextArea enquiryField;
    private JTextField totalCountField;
    private JButton submitButton;

    public EnquiryForm() {
        setTitle("Enquiry Form");
        setBounds(300, 90, 900, 600); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        Container formContainer = getContentPane();
        formContainer.setLayout(new GridLayout(8, 2, 10, 10));
        formContainer.setBackground(new Color(230, 230, 250));

        formContainer.add(createStyledLabel("Name:"));
        nameField = new JTextField();
        formContainer.add(nameField);

        formContainer.add(createStyledLabel("Email:"));
        emailField = new JTextField();
        formContainer.add(emailField);

        formContainer.add(createStyledLabel("Contact Number:"));
        contactField = new JTextField();
        formContainer.add(contactField);

        formContainer.add(createStyledLabel("Subject:"));
        subjectField = new JTextField();
        formContainer.add(subjectField);

        formContainer.add(createStyledLabel("Event Type:"));
        String[] eventTypes = {"Destination Wedding", "Close-Knit Wedding", "Big Fat Indian Wedding"};
        eventTypeComboBox = new JComboBox<>(eventTypes);
        eventTypeComboBox.setFont(new Font("Arial Black", Font.PLAIN, 14));
        eventTypeComboBox.setBackground(new Color(255, 255, 255));
        eventTypeComboBox.setForeground(new Color(75, 0, 130));
        formContainer.add(eventTypeComboBox);

        formContainer.add(createStyledLabel("Enquiry:"));
        enquiryField = new JTextArea(3, 20);
        enquiryField.setFont(new Font("Arial Black", Font.PLAIN, 14));
        enquiryField.setWrapStyleWord(true);
        enquiryField.setLineWrap(true);
        enquiryField.setBackground(new Color(255, 255, 255));
        enquiryField.setForeground(new Color(75, 0, 130));
        formContainer.add(new JScrollPane(enquiryField));

        formContainer.add(createStyledLabel("Total Person Count:"));
        totalCountField = new JTextField();
        formContainer.add(totalCountField);

        submitButton = new JButton("Submit Enquiry");
        submitButton.setFont(new Font("Arial Black", Font.BOLD, 16));
        submitButton.setBackground(new Color(148, 0, 211));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        formContainer.add(new JLabel());
        formContainer.add(submitButton);

        setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial Black", Font.BOLD, 14));
        label.setForeground(new Color(75, 0, 130));
        return label;
    }
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial Black", Font.PLAIN, 14));
        textField.setForeground(new Color(75, 0, 130));
        textField.setBackground(Color.WHITE);
        return textField;
    }

    private void handleSubmit() {
        String name = nameField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();
        String subject = subjectField.getText();
        String eventType = (String) eventTypeComboBox.getSelectedItem();
        String enquiry = enquiryField.getText();
        String totalCount = totalCountField.getText();

        
        if (!contact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Contact Number must be exactly 10 digits!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        if (insertEnquiry(name, email, contact, subject, eventType, enquiry, totalCount)) {
            JOptionPane.showMessageDialog(this, "Enquiry submitted successfully!\n" +
                    "Name: " + name + "\nEmail: " + email + "\nContact: " + contact +
                    "\nSubject: " + subject + "\nEvent Type: " + eventType +
                    "\nEnquiry: " + enquiry + "\nTotal Person Count: " + totalCount);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to submit enquiry!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean insertEnquiry(String name, String email, String contact, String subject, String eventType, String enquiry, String totalCount) {
        String query = "INSERT INTO enquiries (name, email, contact, subject, event_type, enquiry, total_count) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, contact);
            stmt.setString(4, subject);
            stmt.setString(5, eventType);
            stmt.setString(6, enquiry);
            stmt.setString(7, totalCount);
            stmt.executeUpdate();
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EnquiryForm::new);
    }
}

	
	
//feedback 

 class FeedbackSystem extends JFrame {
    private JTextArea feedbackArea;
    private JTextField nameField;
    private JPanel feedbackPanel;
    private JPanel displayPanel;
    private ArrayList<String[]> feedbackList;

   
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/eventdb"; 
    private final String user = "root"; 
    private final String password = "trisha16veer10*"; 

    public FeedbackSystem() {
        setTitle("Feedback Section");
        setSize(900, 600); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        feedbackList = new ArrayList<>();

        // JDBC 
        connectToDatabase();
        loadFeedbackFromDatabase(); 

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(230, 230, 250));
        JLabel headerLabel = new JLabel("Share Your Feedback");
        headerLabel.setFont(new Font("Cooper Black", Font.BOLD, 24));
        headerLabel.setForeground(new Color(75, 0, 130));
        headerPanel.add(headerLabel);
        container.add(headerPanel, BorderLayout.NORTH);

        
        feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new GridBagLayout());
        feedbackPanel.setBackground(new Color(245, 240, 255));
        feedbackPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        
        JLabel nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setFont(new Font("Cooper Black", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0;
        gbc.gridy = 0;
        feedbackPanel.add(nameLabel, gbc);

        nameField = new JTextField(30); 
        nameField.setFont(new Font("Cooper Black", Font.PLAIN, 14));
        nameField.setForeground(new Color(75, 0, 130));
        nameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255)));
        gbc.gridx = 0;
        gbc.gridy = 1;
        feedbackPanel.add(nameField, gbc);

        
        JLabel feedbackLabel = new JLabel("Your Feedback:");
        feedbackLabel.setFont(new Font("Cooper Black", Font.PLAIN, 14));
        feedbackLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0;
        gbc.gridy = 2;
        feedbackPanel.add(feedbackLabel, gbc);

        feedbackArea = new JTextArea(5, 30); 
        feedbackArea.setFont(new Font("Cooper Black", Font.PLAIN, 14));
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setForeground(new Color(75, 0, 130));
        feedbackArea.setBackground(new Color(230, 230, 250));
        feedbackArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255)));

        JScrollPane feedbackScrollPane = new JScrollPane(feedbackArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        feedbackPanel.add(feedbackScrollPane, gbc);

       
        JButton submitButton = new JButton("Submit Feedback");
        submitButton.setBackground(new Color(148, 0, 211));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Cooper Black", Font.BOLD, 16));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        feedbackPanel.add(submitButton, gbc);

        container.add(feedbackPanel, BorderLayout.CENTER);

        
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(0, 1, 10, 10));
        displayPanel.setBackground(new Color(245, 245, 245));
        displayPanel.setBorder(BorderFactory.createTitledBorder("Feedback Display"));
        JScrollPane displayScroll = new JScrollPane(displayPanel);
        displayScroll.setPreferredSize(new Dimension(860, 300)); // Adjust scroll pane size
        container.add(displayScroll, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFeedbackFromDatabase() {
        String query = "SELECT name, feedback FROM feedback";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("name");
                String feedback = rs.getString("feedback");
                feedbackList.add(new String[]{name, feedback});
            }
            displayFeedback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void submitFeedback() {
        String name = nameField.getText().trim();
        String feedback = feedbackArea.getText().trim();
        if (!name.isEmpty() && !feedback.isEmpty()) {
            feedbackList.add(new String[]{name, feedback});
            saveFeedbackToDatabase(name, feedback); 
            nameField.setText("");
            feedbackArea.setText("");
            displayFeedback();
        } else {
            JOptionPane.showMessageDialog(this, "Both name and feedback cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFeedbackToDatabase(String name, String feedback) {
        String insertQuery = "INSERT INTO feedback (name, feedback) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, name);
            pstmt.setString(2, feedback);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save feedback.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayFeedback() {
        
        if (displayPanel == null) {
            displayPanel = new JPanel();
            displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        }
    
        displayPanel.removeAll();
    
        for (String[] entry : feedbackList) {
            JPanel feedbackBox = new JPanel();
            feedbackBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255)));
            feedbackBox.setBackground(new Color(230, 230, 250));
            feedbackBox.setLayout(new BorderLayout());
    
            JLabel feedbackLabel = new JLabel("<html><b>" + entry[0] + ":</b> " + entry[1] + "</html>");
            feedbackLabel.setFont(new Font("Arial Black", Font.ITALIC, 14));
            feedbackLabel.setForeground(new Color(75, 0, 130));
            feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
            feedbackBox.add(feedbackLabel, BorderLayout.CENTER);
            displayPanel.add(feedbackBox);
        }
    
        displayPanel.revalidate();
        displayPanel.repaint();
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FeedbackSystem::new);
    }
}



// contact us 

class ContactUsForm extends JFrame {
    private JFrame parentFrame;

    public ContactUsForm(JFrame parent) {
        this.parentFrame = parent;
        setTitle("Contact Us - Planपल");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);  
        setLocationRelativeTo(parent);
        setResizable(false);

        getContentPane().setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(230, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Get in Touch With Us");
        titleLabel.setFont(new Font("Cooper Black", Font.BOLD, 24));
        titleLabel.setForeground(new Color(128, 0, 128));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 20, 10);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(titleLabel, gbc);

       
        JLabel nameLabel = new JLabel("Your Name:");
        nameLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        
        JLabel emailLabel = new JLabel("Your Email:");
        emailLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailField, gbc);

        
        JLabel contactLabel = new JLabel("Your Contact:");
        contactLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(contactLabel, gbc);

        JTextField contactField = new JTextField(20);
        contactField.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(contactField, gbc);

       
        JLabel enquiryLabel = new JLabel("Your Enquiry:");
        enquiryLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(enquiryLabel, gbc);

        JTextArea enquiryArea = new JTextArea(5, 20);
        enquiryArea.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        enquiryArea.setLineWrap(true);
        enquiryArea.setWrapStyleWord(true);
        JScrollPane enquiryScrollPane = new JScrollPane(enquiryArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(enquiryScrollPane, gbc);

        
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Cooper Black", Font.BOLD, 16));
        submitButton.setBackground(new Color(148, 0, 211));
        submitButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 5; 
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 20, 0);
        formPanel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String contact = contactField.getText();
                String enquiry = enquiryArea.getText();

                
                if (!contact.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(ContactUsForm.this, "Contact number must be exactly 10 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || enquiry.isEmpty()) {
                    JOptionPane.showMessageDialog(ContactUsForm.this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    
                    if (insertData(name, email, contact, enquiry)) {
                        JOptionPane.showMessageDialog(ContactUsForm.this, "Thank you for contacting us, " + name + "!", "Message Sent", JOptionPane.INFORMATION_MESSAGE);
                        nameField.setText("");
                        emailField.setText("");
                        contactField.setText("");
                        enquiryArea.setText("");
                    } else {
                        JOptionPane.showMessageDialog(ContactUsForm.this, "Failed to send your message. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JPanel contactDetailsPanel = new JPanel();
        contactDetailsPanel.setLayout(new BoxLayout(contactDetailsPanel, BoxLayout.Y_AXIS));
        contactDetailsPanel.setBackground(new Color(240, 240, 255)); 
        contactDetailsPanel.setPreferredSize(new Dimension(320, getHeight())); 

        JLabel contactInfoTitle = new JLabel("Contact Information");
        contactInfoTitle.setFont(new Font("Cooper Black", Font.BOLD, 20));
        contactInfoTitle.setForeground(new Color(128, 0, 128));
        contactInfoTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        contactInfoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactDetailsPanel.add(contactInfoTitle);

        JLabel companyNameLabel = new JLabel("PlanPal Event Management Pvt. Ltd.");
        companyNameLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        companyNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        companyNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactDetailsPanel.add(companyNameLabel);

        JLabel companyPhoneLabel = new JLabel("Phone: +91 9876543210");
        companyPhoneLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        companyPhoneLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        companyPhoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactDetailsPanel.add(companyPhoneLabel);

        JLabel companyEmailLabel = new JLabel("Email: contact@planpal.com");
        companyEmailLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        companyEmailLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        companyEmailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactDetailsPanel.add(companyEmailLabel);

        JLabel companyAddressLabel = new JLabel("<html>Address:<br/>123 Kaledonia Andheri,<br/>Mumbai, MH 400001</html>");
        companyAddressLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        companyAddressLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        companyAddressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        companyAddressLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the address
        contactDetailsPanel.add(companyAddressLabel);

        getContentPane().add(contactDetailsPanel, BorderLayout.WEST); 
        getContentPane().add(formPanel, BorderLayout.CENTER); 

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                parentFrame.setVisible(true);
            }
        });

        setVisible(true);
    }

    private boolean insertData(String name, String email, String contact, String enquiry) {
        String url = "jdbc:mysql://localhost:3306/eventdb"; // Update with your database URL
        String user = "root"; // Update with your database username
        String password = "trisha16veer10*"; // Update with your database password

        String query = "INSERT INTO contact_us (name, email, contact, enquiry) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, contact);
            pstmt.setString(4, enquiry);
            pstmt.executeUpdate();
            return true; // Successfully inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Insertion failed
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactUsForm(new JFrame()));
    }
}

//budget tracker


class BudgetTracker extends JFrame {
    private JTextField budgetField;
    private JTextField expenseField;
    private JTextArea expenseListArea;
    private double budget;
    private double totalExpenses;

    public BudgetTracker() {
        setTitle("Event Budget Tracker");
        setSize(900, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(new Color(230, 230, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0; 

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel budgetLabel = new JLabel("Set Budget (INR):");
        budgetLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        inputPanel.add(budgetLabel, gbc);

        budgetField = new JTextField(15);
        budgetField.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(budgetField, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel expenseLabel = new JLabel("Add Expense (INR):");
        expenseLabel.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        inputPanel.add(expenseLabel, gbc);

        expenseField = new JTextField(15);
        expenseField.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(expenseField, gbc);

        
        JButton addButton = new JButton("Add Expense");
        addButton.setBackground(new Color(75, 0, 130));
        addButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(addButton, gbc);

        JButton setBudgetButton = new JButton("Set Budget");
        setBudgetButton.setBackground(new Color(186, 85, 211));
        setBudgetButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        inputPanel.add(setBudgetButton, gbc);

        
        inputPanel.setPreferredSize(new Dimension(800, 150));
        
        add(inputPanel, BorderLayout.NORTH);

        
        expenseListArea = new JTextArea();
        expenseListArea.setEditable(false);
        expenseListArea.setBackground(new Color(240, 240, 255));
        expenseListArea.setFont(new Font("Cooper Black", Font.PLAIN, 12));
        add(new JScrollPane(expenseListArea), BorderLayout.CENTER);

        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        setBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBudget();
            }
        });

        setVisible(true);
    }

    private void setBudget() {
        try {
            budget = Double.parseDouble(budgetField.getText());
            totalExpenses = 0;
            expenseListArea.setText("Budget set to: ₹" + budget + "\n");

           
            if (saveBudgetToDatabase(budget)) {
                JOptionPane.showMessageDialog(this, "Budget saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save budget.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid budget input!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addExpense() {
        try {
            double expense = Double.parseDouble(expenseField.getText());
            totalExpenses += expense;
            expenseListArea.append("Expense added: ₹" + expense + "\n");

            // Save expense to database
            if (saveExpenseToDatabase(expense)) {
                JOptionPane.showMessageDialog(this, "Expense saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save expense.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            checkBudget();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid expense input!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkBudget() {
        if (totalExpenses > budget) {
            JOptionPane.showMessageDialog(this, "Warning: Budget exceeded! Total Expenses: ₹" + totalExpenses, "Budget Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean saveBudgetToDatabase(double budget) {
        String url = "jdbc:mysql://localhost:3306/eventdb"; 
        String user = "root"; 
        String password = "trisha16veer10*"; 

        String query = "INSERT INTO budget_tracker (budget, expense) VALUES (?, 0)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, budget);
            pstmt.executeUpdate();
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }

    private boolean saveExpenseToDatabase(double expense) {
        String url = "jdbc:mysql://localhost:3306/eventdb"; 
        String user = "root"; 
        String password = "trisha16veer10*"; 

        String query = "INSERT INTO budget_tracker (budget, expense) VALUES (0, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, expense);
            pstmt.executeUpdate();
            return true; // Successfully inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Insertion failed
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BudgetTracker());
    }
}





//about us


class AboutUs extends JFrame {
    private Container container;
    private JLabel titleLabel, aboutUsLabel, servicesLabel, whyChooseUsLabel;
    private JTextArea aboutUsTextArea, servicesTextArea, whyChooseUsTextArea;

    public AboutUs() {
        setTitle("About Us - PlanPal");
        setBounds(300, 90, 900, 650);  // Updated container size
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        container = getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(240, 240, 255)); 

        // Title Label
        titleLabel = new JLabel("About Us - PlanPal");
        titleLabel.setFont(new Font("Cooper Black", Font.BOLD, 28));
        titleLabel.setForeground(new Color(75, 0, 130)); 
        titleLabel.setBounds(250, 20, 400, 40);
        container.add(titleLabel);

        // About Us Section
        aboutUsLabel = new JLabel("About Us:");
        aboutUsLabel.setFont(new Font("Cooper Black", Font.BOLD, 22));
        aboutUsLabel.setForeground(new Color(75, 0, 130)); 
        aboutUsLabel.setBounds(50, 80, 200, 30);
        container.add(aboutUsLabel);

        aboutUsTextArea = new JTextArea(
            "At PlanPal, we are passionate about crafting unforgettable experiences through exceptional event management services.\n" +
            "From intimate gatherings to grand celebrations, we specialize in making every event a true reflection of your vision.\n" +
            "With years of industry expertise, our dedicated team brings creativity, precision, and meticulous attention to detail, ensuring flawless execution.\n" +
            "Our mission is simple: to transform your dreams into reality with unparalleled professionalism and innovative solutions.\n" +
            "We believe that every event is unique, and we take pride in delivering tailored solutions that exceed expectations.\n" +
            "With a wide range of services and a commitment to customer satisfaction, we strive to make your special day memorable and hassle-free."
        );
        aboutUsTextArea.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        aboutUsTextArea.setForeground(new Color(75, 0, 130));
        aboutUsTextArea.setBackground(new Color(240, 240, 255));
        aboutUsTextArea.setLineWrap(true);
        aboutUsTextArea.setWrapStyleWord(true);
        aboutUsTextArea.setEditable(false);

        // Scroll Pane for About Us Text Area
        JScrollPane aboutUsScrollPane = new JScrollPane(aboutUsTextArea);
        aboutUsScrollPane.setBounds(50, 120, 800, 120);
        container.add(aboutUsScrollPane);

        // Services Section
        servicesLabel = new JLabel("Our Services Include:");
        servicesLabel.setFont(new Font("Cooper Black", Font.BOLD, 22));
        servicesLabel.setForeground(new Color(75, 0, 130)); 
        servicesLabel.setBounds(50, 260, 300, 30);
        container.add(servicesLabel);

        servicesTextArea = new JTextArea(
            " - Wedding Planning\n" +
            " - Social Gatherings\n" +
            " - Themed Parties\n" +
            " - Corporate Events\n" +
            " - Destination Weddings\n" +
            " - Event Decoration\n" +
            " - Event Coordination and more."
        );
        servicesTextArea.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        servicesTextArea.setForeground(new Color(75, 0, 130));
        servicesTextArea.setBackground(new Color(240, 240, 255));
        servicesTextArea.setLineWrap(true);
        servicesTextArea.setWrapStyleWord(true);
        servicesTextArea.setEditable(false);

        // Scroll Pane for Services Text Area
        JScrollPane servicesScrollPane = new JScrollPane(servicesTextArea);
        servicesScrollPane.setBounds(50, 300, 800, 80);
        container.add(servicesScrollPane);

        // Why Choose Us Section
        whyChooseUsLabel = new JLabel("WHY CHOOSE US:");
        whyChooseUsLabel.setFont(new Font("Cooper Black", Font.BOLD, 22));
        whyChooseUsLabel.setForeground(new Color(75, 0, 130)); 
        whyChooseUsLabel.setBounds(50, 400, 300, 30);
        container.add(whyChooseUsLabel);

        whyChooseUsTextArea = new JTextArea(
            " - Professional Team: Our experienced and passionate team ensures flawless execution.\n" +
            " - Personalized Service: Customized event plans tailored to your vision.\n" +
            " - Proven Track Record: Hundreds of successful events under our belt.\n" +
            " - Creative Solutions: Innovative ideas to make your event stand out.\n" +
            " - End-to-End Management: Full-service event management for a stress-free experience.\n" +
            " - Transparency: We ensure transparency with our clients at every step of the event planning process.\n" +
            " - Cost-Effective: We work with clients to help them meet their event budget expectations.\n" +
            " - Client-Centric Approach: We prioritize your needs and preferences to create a memorable experience."
        );
        whyChooseUsTextArea.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        whyChooseUsTextArea.setForeground(new Color(75, 0, 130));
        whyChooseUsTextArea.setBackground(new Color(240, 240, 255)); 
        whyChooseUsTextArea.setLineWrap(true);
        whyChooseUsTextArea.setWrapStyleWord(true);
        whyChooseUsTextArea.setEditable(false);

        // Scroll Pane for Why Choose Us Text Area
        JScrollPane whyChooseUsScrollPane = new JScrollPane(whyChooseUsTextArea);
        whyChooseUsScrollPane.setBounds(50, 440, 800, 140); 
        container.add(whyChooseUsScrollPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AboutUs();
    }
}



//services


class ServicesFeature extends JFrame implements ActionListener {

    private JLabel titleLabel, selectServiceLabel, descriptionLabel;
    private JComboBox<String> servicesDropdown;
    private JTextArea descriptionArea;
    private JPanel imagePanel; 

    public ServicesFeature() {
        setTitle("Services - PlanPal");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 900, 600);

       
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); 
        mainPanel.setBackground(new Color(240, 240, 255)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); 

        // Title
        titleLabel = new JLabel("Our Services");
        titleLabel.setFont(new Font("Cooper Black", Font.BOLD, 28));
        titleLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER; 
        mainPanel.add(titleLabel, gbc);

        // Service selection
        selectServiceLabel = new JLabel("Select a Service:");
        selectServiceLabel.setFont(new Font("Cooper Black", Font.BOLD, 18));
        selectServiceLabel.setForeground(new Color(75, 0, 130));
        gbc.gridwidth = 1; 
        gbc.gridy = 1; 
        gbc.anchor = GridBagConstraints.CENTER; 
        mainPanel.add(selectServiceLabel, gbc);

        String[] services = {"Weddings", "Social Gatherings", "Themed Parties"};
        servicesDropdown = new JComboBox<>(services);
        servicesDropdown.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        servicesDropdown.setBackground(new Color(138, 43, 226));
        servicesDropdown.setForeground(Color.WHITE);
        gbc.gridx = 1; 
        mainPanel.add(servicesDropdown, gbc);

        
        descriptionLabel = new JLabel("Service Description:");
        descriptionLabel.setFont(new Font("Cooper Black", Font.BOLD, 18));
        descriptionLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0; 
        gbc.gridy = 2; 
        gbc.gridwidth = 2; 
        mainPanel.add(descriptionLabel, gbc);

        descriptionArea = new JTextArea();
        descriptionArea.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        descriptionArea.setForeground(new Color(75, 0, 130));
        descriptionArea.setBackground(new Color(230, 230, 250));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(descriptionArea); 
        textScrollPane.setPreferredSize(new Dimension(700, 100)); 
        gbc.gridy = 3; 
        mainPanel.add(textScrollPane, gbc); 

        
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 2, 10, 10)); 
        imagePanel.setPreferredSize(new Dimension(700, 200)); 
        imagePanel.setBackground(new Color(240, 240, 255)); 
        gbc.gridy = 4; 
        mainPanel.add(imagePanel, gbc); 

        
        scrollPane.setViewportView(mainPanel);
        add(scrollPane); 

        servicesDropdown.addActionListener(this); 

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == servicesDropdown) {
            String selectedService = (String) servicesDropdown.getSelectedItem();
            switch (selectedService) {
                case "Weddings":
                    descriptionArea.setText("Whether it’s a destination wedding, a close-knit gathering, or a grand celebration, we bring your dreams to life." +
                            " Our wedding planning services include:\n" +
                            "Personalized Planning: We work closely with the couple to understand their preferences, from venue selection and décor to the overall theme.\n" +
                            "Destination Weddings: We specialize in arranging destination weddings, taking care of travel, accommodations, venue, local traditions, and legal formalities.\n" +
                            "Vendor Coordination: We manage relationships with trusted vendors, including photographers, caterers, florists, and entertainment providers, ensuring the highest quality for every aspect of the event.\n" +
                            "End-to-End Execution: Every element, from seating arrangements to catering services, is executed flawlessly so that our clients can enjoy their special day stress-free.");
                    updateImages("D:/mpr pic/wedding 1.jpg", "D:/mpr pic/wedding 2.jpg");
                    break;
                case "Social Gatherings":
                    descriptionArea.setText("Social Gatherings: From family reunions to community events, we plan and execute seamless social gatherings that leave a lasting impression on your guests.");
                    updateImages("D:/mpr pic/social gathering 1.jpg", "D:/mpr pic/social gathering 2.jpg");
                    break;
                case "Themed Parties":
                    descriptionArea.setText("Themed Parties: Whether you want a classic, elegant theme or a fun and whimsical one, our team can design the perfect party atmosphere tailored to your preferences.");
                    updateImages("D:/mpr pic/party 1.jpg", "D:/mpr pic/party 2.jpg");
                    break;
            }
        }
    }

    private void updateImages(String imagePath1, String imagePath2) {
        imagePanel.removeAll(); // Clear previous images
        imagePanel.add(new JLabel(new ImageIcon(imagePath1))); // Add first image
        imagePanel.add(new JLabel(new ImageIcon(imagePath2))); // Add second image
        imagePanel.revalidate(); // Refresh the image panel
        imagePanel.repaint(); // Repaint to show updated images
    }

    public static void main(String[] args) {
        new ServicesFeature();
    }
}
