package com.wanderingwyatt.arkham.dao;

public class ArkhamHorrorDaoException extends Exception {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4312883347705798463L;

	public ArkhamHorrorDaoException() {
		// Empty constructor
	}

	public ArkhamHorrorDaoException(String message) {
		super(message);
	}

	public ArkhamHorrorDaoException(Throwable cause) {
		super(cause);
	}

	public ArkhamHorrorDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArkhamHorrorDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
