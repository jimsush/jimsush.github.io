package net.sf.modu.base.resource;

import java.util.List;

/**
 * <p>
 * Title: JarPathFileSearcher
 * </p>
 * <p>
 * Description:
 * <br>��Jar�������������������ļ�
 * </p>
 */
public interface JarPathFileSearcher {
    /**
     * ��ָ��Jar���������ļ�
     * @param jarPath
     *        jar��·��
     * @param fileNameMatchPattern
     *        �ļ�����ƥ�����,����**yuep*.txt
     * @return
     */
    public List<String> search(String jarPath,String fileNameMatchPattern);

}
