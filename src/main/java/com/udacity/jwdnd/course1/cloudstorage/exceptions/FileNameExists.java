package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartException;

public class FileNameExists extends MultipartException {

    public FileNameExists(String msg) {
        super(msg);
    }

    public FileNameExists(String msg, Throwable cause) {
        super(msg, cause);
    }
}
