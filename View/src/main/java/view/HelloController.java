/**
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public License
 *     along with this program.
 */

package view;

import board.Difficulty;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


public class HelloController implements Initializable {

    @FXML
    private ChoiceBox<String> cbDifficulty;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private Button mainButton;

    @FXML
    private Label label;

    @FXML
    private Label welcomeText;

    private final Logger logger = Logger.getLogger(HelloController.class);
    private Difficulty difficulty;
    private ResourceBundle bundle;
    Locale locale = new Locale("pl");

    @FXML
    protected void onStartGameClick() throws IOException {
        if (cbDifficulty.getValue() != null) {

            difficulty.setBundle(bundle);
        Stage stage = (Stage) mainButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("Board-Scene.fxml")));
        stage.setUserData(difficulty);
        logger.info("Game started");

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        } else {
            welcomeText.setText(bundle.getString("chooseDifficulty"));
        }
    }

    public void languageBoxPushed() {
        if (languageChoiceBox.getValue() != null) {
            locale = new Locale(languageChoiceBox.getValue().toLowerCase(Locale.ROOT));
            bundle = ResourceBundle.getBundle("bundles.lang", locale);
            logger.info("Language changed to: "
                    + languageChoiceBox.getValue().toLowerCase(Locale.ROOT));
            if (difficulty != null) {
                difficulty.setLocale(locale);
            }
            cbDifficulty.getItems().clear();
            cbDifficulty.getItems().add(bundle.getString("easy"));
            cbDifficulty.getItems().add(bundle.getString("medium"));
            cbDifficulty.getItems().add(bundle.getString("hard"));
        }
    }

    public void choiceBoxButtonPushed() {
        if (cbDifficulty.getValue() != null) {
            mainButton.setText(bundle.getString("startGame"));
            if (locale.getLanguage().equals("en")) {
                difficulty = Difficulty.valueOf(cbDifficulty.getValue());
                welcomeText.setText(bundle.getString("difficulty") + ": "
                        + bundle.getString(cbDifficulty.getValue().toLowerCase(Locale.ROOT))
                        + " (" + difficulty.getFields()
                        + ")");
            } else {
                switch (cbDifficulty.getValue()) {
                    case ("Łatwy") -> {
                        difficulty = Difficulty.valueOf("Easy");
                        welcomeText.setText(bundle.getString("difficulty") + ": "
                                + bundle.getString("easy")
                                + " (" + difficulty.getFields()
                                + ")");
                    }
                    case ("Średni") -> {
                        difficulty = Difficulty.valueOf("Medium");
                        welcomeText.setText(bundle.getString("difficulty") + ": "
                                + bundle.getString("medium")
                                + " (" + difficulty.getFields()
                                + ")");
                    }
                    case ("Trudny") -> {
                        difficulty = Difficulty.valueOf("Hard");
                        welcomeText.setText(bundle.getString("difficulty") + ": "
                                + bundle.getString("hard")
                                + " (" + difficulty.getFields()
                                + ")");
                    }
                    default -> {

                    }
                }
            }
            logger.info("Difficulty changed to: " + cbDifficulty.getValue());
        } else {
            welcomeText.setText("None");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ResourceBundle.getBundle("bundles.lang", locale);
        mainButton.setText(bundle.getString("startGame"));
        cbDifficulty.getItems().add(bundle.getString("easy"));
        cbDifficulty.getItems().add(bundle.getString("medium"));
        cbDifficulty.getItems().add(bundle.getString("hard"));
        languageChoiceBox.getItems().add("pl");
        languageChoiceBox.getItems().add("en");
        welcomeText.setText("");
        cbDifficulty.setOnAction(event -> choiceBoxButtonPushed());
        languageChoiceBox.setOnAction(event -> languageBoxPushed());
    }

    public void load() {

    }
}