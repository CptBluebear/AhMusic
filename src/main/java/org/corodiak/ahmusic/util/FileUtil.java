package org.corodiak.ahmusic.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {
	
	public String save(MultipartFile multipartFile, String saveDir) {
		
		String newName = System.nanoTime() + multipartFile.getOriginalFilename();
		
		File file = new File(saveDir);
		
		file = new File(saveDir + "/" + newName);
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return newName;	
	}
	
}
