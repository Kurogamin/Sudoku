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
import board.SudokuBoard;
import board.SudokuField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void SudokuField() {
        SudokuField field = new SudokuField();
        assertEquals(0, field.getFieldValue());
    }

    @Test
    void testEquals() {
        SudokuField field = new SudokuField();
        SudokuField field2 = new SudokuField(1);
        SudokuBoard board = new SudokuBoard();
        assertFalse(field.equals(field2));
        assertFalse(field.equals(null));
        assertFalse(field.equals(board));
        field2.setFieldValue(0);
        assertTrue(field.equals(field2));
        assertEquals(field.hashCode(), field2.hashCode());
        assertTrue(field.equals(field));
    }

    @Test
    void testHashCode() {
        SudokuField field = new SudokuField();
        SudokuField field2 = new SudokuField(1);
        assertNotEquals(field.hashCode(), field2.hashCode());
        field2.setFieldValue(0);
        assertEquals(field.hashCode(), field2.hashCode());
    }

    @Test
    void testToString() {
        SudokuField field = new SudokuField();
        assertEquals("SudokuField{value=0}", field.toString());
    }
}