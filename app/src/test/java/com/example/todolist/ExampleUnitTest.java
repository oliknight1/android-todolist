package com.example.todolist;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    // Should pass if the number is 0 or less
    @Test
    public void testValidateNumTrue() {
        int num = 4432;
        boolean result = Validator.validateNum(num);
        assertTrue("The test is true", result);
    }

    @Test
    public void validateLength() {
        String text = " test";
        boolean result = Validator.validateLength(text, " >", 2);
        assertFalse("Test Passed", result);
    }

    @Test
    public void itemExistsTrue() {
        Group group = new Group("Name", 123, 123);
        ArrayList<Group> dataList = new ArrayList<>();
        dataList.add(group);
        boolean result = Validator.itemExists(group, dataList);
        assertTrue("Test is true", result);

    }


}