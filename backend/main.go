package main

import (
	"io/ioutil"
	"log"
	"net/http"

	"github.com/kabukky/httpscerts"
)

func main() {

	http.HandleFunc("/login", func(w http.ResponseWriter, r *http.Request) {
		log.Println(ioutil.ReadAll(r.Body))
		w.WriteHeader(http.StatusOK)
	})

	err := httpscerts.Generate("cert.pem", "key.pem", "127.0.0.1:8000")
	if err != nil {
		log.Fatal("Error: Couldn't create https certs.")
	}
	log.Fatal(http.ListenAndServeTLS(":8000", "cert.pem", "key.pem", nil))
}
