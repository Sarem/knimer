sudo docker run --name mysql-knimer -d -p 3306:3306 -e MYSQL_DATABASE=knimer -e MYSQL_USER=knimer -e MYSQL_PASSWORD=knimerpsw -e MYSQL_ROOT_PASSWORD=rootpsw mysql:8.0.19


sudo docker build --tag knimer:0.2 .
docker run --publish 8081:8081 --detach --name bb knimer:0.2