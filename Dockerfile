FROM tomcat:jre17
ENV HOST_NAME=localhost
COPY target/dep.war /usr/local/tomcat/webapps

