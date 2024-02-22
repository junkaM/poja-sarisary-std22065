package hei.school.sarisary.service.sarisary;

import hei.school.sarisary.file.BucketComponent;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class SaryService {

  @Autowired private BucketComponent bucketComponent;
  private final String directory = "sary/";

  public File ConvertToBlackAndWhite(String name, File saryOriginal) throws IOException {

    String extension = FilenameUtils.getExtension(saryOriginal.getName());
    ImagePlus openedSaryOriginal = IJ.openImage(saryOriginal.getPath());

    new ImageConverter(openedSaryOriginal).convertToGray32();

    File saryBlackAndWhite = File.createTempFile(name + "-N&B", "." + extension);

    ImageIO.write(openedSaryOriginal.getBufferedImage(), extension, saryBlackAndWhite);
    return saryBlackAndWhite;
  }

  public String uploadTransformedImage(String id, MultipartFile sary) throws IOException {

    String suffix = "." + FilenameUtils.getExtension(sary.getOriginalFilename());

    String originalImagePrefix = id + "-original";
    String transformedImagePrefix = id + "-B&W";

    String bucketKeyOriginalImage = directory + originalImagePrefix + suffix;
    String bucketKeyTransformedImage = directory + transformedImagePrefix + suffix;

    File toUpload = File.createTempFile(originalImagePrefix, suffix);
    sary.transferTo(toUpload);
    File blackandWhiteImage = this.ConvertToBlackAndWhite(id, toUpload);

    bucketComponent.upload(toUpload, bucketKeyOriginalImage);
    bucketComponent.upload(blackandWhiteImage, bucketKeyTransformedImage);

    return bucketComponent.presign(bucketKeyTransformedImage, Duration.ofMinutes(30)).toString();
  }

  public String getOriginalImageUrl(String id) {
    URL originalUrl = bucketComponent.presign(id + "/original.png", Duration.ofMinutes(20));
    return originalUrl.toString();
  }

  public String getTransformedImageUrl(String id) {
    URL transformedUrl = bucketComponent.presign(id + "/transformed.png", Duration.ofMinutes(20));
    return transformedUrl.toString();
  }
}
