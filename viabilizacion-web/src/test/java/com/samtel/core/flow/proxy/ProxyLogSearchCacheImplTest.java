package com.samtel.core.flow.proxy;

import com.samtel.core.flow.ValidateRequest;
import com.samtel.core.response.ResponseFlow;
import com.samtel.domain.solicitud.Cliente;
import com.samtel.ports.primary.log.LogService;
import com.samtel.utils.impl.GenerateUniqueIdImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ProxyLogSearchCacheImplTest {

    private ValidateRequest validateRequest;
    
    @Mock
    private LogService logService; 
    
    private GenerateUniqueIdImpl generateUniqueId;
    @Mock
    private ValidateRequest next;
   
    private Cliente cliente;
   
    private String requestId;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        validateRequest = new ProxyLogSearchCache(next, logService);
        generateUniqueId = new GenerateUniqueIdImpl();
        cliente= Cliente.builder().actividad("Actividad")
                .anoNacimiento("anoNacimiento")
                .apellidos("apellidos")
                .celular("celular")
                .correoElectronico("correoElectronio")
                .direccion("direccion")
                .ingresos("ingresos")
                .nombres("nombres")
                .numeroIdentificacion("numer0")
                .plazo("plazo")
                .telefono("telefono")
                .tipoIdentificacion("tipoIdentificacion")
                .valorSolicitado("valorsolicitado")
                .build();

    }

    @Test
    public void testSearchCacheImplSuccess(){
    	requestId = generateUniqueId.generateUniqueIdStr(Long.valueOf(12));
        ResponseFlow result = validateRequest.process(cliente, requestId).orElse(ResponseFlow.DENIED);
        Assert.assertNotNull(result);
    }
}
