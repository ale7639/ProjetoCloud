# Evidência de falha detectada e correção

Este experimento demonstra que o pipeline **interrompe** o fluxo quando um teste falha e **não** publica o artefato aprovado.

## Passo 1 — Introduzir falha controlada

Altere temporariamente o teste de prazo padrão em
`src/test/java/com/unialfa/biblioteca/service/EmprestimoServiceTest.java`:

```java
// ERRADO de propósito (espera 7 dias em vez de 14)
assertEquals(LocalDate.of(2026, 10, 8), devolucao);
```

## Passo 2 — Observar a falha

Localmente:

```bash
./mvnw -B clean verify
```

Esperado:

- Etapa de testes falha (`BUILD FAILURE`)
- Mensagem indicando diferença entre esperado e obtido
- Pacote de entrega não deve ser tratado como válido

No GitHub Actions:

- Workflow fica **vermelho**
- Etapa `Compilar, testar e verificar` falha
- Artifact `pacote-<sha>` **não** é publicado (`if: success()`)
- Relatórios ainda podem ser baixados (`if: always()`)

## Passo 3 — Corrigir

Restaure a asserção correta:

```java
assertEquals(LocalDate.of(2026, 10, 15), devolucao);
```

## Passo 4 — Reexecutar

```bash
./mvnw -B clean verify
```

Envie o commit corrigido e confirme nova execução **verde** na aba Actions, com artifact `pacote-<sha>` disponível.

## Aprendizado

- Status verde sem testes é insuficiente; é preciso ler as evidências
- Falha de teste bloqueia a promoção do artefato
- O feedback chega antes do merge/deploy
