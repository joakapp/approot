package com.ms.db.common;

/**
 * 创建数据源类型
 * 主要用于创建读写分离模式处理
 *
 * 当前仅仅支持两种模式读数据源、写数据源，可以进行扩展
 *
 * @Author lushaonan
 */
public enum DataSourceKey {

    /**
     * 主写数据源
     */
    masterDs,

    /**
     * 应用读数据源
     */
    slaveDs;
}
