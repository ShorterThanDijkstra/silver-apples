#!/usr/bin/bash
./mvnw  jasypt:encrypt   -Djasypt.encryptor.public-key-format="pem" -Djasypt.encryptor.public-key-location="file:src/main/resources/jasypt/public.pem"  -Djasypt.plugin.path="file:src/main/resources/application.yml"
