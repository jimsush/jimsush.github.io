package net.sf.modu.base.resource;

import java.net.URL;
import java.util.List;

/**
 * <p>
 * Title: ClassPathFileSearcher
 * </p>
 * <p>
 * Description:
 * ��·���ļ������ӿ�
 * </p>
 */
public interface ClassPathFileSearcher {
    
    /**
     * ����·���в��ҷ����ļ����ƹ�����ļ�
     * ������matchPattern="����/��probablecause.xml",��ʶ����·���в���probablecause.xml��β���ļ�
     * @param matchPattern
     *        �ļ�����ƥ�����
     * @return 
     *         �������з����������ļ��ľ���·��
     */
    public List<String> search(String matchPattern);
    
    /**
     * ����·���в��ҷ����ļ����ƹ�����Դ
     * ������matchPattern=����/��probablecause.xml,��ʶ����·���в���probablecause.xml��β���ļ�
     * @param matchPattern
     *         �ļ�����ƥ�����
     * @return 
     *         �������з����������ļ���ԴURL
     */
    public List<URL> searchResource(String matchPattern);
    
    /**
     * ������Դ
     * @param matchPattern
     * @param classLoader
     * @return �ҵ���URL
     */
    public List<URL> searchResource(String matchPattern,ClassLoader classLoader);
    
    /**
     * ����·���в��ҷ���������classȫ��
     * @param matchPattern com.yuep.core.smcore.model.*
     * @return
     */
    public List<String> searchClass(String matchPattern);
    
    /**
     * ������
     * @param matchPattern
     * @param classLoader
     * @return
     */
    public List<String> searchClass(String matchPattern,ClassLoader classLoader);

}
