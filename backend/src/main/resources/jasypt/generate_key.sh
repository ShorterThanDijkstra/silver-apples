#!/usr/bin/env bash
openssl genrsa -out private.pem 2048
# https://github.com/ulisesbocchio/jasypt-spring-boot/issues/207
openssl rsa -in private.pem -outform PEM -pubout -out public.pem
openssl pkcs8 -topk8 -inform pem -in private.pem -outform pem -nocrypt -out privatepkcs8.pem