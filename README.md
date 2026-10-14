# ProjetoCloud — Biblioteca + Pipeline CI (Trabalho 5)

Aplicação **Biblioteca** (Spring Boot / Java 21 / Maven) com **pipeline de Integração Contínua** reprodutível via **GitHub Actions**.

Disciplina: **Cloud Computing e DevOps Avançado** (UniALFA)  
Trabalho: **T5 — Pipeline CI mínimo e reprodutível**  
Repositório de entrega: [https://github.com/ale7639/ProjetoCloud](https://github.com/ale7639/ProjetoCloud)

---

## O que este repositório entrega

| Entregável T5 | Onde está |
|---|---|
| Repositório acessível | Este projeto no GitHub |
| Definição do pipeline | [`.github/workflows/ci.yml`](.github/workflows/ci.yml) |
| Instruções de reprodução | Este README |
| Gatilho, etapas, dependências e artefato | Seção abaixo + [`docs/pipeline.md`](docs/pipeline.md) |
| Evidência de sucesso | [`docs/evidencias/`](docs/evidencias/) + aba Actions |
| Evidência de falha detectada | [`docs/evidencias/falha-controlada.md`](docs/evidencias/falha-controlada.md) |
| Proteção de segredos | Seção **Segurança** + [`.env.example`](.env.example) |

---

## Funcionalidades da aplicação

- Interface web (Thymeleaf) com login por formulário
- API REST (`/api/**`) protegida com JWT
- CRUD de **Livros**, **Autores** e **Usuários**
- Cálculo de **prazo de empréstimo** (`/api/emprestimos/calcular-devolucao`)
- Health check público: `GET /api/health`
- Banco H2 em memória (adequado para demonstração e CI)

### Usuários padrão

| Usuário | Senha | Perfil |
|---|---|---|
| `admin` | `admin` | ADMIN |
| `user` | `user` | USER |

---

## Pipeline CI — visão rápida

```text
Commit / Pull Request (main)
        ↓
Checkout
        ↓
Java 21 + cache Maven
        ↓
Validação inicial
        ↓
./mvnw -B clean verify   ← falha interrompe o fluxo
        ↓
Relatórios Surefire (sempre)
        ↓
Artefato JAR (somente se sucesso) → pacote-<sha>
        ↓
Docker build → biblioteca:<sha>
```

### Gatilho

- `push` na branch `main`
- `pull_request` para `main`
- `workflow_dispatch` (execução manual)

### Artefato

- Nome: `pacote-<commit-sha>`
- Conteúdo: JAR gerado em `target/`
- Relatórios: `relatorios-<commit-sha>` (mesmo em falha)

Detalhamento completo: [`docs/pipeline.md`](docs/pipeline.md)

---

## Como reproduzir localmente

### Pré-requisitos

- Java 21+
- Maven 3.8+ **ou** o wrapper `./mvnw` (recomendado)
- Docker (opcional, para imagem)

### Build e testes (mesmo comando do CI)

```bash
./mvnw -B clean verify
```

Saídas esperadas:

- Relatórios em `target/surefire-reports/`
- JAR em `target/biblioteca-0.0.1-SNAPSHOT.jar`

### Executar a aplicação

```bash
export JWT_SECRET="seu-segredo-local-com-pelo-menos-32-chars"
./mvnw spring-boot:run
```

Ou pelo JAR:

```bash
java -jar target/biblioteca-0.0.1-SNAPSHOT.jar
```

Acessos:

- Site: http://localhost:8080/
- Login: http://localhost:8080/login
- Health: http://localhost:8080/api/health
- H2 Console: http://localhost:8080/h2-console

### Testar a API

```bash
# Health (público)
curl -s http://localhost:8080/api/health

# Login JWT
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"admin"}' | sed -n 's/.*"token":"\([^"]*\)".*/\1/p')

# Calcular devolução de empréstimo
curl -s "http://localhost:8080/api/emprestimos/calcular-devolucao?dataRetirada=2026-10-01&prazoDias=14" \
  -H "Authorization: Bearer $TOKEN"

# Listar livros
curl -s http://localhost:8080/api/livros -H "Authorization: Bearer $TOKEN"
```

### Docker (opcional)

```bash
docker build -t biblioteca:local .
docker run --rm -p 8080:8080 -e JWT_SECRET="seu-segredo-local" biblioteca:local
```

---

## Como investigar falhas

1. Abra a execução em **GitHub → Actions**
2. Identifique o **primeiro job/etapa** com erro (geralmente `Compilar, testar e verificar`)
3. Leia a mensagem original do Maven/JUnit no log
4. Baixe o artifact `relatorios-<sha>` se necessário
5. Reproduza localmente com `./mvnw -B clean verify`
6. Corrija a causa, envie novo commit e acompanhe a nova execução

Procedimento de falha controlada (exercício do T5): [`docs/evidencias/falha-controlada.md`](docs/evidencias/falha-controlada.md)

---

## Segurança e segredos

- Senhas, tokens e chaves **não** devem ser commitados.
- O segredo JWT é lido de `JWT_SECRET` (variável de ambiente).
- Há um fallback didático apenas para execução local; em produção use secret forte e externo.
- O workflow usa `permissions: contents: read` (menor privilégio).
- Credenciais de registry/deploy, se forem adicionadas no futuro, devem ficar em **GitHub Secrets**.
- Evite `echo` de variáveis sensíveis nos logs do pipeline.

Modelo: [`.env.example`](.env.example)

---

## Estrutura do projeto

```text
ProjetoCloud/
├── .github/workflows/ci.yml     # Pipeline CI
├── docs/
│   ├── pipeline.md              # Modelo detalhado do pipeline
│   └── evidencias/              # Evidências de sucesso/falha
├── src/main/java/...            # Aplicação Biblioteca
├── src/test/java/...            # Testes automatizados
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Checklist T5

- [x] Repositório acessível
- [x] Pipeline com gatilho compreensível
- [x] Build em ambiente limpo (runner GitHub)
- [x] Testes executados no CI
- [x] Falha interrompe o fluxo
- [x] Artefato identificado pelo SHA do commit
- [x] Logs e relatórios disponíveis
- [x] README permite reproduzir o processo
- [x] Sem credenciais reais no código

---

## Referências

- Aulas 09 e 10 — UniALFA (DevOps, CI e pipelines)
- [GitHub Actions](https://docs.github.com/actions)
- [Apache Maven — Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- Projeto base: [dpRanghetti/biblioteca](https://github.com/dpRanghetti/biblioteca)
