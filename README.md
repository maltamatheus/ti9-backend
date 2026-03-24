# ti9-backend
Teste empresa TI9

Autor: Matheus Malta de Aguiar

Observações:
- Versão Java 17
- Versão Spring: 3
- Versão PostgreSQL: 13

- Para executar a aplicação devem ser setadas as seguintes variáveis de ambiente:
- ENV_PROFILE = Perfil do Spring que será executado. Se não informada, a aplicação subirá no perfil dev por padrão
- SERVER_PORT = Porta em que a aplicação subirá. Se não informada, a aplicação subirá na porta padrão 8080
- USER = Usuário Banco Postgres
- PASSWORD = Password
- DB_HOST = Host do servidor do Banco
- DB_PORT = Porta do Servidor do Banco

Contexto da Aplicação: Se perfil dev, o contexto será /dev/api/v1. Caso contrário, será /api/v1
Documentação:
- Swagger - SERVER_PORT/{contexto}/swagger-ui/index.html
