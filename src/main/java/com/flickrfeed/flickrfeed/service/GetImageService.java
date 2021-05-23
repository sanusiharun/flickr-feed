package com.flickrfeed.flickrfeed.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.flickrfeed.flickrfeed.model.response.GetImageResponse;

@Service
public class GetImageService {

	public GetImageResponse getImage(String link) {
		URL url;
		byte[] response = null;
		String[] destination = null;
		int idx;
		String filename = null;
		GetImageResponse imageResponse = new GetImageResponse();
		try {
			url = new URL(link);

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

			destination = link.split("/");
			idx = destination.length - 1;
			filename = destination[idx].toString();

			FileOutputStream fos = new FileOutputStream("../flickr-feed/src/main/resources/" + filename);
			fos.write(response);
			fos.close();

			
			imageResponse = GetImageResponse.builder().filename(filename).resource(response)
					.link("../flickr-feed/src/main/resources/" + filename).build();

			Path path = Paths.get(imageResponse.getLink());
			imageResponse.setFile(new UrlResource(path.toUri()));
		} catch (IOException e) {
			return imageResponse;
		}

		return imageResponse;
	}
}
