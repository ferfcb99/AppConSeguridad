package com.data.dataWarehouse.service;

import com.data.dataWarehouse.constants.DummyData;
import com.data.dataWarehouse.dto.EmpleadosDto;
import com.data.dataWarehouse.entity.Empleados;
import com.data.dataWarehouse.repository.EmpleadoRepsotory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private EmpleadoRepsotory empleadoRepository;

    public EmpleadoService(EmpleadoRepsotory empleadoRepsotory){
        this.empleadoRepository = empleadoRepsotory;
    }

    public List<EmpleadosDto> findAll(){

        //return this.empleadoRepository.findAll();
        DummyData.initializeData();
        return DummyData.empleadosDtoList;
    }
    public EmpleadosDto findById(Long id){

        //return this.empleadoRepository.findById(id).get();
        DummyData.initializeData();
        return DummyData.empleadosDtoList.get(id.intValue() -1);
    }

}
