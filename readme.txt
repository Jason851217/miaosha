docker mysql:
        //docker run --name mydb -p 3306:3306 -e MYSQL-ROOT-PASSWORD=123456 -d centos/mysql-56-centos7

docker redis:
        //        JasondeMacBook-Pro:~ apple$ docker run --name redis6379 -p 6379:6379 -d redis redis-server --requirepass 123456
        //        f648f9c50326e216447a1fedaca053da3e08502cfe0913fb8e843103a8a28772
        //        JasondeMacBook-Pro:~ apple$ docker exec -it redis6379 /bin/bash
        //        root@f648f9c50326:/data# redis-cli
        //        127.0.0.1:6379> keys *
        //        (error) NOAUTH Authentication required.
        //        127.0.0.1:6379> auth 123456
        //        OK
        //        127.0.0.1:6379> keys *
        //        (empty list or set)
        //        127.0.0.1:6379> shutdown save
        //        JasondeMacBook-Pro:~ apple$ ls
        //        Applications			Downloads			Music				data6379			workspace
        //        Desktop				Library				Pictures			iCloud Drive（归档）
        //        Documents			Movies				Public				redis6379.conf
        //        JasondeMacBook-Pro:~ apple$ docker ps
        //        CONTAINER ID        IMAGE                     COMMAND                  CREATED             STATUS              PORTS                    NAMES
        //        3890f93c5eb3        centos/mysql-56-centos7   "container-entrypoin…"   About an hour ago   Up About an hour    0.0.0.0:3306->3306/tcp   mysql3306
        //        JasondeMacBook-Pro:~ apple$ docker ps -a
        //        CONTAINER ID        IMAGE                     COMMAND                  CREATED             STATUS                      PORTS                    NAMES
        //        f648f9c50326        redis                     "docker-entrypoint.s…"   2 minutes ago       Exited (0) 13 seconds ago                            redis6379
        //        3890f93c5eb3        centos/mysql-56-centos7   "container-entrypoin…"   About an hour ago   Up About an hour            0.0.0.0:3306->3306/tcp   mysql3306
        //        JasondeMacBook-Pro:~ apple$ docker restart redis6379
        //        redis6379
        //        JasondeMacBook-Pro:~ apple$ docker ps -a
        //        CONTAINER ID        IMAGE                     COMMAND                  CREATED             STATUS              PORTS                    NAMES
        //        f648f9c50326        redis                     "docker-entrypoint.s…"   2 minutes ago       Up 5 seconds        0.0.0.0:6379->6379/tcp   redis6379
        //        3890f93c5eb3        centos/mysql-56-centos7   "container-entrypoin…"   About an hour ago   Up About an hour    0.0.0.0:3306->3306/tcp   mysql3306
        //        JasondeMacBook-Pro:~ apple$ docker ps
        //        CONTAINER ID        IMAGE                     COMMAND                  CREATED             STATUS              PORTS                    NAMES
        //        f648f9c50326        redis                     "docker-entrypoint.s…"   2 minutes ago       Up 9 seconds        0.0.0.0:6379->6379/tcp   redis6379
        //        3890f93c5eb3        centos/mysql-56-centos7   "container-entrypoin…"   About an hour ago   Up About an hour    0.0.0.0:3306->3306/tcp   mysql3306

docker rabbitmq:management

JasondeMacBook-Pro:~ apple$ docker pull rabbitmq:management
JasondeMacBook-Pro:~ apple$ docker run --name my-rabbitmq -d -p 5671:5671 -p 5672:5672 -p 15671:15671 -p 15672:15672   rabbitmq:management

rabbitmq管理工具：
 访问http://localhost:15672/
 用户名，密码：guest，guest


 就是点开每个出现CRLF的文件，在idea的右下角有个CRLF选项，我们只需要将它设置成LF，然后保存，最后提交就可以了




warning: CRLF will be replaced by LF in {文件名}.
The file will have its original line endings in your working directory.

解决方案：
 就是点开每个出现CRLF的文件，在idea的右下角有个CRLF选项，我们只需要将它设置成LF，然后保存，最后提交就可以了
