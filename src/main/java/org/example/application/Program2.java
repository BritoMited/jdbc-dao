package org.example.application;

import org.example.model.dao.DaoFactory;
import org.example.model.dao.DepartmentDao;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("teste 1: department find by id");
        Department vDepartment = departmentDao.findById(1);
        System.out.println(vDepartment.getName());

        System.out.println("teste 2: department find all");
        List<Department> vDepartmentList = departmentDao.findAll();
        vDepartmentList.forEach(System.out::println);

        System.out.println("teste 4: department insert");
        Department vTestInsert = vDepartment;
        vTestInsert.setName("bito");
        departmentDao.insert(vTestInsert);
        System.out.println(vTestInsert.getId());

        System.out.println("teste 5: department update");
        Department vUpdate = departmentDao.findById(4);
        vUpdate.setName("bitoooo");
        departmentDao.update(vUpdate);


        System.out.println("teste 6: department delete");


    }
}
