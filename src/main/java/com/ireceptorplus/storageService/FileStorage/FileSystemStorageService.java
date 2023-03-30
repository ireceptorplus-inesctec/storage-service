package com.ireceptorplus.storageService.FileStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import com.ireceptorplus.storageService.Utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;
	@Value("${blockchain-api.hyperledger-fabric.blockchain.network-config-path}")
	private String configPath;
	@Value("${blockchain-api.hyperledger-fabric.blockchain.network-name}")
	private String networkName;

	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file)
	{
		store(file, file.getOriginalFilename());
	}

	@Override
	public void store(MultipartFile file, String customName) {
		System.out.println(configPath);
		System.out.println(networkName);
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}

			Path destinationFile = this.rootLocation.resolve(
					Paths.get(customName))
					.normalize().toAbsolutePath();
			if (!Files.exists(this.rootLocation))
				Files.createDirectories(this.rootLocation);

			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	public String computeSHA256ChecksumOfFile(String filename)
	{
		File file = new File(load(filename).toString());
		try
		{
			return FileUtils.getFileSHA256Checksum(file);
		} catch (NoSuchAlgorithmException e)
		{
			throw new StorageException("Error computing SHA256 checksum of file: " + filename);
		} catch (IOException e)
		{
			throw new StorageException("Error computing SHA256 checksum of file: " + filename);
		}
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
