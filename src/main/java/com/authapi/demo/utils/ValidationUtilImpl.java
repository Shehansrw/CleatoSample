package com.authapi.demo.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service("validationUtil")
public class ValidationUtilImpl implements ValidationUtil{

	@Autowired
	MessageSource messageSource;

	@Override
	public FieldError userNameValidation(String path, String maxLength, String minLength, boolean isRequired, boolean isUnique, String objectName, String dataField, String msg) {
		FieldError fieldError = null;
		if (path == null || (path != null && path.isEmpty() && isRequired)) {
			fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.not.empty", null, Locale.getDefault()));
			return fieldError;
		} else if (path != null && path.isEmpty() && !isRequired) {
			return null;
		} else if (path != null && !path.isEmpty() && Pattern.matches("^([a-zA-Z]{2,}[ -]{1}){0,}[a-zA-Z]{2,}$", path)) {
			if (!Pattern.matches("[a-zA-Z -]{2,"+maxLength+"}$", path)) {
				fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.min.name",
						new String[] { "2", maxLength }, Locale.getDefault()));
				return fieldError;
			} else if (path != null && !path.isEmpty() && isUnique) {
				fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.unique", new String[] { "'"+msg+"'" }, Locale.getDefault()));
				return fieldError;
			}
		} else {
			fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.valid.name", null, Locale.getDefault()));
			return fieldError;
		}
		return fieldError;
	}

	@Override
	public FieldError passwordValidation(String path, String maxLength, String minLength, boolean isRequired, boolean isUnique, String objectName, String dataField, String msg) {
		FieldError fieldError = null;

		if(path == null || path.isEmpty()) {
			fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.not.empty", null, Locale.getDefault()));
			return fieldError;
		}else {
			if (path.length() > Integer.parseInt(maxLength)) {
				fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.length.max", new String[] { "'"+maxLength+"'" }, Locale.getDefault()));
				return fieldError;
			}else if (path.length() < Integer.parseInt(minLength)){
				fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.length.min", new String[] { "'"+minLength+"'" }, Locale.getDefault()));
				return fieldError;
			/*}else if (!Pattern.matches("^(?=\\S+$)$",path)) {
				fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.white.space", null, Locale.getDefault()));
				return fieldError;*/
			/*}else if (!Pattern.matches("^(?=.*[@#$%^&+=])$",path)) {
				fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.special.char", null, Locale.getDefault()));
				return fieldError;*/
			}else if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])$",path)) {
				fieldError = new FieldError(objectName, dataField, messageSource.getMessage("validation.valid.password.char", null, Locale.getDefault()));
				return fieldError;
			}
		}
		return fieldError;
	}
}
