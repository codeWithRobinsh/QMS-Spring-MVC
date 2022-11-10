package com.epam.quizproject.util;

import org.modelmapper.ModelMapper;

public class ModelMapperClass {
    private ModelMapperClass(){

    }
    public static ModelMapper modelMapper;
    public static ModelMapper getMapper(){
        if(modelMapper == null)
            modelMapper = new ModelMapper();
        return modelMapper;
    }
}
