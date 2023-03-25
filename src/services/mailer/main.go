package main

import (
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
	"log"
	"os"
)

func main() {
	// Load env variables from file
	err := godotenv.Load("credentials.env")
	if err != nil {
		log.Fatal("Error loading .env file")
	}

	// Create a new Gin router
	server := gin.Default()

	corsConfig := cors.DefaultConfig()
	corsConfig.AllowOrigins = []string{os.Getenv("FRONTEND_ORIGIN")}
	corsConfig.AllowCredentials = true

	server.Use(cors.New(corsConfig))

	router := server.Group("/api/messenger")
	router.GET("/ping", Ping)
	router.POST("/send/mail", SendMail)

	// Start the server
	log.Println("Starting server on port 8080")
	err = server.Run("localhost:8080")
	if err != nil {
		log.Fatal(err)
	}
}
