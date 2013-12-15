package net.sf.modu.base.resource;

import java.util.List;

/**
 * <p>
 * Title: ClassPathXMLFileReader
 * </p>
 * <p>
 * Description:
 * 类路径文件读取接口
 * </p>
 * 
 */
public interface ClassPathXMLFileReader {

    /**
     * 搜索符合XML文件规则的所有文件XML文件，并读取内容
     * @param <T>
     * @param xmlFileNameMatchPattern
     *        XML文件文件名称匹配规则，如*probablecause.xml,查找以probablecause.xml结尾的文件
     * @param xmlFileMapping
     *        被读取的XML文件的Mapping文件
     * @param objClass
     *        XML文件映射的java Class
     * @return
     *        返回对象列表
     */
    public <T> List<T> searchAndRead(String xmlFileNameMatchPattern,String xmlFileMapping,Class<? extends T> objClass);
    
    
    /**
     * 读取指定XML文件内容
     * @param <T>
     * @param xmlDataFile
     *        XML数据文件名称
     * @param xmlFileMapping
     *        被读取的XML文件的Mapping文件名称
     * @param objClass
     *        对象Java类型
     * @return
     *        返回对象列表
     */
    public <T> List<T> read(String xmlDataFile,String xmlFileMapping,Class<? extends T> objClass);
    
}
