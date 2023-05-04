FROM openjdk:19
EXPOSE 8080
EXPOSE 81
EXPOSE 82
EXPOSE 83
ADD target/raphael.jar raphael.jar
ENTRYPOINT ["java", "-jar", "/raphael.jar"]