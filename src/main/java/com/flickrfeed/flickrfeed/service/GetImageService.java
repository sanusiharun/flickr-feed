package com.flickrfeed.flickrfeed.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.flickrfeed.flickrfeed.model.entity.Media;
import com.flickrfeed.flickrfeed.model.response.GetImageResponse;
import com.flickrfeed.flickrfeed.repository.MediaRepository;

@Service
public class GetImageService {

	@Autowired
	ServletContext context;

	MediaRepository mediaRepository;

	public GetImageService(MediaRepository mediaRepository) {
		this.mediaRepository = mediaRepository;
	}

	public GetImageResponse getImage(String title) {
		URL url;
		byte[] response = null;
		String[] destination = null;
		int idx;
		String filename = null;
		Media urlImage = getUrlImage(title);

		if (urlImage == null)
			return null;

		GetImageResponse imageResponse = new GetImageResponse();
		try {
			destination = urlImage.getM().split("/");
			idx = destination.length - 1;
			filename = destination[idx].toString();
			File file = new File(".");
			String directoryName = file.getAbsolutePath() + File.separator + "image";
			
			url = new URL(urlImage.getM());

			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			response = out.toByteArray();

			File directory = new File(String.valueOf(directoryName));
			boolean exists =  new File(directoryName, filename).exists();
			Path path = Paths.get(directoryName, filename);
			if(exists) {
				boolean deleteIfExists = Files.deleteIfExists(path);
				if(deleteIfExists) {
					Files.write(path, response);
				}
			}else {
				if (!directory.exists()) {

					directory.mkdir();
					if (!file.exists()) {
						file.getParentFile().mkdir();
					}
				}
				Files.write(path, response);
//				FileOutputStream fos = new FileOutputStream(
//						file.getAbsolutePath() + File.separator + "image" + File.separator + filename);
//				fos.write(response);
//				fos.close();
			}

			imageResponse = GetImageResponse.builder().filename(filename).resource(response)
					.link(file.getAbsolutePath() + File.separator + "image" + File.separator + filename).build();

			Path path1 = Paths.get(imageResponse.getLink());
			imageResponse.setFile(new UrlResource(path1.toUri()));
		} catch (IOException e) {
			return imageResponse;
		}

		return imageResponse;
	}

	private Media getUrlImage(String title) {
		Media getUrl = mediaRepository.findByItemTitle(title);
		return getUrl;
	}
}
