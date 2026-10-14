# Evidência de execução bem-sucedida

## Como obter (após o push)

1. Abra [Actions do repositório](https://github.com/ale7639/ProjetoCloud/actions)
2. Selecione o workflow **CI Java** na execução do commit desejado
3. Confirme status **Success**
4. Em **Artifacts**, baixe:
   - `pacote-<sha>` → JAR
   - `relatorios-<sha>` → Surefire

## Evidência local (registrada neste ambiente)

Comando:

```bash
./mvnw -B clean verify
```

Resultado esperado:

- `BUILD SUCCESS`
- Testes de `EmprestimoServiceTest` e `BibliotecaApplicationTests` aprovados
- JAR em `target/biblioteca-0.0.1-SNAPSHOT.jar`

Associação com o commit: o nome do artifact no GitHub Actions inclui `${{ github.sha }}`.
