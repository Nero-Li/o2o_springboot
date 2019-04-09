package com.lym.other;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lym.entity.Area;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JackSonDemo
 * @Description TODO
 * @Author lyming
 * @Date 2019/4/4 1:07 PM
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class JackSonDemo {
    private ObjectMapper objectMapper = null;
    private String jsonString;
    private Area area;

    /**
     * Junit的方法，用于给每个单元测试添加前置条件和结束条件
     */
    @Before
    public void init() {
        // 构建一个Area实例对象并赋值
        area = new Area();
        area.setAreaId(123);
        area.setAreaName("老人与海");
        area.setCreateTime(new Date());
        area.setPriority(30);
        objectMapper = new ObjectMapper();
        try {
//            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
//                    System.out, JsonEncoding.UTF8);
            jsonString = objectMapper.writeValueAsString(area);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destory() {
        try {
            objectMapper = null;
            area = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/********************** java常见数据类型转JSON ****************************/
    /**
     */
    @Test
    public void javaBeanToJson() {

        try {
            System.out.println(jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listAndmap() {
        try {
            List<Area> list = new ArrayList<Area>();
            Area area1 = new Area();
            area1.setAreaName("安徒生");
            area1.setAreaId(456);
            area1.setCreateTime(new Date());
            area1.setPriority(55);
            Area area2 = new Area();
            area2.setAreaName("安徒生");
            area2.setAreaId(456);
            area2.setCreateTime(new Date());
            area2.setPriority(55);
            list.add(area1);
            list.add(area2);

            Area area3 = objectMapper.readValue(jsonString, Area.class);
            System.out.println(area3);

            String text = objectMapper.writeValueAsString(list);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            List<Area> newAreaList = objectMapper.readValue(text, javaType);
            List<Area> newAreaList2 = objectMapper.readValue(text, new TypeReference<List<Area>>() {
            });
            System.out.println(newAreaList);
            System.out.println(newAreaList2);

            Map<String, Object> map = new HashMap<>();
            map.put("age", 25);
            map.put("name", "yitian");
            map.put("interests", new String[]{"pc games", "music"});
            String text2 = objectMapper.writeValueAsString(map);
            JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Object.class);
            Map<String, Object> map2 = objectMapper.readValue(text2, javaType2);
            Map<String, Object> map3 = objectMapper.readValue(text2, new TypeReference<Map<String, Object>>() {
            });
            System.out.println(map2);
            System.out.println(map3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
