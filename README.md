# Login Demo

A simple application to demonstrate authenticating from an android application, using kotlin, against a golang https server that is self-signed.

This is NOT meant for production use. The http server certificate is self-signed and the kotlin code does NOT validate the certificate.

# Steps
```
cd backend
go get ./...
go run main.go
```
Launch the Android Application in the Android studio and login with any email address as username and the string `password` as password. On succesful login, the server will send a JWT (JSON Web Token). This token will be used in the subsequent call which will get a list of group-names from the server, which will be rendered in the post-login screen.
