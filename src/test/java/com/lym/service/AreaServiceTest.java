package com.lym.service;

import com.lym.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest  {

	@Autowired
	private AreaService areaService;
    @Autowired
    private CacheService cacheService;
	
	@Test
	public void testGetAreaList() {
		List<Area> areaList  = areaService.getAreaList();
		System.out.println(areaList.get(0).getAreaName());
        cacheService.removeFromCache(AreaService.AREALISYKEY);
        List<Area> areaList2 = areaService.getAreaList();
        System.out.println(areaList2.get(0).getAreaName());

	}
}
