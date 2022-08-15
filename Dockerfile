FROM openjdk:18-oracle as Build

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon shadowJar

LABEL org.opencontainers.image.source=https://github.com/m2en/citation

CMD ["java", "-jar", "build/libs/citation.jar"]
