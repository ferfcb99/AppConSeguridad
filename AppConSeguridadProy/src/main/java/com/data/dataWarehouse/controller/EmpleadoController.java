package com.data.dataWarehouse.controller;

import com.data.dataWarehouse.dto.EmpleadosDto;
import com.data.dataWarehouse.service.EmpleadoService;
import com.data.dataWarehouse.entity.Empleados;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService){
        this.empleadoService = empleadoService;
    }

    @GetMapping(path = "/empleados")
    public List<EmpleadosDto> findAll(){
        return this.empleadoService.findAll();
    }
    @GetMapping(path = "/empleados{id}")
    public EmpleadosDto findById(@PathVariable Long id){
        return this.empleadoService.findById(id);
    }
}
