package com.data.dataWarehouse.service;
import com.data.dataWarehouse.constants.DummyData;
import com.data.dataWarehouse.dto.SucursalesDto;
import com.data.dataWarehouse.entity.*;
import com.data.dataWarehouse.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository){
        this.sucursalRepository = sucursalRepository;
    }

    public List<SucursalesDto> findAll(){

//        return this.sucursalRepository.findAll().stream()
//                        .map(elem ->
//                                new SucursalesDto(
//                                        elem.getIdSucursal(),
//                                        elem.getNombre(),
//                                        elem.getCiudad(),
//                                        elem.getEstado(),
//                                        elem.getFechaApertura()))
//                .toList();

        DummyData.initializeData();
        return DummyData.sucursalesDtoList;
    }
    public SucursalesDto findById(Long id){

        //return this.sucursalRepository.findById(id).get();
        DummyData.initializeData();
        return DummyData.sucursalesDtoList.get(id.intValue() - 1);
    }
}
