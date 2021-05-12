package kr.ac.jbnu.ampm.Leehayan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class Controller {
    //get부터 id로 받는거 -> type으로 받기 post, delete
    //1. testdb 2. get all 3. get id 4. post 5. delete
    private static HashMap<String, ArrayList<Map<String, Object>>> testDBHashMap = new HashMap<String, ArrayList<Map<String, Object>>>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAllResponseEntity(HttpServletRequest request) {
        ResponseEntity<?> responseEntity = null;

        if (!testDBHashMap.isEmpty()) {
            responseEntity = new ResponseEntity<>(testDBHashMap, HttpStatus.OK);

        } else {
            responseEntity = new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getResponseEntity(HttpServletRequest request, @PathVariable String id) {
        ResponseEntity<?> responseEntity = null;

        if (!testDBHashMap.isEmpty()) {
            if (id != null && !id.equals("") && testDBHashMap.containsKey(id)) {
                responseEntity = new ResponseEntity<>(testDBHashMap.get(id), HttpStatus.OK);

            } else {
                responseEntity = new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
            }

        } else {
            responseEntity = new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> postResponseEntity(HttpServletRequest request, @PathVariable String id, @RequestBody Map<String, Object> requestMap) {
        ResponseEntity<?> responseEntity = null;
        ArrayList<Map<String, Object>> postValueArrayList = null;

        if (id != null && !id.equals("")) {

            if (testDBHashMap.containsKey(id)) {
                postValueArrayList = testDBHashMap.get(id);
            } else {
                postValueArrayList = new ArrayList<Map<String, Object>>();
            }

            postValueArrayList.add(requestMap);

            if (testDBHashMap.containsKey(id)) {
                testDBHashMap.replace(id, postValueArrayList);
            } else {
                testDBHashMap.put(id, postValueArrayList);
            }

            responseEntity = new ResponseEntity<>(requestMap, HttpStatus.OK);

        } else {
            responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> deleteResponseEntity(HttpServletRequest request, @PathVariable String id) {
        ResponseEntity<?> responseEntity = null;

        if (id != null && !id.equals("")) {

            if (testDBHashMap.containsKey(id)) {
                testDBHashMap.remove(id);
                responseEntity = new ResponseEntity<>("", HttpStatus.OK);

            } else {
                responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.BAD_REQUEST);
            }

        } else {
            responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}



