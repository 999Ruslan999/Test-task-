package org.example.coremicroservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.example.coremicroservice.controller.UploadingDocumentsController;
import org.example.coremicroservice.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadingDocumentsControllerImpl implements UploadingDocumentsController {

    private final DocumentService documentService;

    @Override
    public ResponseEntity<HttpStatus> uploadingDocuments(String clientId, MultipartFile[] multipartFiles) {

        documentService.uploadingDocuments(clientId, multipartFiles);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}

