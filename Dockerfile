FROM tomcat:jre17
ENV JVAOPT=localhost
COPY target/dep.war /usr/local/tomcat/webapps

