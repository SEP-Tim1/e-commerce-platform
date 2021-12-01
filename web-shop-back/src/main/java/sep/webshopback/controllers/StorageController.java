package sep.webshopback.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("storage")
public class StorageController {

    @Value("${web-shop-back.storage}")
    private String storageDirectoryPath;

    @GetMapping(value = "/media-content/{image:.+}")
    public @ResponseBody
    ResponseEntity<UrlResource> getImage(@PathVariable(name = "image") String image) {
        try {
            UrlResource resource = new UrlResource("file:" + storageDirectoryPath + File.separator + image);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaTypeFactory
                            .getMediaType(resource)
                            .orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(new UrlResource("file:" + storageDirectoryPath + File.separator + image));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
