package hei.school.sarisary.endpoint.rest.controller.sarisary;

import hei.school.sarisary.service.sarisary.SaryService;
import java.io.IOException;
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
}
