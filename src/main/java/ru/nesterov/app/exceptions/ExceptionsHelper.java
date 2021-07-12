package ru.nesterov.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Nesterov
 */
@ControllerAdvice
public class ExceptionsHelper {

        @ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
        @ExceptionHandler({MyNotFoundException.class})
        public @ResponseBody Map<String, String> notFound(){
                Map<String, String> body = new HashMap<String,String>();
                body.put("error", "записи не найдены");
                return body;
        }

        @ResponseStatus(value = HttpStatus.NO_CONTENT) // 204
        @ExceptionHandler({
                org.springframework.dao.EmptyResultDataAccessException.class,
                java.lang.IllegalArgumentException.class,
                java.util.NoSuchElementException.class
        })
        public void notExistedForDelete(){}

        @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE) // 406
        @ExceptionHandler(org.hibernate.id.IdentifierGenerationException.class)
        public @ResponseBody Map<String, String> idGen(){
                Map<String, String> body = new HashMap<String,String>();
                body.put("error", "не найдена ценная бумага с таким secid");
                return body;
        }

        @ResponseStatus(value= HttpStatus.BAD_REQUEST) //400
        @ExceptionHandler({
                javax.validation.ConstraintViolationException.class,
                NullPointerException.class})
        public @ResponseBody Map<String, String> notValid(){
                Map<String, String> body = new HashMap<String,String>();
                body.put("error", "неверные данные");
                return body;
        }

        @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE) // 415
        @ExceptionHandler(MyParseException.class)
        public @ResponseBody Map<String, String> parsingFault(){
                Map<String, String> body = new HashMap<String,String>();
                body.put("error", "неверное содержимое файлов");
                return body;
        }
}
