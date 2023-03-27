package com.fastroof.ftpr.service.filestorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorage {
    @Value("${link.to.file-storage.service}")
    private String linkToFileStorageService;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();

    public UploadFileResponse uploadFile(MultipartFile file) throws IOException {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getOriginalFilename(), RequestBody.create(file.getBytes()))
                .build();

        Request request = new Request.Builder()
                .url(this.linkToFileStorageService + "/files")
                .method("POST", body)
                .build();

        try (Response response = client.newCall(request).execute()){
            if (response.code() != 200) {
                throw new RuntimeException("File storage service error on file upload");
            }

            return objectMapper.readValue(response.body().string(), UploadFileResponse.class);
        }
    }

    public UploadImageResponse uploadImage(MultipartFile image) throws IOException {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", image.getOriginalFilename(), RequestBody.create(image.getBytes()))
                .build();

        Request request = new Request.Builder()
                .url(this.linkToFileStorageService + "/images")
                .method("POST", body)
                .build();

        try (Response response = client.newCall(request).execute()){
            if (response.code() != 200) {
                throw new RuntimeException("File storage service error on file upload");
            }

            return objectMapper.readValue(response.body().string(), UploadImageResponse.class);
        }
    }
}
