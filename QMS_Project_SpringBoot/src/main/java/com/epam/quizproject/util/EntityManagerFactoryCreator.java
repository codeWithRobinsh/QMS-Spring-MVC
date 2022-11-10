package com.epam.quizproject.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryCreator  {
    private static EntityManagerFactoryCreator emfc;

    private  EntityManagerFactoryCreator(){

    }
    public static EntityManagerFactoryCreator getInstance(){
        if(emfc == null)
            emfc = new EntityManagerFactoryCreator();
        return emfc;
    }

    public EntityManagerFactory getEntityManagerFactory(){
        return Persistence.createEntityManagerFactory("my-local-mysql");
    }
}
