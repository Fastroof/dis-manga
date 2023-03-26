package com.fastroof.imagestorage.controller;

import com.fastroof.imagestorage.entity.ProductImage;
import com.fastroof.imagestorage.repository.ProductImageRepository;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import okhttp3.RequestBody;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*")
public class RestController {

    private final ProductImageRepository productImageRepository;

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "dis-manga";

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final Set<String> SCOPES = DriveScopes.all();


    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException, GeneralSecurityException {
        String serviceAccount = "dis-manga@dis-manga.iam.gserviceaccount.com";
        InputStream inputStream = new FileInputStream("D:\\key.p12");

        return new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(serviceAccount)
                .setServiceAccountPrivateKeyFromP12File(inputStream)
                .setServiceAccountScopes(SCOPES)
                .build();
    }

    @GetMapping("/files")
    public List<File> getFiles() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name, webViewLink, webContentLink)")
                .execute();

        List<File> files = result.getFiles();

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        } else {
            return files;
        }
    }

    @PostMapping("/files")
    public String postFile(@RequestParam MultipartFile file)
            throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        java.io.File fileToUpload = convertMultipartFileToFile(file);

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        FileContent mediaContent = new FileContent("*/*", fileToUpload);

        try {
            File uploadedFile = service
                    .files()
                    .create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            Permission anyoneCanReadPermission = new Permission();
            anyoneCanReadPermission.setType("anyone");
            anyoneCanReadPermission.setRole("reader");

            service
                    .permissions()
                    .create(uploadedFile.getId(), anyoneCanReadPermission)
                    .execute();

            return uploadedFile.getId();
        } catch (GoogleJsonResponseException e) {
            return e.getMessage();
        }
    }


    private java.io.File convertMultipartFileToFile(MultipartFile file) throws IOException {
        java.io.File convertedFile = new java.io.File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }


    private String status = "OK";

    @Autowired
    public RestController(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @GetMapping("/")
    public String alive() throws InterruptedException {
        if (!Objects.equals(status, "OK")) {
            Thread.sleep(10 * 1000);
        }
        return "Слава Україні";
    }

    @GetMapping("/untested-request")
    public String broke() {
        status = "FAILED";
        return "Broken";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam MultipartFile file, @RequestParam Long product_id) throws IOException {
        if (product_id == null) {
            return "Не введено product_id";
        }
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getOriginalFilename(), RequestBody.create(file.getBytes()))
                .build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .method("POST", body)
                .addHeader("Authorization", "Client-ID aaa886b1142464d")
                .addHeader("Token", "Bearer ceca672a398de57e9932703bc43c282cabb029fe")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = Objects.requireNonNull(response.body()).string();
        if (respBody.contains("\"success\":true")) {
            Pattern pattern = Pattern.compile("\"(https.*?)\"");
            Matcher matcher = pattern.matcher(respBody);
            if (matcher.find()) {
                ProductImage productImage = new ProductImage();
                productImage.setProductId(product_id);
                productImage.setFile(matcher.group(1).replaceAll("\\\\/", "/"));
                //productImageRepository.save(productImage);
            }
        }
        return respBody;
    }

    @PutMapping("/{id}")
    public String updateImage(@PathVariable Long id, @RequestParam Long product_id) {
        if (product_id == null) {
            return "Не введено product_id";
        }
        Optional<ProductImage> optionalProductImage = productImageRepository.findById(id);
        if (optionalProductImage.isPresent()) {
            ProductImage productImage = optionalProductImage.get();
            productImage.setProductId(product_id);
            productImageRepository.save(productImage);
            return productImage.toString();
        }
        return "Трапилася помилка";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteImage(@PathVariable Long id) {
        try {
            productImageRepository.deleteById(id);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "true";
    }

    @GetMapping("/products-images")
    public Iterable<ProductImage> getProductImages() {
        return productImageRepository.findAll();
    }

}
