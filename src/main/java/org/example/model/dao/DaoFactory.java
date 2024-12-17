package org.example.model.dao;

import org.example.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDAo(){
        return  new SellerDaoJDBC();
    }

}
