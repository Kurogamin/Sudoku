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

import board.Authors;
import board.BoardRepository;
import board.Database;
import board.Difficulty;
import board.Exceptions;
import board.FileSudokuBoardDao;
import board.SudokuBoard;
import board.SudokuBoardDaoFactory;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.apache.log4j.Logger;

public class BoardSceneController implements Initializable {

    final String saveFile = "Sav22e";

    @FXML private AnchorPane ap;

    @FXML
    private Button button;

    @FXML
    private Label authorsLabel;

    @FXML
    private ChoiceBox savesChoiceBox;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button loadButton;

    @FXML
    private Button checkButton;

    @FXML
    private Button saveButton;

    private boolean savedYet = false;
    private Difficulty difficulty;
    private BoardRepository repository = new BoardRepository(new SudokuBoard());
    private SudokuBoard clonedBoard;
    private SudokuBoard originalBoard;
    private Label position;
    private Label[][] labels = new Label[9][9];
    private int positionX;
    private int positionY;
    private Logger logger = Logger.getLogger(BoardSceneController.class);
    private Database base = new Database("sudokuBoards");
    private String save = String.valueOf(base.nextSave());
    private String boardName = "board";
    private String originalBoardName = "originalBoard";

    private ResourceBundle bundle;
    Locale locale = new Locale("pl");

    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    FileSudokuBoardDao dao = factory.getFileDao2(saveFile);
    StringConverter<Number> converter = new NumberStringConverter();

    public BoardSceneController() throws SQLException {
    }

    public String makeBoardName() {
        return boardName + save;
    }

    public String makeOriginalBoardName() {
        return originalBoardName + save;
    }

    public void startGame() throws CloneNotSupportedException {
        savesChoiceBox.setOnAction(event -> {
            save = String.valueOf(savesChoiceBox.getValue());
        });
        logger.info(System.getProperty("user.dir"));
        authorsLabel.setVisible(false);
        authorsLabel.setDisable(true);
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
        savesChoiceBox.setVisible(true);
        savesChoiceBox.setDisable(false);
        savesChoiceBox.setStyle(null);
        for (int i = 1; i < Integer.parseInt(save); i++) {
            savesChoiceBox.getItems().add(i);
        }

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
        Authors authors = new Authors();
        String authorsString = authors.getString("author1") + "\n" + authors.getString("author2");
        authorsLabel.setText(authorsString);
        authorsLabel.setAlignment(Pos.CENTER);
        authorsLabel.setTextAlignment(TextAlignment.CENTER);
        savesChoiceBox.setVisible(false);
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
        } catch (Exceptions.NoFileException ignored) {
            logger.info("lol");
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

    public void saveToDb() {
        String tempId = "";
        if (!savedYet) {
            base.createTable(makeBoardName());
            base.createTable(makeOriginalBoardName());
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    tempId = String.valueOf(i) + String.valueOf(j);
                    base.insert(makeBoardName(), tempId, clonedBoard.getNumber(i, j));
                    base.insert(makeOriginalBoardName(), tempId, originalBoard.getNumber(i, j));
                }
            }
            savesChoiceBox.getItems().add(save);
            savedYet = true;
        } else {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    tempId = String.valueOf(i) + String.valueOf(j);
                    base.modify(makeBoardName(), tempId, clonedBoard.getNumber(i, j));
                    base.modify(makeOriginalBoardName(), tempId, originalBoard.getNumber(i, j));
                }
            }
        }
        logger.info("Game saved");

    }

    public void loadFromDb() {
        savedYet = true;
        if (savesChoiceBox.getValue() == null) {
            return;
        }
        logger.info(clonedBoard.getRow(0));
        save = String.valueOf(savesChoiceBox.getValue());
        int number = 0;
        int originalNumber = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                number = base.select(makeBoardName(), i, j);
                originalNumber = base.select(makeOriginalBoardName(), i, j);
                clonedBoard.setNumber(i, j, number);
                originalBoard.setNumber(i, j, originalNumber);
            }
        }
        Node node = gridPane.getChildren().get(0);
        gridPane.getChildren().clear();
        gridPane.getChildren().add(0, node);
        makeLabels();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (clonedBoard.getNumber(i, j) != originalBoard.getNumber(i, j)) {
                    int finalI = i;
                    int finalJ = j;
                    labels[i][j].setOnMouseClicked(mouseEvent -> {
                        if (position != null) {
                            position.setStyle("");
                        }
                        position = labels[finalI][finalJ];
                        positionX = finalI;
                        positionY = finalJ;
                        position.setStyle("-fx-background-color: #6E5959;");
                    });
                }
                Bindings.bindBidirectional(labels[i][j].textProperty(),
                        clonedBoard.getFieldProperty(i, j), converter);
            }
        }
        logger.info(clonedBoard.getRow(0));
    }
}

