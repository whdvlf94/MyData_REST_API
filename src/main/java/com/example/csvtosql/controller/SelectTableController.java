package com.example.csvtosql.controller;

import com.example.csvtosql.repository.SqlToJsonRepository;
import com.example.csvtosql.security.DatabaseAccessServiceIm;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/{tableName}")
public class SelectTableController {


    @Autowired
    private SqlToJsonRepository repository;

    @Autowired
    private DatabaseAccessServiceIm databaseAccessServiceIm;

    //테이블 전체 조회
    //사용자가 입력한 key 값이 발급받은 Key 값과 다를 경우 데이터가 화면에 출력되지 않도록 설정
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getData(@PathVariable String tableName, @RequestParam("key") String uuid) throws SQLException, ClassNotFoundException, JSONException {
        String value = databaseAccessServiceIm.compareUuid(uuid);

        if (value == "1") {

            JSONArray jsonArray = repository.findAll(tableName);
            return String.valueOf(jsonArray);
        } else {
            return "Wrong Key Value!!";
        }

    }


    //테이블 특정 파라미터 값 조회, HttpServletRequest를 이용하여 동적으로 처리
    //@RequestParam의 경우 Entity가 정해져야 사용할 수 있기 때문에 사용 불가
    //사용자가 입력한 key 값이 발급받은 Key 값과 다를 경우 데이터가 화면에 출력되지 않도록 설정
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getDataByColNum(
            @PathVariable String tableName,
            @RequestParam("key") String uuid,
            HttpServletRequest request
    ) throws SQLException, ClassNotFoundException, JSONException, UnsupportedEncodingException {

        String value = databaseAccessServiceIm.compareUuid(uuid);

        if (value == "1") {

            String queryString = URLDecoder.decode(request.getQueryString(), "UTF-8");

            List<String> queryList = new ArrayList<>(Arrays.asList(queryString.split("&")));

            for(Iterator<String> it = queryList.iterator(); it.hasNext();){
                String listValue = it.next();
                if(listValue.startsWith("k")){
                    it.remove();
                }
            }

            Map<String, Object> queryMap = new HashMap<>();
            for (String query : queryList) {
                String[] queryTuple = query.split("=");
                queryMap.put(queryTuple[0], queryTuple[1]);
            }

            JSONArray jsonArrayCol = repository.findByColNum(tableName, queryMap);
            return String.valueOf(jsonArrayCol);

        } else {
            return "Wrong Key Value!!";
        }

    }

}


