package org.example.model.dao.impl;

import org.example.db.DB;
import org.example.db.DbException;
import org.example.model.dao.DepartmentDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection vConn;

    public DepartmentDaoJDBC(Connection vConn){
        this.vConn = vConn;
    }

    @Override
    public void insert(Department vObj) {

        PreparedStatement st = null;

        try{

            st = vConn.prepareStatement(
                    "INSERT INTO department " +
                            "(Name) " +
                            "VALUES " +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1,vObj.getName());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    vObj.setId(rs.getInt(1));
                }
            }else{
                throw new DbException("Erro du..na..da, mano slk da uma olhada nessa xabranga ai");
            }

        }catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Department vObj) {
        PreparedStatement st = null;

        try{

            st = vConn.prepareStatement(
                    "UPDATE department " +
                            "SET Name = ? " +
                            "WHERE Id = ?");

            st.setString(1,vObj.getName());
            st.setInt(2,vObj.getId());

            st.executeUpdate();


        }catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            st = vConn.prepareStatement("SELECT * FROM department WHERE Id = ?");

            st.setInt(1,id);
            rs = st.executeQuery();

            if(rs.next()){
                Department dep = instatiateDepartment(rs);

                return dep;
            }

            return null;

        }catch (SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = vConn.prepareStatement(
                    "select * from department");

            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while(rs.next()) {

                Department vDepartment = instatiateDepartment(rs);

                list.add(vDepartment);

            }

            return list;

        }catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department vDep = new Department();

        vDep.setId(rs.getInt("Id"));
        vDep.setName(rs.getString("Name"));

        return vDep;
    }
}
