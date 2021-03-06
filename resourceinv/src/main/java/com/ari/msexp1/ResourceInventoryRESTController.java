package com.ari.msexp1;

import com.ari.msexp1.mongoDAL.DAL.ResourceDAL;
import com.ari.msexp1.mongoDAL.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("resourceinventory")
public class ResourceInventoryRESTController {
    private final ResourceDAL resourceDAL;

    @Autowired
    public ResourceInventoryRESTController(ResourceDAL resourceDAL) {
        this.resourceDAL = resourceDAL;
    }

    @GetMapping("/resources")
    public Flux<Resource> getResources() {
        return resourceDAL.findAll();
    }

    @GetMapping(value="/resource")
    public Mono<Resource> getResource(
            @RequestParam(value = "name",required=true) String name)
    {
        return resourceDAL.findOneByName(name);
    }

    @GetMapping(value="/resource/{id}")
    public Mono<Resource> getResourceById(
            @PathVariable("id") String id)
    {
        return resourceDAL.findOneById(id);
    }


}
