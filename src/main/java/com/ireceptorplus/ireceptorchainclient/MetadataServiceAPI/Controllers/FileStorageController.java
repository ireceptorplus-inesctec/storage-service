package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.FileStorage.DatasetStorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

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

    @GetMapping("/{uuid}/getFirstLines")
    public @ResponseBody String[] getFirstLinesOfFile(@PathVariable String uuid, @RequestParam int numberOfLines) throws IOException
    {
        Path path = storageService.load(uuid);
        BufferedReader reader;
        ArrayList<String> lines = new ArrayList<>();
        try
        {
            reader = new BufferedReader(new FileReader(path.toFile().getAbsolutePath()));
            String line = reader.readLine();
            for (int i = 0; i < numberOfLines && line != null; i++)
            {
                line = reader.readLine();
                lines.add(line);
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        String[] linesArr = new String[lines.size()];
        lines.toArray(linesArr);

        return linesArr;
    }
}
