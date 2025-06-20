# Cat API App

Aplicação Java Spring Boot que consome a [The Cat API](https://thecatapi.com/) para coletar informações de raças de gatos, salvar imagens e expor APIs REST com métricas.

## 🔧 Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Banco de dados em memória H2
- Docker e Docker Compose
- Prometheus + Grafana
- Splunk (Logs)
- Insomnia (testes de API)

## 🚀 Como rodar o projeto localmente

### 1. Pré-requisitos

- Java 17+
- Maven
- Docker e Docker Compose (opcional)
- Insomnia

### 2. Rodando com Docker (recomendado)

```bash
docker-compose up --build
```

A aplicação estará disponível em `http://localhost:8080`.

Acesse:
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000` (usuário/senha: `admin/admin`)

### 3. Rodando localmente sem Docker

```bash
mvn spring-boot:run
```

---

## 📚 Documentação das APIs

Base URL: `http://localhost:8080`

| Método | Endpoint                                | Descrição                               |
|--------|------------------------------------------|-----------------------------------------|
| GET    | `/racas`                                 | Lista todas as raças                    |
| GET    | `/racas/{id}`                            | Detalhes de uma raça por ID             |
| GET    | `/racas/temperamento/{temp}`             | Lista raças com temperamento específico |
| GET    | `/racas/origem/{origem}`                 | Lista raças de uma origem específica    |

A coleção `insomnia_catapi.json` está incluída no projeto.

---

## 🛠️ Arquitetura

- Camadas bem definidas:
  - `controller`
  - `service`
  - `repository`
  - `model`
- Coleta inicial automática de dados e imagens da API externa
- Banco H2 com persistência temporária dos dados
- Exposição de métricas via `/actuator/prometheus`

---

## 📊 Dashboards Prometheus e Grafana

O sistema coleta:
1. Quantidade de execuções dos endpoints
2. Latência média das requisições
3. Número de erros

Exemplos de gráficos disponíveis no Grafana:

- 📈 **Total de requisições por rota**
- ⏱️ **Tempo de resposta médio**
- ❌ **Taxa de erros (4xx/5xx)**

---

## 🔍 Logs em tempo real via Splunk

A aplicação exporta logs categorizados por:
- INFO (requisições processadas)
- DEBUG (detalhes de serviços)
- WARN (avisos de dados ausentes)
- ERROR (falhas de conexão ou persistência)

### 🔍 Exemplo de query Splunk

```splunk
index=catapi_logs sourcetype="springboot"
| stats count by level
```

---

## 📦 Estrutura do projeto

```
cat-api-app/
├── src/
├── Dockerfile
├── docker-compose.yml
├── insomnia_catapi.json
├── README.md
```

---

## 🐳 Docker Hub (Opcional)

Caso deseje, pode publicar sua imagem:

```bash
docker build -t seu-usuario/cat-api-app .
docker push seu-usuario/cat-api-app
```

---

## ✅ Prints dos dashboards e logs (manual do projeto)

**Inclua prints aqui dos painéis Grafana e logs Splunk.**

---

## 👨‍💻 Autor

Anderson Mendes

---

## 📥 Importar painel no Grafana

1. Acesse o Grafana: `http://localhost:3000`
2. Login: `admin / admin`
3. Vá até **Dashboards > Import**
4. Clique em **Upload JSON file**
5. Selecione o arquivo `grafana_dashboard_catapi.json` incluído no projeto
6. Escolha o datasource `Prometheus` e clique em **Import**

Ou [clique aqui para baixar o painel diretamente](./grafana_dashboard_catapi.json).

---

## 🐙 Publicação no GitHub

1. Crie um repositório no GitHub
2. Faça push do conteúdo extraído do ZIP:

```bash
git init
git remote add origin https://github.com/seu-usuario/cat-api-app.git
git add .
git commit -m "Projeto Cat API completo"
git push -u origin master
```

---

## 🐳 Publicar imagem no Docker Hub

1. Faça login no Docker Hub:
```bash
docker login
```

2. Crie a imagem:
```bash
docker build -t seu-usuario/cat-api-app .
```

3. Envie para o Docker Hub:
```bash
docker push seu-usuario/cat-api-app
```

Depois poderá rodar com:

```bash
docker run -p 8080:8080 seu-usuario/cat-api-app
```

---

✅ Projeto pronto para produção, testes, monitoramento e publicação!
