FROM eclipse-temurin:8

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/demo.jar

ENTRYPOINT ["java", "-jar", "/usr/share/demo.jar"]

