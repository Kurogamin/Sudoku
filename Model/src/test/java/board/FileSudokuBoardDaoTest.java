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
import board.Dao;
import board.SudokuBoard;
import board.SudokuBoardDaoFactory;
import board.SudokuField;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    Dao<SudokuBoard> dao;
    FileSudokuBoardDao dao2;
    Dao<SudokuBoard> daoFail;

    {
        try {
            dao = factory.getFileDao("save");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            daoFail = factory.getFileDao("fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void readWriteTest() throws IOException, ClassNotFoundException, Exceptions.NoFileException {
        SudokuBoard board = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();
        dao = factory.getFileDao("save");
        dao2 = factory.getFileDao2("save");

        board.solveGame();
        board2.solveGame();

        SudokuField[][] fields = board.getBoard();
        SudokuField[][] fields2 = board.getBoard();

        assertTrue(dao.write(board));
        assertEquals(dao.read(), board);

        assertTrue(dao2.write2(board, board2));
        assertEquals(dao2.read2()[0], board);
        assertEquals(dao2.read2()[1], board2);


        Dao<SudokuBoard> failDao = factory.getFileDao("fail");
        Dao<SudokuBoard> failClassDao = factory.getFileDao("failCLass");

        assertThrows(Exceptions.NoFileException.class, () -> daoFail.read());
    }


}