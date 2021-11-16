package com.ireceptorplus.ireceptorchainclient.DatasetStorage.storage;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public interface StorageService {

	void init();

	void store(MultipartFile file);

	void store(MultipartFile file, String customName);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

}
