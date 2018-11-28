package com.easylinkin.service.impl;

import net.sf.json.JSONObject;

public class Test {

    public static void main(String[] args) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("batteryStatus","1");
        jsonObj.put("temperature","2");
        jsonObj.put("elapsedTime","3");
        jsonObj.put("batteryCapacity","4");
        jsonObj.put("inputFrequency","5");
        jsonObj.put("inputVoltage","6");
        jsonObj.put("maxLoad","7");
        jsonObj.put("outputActivePower","8");
        jsonObj.put("outputVoltage","9");
        jsonObj.put("outputElectricity","10");
        System.out.println(jsonObj);
    }
}
