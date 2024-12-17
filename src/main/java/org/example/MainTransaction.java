package org.example;

import org.example.db.DB;
import org.example.db.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MainTransaction {
    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;

        try{
            conn = DB.getConnection();

            conn.setAutoCommit(false); // bloco de operação que apenas será feita se tudo estiver correto, pois caso de erro no meio
            // do caminho, ele nao sera executado na metade, ou é tudo, ou é nada

            st = conn.createStatement();

            int rowsAffected1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
            int rowsAffected2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            conn.commit();

            System.out.println("rowsAffected1 "+rowsAffected1);
            System.out.println("rowsAffected2 "+rowsAffected2);

        }catch (SQLException e){

            try {

                conn.rollback();
                throw new DbException("Transaction Rolled Back! Caused by: " + e.getMessage());

            } catch (SQLException ex) {
                throw new DbException("Error trying to rollback! Caused by: "+ ex.getMessage());
            }

        }finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }

    }
}
