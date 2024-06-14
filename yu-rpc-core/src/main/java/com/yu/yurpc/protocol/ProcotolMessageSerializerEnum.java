package com.yu.yurpc.protocol;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 协议消息的序列化器枚举
 */
@Getter
public enum ProcotolMessageSerializerEnum {
    JDK(0,"jdk"),
    JSON(1,"json"),
    KYRO(2,"kyro"),
    HESSIAN(3,"hessian");

    private final int key;

    private final String value;

    ProcotolMessageSerializerEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取值列表
     * @return
     */
    public static List<String> getValues(){
        return Arrays.stream(values()).map(item->item.value).collect(Collectors.toList());
    }

    /**
     * 根据 key 获取枚举
     */
    public static ProcotolMessageSerializerEnum getEnumByKey(int key){
        for (ProcotolMessageSerializerEnum anEnum : ProcotolMessageSerializerEnum.values()) {
            if (anEnum.key == key){
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 根据value 获取枚举
     * @param value
     * @return
     */
    public static ProcotolMessageSerializerEnum getEnumByValue(String value){
        if (ObjectUtil.isEmpty(value)){
            return null;
        }
        for (ProcotolMessageSerializerEnum anEnum : ProcotolMessageSerializerEnum.values()) {
            if (Objects.equals(anEnum.value, value)){
                return anEnum;
            }
        }
        return null;
    }

}
