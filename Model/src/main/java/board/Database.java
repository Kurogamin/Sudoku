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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database implements AutoCloseable {

    private static final String url = "jdbc:sqlite:Bases/";
    private static final String endUrl = ".db";
    private String databaseName = "";

    public Database(String name) {
        databaseName = name;
        this.connect(name);
    }

    public void setDatabaseName(String name) {
        databaseName = name;
    }

    public boolean connect(String name) {

        try(Connection connection = DriverManager.getConnection(url + databaseName + endUrl)) {
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean createTable(String tableName) {

        tableName.replaceAll(" ","");
        tableName = " " + tableName + " ";

        String sql = "CREATE TABLE IF NOT EXISTS" + tableName + "(\n"
                + " id VARCHAR(2) PRIMARY KEY,\n"
                + " cyfra INT\n"
                + ")";

        try(Connection connection = DriverManager.getConnection(url + databaseName + endUrl)) {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void insert(String tableName, String id, int value) {

        tableName.replaceAll(" ","");
        tableName = " " + tableName + " ";
        String sql = "INSERT INTO" + tableName + "(id,cyfra) VALUES(?,?)";
        try(Connection connection = DriverManager.getConnection(url + databaseName + endUrl)) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setInt(2, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modify(String tableName, String id, int value) {

        tableName.replaceAll(" ","");
        tableName = " " + tableName + " ";

        String sql = "UPDATE" + tableName + " SET cyfra = ? WHERE id = ?";
        try(Connection connection = DriverManager.getConnection(url + databaseName + endUrl)) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, value);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer select(String tableName, int x, int y) {

        tableName.replaceAll(" ", "");
        tableName = " " + tableName + " ";
        String stringId = String.valueOf(x) + String.valueOf(y);
        stringId = "'" + stringId + "'";

        String sql = "SELECT cyfra FROM" + tableName + "WHERE id=" + stringId;

        try(Connection connection = DriverManager.getConnection(url + databaseName + endUrl)) {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs.getInt("cyfra");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int nextSave() throws SQLException {

        String sql = "SELECT count(name) FROM sqlite_schema WHERE type='table'";
        try(Connection connection = DriverManager.getConnection(url + databaseName + endUrl)) {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs.getInt("count(name)") / 2 + 1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }


    }

    @Override
    public void close() throws Exception {
    }
}
