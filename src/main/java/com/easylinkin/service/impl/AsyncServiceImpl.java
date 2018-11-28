package com.easylinkin.service.impl;

import com.easylinkin.service.AsyncService;
import com.easylinkin.utils.SnmpData;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description : 异步任务的实现
 * @Author : zq2599@gmail.com
 * @Date : 2018-01-21 21:25
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Value("${snmp.second.ip}")
    private String snmpSecondIp;

    @Value("${snmp.community}")
    private String snmpCommunity;

    @Value("${snmp.targetOid}")
    private String targetOid;

    @Value("${dev.code.ups02}")
    private String devCodeUps02;

   /* @Value("${ups01.oid.key.yxzt}")
    private String ups01OidKeyYxzt;

    @Value("${ups01.oid.key.dl}")
    private String ups01OidKeyDl;

    @Value("${ups01.oid.key.dy}")
    private String ups01OidKeyDy;

    @Value("${ups01.oid.key.zlq}")
    private String ups01OidKeyZlq;

    @Value("${ups01.oid.key.nbq}")
    private String ups01OidKeyNbq;

    @Value("${ups01.oid.key.pl}")
    private String ups01OidKeyPl;

    @Value("${ups01.oid.key.fz}")
    private String ups01OidKeyFz;*/

    @Value("${ups01.oid.value.battery.status}")
    private String ups01OidValueBatteryStatus;

    @Value("${ups01.oid.value.temperature}")
    private String ups01OidValueTemperature;

    @Value("${ups01.oid.value.elapsed.time}")
    private String ups01OidValueElapsedTime;

    @Value("${ups01.oid.value.battery.capacity}")
    private String ups01OidValueBatteryCapacity;

    @Value("${ups01.oid.value.input.frequency}")
    private String ups01OidValueInputFrequency;

    @Value("${ups01.oid.value.input.voltage}")
    private String ups01OidValueInputVoltage;

    @Value("${ups01.oid.value.max.load}")
    private String ups01OidValueMaxLoad;

    @Value("${ups01.oid.value.total.output.active.power}")
    private String ups01OidValueTotalOutputActivePower;

    @Value("${ups01.oid.value.output.voltage}")
    private String ups01OidValueOutputVoltage;

    @Value("${ups01.oid.value.output.electricity}")
    private String ups01OidValueOutputElectricity;


    @Value("${smart.switch.center.snmp.url}")
    private String smartSwitchCenterSnmpUrl;

    @Value("${smart.switch.center.frequency}")
    private String smartSwitchCenterFrequency;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        while (true){
            log.info("start executeAsync");
            try{
                Thread.sleep(new Long(smartSwitchCenterFrequency));
                log.info("==============AsyncServiceImpl===executeAsync()==============================");

                //查询根节点下所有的设备属性数据
                SnmpData.snmpWalk(snmpSecondIp, snmpCommunity, targetOid);

                //查询设备指定的属性比如:电流电压等
                List<String> oidList=new ArrayList<String>();

                oidList.add(ups01OidValueBatteryStatus);
                oidList.add(ups01OidValueTemperature);
                oidList.add(ups01OidValueElapsedTime);
                oidList.add(ups01OidValueBatteryCapacity);
                oidList.add(ups01OidValueInputFrequency);
                oidList.add(ups01OidValueInputVoltage);
                oidList.add(ups01OidValueMaxLoad);
                oidList.add(ups01OidValueTotalOutputActivePower);
                oidList.add(ups01OidValueOutputVoltage);
                oidList.add(ups01OidValueOutputElectricity);

                JSONObject snmpJsonObj = SnmpData.snmpGetList(snmpSecondIp, snmpCommunity, oidList);

                Iterator<String> it = snmpJsonObj.keys();

                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("deviceCode",devCodeUps02);

                jsonObj.put("batteryStatus",snmpJsonObj.get(ups01OidValueBatteryStatus));
                jsonObj.put("temperature",snmpJsonObj.get(ups01OidValueTemperature));
                jsonObj.put("elapsedTime",snmpJsonObj.get(ups01OidValueElapsedTime));
                jsonObj.put("batteryCapacity",snmpJsonObj.get(ups01OidValueBatteryCapacity));
                jsonObj.put("inputFrequency",snmpJsonObj.get(ups01OidValueInputFrequency));
                jsonObj.put("inputVoltage",snmpJsonObj.get(ups01OidValueInputVoltage));
                jsonObj.put("maxLoad",snmpJsonObj.get(ups01OidValueMaxLoad));
                jsonObj.put("outputActivePower",snmpJsonObj.get(ups01OidValueTotalOutputActivePower));
                jsonObj.put("outputVoltage",snmpJsonObj.get(ups01OidValueOutputVoltage));
                jsonObj.put("outputElectricity",snmpJsonObj.get(ups01OidValueOutputElectricity));

                HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
                log.info("推送地址 " + smartSwitchCenterSnmpUrl + " ,推送参数 " + jsonObj);
                String result = restTemplate.postForObject(smartSwitchCenterSnmpUrl, formEntity, String.class);

                JSONObject retJsonObj = JSONObject.fromObject(result);
                if ((retJsonObj.get("success").equals(true) && retJsonObj.get("returnCode").equals("0000"))) {
                    System.out.println("===================AsyncServiceImpl推送数据到鄂州工学院机房成功============================");
                    log.info("===================推送数据到鄂州工学院机房成功============================");
                }else{
                    System.out.println("--------------------------AsyncServiceImpl推送数据到鄂州工学院机房失败----------------------------");
                    log.info("--------------------------推送数据到鄂州工学院机房失败----------------------------");
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            log.info("end executeAsync");

        }

    }
}
