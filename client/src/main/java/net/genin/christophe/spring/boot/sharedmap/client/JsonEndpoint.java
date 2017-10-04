package net.genin.christophe.spring.boot.sharedmap.client;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class JsonEndpoint {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Value get(@RequestHeader HttpHeaders headers) {
        System.out.println();

        IMap<String, Integer> test = hazelcastInstance.getMap("test");

        return Optional.ofNullable(headers.get("X-Session"))
                .flatMap(l -> l.stream().findFirst())
                .map(uuid -> test.get(uuid))
                .map(n -> new Value(++n))
                .orElseGet(() -> new Value(0));


    }
}
