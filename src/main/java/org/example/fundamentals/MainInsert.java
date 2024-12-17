package org.example.fundamentals;

import org.example.db.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainInsert {
    public static void main(String[] args) {

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        Connection conn = null;
        PreparedStatement statement = null;

        try{

            conn = DB.getConnection();
            statement = conn.prepareStatement(  "INSERT INTO seller " +
                                                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES" +
                                                    "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, "Star Platinum");
            statement.setString(2, "jotaro@gmail.com");
            statement.setDate(3, new java.sql.Date(sfd.parse("05/03/2005").getTime()));
            statement.setDouble(4, 3000.0);
            statement.setInt(5, 4);

            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                ResultSet rs = statement.getGeneratedKeys();

                while(rs.next()){
                    int id = rs.getInt(1);
                    System.out.println("Done! Id = " + id);
                }
            }
            else System.out.println("No rows affected!");

        }catch (SQLException e){
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }

    }
}
