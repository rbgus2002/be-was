package webserver.http.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileMapper {

	private final List<String> resourceDirectoryPathList;

	public FileMapper() {
		resourceDirectoryPathList = new ArrayList<>();
		resourceDirectoryPathList.add("src/main/resources/static");
		resourceDirectoryPathList.add("src/main/resources/templates");
	}

	public File findFile(String path) {
		File file;
		for (String resourceDirectoryPath : resourceDirectoryPathList) {
			file = new File(resourceDirectoryPath + path);
			if (file.exists() && file.isFile()) {
				return file;
			}
		}
		return null;
	}
}
