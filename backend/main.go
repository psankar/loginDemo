package main

import (
	"encoding/json"
	"log"
	"net/http"
	"os"
	"strings"
	"time"

	jwt "github.com/dgrijalva/jwt-go"
	"github.com/gorilla/handlers"
	"github.com/kabukky/httpscerts"
	"gitlab.com/secretsapp/secretsapp/backend/conf"
)

const topSecret = "Top Secret"

type LoginReq struct {
	Username string
	Password string
}

type LoginRes struct {
	JWT string
}

type MyGroupsRes struct {
	Groups []string
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

			if x.Password != "password" {
				w.WriteHeader(http.StatusForbidden)
				return
			}

			token := jwt.NewWithClaims(
				jwt.SigningMethodHS256,
				jwt.StandardClaims{
					ExpiresAt: time.Now().Add(time.Hour * 5).Unix(),
				})
			tokenString, err := token.SignedString([]byte(topSecret))
			if err != nil {
				http.Error(w, "JSON Marshal Error", http.StatusInternalServerError)
				return
			}
			log.Println(tokenString, err)

			var jData []byte
			jData, err = json.Marshal(LoginRes{JWT: tokenString})
			if err != nil {
				http.Error(w, "JSON Marshal Error", http.StatusInternalServerError)
				return
			}
			log.Println(string(jData))
			w.Header().Set("Content-Type", "application/json")
			w.WriteHeader(http.StatusOK)
			w.Write(jData)
		})))

	http.Handle("/my-groups", handlers.LoggingHandler(os.Stderr, http.HandlerFunc(
		func(w http.ResponseWriter, r *http.Request) {
			tokenString := r.Header.Get("Authorization")
			if tokenString == "" {
				http.Error(w, "Missing Authorization header", http.StatusUnauthorized)
				return
			}

			if !strings.HasPrefix(tokenString, "Bearer ") {
				http.Error(w, "Missing Bearer Keyword", http.StatusUnauthorized)
				return
			}

			tokenString = strings.TrimPrefix(tokenString, "Bearer ")

			token, err := jwt.ParseWithClaims(tokenString, &jwt.StandardClaims{},
				func(token *jwt.Token) (interface{}, error) {
					return []byte(conf.JWTSigningKey()), nil
				})

			if err != nil {
				http.Error(w, "JWT Parsing Error", http.StatusUnauthorized)
			}
			if _, ok := token.Claims.(*jwt.StandardClaims); ok && token.Valid {
				http.Error(w, "Invalid JWT", http.StatusUnauthorized)
			}

			myGroups := []string{"Hello", "World"}
			var jData []byte
			jData, err = json.Marshal(MyGroupsRes{myGroups})
			if err != nil {
				http.Error(w, "JSON Marshal Error", http.StatusInternalServerError)
				return
			}
			log.Println(string(jData))
			w.Header().Set("Content-Type", "application/json")
			w.WriteHeader(http.StatusOK)
			w.Write(jData)
		})))

	err := httpscerts.Generate("cert.pem", "key.pem", "127.0.0.1:8000")
	if err != nil {
		log.Fatal("Error: Couldn't create https certs.")
	}
	log.Fatal(http.ListenAndServeTLS(":8000", "cert.pem", "key.pem", nil))
}
