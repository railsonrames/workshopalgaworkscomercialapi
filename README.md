Montei um container com o MySql versão 5.7 porque o gerenciador de migrações Flyway não aceita versões diferentes da 8.0 e 5.7, as demais são "enterprise stuff".

docker run -it --name mysql-container-c -p 3306:3306 -e MYSQL_USER=rames -e MYSQL_PASSWORD=123456 -e MYSQL_DATABASE=comercial -e MYSQL_ROOT_PASSWORD=654321 mysql/mysql-server:5.7.28

Agora é hora de expor o banco a outro container com a aplicação Spring

