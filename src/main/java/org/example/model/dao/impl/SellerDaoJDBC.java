package org.example.model.dao.impl;

import org.example.db.DB;
import org.example.db.DbException;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection vConn;

    public SellerDaoJDBC(Connection vConn){
        this.vConn = vConn;
    }

    @Override
    public void insert(Seller vObj) {
        PreparedStatement st = null;

        try{

            st = vConn.prepareStatement(
                    "INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1,vObj.getName());
            st.setString(2,vObj.getEmail());
            st.setDate(3, new java.sql.Date(vObj.getBirthDate().getTime()));
            st.setDouble(4,vObj.getBaseSalary());
            st.setInt(5,vObj.getDepartment().getId());

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
    public void update(Seller vObj) {

        PreparedStatement st = null;

        try{

            st = vConn.prepareStatement(
                    "UPDATE seller " +
                            "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                            "WHERE Id = ?");

            st.setString(1,vObj.getName());
            st.setString(2,vObj.getEmail());
            st.setDate(3, new java.sql.Date(vObj.getBirthDate().getTime()));
            st.setDouble(4,vObj.getBaseSalary());
            st.setInt(5,vObj.getDepartment().getId());

            st.setInt(6,vObj.getId());

            st.executeUpdate();
        }catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st = null;

        try{

            st = vConn.prepareStatement(
                    "DELETE FROM seller " +
                            "WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();

        }catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = vConn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Department vDepartment = instatiateDepartment(rs);

                Seller vSeller = instatiateSeller(rs, vDepartment);

                return vSeller;
            }
            return null;
        }catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department vDep = new Department();

        vDep.setId(rs.getInt("DepartmentId"));
        vDep.setName(rs.getString("DepName"));

        return vDep;
    }

    private Seller instatiateSeller(ResultSet rs, Department vDep) throws SQLException {
        Seller vSeller = new Seller();

        vSeller.setId(rs.getInt("Id"));
        vSeller.setName(rs.getString("Name"));
        vSeller.setEmail(rs.getString("Email"));
        vSeller.setBaseSalary(rs.getDouble("BaseSalary"));
        vSeller.setBirthDate(rs.getDate("BirthDate"));
        vSeller.setDepartment(vDep);

        return vSeller;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = vConn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();

            while(rs.next()) {

                Department vDepartment = instatiateDepartment(rs);

                Seller vSeller = instatiateSeller(rs, vDepartment);

                list.add(vSeller);

            }

            return list;

        }catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }


    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = vConn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller vSeller = instatiateSeller(rs, dep);

                list.add(vSeller);

            }

            return list;

        }catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
