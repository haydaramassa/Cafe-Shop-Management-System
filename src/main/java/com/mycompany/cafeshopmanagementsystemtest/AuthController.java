package com.mycompany.cafeshopmanagementsystemtest;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AuthController implements Initializable {

    @FXML
    private TextField fp_answer;

    @FXML
    private TextField fp_username;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceed;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private Hyperlink si_forgotpass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreatBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private String[] questionList = {"What`s your favourite color?", "What`s your favourite food?", "When`s your birthday?"};
    private Alert alert;

    public void loginBtn() {
        
        
        
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);              // Create error alert
            alert.setTitle("Error Message");                       // Set alert title
            alert.setHeaderText(null);                             // No header text
            alert.setContentText("Incorrect Username / Password ");  // Message text
            alert.showAndWait();

        } else {

            String selectData = "SELECT username , password From employee WHERE username=? AND password=? ";

            connect = database.connectDB();
            try {
                prepare = connect.prepareStatement(selectData);            // Prepare SQL statement
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();
                //if successfully login then proceed to another form which is our main form
                if (result.next()) {
                    // To get the used username
                    
                    data.username = si_username.getText();
                    
                    alert = new Alert(Alert.AlertType.INFORMATION);              // Create error alert
                    alert.setTitle("Information Message");                       // Set alert title
                    alert.setHeaderText(null);                             // No header text
                    alert.setContentText("successfully  logged in!");  // Message text
                    alert.showAndWait();
                    
                    // Open the Main Form (mainForm.fxml) in a new window after successful login
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainForm.fxml")); // Load the UI layout from the FXML file
                    Stage stage = new Stage();                                              // Create a new window (Stage)
                    Scene scene = new Scene(root);                                          // Put the loaded UI (root) inside a Scene
                    stage.setTitle("Cafe Shop Management System");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    stage.setScene(scene);                                                  // Attach the Scene to the window
                    stage.show();                                                           // Display the window on screen
                    
                    si_loginBtn.getScene().getWindow().hide();



                } else {             //if not then error message appears

                    alert = new Alert(Alert.AlertType.ERROR);              // Create error alert
                    alert.setTitle("Error Message");                       // Set alert title
                    alert.setHeaderText(null);                             // No header text
                    alert.setContentText("Incorrect Username / Password ");  // Message text
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void regBtn() {
        //  Basic validation: make sure all required fields are filled
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);              // Create error alert
            alert.setTitle("Error Message");                       // Set alert title
            alert.setHeaderText(null);                             // No header text
            alert.setContentText("Please fill all blank fields");  // Message text
            alert.showAndWait();                                   // Show alert and wait

        } else {
            // SQL INSERT statement (placeholders ? will be filled later)
            String regData = "INSERT INTO employee (username, password, question, answer, date) "
                    + "VALUES (?,?,?,?,?)";

            connect = database.connectDB();   // Connect to the database

            try {
                // Check if the username already exists in the database
                String checkUsername = "SELECT username FROM employee WHERE username = '"
                        + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkUsername);          // Prepare the SELECT query
                result = prepare.executeQuery();                              // Run SELECT query

                if (result.next()) {                                     // If a row is returned, username is taken
                    alert = new Alert(Alert.AlertType.ERROR);              // Create error alert
                    alert.setTitle("Error Message");                       // Set alert title
                    alert.setHeaderText(null);                             // No header text
                    alert.setContentText(su_username.getText() + " is already taken");  // Message text
                    alert.showAndWait();
                } else if (su_password.getText().length() < 8) {          //Password rule: must be at least 8 characters

                    alert = new Alert(Alert.AlertType.ERROR);              // Create error alert
                    alert.setTitle("Error Message");                       // Set alert title
                    alert.setHeaderText(null);                             // No header text
                    alert.setContentText("Password must be at least 8 characters");  // Message text
                    alert.showAndWait();

                    // Prepare the INSERT statement and fill in the placeholders (?)
                } else {
                    prepare = connect.prepareStatement(regData);            // Prepare SQL statement
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());

                    //Insert today's date into the 5th placeholder
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    //Execute the INSERT (write new user into the database)
                    prepare.executeUpdate();                             // Execute INSERT in DB

                    //Show success message
                    alert = new Alert(AlertType.INFORMATION);             // Create info alert (success)
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Account registered successfully");
                    alert.showAndWait();

                    // Reset/clear the Sign Up form fields after successful registration
                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    // Slide the side panel back to the Login view after successful registration
                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));
                    slider.setOnFinished((ActionEvent e) -> {

                        side_alreadyHave.setVisible(false);
                        side_CreatBtn.setVisible(true);

                    });
                    slider.play();         // Start animation

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void regQuestionList() {

        List<String> listQ = new ArrayList<>(); //Create an empty normal Java List to store the questions

        for (String data : questionList) { // Loop through every String inside the questionList array
            listQ.add(data); // Add the current question (data) into the listQ
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);// Convert the normal List into an ObservableList (JavaFX needs this for UI updates)
        su_question.setItems(listData); // Put the questions into the ComboBox 

    }

    public void switchForgotPass() {

        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);

        ForgotPassQuestionList();

    }

    public void proceedBtn() {
        if (fp_username.getText().isEmpty()
                || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);              // Create error alert
            alert.setTitle("Error Message");                       // Set alert title
            alert.setHeaderText(null);                             // No header text
            alert.setContentText("Please fill all blank fields");  // Message text
            alert.showAndWait();
        } else {
            String selectData = "SELECT username, question, answer FROM employee WHERE username=? AND question=? AND answer=?";

            connect = database.connectDB();

            try {
                prepare = connect.prepareStatement(selectData);            // Prepare SQL statement
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);              // Create error alert
                    alert.setTitle("Error Message");                       // Set alert title
                    alert.setHeaderText(null);                             // No header text
                    alert.setContentText("Incorrect Information");  // Message text
                    alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void changePassBtn() {

        // 1) Check if new/confirm password fields are empty
        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;

        }

        // 2) Check password length (min 8 chars)
        if (np_newPassword.getText().length() < 8) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password must be at least 8 characters");
            alert.showAndWait();
            return;

        }

        // 3) Check if passwords match
        if (!np_newPassword.getText().equals(np_confirmPassword.getText())) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password does not match");
            alert.showAndWait();
            return;

        }

        // 4) Update password in DB
        String updatePass = "UPDATE employee SET password = ? "
                + "WHERE username = ? AND question = ? AND answer = ?";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(updatePass);
            prepare.setString(1, np_newPassword.getText());
            prepare.setString(2, fp_username.getText());
            prepare.setString(3, (String) fp_question.getSelectionModel().getSelectedItem());
            prepare.setString(4, fp_answer.getText());

            int rows = prepare.executeUpdate();

            if (rows > 0) {

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Password changed successfully!");
                alert.showAndWait();

                // 5) Go back to Login page (hide new pass form, show login form)
                np_newPassForm.setVisible(false);     // hide "new password" pane
                fp_questionForm.setVisible(false);    // hide "forgot question" pane (optional)
                si_loginForm.setVisible(true);        // show login pane

                // Optional: reset fields
                np_newPassword.setText("");
                np_confirmPassword.setText("");
                fp_username.setText("");
                fp_question.getSelectionModel().clearSelection();
                fp_answer.setText("");

                // If you also use the slider panel, return it to login position
                TranslateTransition slider = new TranslateTransition();
                slider.setNode(side_form);
                slider.setToX(0);
                slider.setDuration(Duration.seconds(.5));
                slider.setOnFinished((ActionEvent e) -> {
                    side_alreadyHave.setVisible(false);
                    side_CreatBtn.setVisible(true);
                });
                slider.play();

            } else {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect Information");
                alert.showAndWait();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
    public void ForgotPassQuestionList() {

        List<String> listQ = new ArrayList<>();

        for (String data : questionList) { // Loop through every String inside the questionList array
            listQ.add(data); // Add the current question (data) into the listQ
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);// Convert the normal List into an ObservableList (JavaFX needs this for UI updates)
        fp_question.setItems(listData);
    }
    
    public void backToLOginForm() {
        // Clear all input fields (Forgot Password flow)
        fp_username.setText("");                           // clear username
        fp_answer.setText("");                             // clear answer
        fp_question.getSelectionModel().clearSelection();  // reset combo selection
        
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);

    }

    public void backToQuestionForm() {
        // Clear all input fields (Forgot Password flow)
        fp_username.setText("");                           // clear username
        fp_answer.setText("");                             // clear answer
        fp_question.getSelectionModel().clearSelection();  // reset combo selection

        np_newPassword.setText("");                        // clear new password
        np_confirmPassword.setText("");                    // clear confirm password

        // Switch back to the question form
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }
    
    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition(); // Create a slide animation (move a node on X/Y axis)
        if (event.getSource() == side_CreatBtn) {  // If the "Create Account" button was clicked
            slider.setNode(side_form);            // Node to move (the side panel/form)
            slider.setToX(300);                     // Move to the right (X = 300)
            slider.setDuration(Duration.seconds(.5)); // Animation duration = 0.5 sec
            slider.setOnFinished((ActionEvent e) -> {  // After the animation ends

                side_alreadyHave.setVisible(true);    // Show "Already have account" button
                side_CreatBtn.setVisible(false);       // Hide "Create Account" button

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

                regQuestionList();

            });
            slider.play();                               // Start the animation
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {

                side_alreadyHave.setVisible(false);
                side_CreatBtn.setVisible(true);

                // to make  login form the default mood
                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

            });
            slider.play();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
