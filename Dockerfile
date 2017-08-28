FROM openjdk:8-jre-alpine
ADD build/libs/application.jar /app/application.jar
EXPOSE 10040
CMD java -jar /app/application.jar --connection=cont_postgresql