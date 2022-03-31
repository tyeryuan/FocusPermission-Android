package com.focus.focuspermission.core;

import android.app.Activity;
import android.provider.DocumentsContract;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PermissionInfo {
    private String id;
    private String level;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static Map<String,PermissionInfo> permissionInfoMap = null;

    public static PermissionInfo GetPermissionInfo(Activity activity, String permission){
        if (permissionInfoMap == null){
            permissionInfoMap = new HashMap<>();
            //读取xml
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
                Document document = builder.parse(activity.getAssets().open("PermissionsInfoMap.xml"));
                NodeList nList = document.getElementsByTagName("Permission");
                for(int i = 0;i < nList.getLength();i++) {
                    Element element = (Element) nList.item(i);
                    PermissionInfo permissionInfo = new PermissionInfo();
                    permissionInfo.setId(element.getAttribute("id"));
                    permissionInfo.setLevel(element.getAttribute("level"));
                    permissionInfo.setName(element.getAttribute("name"));
                    permissionInfo.setDescription(element.getAttribute("description"));
                    if (!permissionInfoMap.containsKey(permissionInfo.getId())){
                        permissionInfoMap.put(permissionInfo.getId(), permissionInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return permissionInfoMap.get(permission);
    }
}
