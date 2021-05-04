package kr.ac.jbnu.ampm.Leehayan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> responseEntity(HttpServletRequest request, @PathVariable String id){
        ResponseEntity<?> responseEntity = null;
        Map<String, Object> voMap = null;
        if (id != null && !id.equals("")){
            voMap = new HashMap<String, Object>(); //vo사용

            voMap.put("name", "이하얀");
            voMap.put("age", "22");
            voMap.put("books", new HashMap<String, Object>() {{
                put("book1", "마션");
                put("book2", "소프트웨어공학개론");

                put("book3", new HashMap<String, Object>(){{
                    put("name", "컴퓨터구조");
                }});
            }});

            responseEntity = new ResponseEntity<>(voMap, HttpStatus.OK);

        }else {
            responseEntity = new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }
}


