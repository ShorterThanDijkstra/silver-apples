#!/usr/bin/bash
### 生成key pair: src/main/resources/jasypt/generate_key.sh，祥见https://rietta.com/blog/openssl-generating-rsa-key-from-command/

### 编辑application.yml，模板：
#jasypt:
#  encryptor:
#    iv-generator-classname: org.jasypt.iv.NoIvGenerator
#    algorithm: PBEWithMD5AndDES
#    private-key-format: pem
#    private-key-location: classpath:jasypt/private.pem
#    public-key-location: classpath:jasypt/public.pem
#    public-key-format: pem
#
#spring:
#  datasource:
#    url: jdbc:postgresql://172.17.0.2:5432/mwvb
#    username: DEC(your_name)
#    password: DEC(your_password)
#    driver-class-name: org.postgresql.Driver
#  mail:
#    host: smtp.163.com
#    username: buildvocabulary@163.com
#    password: DEC(your_password)

./mvnw  jasypt:encrypt -Djasypt.encryptor.private-key-format="pem"  -Djasypt.encryptor.private-key-location="file:src/main/resources/jasypt/privatepkcs8.pem" -Djasypt.encryptor.public-key-format="pem" -Djasypt.encryptor.public-key-location="file:src/main/resources/jasypt/public.pem"  -Djasypt.plugin.path="file:src/main/resources/application.yml"
