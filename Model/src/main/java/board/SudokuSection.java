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
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SudokuSection implements Serializable, Cloneable {

    private Vector<SudokuField> fields = new Vector<>();

    public boolean verify() {
        Vector<Integer> numbers = new Vector<>();
        for (SudokuField field : fields) {
            numbers.add(field.getFieldValue());
        }
        Set<Integer> unique = new HashSet<>(numbers);
        return unique.size() == 9 && !numbers.contains(0);
    }

    public void add(SudokuField field) {
        if (fields.size() < 9) {
            fields.add(field);
        }
    }

    public SudokuField getField(int index) {
        return fields.get(index);
    }

    public int getSize() {
        return fields.size();
    }

    public void set(int index, int number) {
        fields.get(index).setFieldValue(number);
    }

    public void clear() {
        fields.clear();
    }

    public int getValue(int position) {
        return fields.get(position).getFieldValue();
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuSection that = (SudokuSection) o;
        boolean equals = true;
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).getFieldValue() != that.getValue(i)) {
                equals = false;
            }
        }
        return equals;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for (SudokuField field : fields) {
            hash += 11;
            hash *= field.getFieldValue();
        }
        return hash;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuSection that = (SudokuSection) o;

        return new EqualsBuilder().append(fields, that.fields).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(fields).toHashCode();
    }

    @Override
    public String toString() {
        MoreObjects.ToStringHelper result = MoreObjects.toStringHelper(this);
        for (int i = 0;i < fields.size(); i++) {
            String field = "Field " + (i + 1);
            result.add(field, String.valueOf(fields.get(i).getFieldValue()));
        }
        return result.toString();
    }

    @Override
    public SudokuSection clone() throws CloneNotSupportedException {
        SudokuSection clonedSection = new SudokuSection();
        for (int i = 0; i < 9; i++) {
            clonedSection.add(this.fields.get(i).clone());
        }
        return clonedSection;
    }
}
