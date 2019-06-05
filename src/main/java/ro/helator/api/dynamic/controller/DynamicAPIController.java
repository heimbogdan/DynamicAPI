package ro.helator.api.dynamic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.helator.api.dynamic.DynamicAPIApplication;
import ro.helator.api.dynamic.service.DynamicAPIService;
import ro.helator.api.dynamic.service.def.ServiceModel;

@RestController
@RequestMapping("/dynamic/api")
public class DynamicAPIController {

    @Autowired
    private DynamicAPIService dynamicAPIService;

    @PostMapping("/add")
    public String add(@RequestBody ServiceModel serviceModel) throws Exception {

        dynamicAPIService.generateAPI(serviceModel);
        DynamicAPIApplication.restart();
        return "ok";
    }
}
