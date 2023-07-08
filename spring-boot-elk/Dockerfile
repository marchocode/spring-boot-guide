FROM eclipse-temurin:8

ARG JAR_FILE
ARG SERVICE_NAME

ENV TZ="Asia/Shanghai"

WORKDIR /usr/${SERVICE_NAME}
ADD target/${JAR_FILE} app.jar

CMD [ "java", "-jar", "app.jar" ]