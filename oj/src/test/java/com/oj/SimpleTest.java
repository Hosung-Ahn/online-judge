package com.oj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SimpleTest {
    @Test
    public void mapperTest() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"name\":\"Bob\"}";
        try {
            User user = mapper.readValue(json, User.class);
            System.out.println(user) ;
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
    }

    private static class User {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
