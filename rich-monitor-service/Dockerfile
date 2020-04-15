FROM pettyfer/apline-open-jre8:latest
RUN mkdir -p /home
WORKDIR /home
EXPOSE 10158
COPY ./target/rich-monitor-service-2.0.1.jar app.jar
COPY ./target/lib lib
ENTRYPOINT java -jar -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=UTF-8 -Xmn64m -Xms256m -Xmx256m -Dloader.path=. app.jar --spring.profiles.active=uat