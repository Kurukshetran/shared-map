package net.genin.christophe.spring.boot.sharedmap;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/api")
public class JsonEndpoint {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private CallClient.Cli clientRest;


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Result get(){
        String uuid = UUID.randomUUID().toString();
        IMap<String, Integer> test = hazelcastInstance.getMap("test");
        test.put(uuid, 1, 1, TimeUnit.MINUTES);

        CallClient.Cli.Value value = clientRest.get(uuid);
        System.out.println(value.nb);

        return new Result("master", value.nb);
    }
}
