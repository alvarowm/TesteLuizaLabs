From anapsix/alpine-java

VOLUME /tmp
ADD /target/teste-java-cliente-0.0.1-SNAPSHOT.jar startup.jar
RUN sh -c 'touch /startup.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/;/urandom","-jar","/startup.jar"]