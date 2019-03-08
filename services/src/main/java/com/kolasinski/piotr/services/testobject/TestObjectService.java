package com.kolasinski.piotr.services.testobject;

import com.kolasinski.piotr.services.exception.EntityNotFoundException;
import com.kolasinski.piotr.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class TestObjectService {
    private final static Logger logger = Logger.getLogger(TestObjectService.class.getName());

    private final TestObjectRepository testObjectRepository;
    private final UserService userService;

    @Autowired
    public TestObjectService(TestObjectRepository testObjectRepository, UserService userService) {
        this.testObjectRepository = testObjectRepository;
        this.userService = userService;
    }

    public TestObject findOneByIdAndUserId(long id, long userId) {
        TestObject existingTestObject = testObjectRepository.findById(id).orElse(null);

        if (existingTestObject == null) {
            throw new EntityNotFoundException("Cannot find test object with id: " + id);
        }

        if (existingTestObject.getUser().getId() != userId) {
            throw new IllegalStateException("Wrong user id");
        }

        return existingTestObject;
    }

    public TestObject save(TestObject testObjectTemplate, long userId) {
        testObjectTemplate.setUser(userService.findOne(userId));

        TestObject testObject = testObjectRepository.save(testObjectTemplate);

        logger.info("[TestObjectService] save test object with id: " + testObject.getId());
        return testObject;
    }

    public TestObject update(long id, TestObject testObjectTemplate, long userId) {
        TestObject existingTestObject = findOneByIdAndUserId(id, userId);

        existingTestObject.setName(testObjectTemplate.getName());
        existingTestObject.setType(testObjectTemplate.getType());
        existingTestObject.setDate(testObjectTemplate.getDate());

        logger.info("[TestObjectService] update test object with id: " + id);
        return testObjectRepository.save(existingTestObject);
    }

    public void delete(long id, long userId) {
        TestObject existingTestObject = findOneByIdAndUserId(id, userId);
        testObjectRepository.deleteById(existingTestObject.getId());
        logger.info("[TestObjectService] delete test object with id: " + id);
    }
}
