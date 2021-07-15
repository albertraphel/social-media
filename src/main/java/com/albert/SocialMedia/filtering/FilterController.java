package com.albert.SocialMedia.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilterController {

    @GetMapping("/filtering")
    public SomeBean retrieveSomeBean() {
        return new SomeBean("value 1", "value 2", "value 3");
    }

    @GetMapping("/filtering-list")
    public List<SomeBean> retrieveListOfSomeBean() {
        return Arrays.asList(new SomeBean("value 1", "value 2", "value 3"),
                new SomeBean("value 4", "value 5", "value 6"));
    }

    @GetMapping("/dynamic-filtering")
    public MappingJacksonValue retrieveDynamicSomeBean() {
        SomeBean someBean = new SomeBean("value 1", "value 2", "value 3");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("someBeanFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);

        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping("/dynamic-filtering-list")
    public MappingJacksonValue retrieveDynamicListOfSomeBean() {


        List<SomeBean> someBeanList = Arrays.asList(new SomeBean("value 1", "value 2", "value 3"),
                new SomeBean("value 4", "value 5", "value 6"));

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field3");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("someBeanFilter", simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBeanList);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

}
