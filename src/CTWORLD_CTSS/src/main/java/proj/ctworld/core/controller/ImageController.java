package proj.ctworld.core.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import proj.ctworld.core.lib.Utils;
import proj.ctworld.core.service.ImageStorageService;

@RestController
@RequestMapping("/api/image")
public class ImageController {

	private static Logger logger = Logger.getLogger(ImageController.class);

	@Autowired
	ImageStorageService isService;

	@Autowired
	Utils utils;

	@RequestMapping(value = "/{isId}", method = RequestMethod.GET)
	public void showImage(HttpServletResponse response, @PathVariable Long isId) throws Exception {

		String location = isService.getLocationLarge(isId);
		// logger.debug(isId.toString()+" img location: "+location);
		if (!utils.existImgLocation(location))
			location = utils.getImgPlaceholder();

		File file = new File(location);
		InputStream fis = new FileInputStream(file);

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		byte[] buffer = new byte[10240];
		for (int length = 0; (length = fis.read(buffer)) > 0;) {
			responseOutputStream.write(buffer, 0, length);
		}
		responseOutputStream.flush();
		responseOutputStream.close();
		fis.close();
	}
}
