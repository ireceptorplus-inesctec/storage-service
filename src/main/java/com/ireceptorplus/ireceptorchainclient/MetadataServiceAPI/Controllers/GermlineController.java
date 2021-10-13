package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.GermlineService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.ToolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("germline")
public class GermlineController
{

    @Autowired
    private final GermlineService germlineService;

    @Autowired
    private ModelMapper modelMapper;


    public GermlineController(GermlineService germlineService)
    {
        this.germlineService = germlineService;
    }


}
