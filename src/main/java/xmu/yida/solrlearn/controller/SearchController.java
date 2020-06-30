package xmu.yida.solrlearn.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xmu.yida.solrlearn.domain.fact.Fact;
import xmu.yida.solrlearn.service.SearchService;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class SearchController {

    @Resource(name = "searchServiceImpl")
    SearchService searchServiceImpl;


    @HystrixCommand(fallbackMethod = "querySimilarityFallbackMethod", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000")
    })
    @GetMapping("/query")
    Object similarity(@RequestParam String content){
        List<Fact> facts= searchServiceImpl.similarity(content);
        return facts;
    }


    public String querySimilarityFallbackMethod(){
        return "语义查询超时！查询失败";
    }
}
