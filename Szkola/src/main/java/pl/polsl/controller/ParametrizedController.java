package pl.polsl.controller;

import java.util.Map;

public interface ParametrizedController {
    void receiveArguments(Map<String, Object> params);
}
