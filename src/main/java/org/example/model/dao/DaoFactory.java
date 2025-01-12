package org.example.model.dao;

import org.example.db.DB;
import org.example.model.dao.impl.DepartmentDaoJDBC;
import org.example.model.dao.impl.SellerDaoJDBC;
import org.example.model.entities.Department;

import java.util.List;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }

}


// dao factory cria uma conexao com o jdbc
// neste caso foi criado uma conexao para a implementacao do seller dao,
// para que caso mude o banco de dados, só sera mudado a implemetação