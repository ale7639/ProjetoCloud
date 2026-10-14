# Como enviar este projeto para https://github.com/ale7639/ProjetoCloud

O código está completo e validado neste agente. Para publicar no GitHub da entrega, use uma das opções:

## Opção A — Personal Access Token (recomendado para o agente concluir)

1. No GitHub: **Settings → Developer settings → Personal access tokens**
2. Crie um token (classic) com escopo `repo`, ou fine-grained com **Contents: Read and write** no repositório `ProjetoCloud`
3. Em Cursor: **Cloud Agents → Secrets**, adicione `GH_TOKEN` = valor do token
4. Peça ao agente: "use o GH_TOKEN e faça push para ale7639/ProjetoCloud"

## Opção B — Push manual na sua máquina

```bash
git clone https://github.com/ale7639/ProjetoCloud.git
cd ProjetoCloud
# baixe o arquivo ProjetoCloud-t5.tar.gz do agente e extraia sobre o clone
# ou copie os arquivos do workspace do agente
git add -A
git commit -m "Trabalho 5: Biblioteca + pipeline CI"
git push origin main
```

Após o push, abra a aba **Actions** e confirme a execução verde do workflow **CI Java**.
