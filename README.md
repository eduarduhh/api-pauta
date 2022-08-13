# Desafio API de votação

API REST para votação

Descrição do desafio:

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias,
por votação. Imagine que você deve criar uma solução backend para gerenciar essas sessões de
votação.

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de
uma API REST:

- Cadastrar uma nova pauta
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta)
- Contabilizar os votos e dar o resultado da votação na pauta

Tarefa Bônus 1 - Integração com sistemas externos

    Integrar com um sistema que verifique, a partir do CPF do associado, se ele pode votar
    GET https://user-info.herokuapp.com/users/{cpf}
    Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos;
    Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação Exemplos de retorno do serviço
    aaa

Tarefa Bônus 2 - Mensageria e filas

    Classificação da informação: Uso Interno O resultado da votação precisa ser informado para o restante da plataforma, isso deve ser feito preferencialmente através de mensageria. Quando a sessão de votação fechar, poste uma mensagem com o resultado da votação.

Tarefa Bônus 3 - Performance

    Imagine que sua aplicação possa ser usada em cenários que existam centenas de milhares de votos. Ela deve se comportar de maneira performática nesses cenários;
    Testes de performance são uma boa maneira de garantir e observar como sua aplicação se comporta.

Tarefa Bônus 4 - Versionamento da API

    Como você versionaria a API da sua aplicação? Que estratégia usar?

## Stack utilizada

- Java 8
- Spring
- Maven
- H2
- RabbitMQ
- Swagger
- JUnit
- Mockito
- Docker

## Requisitos
- Maven 3.6+
- Java 8
- Docker instalado localmente

## Rodar a aplicação

O Docker deve estar rodando na maquina.

Clone o projeto e navegue até a pasta root do mesmo por um terminal.

Então, execute os comandos na sequencia abaixo para compilar, rodar os testes unitarios da aplicação e gerar as imagens docker:

- mvn clean
- mvn package
- docker-compose up

Caso deseje rodar apenas os testes:
- mvn test

## Acesso a API
Para visualizar a documentação da api:
- http://localhost:8080/swagger-ui.html

OBS: Cuidado para que outras aplicações não estejam rodando na porta 8080


#### Tarefa Bônus 1 - Integração com sistemas externos
    Foi implementado um servico 'CpfClient' utilizando o OpenFeing para comunicação com outros sistemas via http;

#### Tarefa Bônus 2 - Mensageria e filas
    Em construção...

#### Tarefa Bônus 3 - Performance
    Não foi realizado.

#### Tarefa Bônus 4 - Versionamento da API
    A forma escolhida foi versionar a url com o prefixo v seguida de um numero inteiro, ex: v1/pauta
    
    
    
   