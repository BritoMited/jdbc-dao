package org.example.application;

import org.example.model.dao.DaoFactory;
import org.example.model.dao.SellerDao;
import org.example.model.entities.Department;
import org.example.model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("teste 1: seller find by id");
        Seller vSeller = sellerDao.findById(3);
        System.out.println(vSeller);

        System.out.println("teste 2: seller find by department");
        Department department = new Department(4,null);
        List<Seller> list = sellerDao.findByDepartment(department);
        list.forEach(System.out::println);

        System.out.println("teste 3: seller find all");
        List<Seller> listall = sellerDao.findAll();
        listall.forEach(System.out::println);

        System.out.println("teste 4: seller insert");
      //  Seller newSeller = new Seller(null, "bito", "tobipvp@mgail.com", new Date(),3000.00, department);
       // sellerDao.insert(newSeller);
       // System.out.println("toma " + newSeller.getId());

        System.out.println("teste 5: update");
        vSeller = sellerDao.findById(4);
        vSeller.setName("GON FRIECCSS");
        sellerDao.update(vSeller);

        System.out.println("teste 6: delete");
        sellerDao.deleteById(11);

    }
}
