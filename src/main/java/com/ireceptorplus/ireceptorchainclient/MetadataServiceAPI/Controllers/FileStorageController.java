package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.FileStorage.DatasetStorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/file")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class FileStorageController
{

    @Autowired
    private DatasetStorageService storageService;

    @GetMapping("/{uuid}")
    @Operation(summary = "Returns all Dataset objects stored")
    public ResponseEntity<Resource> serveFile(@PathVariable String uuid)
    {
        Resource file = storageService.loadAsResource(uuid);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
