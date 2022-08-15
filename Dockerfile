FROM openjdk:17-oracle as Build

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon shadowJar

FROM openjdk:17-oracle as Run

RUN mkdir /app
COPY --from=Build build/libs/citation.jar /app/citation.jar
COPY --from=Build .env.example /app/.env

WORKDIR /app
LABEL org.opencontainers.image.source=https://github.com/m2en/citation

CMD ["java", "-jar", "/app/citation.jar"]
