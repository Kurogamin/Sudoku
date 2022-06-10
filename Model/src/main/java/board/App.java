package board;

import java.io.FileNotFoundException;

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
public class App {
    public static void main(String [] args) throws CloneNotSupportedException, FileNotFoundException, Exceptions.NoFileException {
        SudokuBoard board = new SudokuBoard();
        System.out.println("Czysta tablica: ");
        board.printBoard();
        board.solveGame();
        System.out.println("Wypełniona tablica");
        board.printBoard();
        board.clearBoard();
        System.out.println("Czysta tablica2: ");
        board.printBoard();
        board.solveGame();
        System.out.println("Wypełniona tablica2: ");
        board.printBoard();
        SudokuField [][] newBoard = new SudokuField [9][9];
        BoardListener observer = new BoardListener();
        board.addPropertyChangeListener(observer);
        int oldNumber00 = board.getNumber(0,0);
        board.setNumber(0,0,0);
        board.setNumber(0,0,oldNumber00);
        board.setNumber(0,0,0);
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> dao = factory.getFileDao("Save");
        dao.write(board);
        SudokuBoard savedBoard = dao.read();
        board.printBoard();
        System.out.println(savedBoard.equals(board));
        SudokuBoard newBoard2 = board.clone();
        System.out.println(newBoard2.equals(board));
        BoardRepository repo = new BoardRepository(board);
        SudokuBoard board33 = repo.createInstance();
        board33.removeFields(Difficulty.Easy);
    }
}

