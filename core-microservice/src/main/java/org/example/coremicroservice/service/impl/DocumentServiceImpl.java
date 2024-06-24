package org.example.coremicroservice.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.coremicroservice.config.properties.MinioProperties;
import org.example.coremicroservice.exception.ValidationException;
import org.example.coremicroservice.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("application/pdf", "image/jpeg", "image/png");

    private static final long MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024;

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void uploadingDocuments(String clientId, MultipartFile[] multipartFiles) {

        verifyDocuments(multipartFiles);

        for (var file : multipartFiles) {
            storeFile(file);
        }

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("registration-new-events-topic",
                clientId,
                Arrays.toString(multipartFiles));

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                LOGGER.error("Не удалось отправить сообщение: {}", exception.getMessage());
            } else {
                LOGGER.info("Сообщение успешно отправлено: {}", result.getRecordMetadata());
            }
        });

    }

    private void verifyDocuments(MultipartFile[] confirmationDocument) {
        for (var document : confirmationDocument) {
            if (document.isEmpty()) {
                throw new ValidationException("Файл пуст");
            }
            if (document.getContentType() != null && !ALLOWED_CONTENT_TYPES.contains(document.getContentType())) {
                throw new ValidationException("Тип файла должен быть pdf, jpg или png");
            }
            if (document.getSize() > MAX_FILE_SIZE_BYTES) {
                throw new ValidationException("Размер файла не должен превышать 10 мб");
            }
        }
    }

    @SneakyThrows
    private void storeFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID() + fileExtension;

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucket())
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1)
                .object(uniqueFilename)
                .build());
    }

}
