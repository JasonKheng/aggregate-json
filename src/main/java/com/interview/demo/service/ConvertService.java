package com.interview.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConvertService {
    public String conversion(String input) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parsed the input request
            List<Map<String, Object>> records = objectMapper.readValue(input, new TypeReference<List<Map<String, Object>>>() {
            });


            // Group by userId and Completed
            Map<Integer, Map<Boolean, List<Map<String, Object>>>> mappedResult = records
                    .stream()
                    .collect(Collectors.groupingBy(rcd -> (Integer) rcd.get("userId"),
                            Collectors.groupingBy(rcd -> (Boolean) rcd.get("completed"))));

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mappedResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting " + e);
        }
    }
}
