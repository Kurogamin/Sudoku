package board;

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
public class SudokuRow extends SudokuSection {
    @Override
    public SudokuRow clone() throws CloneNotSupportedException {
        SudokuRow clonedRow = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            clonedRow.add(this.getField(i).clone());
        }
        return clonedRow;
    }
}
