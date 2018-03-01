# Login Demo

A simple application to demonstrate authenticating from an android application, using kotlin, against a golang https server that is self-signed.

This is NOT meant for production use. The http server certificate is self-signed and the kotlin code does NOT validate the certificate.

# Steps
```
cd backend
go get ./...
go run main.go
```
Launch the Android Application in the Android studio and login with the username/password.
