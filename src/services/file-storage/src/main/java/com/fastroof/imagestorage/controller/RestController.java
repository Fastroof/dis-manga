package com.fastroof.imagestorage.controller;

import com.fastroof.imagestorage.entity.ProductImage;
import com.fastroof.imagestorage.repository.ProductImageRepository;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*")
public class RestController {

    private final ProductImageRepository productImageRepository;
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
