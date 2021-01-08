package com.example.uploadingfiles.storage;

public class StorageException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3692286592359947241L;
	
	StorageException(String message){
		super(message);
	}
	
	StorageException(String message, Throwable cause){
		super(message, cause);
	}
}
