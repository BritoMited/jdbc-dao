package org.example;

import org.example.db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainSelect {
    public static void main(String[] args) {

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{

            conn = DB.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM department");

            //Integer soma = 0;

            while (resultSet.next()){

                System.out.println(resultSet.getInt("Id") + ", " + resultSet.getString("Name"));

             //   soma += resultSet.getInt("Id");

            }

            //System.out.println(soma);

        }catch (SQLException e) {
            e.printStackTrace();

        }finally{
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
            DB.closeConnection();
        }

    }
}