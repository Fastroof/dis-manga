package com.fastroof.imagestorage.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The RestController Class.
 */
@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*")
public class RestController {

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "dis-manga";

    /** Files will be uploaded to folder with FOLDER_ID. */
    private static final String FOLDER_ID = "1knOgegL95YzXktDb_3YoHS1HKljxFNMz";

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final Set<String> SCOPES = DriveScopes.all();

    /** The http transport. */
    // Build a new authorized API client service.
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    
    /** The Google Drive service. */
    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();


    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     * @throws GeneralSecurityException the general security exception
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

    /**
     * Gets the files.
     *
     * @return the list of files
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @GetMapping("/files")
    public List<File> getFiles() throws IOException {

        FileList result = service.files().list()
                .setPageSize(10)
                .setQ("mimeType='application/pdf'")
                .set("trashed", false)
                .setFields("nextPageToken, files(id, name, webViewLink, webContentLink)")
                .execute();

        List<File> files = result.getFiles();

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        } else {
            files.forEach(file ->
                    // Add embeddable link
                    file.set("previewLink", file.getWebViewLink().replace("/view?usp=drivesdk", "/preview"))
            );
            return files;
        }
    }

    /**
     * Post file to Google Drive.
     *
     * @param file the file
     * @return the response entity
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @PostMapping("/files")
    public ResponseEntity<?> postFile(@RequestParam MultipartFile file)
            throws IOException {

        // Convert MultiPartFile to java.io.File
        java.io.File fileToUpload = convertMultipartFileToFile(file);

        // Create file metadata(name, parent folder)
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(List.of(FOLDER_ID));
        // Create file contents(pdf)
        FileContent mediaContent = new FileContent("application/pdf", fileToUpload);

        try {
            // Upload file to Google Drive
            File uploadedFile = service
                    .files()
                    .create(fileMetadata, mediaContent)
                    .setFields("id, name, webViewLink, webContentLink")
                    .execute();

            // Make file readable by anyone
            Permission anyoneCanReadPermission = new Permission();
            anyoneCanReadPermission.setType("anyone");
            anyoneCanReadPermission.setRole("reader");

            service
                    .permissions()
                    .create(uploadedFile.getId(), anyoneCanReadPermission)
                    .execute();

            // Add embeddable link
            uploadedFile.set("previewLink", uploadedFile.getWebViewLink().replace("/view?usp=drivesdk", "/preview"));

            return ResponseEntity
                    .ok(uploadedFile);
        } catch (GoogleJsonResponseException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Helper method to convert multipart file to file.
     *
     * @param file the file
     * @return the java.io. file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private java.io.File convertMultipartFileToFile(MultipartFile file) throws IOException {
        java.io.File convertedFile = new java.io.File("uploads/" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    /**
     * Delete file by id.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/files/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable String id) {
        try {
            service.files().delete(String.valueOf(id)).execute();
            return ResponseEntity
                    .ok("Successful");
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    /**
     * Instantiates a new rest controller.
     *
     * @throws GeneralSecurityException the general security exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Autowired
    public RestController() throws GeneralSecurityException, IOException {
    }

    /**
     * Return string if service is alive
     *
     * @return the string, if alive
     */
    @GetMapping("/")
    public String alive() {
        return "Слава Україні";
    }

    /**
     * Post image to imgur.
     *
     * @param file the file
     * @return the response entity
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @PostMapping({"/images"})
    public ResponseEntity<String> postImage(@RequestParam MultipartFile file) throws IOException {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getOriginalFilename(), RequestBody.create(file.getBytes()))
                .build();

        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .method("POST", body)
                .addHeader("Authorization", "Client-ID aaa886b1142464d")
                .addHeader("Token", "Bearer ceca672a398de57e9932703bc43c282cabb029fe")
                .build();

        Response response = client
                .newCall(request)
                .execute();

        String respBody = Objects.requireNonNull(response.body()).string();
        return respBody.contains("\"success\":true") ? ResponseEntity.ok(respBody) : ResponseEntity.badRequest().body(respBody);
    }

}
