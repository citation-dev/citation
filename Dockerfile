FROM amazoncorretto:19.0.1-alpine3.16 as Build

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon shadowJar

FROM amazoncorretto:19.0.1-alpine3.16 as Run

RUN mkdir /app
COPY --from=Build build/libs/citation.jar /app/citation.jar
COPY --from=Build .env.example /app/.env

WORKDIR /app
LABEL org.opencontainers.image.source=https://github.com/citation-dev/citation

CMD ["java", "-jar", "/app/citation.jar"]
