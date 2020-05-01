 构建镜像
  docker build -f ./docker/test2/Dockerfile -t springboot-start:v2 .
 运行容器
   构建镜像的时候，通过dockerfile文件的CMD指定需要运行的jar位置。在运行的时候通过-v指定挂载卷，其中包含需要运行的jar。再通过命令参数覆盖CMD。  
   本例将存放jar包的jars文件夹，挂载到容器的/apps目录，再指定运行参数/apps/HelloWorld.jar
  docker run -it -p 80:80 -v /Users/jiangbaojun/myProject/ideaProject/springbootDemo/jars:/apps/ --name t2 springboot-start:v2 /apps/HelloWorld.jar
