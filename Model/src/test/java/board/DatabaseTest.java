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
package board;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void databaseTests() {
        System.out.println(System.getProperty("user.dir"));
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        SudokuBoard board2 = new SudokuBoard();
        board2.solveGame();
        Database testBase = new Database("TestBase");
        testBase.setDatabaseName("TestBase");
        testBase.createTable("board1");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBase.modify("board1", String.valueOf(i) + String.valueOf(j), board.getNumber(i, j));
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2.setNumber(i, j, testBase.select("board1", i, j));
            }
        }

        assertEquals(board, board2);
    }
}