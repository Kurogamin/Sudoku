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
import board.SudokuField;
import board.SudokuSection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuSectionTest {

    @Test
    void set() {
        SudokuSection section = new SudokuSection();
        SudokuField field = new SudokuField(3);
        section.add(field);
        section.set(0, 5);
        assertEquals(5, section.getValue(0));
    }

    @Test
    void testEquals() {
        SudokuSection section = new SudokuSection();
        SudokuField field5 = new SudokuField(5);
        SudokuSection section3 = null;
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(3);
            section.add(field);
        }
        SudokuSection section2 = new SudokuSection();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(4);
            section2.add(field);
        }
        assertFalse(section.equals(section2));
        assertFalse(section.equals(section3));
        assertFalse(section.equals(field5));
        for (int i = 0; i < 9; i++) {
            section2.set(i, 3);
        }
        assertTrue(section.equals(section2));
        assertEquals(section.hashCode(), section2.hashCode());
        assertTrue(section.equals(section));
    }

    @Test
    void testHashCode() {
        SudokuSection section = new SudokuSection();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(3);
            section.add(field);
        }
        SudokuSection section2 = new SudokuSection();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(4);
            section2.add(field);
        }
        assertNotEquals(section.hashCode(), section2.hashCode());
        for (int i = 0; i < 9; i++) {
            section2.set(i, 3);
        }
        assertEquals(section.hashCode(), section2.hashCode());
        List<Integer> numbers = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            numbers.add(i + 1);
        }
        Collections.shuffle(numbers);
        for (int i = 0; i < 9; i++) {
            section.set(i, numbers.get(i));
        }
        Collections.shuffle(numbers);
        for (int i = 0; i < 9; i++) {
            section2.set(i, numbers.get(i));
        }
        assertNotEquals(section.hashCode(), section2.hashCode());
    }

    @Test
    void testToString() {
        SudokuSection section = new SudokuSection();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(3);
            section.add(field);
        }
        assertEquals("SudokuSection{Field 1=3, Field 2=3, Field 3=3, Field 4=3, Field 5=3, Field 6=3, Field 7=3, Field 8=3, Field 9=3}"
                     , section.toString());
    }
    @Test
    void testAdd() {
        SudokuSection section = new SudokuSection();
        for (int i = 0; i < 9; i++) {
            section.add(new SudokuField(i));
        }
        section.add(new SudokuField(2));
        assertEquals(9, section.getSize());
    }
}