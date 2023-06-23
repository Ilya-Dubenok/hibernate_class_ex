FROM tomcat:jre17
ENV JVAOPT=localhost $ADD_JAVA_OPT
COPY target/dep.war /usr/local/tomcat/webapps

