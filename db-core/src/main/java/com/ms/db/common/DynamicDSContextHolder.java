package com.ms.db.common;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Set;

/**
 * 数据源服务应用
 * 设置数据源，已经处理都数据源的算法
 *
 * @Author lushaonan
 */
@Slf4j
public class DynamicDSContextHolder {

    //线程安全级别变量数据
    private static final ThreadLocal<String> DB_CONTEXT = ThreadLocal.withInitial(DataSourceKey.masterDs::name);

    //写数据源集合
    public static Set<Object> writeDsSet = Sets.newHashSet();

    //读数据源集合
    public static Set<Object> readDsSet = Sets.newHashSet();

    /**
     * 设置数据源
     * @param dsKey
     */
    public static void setDsKey(String dsKey){
        DB_CONTEXT.set(dsKey);
    }

    /**
     * 获得当前使用的数据源
     * @return
     */
    public static Object getDsKey(){
        return DB_CONTEXT.get();
    }

    /**
     * 清空当前数据源
     */
    public static void clearDsKey(){
        DB_CONTEXT.remove();
    }

    /**
     * 检查数据源在是存在整个数据源集合中
     * @param dsKey
     * @returnK
     */
    public static boolean containDsKey(String dsKey){
        return readDsSet.contains(dsKey)||writeDsSet.contains(dsKey);
    }

    /**
     * 随机算法处理
     * @param dsSet
     */
    public static void setRandomDs(Set<Object> dsSet){
        Object [] dsKey = dsSet.toArray();
        int randomIndex = new Random().nextInt(dsKey.length);
        DB_CONTEXT.set(dsKey[randomIndex].toString());
    }

    /**
     * 设置默认数据源，与主数据源挂钩
     */
    public static void useDefaultDataSource(){
        setRandomDs(writeDsSet);
    }

    /**
     * 设置读数据源
     */
    public static void useReadDataSource(){
        setRandomDs(readDsSet);
    }

}
