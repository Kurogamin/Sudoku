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

import board.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.apache.log4j.Logger;

public class BoardSceneController implements Initializable {

    final String SAVE_FILE = "Save";

    @FXML private AnchorPane ap;

    @FXML
    private Button button;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button loadButton;

    @FXML
    private Button checkButton;

    @FXML
    private Button saveButton;

    private Difficulty difficulty;
    private BoardRepository repository = new BoardRepository(new SudokuBoard());
    private SudokuBoard clonedBoard;
    private SudokuBoard originalBoard;
    private Label position;
    private Label[][] labels = new Label[9][9];
    private int positionX;
    private int positionY;
    private Logger logger = Logger.getLogger(BoardSceneController.class);

    private ResourceBundle bundle;
    Locale locale = new Locale("pl");

    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    FileSudokuBoardDao dao = factory.getFileDao2(SAVE_FILE);
    StringConverter<Number> converter = new NumberStringConverter();

    public void startGame() throws CloneNotSupportedException {
        Window window = button.getScene().getWindow();
        button.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getText().matches("\\d*") && Integer.parseInt(key.getText()) != 0) {
                if (position != null) {
                    if (checkButton.getText() != bundle.getString("check")) {
                        checkButton.setText(bundle.getString("check"));
                    }
                    position.setFont(Font.font(35));
                    position.setText(key.getText());
                    clonedBoard.setNumber(positionX, positionY, Integer.parseInt(key.getText()));
                }
            }
        });
        button.getScene().getStylesheets().add("Scene.css");
        difficulty = (Difficulty) button.getScene().getWindow().getUserData();
        locale = difficulty.getLocale();
        bundle = difficulty.getBundle();
        saveButton.setText(bundle.getString("save"));
        loadButton.setText(bundle.getString("load"));
        checkButton.setText(bundle.getString("check"));
        originalBoard = clonedBoard.clone();
        clonedBoard.removeFields(difficulty);
        button.setDisable(true);
        button.setVisible(false);
        loadButton.setVisible(true);
        saveButton.setVisible(true);
        checkButton.setVisible(true);
        checkButton.setOnAction(event -> checkBoard());
        gridPane.setDisable(false);
        gridPane.setVisible(true);
        makeLabels();

    }

    private void checkBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Integer.parseInt(labels[i][j].getText()) != originalBoard.getNumber(i, j)) {
                    labels[i][j].setStyle("-fx-background-color: #b75f5f");
                }
            }
        }
        String good = String.valueOf(clonedBoard.checkBoard());
        checkButton.setText(bundle.getString(good));
        if (good.equals("true")) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    labels[i][j].setStyle("-fx-background-color: #acd75c");
                    labels[i][j].setOnMouseClicked(null);
                    position = null;
                }
            }
        }
    }

    public void makeLabels() {
        clonedBoard.setProperties();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Label label = new Label(String.valueOf(clonedBoard.getNumber(i, j)));/*,
                        new Rectangle(window.getHeight() / 9 - 15, window.getWidth() / 9 - 15));*/
                int finalI = i;
                int finalJ = j;
                if (clonedBoard.getNumber(i, j) == 0) {
                    label.setFont(Font.font(0));
                    label.setOnMouseClicked(mouseEvent -> {
                        if (position != null) {
                            position.setStyle("");
                        }
                        position = label;
                        positionX = finalI;
                        positionY = finalJ;
                        position.setStyle("-fx-background-color: #6E5959;");
                    });
                } else {
                    label.setFont(Font.font(35));
                }
                gridPane.add(label, j, i);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setWrapText(true);

                labels[i][j] = label;
                /*label.setOnMouseClicked(mouseEvent -> {
                    if (position != null) {
                        position.setStyle("");
                    }
                    position = label;
                    positionX = finalI;
                    positionY = finalJ;
                    position.setStyle("-fx-background-color: #6E5959;");
                });*/
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
                GridPane.setFillWidth(label, true);
                GridPane.setFillHeight(label, true);
                Bindings.bindBidirectional(label.textProperty(),
                        clonedBoard.getFieldProperty(i, j), converter);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repository.board.solveGame();
        clonedBoard = repository.createInstance();
    }

    public void load() {

        try {
            logger.info(clonedBoard = dao.read2()[0]);
            logger.info("Game loaded");
        } catch (Exceptions.NoFileException e) {
            logger.info(bundle.getString(e.getMessage()));
        }

        try {
            logger.info(originalBoard = dao.read2()[1]);
            logger.info("Game loaded");
        } catch (Exceptions.NoFileException e) {
            logger.info(bundle.getString(e.getMessage()));
        }
        Node node = gridPane.getChildren().get(0);
        gridPane.getChildren().clear();
        gridPane.getChildren().add(0, node);
        makeLabels();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Bindings.bindBidirectional(labels[i][j].textProperty(),
                        clonedBoard.getFieldProperty(i, j), converter);
            }
        }
    }

    public void save() throws Exceptions.NoFileException {
        logger.info("Game saved");
        System.out.println(dao.write2(clonedBoard, originalBoard));
    }
}
