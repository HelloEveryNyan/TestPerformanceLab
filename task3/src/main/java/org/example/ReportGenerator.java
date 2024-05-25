package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("error path values.json, tests.json, and report.json");
            return;
        }

        String valuesPath = args[0];
        String testsPath = args[1];
        String reportPath = args[2];

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("reading values from: " + valuesPath);
        JsonNode valuesNode = mapper.readTree(new File(valuesPath));
        System.out.println("valuesNode: " + valuesNode);

        if (valuesNode == null) {
            System.err.println("failed to read values");
            return;
        }

        Map<String, String> valuesMap = new HashMap<>();
        for (JsonNode valueNode : valuesNode) {
            String id = valueNode.get("id").asText();
            String value = valueNode.get("value").asText();
            valuesMap.put(id, value);
        }

        System.out.println("valuesMap: " + valuesMap);

        JsonNode testsNode = mapper.readTree(new File(testsPath));
        System.out.println("testsNote before fill: " + testsNode);

        fillValues(testsNode, valuesMap);

        System.out.println("testsNote after fill: " + testsNode);

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(reportPath), testsNode);
    }

    private static void fillValues(JsonNode node, Map<String, String> valuesMap) {
        if (node.has("id") && node.has("value")) {
            String id = node.get("id").asText();
            System.out.println("processing id: " + id);
            if (valuesMap.containsKey(id)) {
                ((ObjectNode) node).put("value", valuesMap.get(id));
                System.out.println("updated value" + id + ": " + valuesMap.get(id));
            } else {
                System.out.println("no value found: " + id);
            }
        }

        if (node.isArray()) {
            for (JsonNode childNode : node) {
                fillValues(childNode, valuesMap);
            }
        } else if (node.has("values")) {
            fillValues(node.get("values"), valuesMap);
        } else if (node.has("tests")) {
            fillValues(node.get("tests"), valuesMap);
        }
    }
}