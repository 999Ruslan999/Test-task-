package org.example.coremicroservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/documents")
public interface UploadingDocumentsController {

    @PostMapping("/uploading")
    ResponseEntity<HttpStatus> uploadingDocuments(String clientId, MultipartFile[] multipartFiles);

}
