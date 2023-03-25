package main

import (
	"github.com/gin-gonic/gin"
	"gopkg.in/gomail.v2"
	"io"
	"log"
	"mime/multipart"
	"net/http"
	"os"
)

type EmailDetails struct {
	Recipient  string                `form:"recipient" binding:"required"`
	MsgBody    string                `form:"msg_body" binding:"required"`
	Subject    string                `form:"subject" binding:"required"`
	Attachment *multipart.FileHeader `form:"attachment"`
}

// Ping is method for check messenger service alive
func Ping(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, "Messenger is alive")
}

// SendMail is method for sending a simple email or email with attachment
func SendMail(c *gin.Context) {
	var emailDetails EmailDetails
	if err := c.ShouldBind(&emailDetails); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	log.Println("Received Mail information : ", emailDetails)

	// get mail credentials
	from := os.Getenv("MAIL_USERNAME")
	password := os.Getenv("MAIL_PASSWORD")

	// create new message with NewMessage
	msg := gomail.NewMessage()

	// set message details
	msg.SetHeader("From", from)
	msg.SetHeader("To", emailDetails.Recipient)
	msg.SetHeader("Subject", emailDetails.Subject)
	msg.SetBody("text/plain", emailDetails.MsgBody)

	// create new file part for attachment if exist
	attachment := emailDetails.Attachment
	if attachment != nil {
		// open the uploaded file
		file, err := attachment.Open()
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		// close the uploaded file after finish
		defer func(file multipart.File) {
			err := file.Close()
			if err != nil {
				c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
				return
			}
		}(file)

		// add file part to message
		msg.Attach(attachment.Filename, gomail.SetCopyFunc(func(w io.Writer) error {
			_, err := io.Copy(w, file)
			return err
		}))
	}

	// send the email
	n := gomail.NewDialer("smtp.gmail.com", 587, from, password)
	if err := n.DialAndSend(msg); err != nil {
		panic(err)
	}

	c.IndentedJSON(http.StatusOK, "Mail Sent Successfully")
}
