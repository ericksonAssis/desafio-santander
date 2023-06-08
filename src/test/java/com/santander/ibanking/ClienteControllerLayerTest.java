package com.santander.ibanking;

import com.santander.ibanking.controller.ClienteController;
import com.santander.ibanking.exception.AccountAlreadyExistsException;
import com.santander.ibanking.model.dto.ClienteDto;
import com.santander.ibanking.model.dto.ConsultaHistoricoRequestDto;
import com.santander.ibanking.model.dto.DepositoRequestDto;
import com.santander.ibanking.model.dto.SaqueRequestDto;
import com.santander.ibanking.service.IClienteServiceImpl;
import com.santander.ibanking.service.response.TransacaoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClienteControllerLayerTest {

    @Mock
    private IClienteServiceImpl clienteService;
    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscaTodosClientes_ReturnsListOfClientes() {
        // Arrange
        List<ClienteDto> clientes = new ArrayList<>();
        clientes.add(new ClienteDto());

        when(clienteService.buscaTodos()).thenReturn(clientes);

        // Act
        ResponseEntity response = clienteController.buscaTodosClientes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientes, response.getBody());
        verify(clienteService, times(1)).buscaTodos();
    }

    @Test
    void adicionaCliente_ValidClienteDto_ReturnsOkResponse() throws AccountAlreadyExistsException {
        // Arrange
        ClienteDto clienteDto = new ClienteDto();

        // Act
        ResponseEntity response = clienteController.adicionaCliente(clienteDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente criado", response.getBody());
        verify(clienteService, times(1)).cadastraCliente(clienteDto);
    }

    @Test
    void saque_ValidSaqueRequestDto_ReturnsOkResponse() {
        // Arrange
        SaqueRequestDto saqueReqDto = new SaqueRequestDto();

        TransacaoResponse expectedResponse = new TransacaoResponse();
        when(clienteService.sacar(saqueReqDto)).thenReturn(expectedResponse);

        // Act
        ResponseEntity response = clienteController.saque(saqueReqDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(clienteService, times(1)).sacar(saqueReqDto);
    }

    @Test
    void deposito_ValidDepositoRequestDto_ReturnsOkResponse() {
        // Arrange
        DepositoRequestDto depositoRequestDto = new DepositoRequestDto();

        TransacaoResponse expectedResponse = new TransacaoResponse();
        when(clienteService.depositar(depositoRequestDto)).thenReturn(expectedResponse);

        // Act
        ResponseEntity response = clienteController.deposito(depositoRequestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(clienteService, times(1)).depositar(depositoRequestDto);
    }

    @Test
    void historicoMovimentacoes_ValidConsultaHistoricoRequestDto_ReturnsOkResponse() {
        // Arrange
        ConsultaHistoricoRequestDto consulta = new ConsultaHistoricoRequestDto();

        TransacaoResponse expectedResponse = new TransacaoResponse();
        when(clienteService.consultaHistorico(consulta)).thenReturn(expectedResponse);

        // Act
        ResponseEntity response = clienteController.historicoMovimentacoes(consulta);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(clienteService, times(1)).consultaHistorico(consulta);
    }
}