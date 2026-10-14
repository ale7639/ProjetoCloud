package com.unialfa.biblioteca.api;

import com.unialfa.biblioteca.service.EmprestimoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("api/emprestimos")
public class EmprestimoApi {

    private final EmprestimoService emprestimoService;

    public EmprestimoApi(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping("/calcular-devolucao")
    public ResponseEntity<Map<String, Object>> calcularDevolucao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRetirada,
            @RequestParam(defaultValue = "14") int prazoDias) {
        LocalDate dataDevolucao = emprestimoService.calcularDataDevolucao(dataRetirada, prazoDias);
        return ResponseEntity.ok(Map.of(
                "dataRetirada", dataRetirada.toString(),
                "prazoDias", prazoDias,
                "dataDevolucao", dataDevolucao.toString()
        ));
    }
}
