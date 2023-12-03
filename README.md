# delivery-management

Este projeto é parte da atividade de sistematização da disciplina de Programação Orientada a Objetos do curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas do Centro Universitátio de Brasília (CEUB).

# Problema Solucionado
O projeto visa solucionar a necessidade de gerenciamento de pacotes da empresa Dianome. A Dianome é uma empresa fictícia que atua no ramo de entregas.

Conforme solicitado no enunciado da atividade, o projeto é apenas uma solução simplificada com objetivo de controlar código de rastreamento, status e data de recebimento e entrega dos pacotes. Soluções mais completas exigirão sobretudo evolução do modelo de dados e tratamento de regras de negócios, conforme as necessidades e especificidades da empresa.

# Funcionalidades
Para implementação do controle de pacotes foram criadas quatro entidades: entrega, eventos, pessoa, pacote.
- Entrega: Entidade principal que tem como atributos o pacote a ser entregue, destinatário, entregador, eventos do ciclo de vida do pacote e campo opcional para observações;
- Pacote: Entidade simplificada com a descrição dos itens contantes do pacote;
- Pessoa: Cadastro de destinatários/clientes e entregadores;
- Eventos: Marcos históricos do ciclo de vida de uma entrega.

O projeto contém as funções básicas de CRUD para uma entrega:
- Create: cria uma entrega a partir de um código de pacote e de destinatário e retorna os dados da entrega criada, inclusive o código de rastreamento;
- Update: permite registrar o entregador designado para a entrega e as movimentações da entrega, por exemplo, saída para entrega, pacote entregue, recusado, etc. Em cada atualização é possível registrar uma observação para a entrega;
- Recover:
  - Listar: Lista todas as entregas não deletadas logicamente;
  - Detalhar: detalha uma entrega específica;
- Delete: Deleta logicamente uma entrega;

Obs.: A cada operação de create, update e delete é salvo um registro na entidade eventos.

# Executando o projeto
O projeto foi construído o utilizando o framework spring, versão 3.2.0. Foi utilizada a versão 17 do Java e banco do dados mysql. 

Configuração do banco de dados:

`usuário:dianomeusr`

`senha:dianomepw`

Existem várias maneiras de executar uma aplicação Spring Boot em sua máquina local. Uma maneira é executar o método principal na classe `br.com.dianome.deliverymanagement.DeliveryManagementApplication` na sua IDE.

- Baixe o zip ou clone o repositório Git.
- Descompacte o arquivo zip (se você baixou um);
- Abra o Prompt de Comando e altere o diretório (cd) para a pasta que contém o pom.xml;
- Importe o projeto conforme as especificidades de sua IDE;
- Crie o usuário para o banco de dados, conforme acima ou atualize os dados de usuário e senha no arquivo application.properties;
- Escolha o arquivo Spring Boot Application (procure por @SpringBootApplication)
 - Clique com o botão direito do mouse no arquivo e execute como aplicativo Java

Alternativamente, você pode usar o plugin Spring Boot Maven como segue:

`mvn spring-boot:run`

Utilize o postman, insomnia ou ferramentas equivalentes para testar as funcionalidades.

Obs.: 
- A fim de facilitar os testes, na inicialização da aplicação são carregados dados fictícios para as entidades Person (pessoa) e Package (Pacote);
- Os Ids válidos para cliente (costumerId) são: 1 a 9
- Os Ids válidos para entregador (deliverPersonId) são: 1, 5 e 8 
- Os Ids válidos para pacote (packageId) são: 1 a 6

```
* Endpoints
GET [/api/deliveries/{id}]
DELETE [/api/deliveries/{id}]
PUT [/api/deliveries/{id}]
POST [/api/deliveries/]
GET [/api/deliveries/]

* body:
  - create:

{
  "pacakgeId": 1,
  "costumerId": 1,  
  "note": "Registro de Entrega."
}

- Update: Atribuir entregador

{
  "action": "REGISTER_DELIVERY_PERSON",
  "deliveryPersonId": 2,
  "note": "Entregador atribuido" (opcional)
}

- Update: demais atualizações

{
  "action": "DELIVER",
  "note": "Entregue na portaria do Prédio" (opcional)
}

- Delete

{
  "action": "DELETE",
  "note": "Entrega duplicada" (opcional)
}

```
