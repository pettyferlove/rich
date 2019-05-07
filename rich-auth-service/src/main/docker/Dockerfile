FROM pettyfer/jre-8:latest
RUN mkdir -p /home
RUN echo "Asia/shanghai" > /etc/timezone;
WORKDIR /home
EXPOSE 6060
ADD /rich-auth-service-1.0.0-RELEASE.jar app.jar
ADD /lib lib
CMD java -jar -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=UTF-8 -Xmn128m -Xms512m -Xmx512m -Dloader.path=. app.jar --spring.profiles.active=dev