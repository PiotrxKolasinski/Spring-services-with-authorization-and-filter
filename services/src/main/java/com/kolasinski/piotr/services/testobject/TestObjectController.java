package com.kolasinski.piotr.services.testobject;

import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.services.util.UserHeaderExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test-objects")
public class TestObjectController {
    private final TestObjectService testObjectService;
    private final UserHeaderExtractor userHeaderExtractor;

    @Autowired
    public TestObjectController(TestObjectService testObjectService, UserHeaderExtractor userHeaderExtractor) {
        this.testObjectService = testObjectService;
        this.userHeaderExtractor = userHeaderExtractor;
    }

    @GetMapping("/{id}")
    @JsonView(TestObjectViews.Public.class)
    public TestObject findOneByIdAndUserId(@PathVariable long id, HttpServletRequest request) {
        long userId = userHeaderExtractor.extractUserId(request);
        return testObjectService.findOneByIdAndUserId(id, userId);
    }

    @PostMapping
    @JsonView(TestObjectViews.Public.class)
    public TestObject save(@RequestBody TestObject testObject, HttpServletRequest request) {
        long userId = userHeaderExtractor.extractUserId(request);
        return testObjectService.save(testObject, userId);
    }

    @PutMapping("/{id}")
    @JsonView(TestObjectViews.Public.class)
    public TestObject update(@PathVariable long id, @RequestBody TestObject testObject, HttpServletRequest request) {
        long userId = userHeaderExtractor.extractUserId(request);
        return testObjectService.update(id, testObject, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id, HttpServletRequest request) {
        long userId = userHeaderExtractor.extractUserId(request);
        testObjectService.delete(id, userId);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
