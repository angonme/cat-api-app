# Cat API App

Aplicação Java Spring Boot que consome a [The Cat API](https://thecatapi.com/) para coletar informações de raças de gatos, salvar imagens e expor APIs REST com métricas.

## 🔧 Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Banco de dados em memória H2
- Prometheus + Grafana
- Splunk (Logs)
- Insomnia (testes de API)
  
- Ferramentas de Qualidade Itaú:
- SonarQube (análise estática, cobertura de testes)
- TAAC (Test as a Code – automação de testes)
- Hopper (gestão e execução de testes manuais)
- Qualifier (execução de suítes automatizadas)
- Performance4ALL (testes de performance)
- IU Chaos (testes de resiliência)
- IU Pipes/GitHub Actions (pipelines CI/CD para testes)
- IU Quali (gerenciamento de testes automatizados)

## 🚀 Como rodar o projeto localmente

### 1. Pré-requisitos

- Java 17+
- Maven
- IntelliJ IDEA (ou outra IDE Java)
- Insomnia

### 2. Rodando localmente pelo IntelliJ IDEA (recomendado)

Clone o repositório:
comando: git clone https://github.com/angonme/cat-api-app.git
Abra o projeto no IntelliJ IDEA.
Aguarde o carregamento do Maven e do projeto.
Localize a classe principal (CatApiApp.java) e execute como aplicação Spring Boot (botão verde "Run" na IDE).
A aplicação estará disponível em http://localhost:8080.

Acesse:
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000` (usuário/senha: `admin/admin`)

### 3. Ferramentas de Teste e Qualidade disponíveis no Itaú

Testes Unitários e Regressivos
SonarQube: Análise de cobertura e qualidade de código. Integre na pipeline para garantir padrões e detectar falhas.
TAAC: Criação e execução de testes automatizados versionados junto ao código.
Qualifier: Execução de suítes de testes automatizados em diferentes ambientes.
IU Pipes/GitHub Actions: Orquestração automática de testes em CI/CD.

Testes Manuais
Hopper: Gestão e execução de casos de teste manuais, com rastreabilidade, evidências e documentação.

Testes Integrados
TAAC/Qualifier: Suporte a testes de integração automatizados.
IU Quali: Gerenciamento de execuções de testes integrados.

Testes de Performance
Performance4ALL: Criação e execução de scripts de performance (JMeter), com integração ao Grafana para análise de resultados.
Grafana/Prometheus: Monitoramento de métricas de performance em tempo real.

Testes de Resiliência
IU Chaos: Simulação de falhas e validação da resiliência da aplicação.

Observabilidade e Logs
Splunk: Centralização e análise de logs para troubleshooting e auditoria.
Grafana/Prometheus: Dashboards para acompanhamento de métricas técnicas e de negócio.

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

---

✅ Projeto Finalizado.
