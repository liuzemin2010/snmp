/*
package com.easylinkin.controller;

import com.easylinkin.utils.HttpUtils;
import com.easylinkin.utils.SnmpData;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class SnmpController {

    private static final Logger log = Logger.getLogger(SnmpController.class);

    @Value("${snmp.ip}")
    private String snmpIp;

    @Value("${snmp.community}")
    private String snmpCommunity;

    @Value("${snmp.targetOid}")
    private String targetOid;

    @Value("${dev.code.ups01}")
    private String devCodeUps01;

    @Value("${ups01.oid.key.yxzt}")
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
    private String ups01OidKeyFz;



    //ups01.oid.key.yxzt=yxzt
    //ups01.oid.key.dl=dl
    //ups01.oid.key.dy=dy
    //ups01.oid.key.zlq=zlq
    //ups01.oid.key.nbq=nbq
    //ups01.oid.key.pl=pl
    //ups01.oid.key.fz=fz

    //ups01.oid.value.yxzt=1.3.6.1.2.1.25.4.2.1.2.10700
    //ups01.oid.value.dl=1.3.6.1.2.1.25.4.2.1.2.10720
    //ups01.oid.value.dy=1.3.6.1.2.1.25.4.2.1.2.10860
    //ups01.oid.value.zlq=1.3.6.1.2.1.25.4.2.1.2.10880
    //ups01.oid.value.nbq=1.3.6.1.2.1.25.4.2.1.2.11200
    //ups01.oid.value.pl=1.3.6.1.2.1.25.4.2.1.2.11248
    //ups01.oid.value.fz=1.3.6.1.2.1.25.4.2.1.2.11256

    @Value("${ups01.oid.value.yxzt}")
    private String ups01OidValueYxzt;

    @Value("${ups01.oid.value.dl}")
    private String ups01OidValueDl;

    @Value("${ups01.oid.value.dy}")
    private String ups01OidValueDy;

    @Value("${ups01.oid.value.zlq}")
    private String ups01OidValueZlq;

    @Value("${ups01.oid.value.nbq}")
    private String ups01OidValueNbq;

    @Value("${ups01.oid.value.pl}")
    private String ups01OidValuePl;

    @Value("${ups01.oid.value.fz}")
    private String ups01OidValueFz;

    @Value("${smart.switch.center.snmp.url}")
    private String smartSwitchCenterSnmpUrl;



    @RequestMapping("/snmpGetList")
    private String index(@RequestParam(value = "oid") String oid){


        //查询根节点下所有的设备属性数据
        //SnmpData.snmpWalk(snmpIp, snmpCommunity, targetOid);

        //查询设备指定的属性比如:电流电压等
        List<String> oidList=new ArrayList<String>();

        oidList.add(ups01OidValueYxzt);
        oidList.add(ups01OidValueDl);
        oidList.add(ups01OidValueDy);
        oidList.add(ups01OidValueZlq);
        oidList.add(ups01OidValueNbq);
        oidList.add(ups01OidValuePl);
        oidList.add(ups01OidValueFz);

        JSONObject snmpJsonObj = SnmpData.snmpGetList(snmpIp, snmpCommunity, oidList);

        Iterator<String> it = snmpJsonObj.keys();
        while(it.hasNext()){
            // 获得key
            String key = it.next();
            String value = snmpJsonObj.getString(key);
            System.out.println("key: "+key+",value:"+value);
        }

System.out.println(snmpJsonObj);



        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("deviceCode",devCodeUps01);
        jsonObj.put("yxzt",snmpJsonObj.get(ups01OidValueYxzt));
        jsonObj.put("dl",snmpJsonObj.get(ups01OidValueDl));
        jsonObj.put("dy",snmpJsonObj.get(ups01OidValueDy));
        jsonObj.put("zlq",snmpJsonObj.get(ups01OidValueZlq));
        jsonObj.put("nbq",snmpJsonObj.get(ups01OidValueNbq));
        jsonObj.put("pl",snmpJsonObj.get(ups01OidValuePl));
        jsonObj.put("fz",snmpJsonObj.get(ups01OidValueFz));

        // String nbq = obj.getString("nbq");
        //        String pl = obj.getString("pl");
        //        String fz = obj.getString("fz");

//                  analyticDataPush.setDeviceCode(analyticSnmpDevice.getDeviceCode());
//               	analyticDataPush.setSystemCode(analyticDataPushPar.getSystemCode());
//            		analyticDataPush.setAppEui(analyticSnmpConfigList.get(i).getAppEui());
//            		analyticDataPush.setDeviceType(analyticSnmpDevice.getDeviceType().toString());
//            		analyticDataPush.setData(jsonObj.toString());
//            		analyticDataPush.setLastUpdateTime(new Date());
//            		analyticDataPush.setDataType(Constants.DATA_TYPE_DEVICE_DATA.toString());// 0心跳包， 1 设备数据,后期改成常量
//            		analyticDataPush.setCreateTime(new Date());
//            		analyticDataPush.setPushFlag(Constants.PUSH_FLAG_NO);//0未推送 1已推送
//            		analyticDataPush.setCompanyId(analyticSnmpConfigList.get(i).getCompanyId());
//            		analyticDataPush.setWdmsCompanyId(analyticSnmpConfigList.get(i).getWdmsCompanyId());

        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
        log.info("推送地址 " + smartSwitchCenterSnmpUrl + " ,推送参数 " + jsonObj);
        String result = restTemplate.postForObject(smartSwitchCenterSnmpUrl, formEntity, String.class);

        JSONObject retJsonObj = JSONObject.fromObject(result);
        if ((retJsonObj.get("success").equals(true) && retJsonObj.get("returnCode").equals("0000"))) {
System.out.println("===================推送数据到鄂州工学院机房成功============================");
        }else{
System.out.println("--------------------------推送数据到鄂州工学院机房失败----------------------------");
        }

        return snmpJsonObj.toString();
        //return "===================";
    }



    @ResponseBody
    @RequestMapping(value = "receiveSnmpData", method = RequestMethod.POST)
    public JSONObject receiveSnmpData(HttpServletRequest request){

        String json = HttpUtils.getStringFromHttpRequest(request);
        System.out.println("接收数据为："+json);
        log.info("接收数据为："+json);
        JSONObject obj = JSONObject.fromObject(json);
        String yxzt = obj.getString("yxzt");
        String dl = obj.getString("dl");
        String dy = obj.getString("dy");
        String zlq = obj.getString("zlq");
        String nbq = obj.getString("nbq");
        String pl = obj.getString("pl");
        String fz = obj.getString("fz");
        String deviceCode = obj.getString("deviceCode");




        String mac = request.getParameter("mac");
        System.out.println("----------------------------------------接收鄂州工学院机房snmp服务推送到交换中心的数据-----------------------------------");
        JSONObject retJson = new JSONObject();
        retJson.put("returnCode", "0000");
        retJson.put("success", true);

        return retJson;

    }
}*/
