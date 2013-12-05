package net.sf.modu.base.resource;

import java.util.List;

/**
 * <p>
 * Title: ClassPathXMLFileReader
 * </p>
 * <p>
 * Description:
 * ��·���ļ���ȡ�ӿ�
 * </p>
 * 
 */
public interface ClassPathXMLFileReader {

    /**
     * ��������XML�ļ�����������ļ�XML�ļ�������ȡ����
     * @param <T>
     * @param xmlFileNameMatchPattern
     *        XML�ļ��ļ�����ƥ�������*probablecause.xml,������probablecause.xml��β���ļ�
     * @param xmlFileMapping
     *        ����ȡ��XML�ļ���Mapping�ļ�
     * @param objClass
     *        XML�ļ�ӳ���java Class
     * @return
     *        ���ض����б�
     */
    public <T> List<T> searchAndRead(String xmlFileNameMatchPattern,String xmlFileMapping,Class<? extends T> objClass);
    
    
    /**
     * ��ȡָ��XML�ļ�����
     * @param <T>
     * @param xmlDataFile
     *        XML�����ļ�����
     * @param xmlFileMapping
     *        ����ȡ��XML�ļ���Mapping�ļ�����
     * @param objClass
     *        ����Java����
     * @return
     *        ���ض����б�
     */
    public <T> List<T> read(String xmlDataFile,String xmlFileMapping,Class<? extends T> objClass);
    
}
