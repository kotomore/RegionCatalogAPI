package ru.kotomore.regioncatalogapi.advices;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.kotomore.regioncatalogapi.dto.ErrorMessage;
import ru.kotomore.regioncatalogapi.exceptions.BadRequestRegionException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotDeletedException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotFoundException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotSavedException;

@ControllerAdvice
public class RegionExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(RegionNotFoundException.class)
    public ResponseEntity<?> regionNotFoundException(RegionNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(RegionNotDeletedException.class)
    public ResponseEntity<?> regionNotDeletedException(RegionNotDeletedException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(RegionNotSavedException.class)
    public ResponseEntity<?> regionNotSavedException(RegionNotSavedException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(BadRequestRegionException.class)
    public ResponseEntity<?> badRequestRegionException(BadRequestRegionException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}