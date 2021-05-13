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

    //DB
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)//id받았을 때, id값만 출력
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
        ArrayList<Map<String, Object>> postValueArrayList = null;//data 임시저장, replace에 사용

        if (id != null && !id.equals("")) {
            if (testDBHashMap.containsKey(id)) {//check DB has key
                postValueArrayList = testDBHashMap.get(id);//있으면 db값 가져옴
            } else {
                postValueArrayList = new ArrayList<Map<String, Object>>();//없으면 새로만듦
            }

            postValueArrayList.add(requestMap);

            if (testDBHashMap.containsKey(id)) {//있을경우 replace인데 안됨, 계속 추가함(Arraylist라서 순차적으로 들어감)
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

    @RequestMapping(value = "/put/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> putResponseEntity(HttpServletRequest request, @PathVariable String id, @RequestBody Map<String,Object> requestMap){
        ResponseEntity<?> responseEntity = null;
        ArrayList<Map<String,Object>> postValueArrayList = null;

        if(!testDBHashMap.isEmpty()){
            if(id!=null && !id.equals("")&&testDBHashMap.containsKey(id)){
            postValueArrayList = new ArrayList<Map<String,Object>>();//새로운 db
            postValueArrayList.add(requestMap);

            if(postValueArrayList.get(0).keySet().equals(testDBHashMap.get(id).get(0).keySet())){//keySet = hashMap에 저장된 모든 key
                testDBHashMap.replace(id, postValueArrayList);
                responseEntity = new ResponseEntity<>(requestMap, HttpStatus.OK);

            }else{
                responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.NOT_FOUND);
            }

            }else{
                responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.NOT_FOUND);
            }

        }else {
            responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}



