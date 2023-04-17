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

/**
 * The FileStorage Service Class.
 */
@Service
public class FileStorage {
    
    /** The link to file storage service. */
    @Value("${link.to.file-storage.service}")
    private String linkToFileStorageService;
    
    /** The objectMapper Constant. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    /** The OkHttpClient Constant. */
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    /**
     * Upload file using external FileStorage Service.
     *
     * @param file the file
     * @return the upload file response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public UploadFileResponse uploadFile(MultipartFile file) throws IOException {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getOriginalFilename(), RequestBody.create(file.getBytes()))
                .build();

        Request request = new Request.Builder()
                .url(this.linkToFileStorageService + "/files")
                .method("POST", body)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            if (response.code() != 200) {
                throw new RuntimeException("File storage service error on file upload");
            }

            return OBJECT_MAPPER.readValue(response.body().string(), UploadFileResponse.class);
        }
    }

    /**
     * Upload image using external FileStorage Service.
     *
     * @param image the image
     * @return the upload image response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public UploadImageResponse uploadImage(MultipartFile image) throws IOException {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", image.getOriginalFilename(), RequestBody.create(image.getBytes()))
                .build();

        Request request = new Request.Builder()
                .url(this.linkToFileStorageService + "/images")
                .method("POST", body)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            if (response.code() != 200) {
                throw new RuntimeException("File storage service error on file upload");
            }

            return OBJECT_MAPPER.readValue(response.body().string(), UploadImageResponse.class);
        }
    }
}
