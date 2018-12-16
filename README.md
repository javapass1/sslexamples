ssl example
1. creating server certificate by using the openssl.conf file to apply alternateive names
2. applying certificate to web application develioped using spring boot 
3. retrive the certificate from broser created using step 2 , and preparing the keystore[trust] of format p12[pkcs12] with openssl.
4. invoking the ssl enable web app using keystore creted by spring boot rest template



commands used are below

Worked :

openssl req -newkey rsa:2048 -nodes -keyout key.pem -x509 -days 365 -out certificate.pem
openssl x509 -text -noout -in certificate.pem
openssl pkcs12 -inkey key.pem -in certificate.pem -export -out certificate.p12
openssl pkcs12 -in certificate.p12 -noout -info

Store from certificate

openssl pkcs12 -export -in importedCertificate.crt -out importedStore.p12 -clcerts -nokeys
openssl pkcs12 -in  importedStore.p12 -noout -info


openssl.cnf config file is available in location ./opensslconf/openssl.cnf