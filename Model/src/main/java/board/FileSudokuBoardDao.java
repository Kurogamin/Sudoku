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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    String fileName;
    File file;
    final String readError = "nofile";

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    @Override
    public SudokuBoard read() throws Exceptions.NoFileException {

        try (FileInputStream fileInput = new FileInputStream(fileName);
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)
        ) {
            SudokuBoard board;
            board = (SudokuBoard) Objects.requireNonNull(objectInput).readObject();
            return board;
        } catch (IOException | ClassNotFoundException e) {
            throw new Exceptions.NoFileException(readError, e);
        }
    }

    @Override
    public boolean write(SudokuBoard board) {

        try (FileOutputStream fileOutput = new FileOutputStream(fileName);
             ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)
        ) {
            Objects.requireNonNull(objectOutput).writeObject(board);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean write2(SudokuBoard board, SudokuBoard board2) {

        try (FileOutputStream fileOutput = new FileOutputStream(fileName);
             ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)
        ) {
            Objects.requireNonNull(objectOutput).writeObject(board);
            Objects.requireNonNull(objectOutput).writeObject(board2);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public SudokuBoard[] read2() throws Exceptions.NoFileException {

        SudokuBoard [] boards = new SudokuBoard[2];
        try (FileInputStream fileInput = new FileInputStream(fileName);
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)
        ) {
            SudokuBoard board;
            SudokuBoard board2;
            board = (SudokuBoard) Objects.requireNonNull(objectInput).readObject();
            board2 = (SudokuBoard) Objects.requireNonNull(objectInput).readObject();
            boards[0] = board;
            boards[1] = board2;
            return boards;
        } catch (IOException | ClassNotFoundException e) {
            throw new Exceptions.NoFileException(readError, e);
        }
    }

    @Override
    public void close() {

    }
}
