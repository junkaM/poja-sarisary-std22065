package hei.school.sarisary.endpoint.rest.controller.sarisary;

import hei.school.sarisary.service.sarisary.SaryService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SaryController {

  @Autowired private SaryService saryService;

  @RequestMapping(
      method = RequestMethod.PUT,
      path = "/black-and-white/{id}",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String turnToBlackAndWhite(
      @PathVariable(name = "id") String id, @RequestPart MultipartFile file) throws IOException {
    System.out.println(file);
    return saryService.uploadTransformedImage(id, file);
  }

  @GetMapping("/black-and-white/{id}")
  public Map<String, String> getBlackAndWhiteImage(@PathVariable String id) {

    String originalUrl = saryService.getOriginalImageUrl(id);
    String transformedUrl = saryService.getTransformedImageUrl(id);

    Map<String, String> response = new HashMap<>();
    response.put("original_url", originalUrl);
    response.put("transformed_url", transformedUrl);
    return response;
  }
}
