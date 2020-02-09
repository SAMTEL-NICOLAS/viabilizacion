package com.samtel.core.flow.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.samtel.core.flow.ValidateRequest;
import com.samtel.core.response.ResponseFlow;
import com.samtel.domain.solicitud.Cliente;
import com.samtel.ports.secondary.solicitud.ValidarCiudadService;

import lombok.Getter;
import lombok.Setter;

@Component("validateCity")
public class ValidateCityImpl implements ValidateRequest {

    private static final Logger log= LoggerFactory.getLogger(ValidateCityImpl.class);
    
    private ValidateRequest validateRequest;
    
    private ValidarCiudadService validarCiudadService;
    
    @Getter @Setter
    private Cliente cliente;
    
    @Autowired
    public ValidateCityImpl(@Qualifier("proxyLogSearchVigia")ValidateRequest validateRequest, ValidarCiudadService validarCiudadService) {
        this.validateRequest = validateRequest;
        this.validarCiudadService = validarCiudadService;
    }

    @Override
    public Optional<ResponseFlow> process(Cliente cliente, String requestId) {
        setCliente(cliente);
        String validaCiudad = validarCiudadService.validarCodigoCiudad(getCliente().getCiudad());
        log.info("Respuesta al validar ciudad {} ", validaCiudad );
        if("true".equalsIgnoreCase(validaCiudad)) {
        	return validateRequest.process(getCliente(), requestId);
        }else {
        	return Optional.of(ResponseFlow.INVALID_CITY) ;
        }
    }
    
}