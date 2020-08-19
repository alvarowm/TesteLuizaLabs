# Desafio Java LuizaLabs

Tecnologias presentes na prova:  

* Spring Boot    
* Spring Data  
* Spring MVC
* H2
* Maven
* Swagger
* Rest-assured
* Flyway
* ActiveMQ

 O design arquitetural escolhido foi o MVC, foram isoladas as entidades, serviços e infra, aplicando conceitos básicos de DDD.

 Escolhi o caminho de separar a solução em dois serviços, Cliente e Campanha, sendo a comunição feita por uma fila; quando uma campanha é atualizada o objeto é serializado e por meio da fila é enviado ao serviço de Cliente, quando se recebe a campanha o serviço relaciona a campanha com os clientes de acordo com o time do torcedor.
	
Para testes usei o Rest-assured, subindo um contexto do Spring e executando os métodos básicos REST.

 A API foi versionada, V1, se usou um banco em memória, H2, buscando autonomia, H2 é um banco de dados e ele todas as entidades foram mapeadas, para mudar o banco basta mudar a configuração já que o Spring Data está sendo usado.
 
 Utilizei Swagger para documentar e para acessar basta inserir swagger-ui.htm no final da url, exemplo:
  
  http://localhost:8080/swagger-ui.htm
  
  Como se tratam de serviços, criei dois arquivos Dockerfile para facilitar a criação de imagens e seu deploy em um container, simulando o que se faz no dia a dia.
