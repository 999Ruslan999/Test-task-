package org.example.coremicroservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    void uploadingDocuments(String clientId, MultipartFile[] multipartFiles);


}
