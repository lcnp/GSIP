# build
FROM maven:3.8.6-openjdk-11-slim AS build
COPY src /usr/src/gsip/src
COPY WebContent /usr/src/gsip/WebContent
COPY pom.xml /usr/src/gsip
RUN mvn -f /usr/src/gsip/pom.xml clean package

#tomcat 10
###
# Expose ports
###
FROM tomcat:jre11-openjdk-slim-buster AS app

EXPOSE 8080 8443

WORKDIR ${CATALINA_HOME}
# redirect access logs to stdout
COPY --from=build /usr/src/gsip/WebContent/conf/server.xml /usr/local/tomcat/conf/
COPY --from=build /usr/src/gsip/target/gsip.war /usr/local/tomcat/webapps/
RUN ln -sf /dev/stdout /usr/local/tomcat/logs/access_log 
HEALTHCHECK CMD curl --fail http://localhost:8080/gsip/id/x/x || exit 1


