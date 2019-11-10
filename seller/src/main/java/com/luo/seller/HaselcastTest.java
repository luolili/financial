package com.luo.seller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.luo.seller.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class HaselcastTest {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @PostConstruct
    public void test() {
        Map map = hazelcastInstance.getMap("mee");
        ((IMap<Object, Object>) map).put("name", "mee");
    }


}
