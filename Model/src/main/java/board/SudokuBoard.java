package board; /**
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


import com.google.common.base.MoreObjects;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.beans.property.IntegerProperty;



public class SudokuBoard implements Serializable, Cloneable {
    private final SudokuSolver solver;
    private SudokuField [][] board = new SudokuField[9][9];
    //private SudokuRow [] rows = new SudokuRow [9];
    //private SudokuColumn [] columns = new SudokuColumn [9];
    //private SudokuBox [] boxes = new SudokuBox [9];
    private List<SudokuRow> rows = Arrays.asList(new SudokuRow[9]);
    private List<SudokuColumn> columns = Arrays.asList(new SudokuColumn[9]);
    private List<SudokuBox> boxes = Arrays.asList(new SudokuBox[9]);
    private PropertyChangeSupport support = new PropertyChangeSupport(this);


    void makeField() {
        for (int i = 0;i < 9; i++) {
            rows.set(i, new SudokuRow());
            columns.set(i, new SudokuColumn());
            boxes.set(i, new SudokuBox());
            for (int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        initializeSections();
    }

    void makeField(int [][] board) {
        for (int i = 0;i < 9; i++) {
            rows.set(i, new SudokuRow());
            columns.set(i, new SudokuColumn());
            boxes.set(i, new SudokuBox());
            for (int j = 0;j < 9; j++) {
                this.board[i][j] = new SudokuField(board[i][j]);
            }
        }
        initializeSections();
    }

    void makeField(SudokuField [][] board) {
        for (int i = 0;i < 9; i++) {
            rows.set(i, new SudokuRow());
            columns.set(i, new SudokuColumn());
            boxes.set(i, new SudokuBox());
            System.arraycopy(board[i], 0, this.board[i], 0, 9);
        }
        initializeSections();
    }

    private void initializeSections() {
        for (int i = 0;i < 9; i++) {
            rows.get(i).clear();
            columns.get(i).clear();
            boxes.get(i).clear();
        }
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                SudokuField field = new SudokuField(board[i][j].getFieldValue());
                rows.get(i).add(field);
                columns.get(j).add(field);
                boxes.get(i / 3 + j / 3 * 3).add(field);
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public SudokuBoard(int [][] board) {
        makeField(board);
        solver = new BacktrackingSudokuSolver();
    }

    public SudokuBoard(SudokuField [][] board) {
        makeField(board);
        solver = new BacktrackingSudokuSolver();
    }

    public SudokuBoard(SudokuField [][] board, SudokuSolver solver) {
        makeField(board);
        this.solver = solver;
    }

    public SudokuBoard(int [][] board, SudokuSolver solver) {
        makeField(board);
        this.solver = solver;
    }

    public SudokuBoard() {
        makeField();
        solver = new BacktrackingSudokuSolver();
    }

    public void solveGame() {
        /*List<Integer> numbers = new ArrayList<>(9);
        for(int i = 0; i < 9; i++) {
            numbers.add(i + 1);
        }
        Collections.shuffle(numbers);
        for(int i = 0; i < 9; i++) {
            board[0][i].setFieldValue(numbers.get(i));
        }*/
        solver.solve(board);
        initializeSections();
    }

    public boolean checkBoard() {
        initializeSections();
        for (int i = 0;i < 9; i++) {
            if (!rows.get(i).verify() || !columns.get(i).verify() || !boxes.get(i).verify()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        MoreObjects.ToStringHelper result = MoreObjects.toStringHelper(this);
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                String field = "Field " + (i * 9 + j + 1);
                result.add(field, String.valueOf(board[i][j].getFieldValue()));
            }
        }
        return result.toString();
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        boolean equals = true;
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                if (board[i][j].getFieldValue() != that.getNumber(i, j)) {
                    equals = false;
                }
                if (rows.get(i).getValue(j) != that.getRow(i).getValue(j)
                    || columns.get(i).getValue(j) != that.getColumn(i).getValue(j)
                    || boxes.get(i).getValue(j) != that.getBox(i, j).getValue(j)) {
                    equals = false;
                }
            }
        }
        return equals;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(board).toHashCode();
    }

    /*@Override
    public int hashCode() {
        int hash = 1;
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                hash += 11;
                hash *= board[i][j].getFieldValue();
                hash *= rows.get(i).getValue(j);
                hash *= columns.get(i).getValue(j);
                hash *= boxes.get(i).getValue(j);
            }
        }
        return hash;
    }*/

    public SudokuField [][] getBoard() {
        SudokuField [][] board = new SudokuField [9][9];
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(this.board[i], 0, board[i], 0, board.length);
        }
        return board;
    }

    public int getNumber(int row, int col) {
        return board[row][col].getFieldValue();
    }

    public void setNumber(int row, int col, int number) {
        SudokuField [][] oldBoard = new SudokuField[9][9];
        for (SudokuField[] sudokuFields : this.board) {
            System.arraycopy(sudokuFields, 0, sudokuFields, 0, board.length);
        }
        board[row][col].setFieldValue(number);
        support.firePropertyChange("board", oldBoard, this.board);
        initializeSections();
    }

    public void clearBoard() {
        for (SudokuField[] sudokuFields : board) {
            for (int j = 0; j < board.length; j++) {
                sudokuFields[j].setFieldValue(0);
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j].getFieldValue());
                System.out.print(" ");
                if ((j + 1) % 3 == 0) {
                    System.out.print(" ");
                }
            }
            if ((i + 1) % 3 == 0) {
                System.out.print("\n");
            }
            System.out.print("\n");
        }
    }

    public void setBoard(SudokuField [][] board) {
        SudokuField [][] board2 = new SudokuField [9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2[i][j] = new SudokuField(board[i][j].getFieldValue());
            }
        }
        this.board = board2;
        initializeSections();
    }

    public void setField(int row, int col, SudokuField field) {
        this.board[row][col] = field;
    }

    public SudokuRow getRow(int y) {
        //initializeSections();
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(rows.get(y).getValue(i));
            row.add(field);
        }
        return row;
    }

    public SudokuColumn getColumn(int x) {
        //initializeSections();
        SudokuColumn column = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(columns.get(x).getValue(i));
            column.add(field);
        }
        return column;
    }

    public SudokuBox getBox(int x, int y) {
        //initializeSections();
        SudokuBox box = new SudokuBox();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(boxes.get((int)Math.floor(x / 3) * 3
                    + (int)Math.floor(y / 3)).getValue(i));
            box.add(field);
        }
        return box;
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard clonedBoard = new SudokuBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                clonedBoard.setField(i, j, (SudokuField) board[i][j].clone());
            }
        }
        return clonedBoard;
    }

    public void removeFields(Difficulty difficulty) {
        int fieldsToRemove = difficulty.getFields();
        SudokuField field = this.board[0][0];
        int row = 0;
        int col = 0;
        while (fieldsToRemove > 0) {
            row = ThreadLocalRandom.current().nextInt(0, 8 + 1);
            col = ThreadLocalRandom.current().nextInt(0, 8 + 1);
            field = this.board[row][col];
            if (field.getFieldValue() != 0) {
                field.setFieldValue(0);
                fieldsToRemove--;
            }
        }
    }

    public IntegerProperty getFieldProperty(int row, int col) {
        return this.board[row][col].getProperty();
    }

    public void setProperties() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setProperty(board[i][j].getFieldValue());
            }
        }
    }
}

