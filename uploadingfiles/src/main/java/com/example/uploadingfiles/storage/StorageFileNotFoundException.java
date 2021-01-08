package com.example.uploadingfiles.storage;

public class StorageFileNotFoundException extends StorageException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4740777507774975363L;
	
	StorageFileNotFoundException(String message){
		super(message);
	}
	
	StorageFileNotFoundException(String message, Throwable cause){
		super(message, cause);
	}
	
}
