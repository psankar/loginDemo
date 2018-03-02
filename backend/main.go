package main

import (
	"encoding/json"
	"log"
	"net/http"
	"os"

	"github.com/gorilla/handlers"
	"github.com/kabukky/httpscerts"
)

type LoginReq struct {
	Username string
	Password string
}

func main() {

	http.Handle("/login", handlers.LoggingHandler(os.Stderr, http.HandlerFunc(
		func(w http.ResponseWriter, r *http.Request) {
			x := LoginReq{}

			decoder := json.NewDecoder(r.Body)
			if err := decoder.Decode(&x); err != nil {
				log.Println(err)
				http.Error(w, "JSON decode failure: "+err.Error(), http.StatusBadRequest)
				return
			}

			log.Println(x)

			w.WriteHeader(http.StatusOK)
		})))

	err := httpscerts.Generate("cert.pem", "key.pem", "127.0.0.1:8000")
	if err != nil {
		log.Fatal("Error: Couldn't create https certs.")
	}
	log.Fatal(http.ListenAndServeTLS(":8000", "cert.pem", "key.pem", nil))
}
