FROM java:8
EXPOSE 80
VOLUME /tmp
ADD ./target/HelloWorld.jar /app.jar
WORKDIR /
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]