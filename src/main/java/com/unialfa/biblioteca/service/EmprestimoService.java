package com.unialfa.biblioteca.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Regras de cálculo de prazo de empréstimo de livros.
 * Prazo padrão: 14 dias a partir da data de retirada.
 */
@Service
public class EmprestimoService {

    public static final int PRAZO_PADRAO_DIAS = 14;

    /**
     * Calcula a data de devolução a partir da data de retirada.
     *
     * @param dataRetirada data em que o livro foi retirado
     * @return data limite para devolução
     * @throws IllegalArgumentException se a data for nula
     */
    public LocalDate calcularDataDevolucao(LocalDate dataRetirada) {
        return calcularDataDevolucao(dataRetirada, PRAZO_PADRAO_DIAS);
    }

    /**
     * Calcula a data de devolução com prazo customizado em dias.
     *
     * @param dataRetirada data em que o livro foi retirado
     * @param prazoDias    quantidade de dias do empréstimo (deve ser &gt; 0)
     * @return data limite para devolução
     */
    public LocalDate calcularDataDevolucao(LocalDate dataRetirada, int prazoDias) {
        if (dataRetirada == null) {
            throw new IllegalArgumentException("Data de retirada é obrigatória");
        }
        if (prazoDias <= 0) {
            throw new IllegalArgumentException("Prazo em dias deve ser maior que zero");
        }
        return dataRetirada.plusDays(prazoDias);
    }

    /**
     * Indica se o empréstimo está atrasado na data de referência.
     */
    public boolean estaAtrasado(LocalDate dataDevolucaoPrevista, LocalDate dataReferencia) {
        if (dataDevolucaoPrevista == null || dataReferencia == null) {
            throw new IllegalArgumentException("Datas de devolução e referência são obrigatórias");
        }
        return dataReferencia.isAfter(dataDevolucaoPrevista);
    }
}
