package com.fastroof.security.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 * The MessageResponse Class.
 */
@Getter
@Setter
public class MessageResponse {
	
	/** The message. */
	private String message;

	/**
	 * Instantiates a new message response.
	 *
	 * @param message the message
	 */
	public MessageResponse(String message) {
	    this.message = message;
	  }
}
