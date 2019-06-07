FROM pettyfer/jre-8:latest
RUN mkdir -p /home
RUN echo "Asia/shanghai" > /etc/timezone;
WORKDIR /home
EXPOSE 10149
ADD /rich-attachment-service-1.0.0-RELEASE.jar app.jar
ADD /lib lib
CMD java -jar -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=UTF-8 -Xmn64m -Xms256m -Xmx256m -Dloader.path=. app.jar --spring.profiles.active=dev