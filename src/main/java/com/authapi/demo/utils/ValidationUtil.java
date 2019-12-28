package com.authapi.demo.utils;

import java.util.Date;
import java.util.Map;

import org.springframework.validation.FieldError;


public interface ValidationUtil {

	FieldError userNameValidation(String path, String maxLength, String minLength, boolean isRequired, boolean isUnique, String objectName, String dataField, String msg);
	FieldError passwordValidation(String path, String maxLength, String minLength, boolean isRequired, boolean isUnique, String objectName, String dataField, String msg);

}
