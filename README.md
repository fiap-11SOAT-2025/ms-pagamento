# ms-pagamento

Microserviço para pagamento.

## Descrição

Microserviço responsável por operacionalizar a cobrança de um  pedido, registrando a solicitação de pagamento, recebendo o  retorno do processador de pagamento e atualizando o status do  pedido.

## Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

* [Java 21 (JDK)](https://www.google.com/search?q=https://www.oracle.com/java/technologies/downloads/%23java21)
* [Maven](https://maven.apache.org/download.cgi)
* [Docker](https://www.docker.com/products/docker-desktop/) e [Docker Compose](https://docs.docker.com/compose/install/)

## Execução Local

Para executar o microsserviço localmente, siga estes passos:

### 1\. Iniciar Dependências

Este microsserviço depende do **PostgreSQL**, do `ms-pedido` e do `ms-producao`.

**a. Iniciar o PostgreSQL Local:**
Usaremos o Docker Compose para iniciar apenas o contêiner do PostgreSQL:

```bash
docker-compose up -d postgres
```

O PostgreSQL estará acessível em `http://localhost:5432`.

**b. Iniciar outros microsserviços:**
Certifique-se de que os outros microsserviços estejam em execução e acessíveis localmente:

* `ms-pedido` em `http://localhost:8081`
* `ms-producao` em `http://localhost:8082`

### 2\. Compilar e Executar a Aplicação

**a. Compilar o projeto:**
Navegue até a raiz do projeto e execute:

```bash
./mvnw clean install
```

**b. Executar o .jar:**
Execute a aplicação. Recomendamos passar as variáveis de ambiente explicitamente para evitar conflitos de porta (já que o `ms-cadastro-catalogo` também usa a porta 8080 por padrão):

```bash
java -jar -Dserver.port=8083 \
          -Dapi.pedido.host=http://localhost:8081 \
          -Dapi.producao.host=http://localhost:8082 \
          -Dapi.mercadopago.userId=2409646693 \
          -Dapi.mercadopago.externalPosId=FIAP001POS \
          -Dapi.mercadopago.token=APP_USR-5511707722892084-042814-00c965a2a4ecf4475115062da61fb13d-2409646693 \
          -Dspring.datasource.url=jdbc:postgresql://postgres:5432/microservice \
          -Dspring.datasource.username=root \
          -Dspring.datasource.password=root \
          target/ms-pagamento-0.0.1-SNAPSHOT.jar
```

A aplicação `ms-pagamento` estará disponível em `http://localhost:8083`.

## Execução via Docker Compose

O `docker-compose.yaml` fornecido orquestra o `ms-pagamento` e o `postgres`.

### 1\. Pré-requisito: Outros Serviços no Host

Este `docker-compose` foi configurado para permitir que o serviço **`ms-pagamento`**, executado dentro do contêiner, se comunique com os microsserviços **`ms-peido`** e **`ms-producao`** que estão rodando em contêineres.

🔹 **Caso os microsserviços também estejam sendo executados em contêineres:**
Você pode apontar diretamente para os nomes dos serviços definidos no `docker-compose`, por exemplo:

* `ms-pedido` disponível em **[http://ms-pedido:8080](http://ms-cadastro:8080)**
* `ms-producao` disponível em **[http://ms-producao:8080](http://ms-producao:8080)**

---

🔹 **Caso os microsserviços estejam rodando localmente (fora do Docker):**
Certifique-se de que:

* `ms-pedido` está disponível em **[http://localhost:8081](http://localhost:8081)**
* `ms-producao` está disponível em **[http://localhost:8082](http://localhost:8082)**

> O arquivo `docker-compose.yaml` deve utilizar `host.docker.internal` para que o contêiner `ms-pagamento` consiga acessar os serviços que estão no `localhost` da sua máquina.

---

### 2\. Iniciar os Contêineres

Na raiz do projeto, execute o comando a seguir. O `--build` garante que a imagem Docker será criada com o código mais recente:

```bash
docker compose up --build -d
```

Este comando irá:

1.  Construir a imagem Docker do `ms-pagamento` conforme o `Dockerfile`.
2.  Iniciar o contêiner `postgres_microservice_pagamento` (porta `5432`).
3.  Iniciar o contêiner `ms-pagamento` (porta `8083`).

A aplicação `ms-pagamento` estará disponível em `http://localhost:8083`.

## Documentação da API (Swagger)

A documentação da API (Swagger UI) estará disponível nos seguintes endereços:

* `http://localhost:8083/swagger-ui.html`