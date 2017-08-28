[![Build Status](https://travis-ci.org/mental-soft/application.svg?branch=master)](https://travis-ci.org/mental-soft/application)

# Mental Application

# Dependency
 
 - Postgresql

### Run postgresql with docker
 
 docker run --name cont_postgresql -itd -p 5432:5432 --restart always -e DB_NAME=application-dev,application-qa -e DB_USER=dbuser -e DB_PASS=12345 sameersbn/postgresql
 
 >Note: Locale makinenizi hem developer hem de qa makinesi olarak düşünebilirsiniz.
 Qa makineniz varsa application-qa databasei oluşturmanıza gerek yok.
 
#### Environment
 
 3 tane environmentimiz vardır. 
 - [Default](src/main/resources/config/application-default.yml)
 - [Quality Assurance](src/main/resources/config/application-qa.yml)
 - [Production](src/main/resources/config/application-prod.yml)
 
# Build
 
 Gradle build işlemi için
 
 <code>gradlew clean build</code>
 
# Run
 
#### Gradle üzerinden run etmek için
 
 <code>gradlew bootRun</code>
 
###### Environment
 
 İstediğiniz environment için komutun sonuna -Dspring.profiles.active={profile-name} ekleyin.
 
 Örneğin
 
 <code>gradlew bootRun -Dspring.profiles.active=qa</code>
 
#### Java üzerinden run etmek için
 
 <code>java -jar build/libs/application.jar</code>
 
###### Environment
 
 İstediğiniz environment için komutun sonuna --spring.profiles.active={profile-name} ekleyin.
 
 Örneğin
 
 <code>java -jar build/libs/application.jar --spring.profiles.active=qa</code>
 
# Docker

## Projeyi docker komutundan çalıştırma
 
 >Docker run ile çalıştırırken dependent olan containerları ayağa kaldırmalısınız.
 
 <code>docker run -it --name cont_application -p 8080:10040 --link cont_postgresql mental/application</code>
  
# Docker compose dan çalıştırma
 
 > Compose ile çalıştırırken dependent olan başka containerlar var ise durdurunuz. Yoksa port çakışması olur.
 
 <code>docker-compose up</code>
  
## Up yaparken build etmek için

 <code>docker-compose up --build</code>
  
## Build docker

 > Imageyi değiştirmek isterseniz.
  
 Projeyi gradle ile build ettikten sonra docker imagesini oluşturmak için docker build etmelisiniz.
  
 <code>docker build -t mental/application .</code>
 
 Proje docker üzerinde çalışıyor ise öncelikle containerı durdurmalısınız.
 
 <code>docker rm -f cont_application</code>
  
# Browse
  
 [http://localhost:10040/application](http://localhost:10040/application)
  