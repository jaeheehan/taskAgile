package com.taskagile.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListTest {

    class CourseAndData {
        private String course;
        private List boo;

        public CourseAndData(String course, List boo){
            this.course = course;
            this.boo = boo;
        }

        @Override
        public String toString() {
            return "CourseAndData{" +
                    "course='" + course + '\'' +
                    ", boo=" + boo +
                    '}';
        }
    }

    @Test
    public void list_stream_test() {

        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> course1_1 = new HashMap<>();
        course1_1.put("코스", "1코스");
        course1_1.put("부", "1부");
        Map<String, String> course2_2 = new HashMap<>();
        course2_2.put("코스", "2코스");
        course2_2.put("부", "2부");

        Map<String, String> course2_1 = new HashMap<>();
        course2_1.put("코스", "2코스");
        course2_1.put("부", "1부");

        Map<String, String> course1_2 = new HashMap<>();
        course1_2.put("코스", "1코스");
        course1_2.put("부", "2부");

        list.add(course1_1);
        list.add(course1_2);
        list.add(course2_1);
        list.add(course2_2);

        for (Map<String, String> m : list) {
            System.out.println("m = " + m);
        }

        List<CourseAndData> newList = list.stream().collect(
                Collectors.groupingBy((map) -> map.get("코스"))
        ).entrySet().stream()
                .map(entry -> {
                    return new CourseAndData(entry.getKey(), entry.getValue());
                })
                .collect(Collectors.toList());


        for (CourseAndData courseAndData : newList) {
            System.out.println("courseAndData = " + courseAndData);
        }

    }
}
