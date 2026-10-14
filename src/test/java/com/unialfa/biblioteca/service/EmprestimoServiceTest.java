package com.unialfa.biblioteca.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmprestimoServiceTest {

    private EmprestimoService service;

    @BeforeEach
    void setUp() {
        service = new EmprestimoService();
    }

    @Test
    @DisplayName("Deve calcular devolução com prazo padrão de 14 dias")
    void deveCalcularDevolucaoComPrazoPadrao() {
        LocalDate retirada = LocalDate.of(2026, 10, 1);
        LocalDate devolucao = service.calcularDataDevolucao(retirada);
        assertEquals(LocalDate.of(2026, 10, 15), devolucao);
    }

    @Test
    @DisplayName("Deve calcular devolução com prazo customizado")
    void deveCalcularDevolucaoComPrazoCustomizado() {
        LocalDate retirada = LocalDate.of(2026, 10, 1);
        LocalDate devolucao = service.calcularDataDevolucao(retirada, 7);
        assertEquals(LocalDate.of(2026, 10, 8), devolucao);
    }

    @Test
    @DisplayName("Deve rejeitar data de retirada nula")
    void deveRejeitarDataNula() {
        assertThrows(IllegalArgumentException.class, () -> service.calcularDataDevolucao(null));
    }

    @Test
    @DisplayName("Deve rejeitar prazo inválido")
    void deveRejeitarPrazoInvalido() {
        LocalDate retirada = LocalDate.of(2026, 10, 1);
        assertThrows(IllegalArgumentException.class, () -> service.calcularDataDevolucao(retirada, 0));
        assertThrows(IllegalArgumentException.class, () -> service.calcularDataDevolucao(retirada, -3));
    }

    @Test
    @DisplayName("Deve identificar empréstimo atrasado")
    void deveIdentificarAtraso() {
        LocalDate prevista = LocalDate.of(2026, 10, 10);
        assertTrue(service.estaAtrasado(prevista, LocalDate.of(2026, 10, 11)));
        assertFalse(service.estaAtrasado(prevista, LocalDate.of(2026, 10, 10)));
        assertFalse(service.estaAtrasado(prevista, LocalDate.of(2026, 10, 9)));
    }
}
