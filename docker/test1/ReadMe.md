 构建镜像
  docker build -f ./docker/test1/Dockerfile -t springboot-start:v1 .
 运行容器
  docker run -it -p 80:80 --name t1 springboot-start:v1