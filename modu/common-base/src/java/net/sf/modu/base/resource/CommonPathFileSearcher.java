package net.sf.modu.base.resource;

import java.util.List;

/**
 * <p>
 * Title: CommonPathFileSearcher
 * </p>
 * <p>
 * Description:
 * <br>��ָ���ļ�Ŀ¼�������ļ�
 * </p>
 */
public interface CommonPathFileSearcher {
    
    /**
     * ��ָ���ļ�Ŀ¼�������ļ�
     * @param filePath
     *        �ļ�Ŀ¼
     * @param fileNameMatchPattern
     *        �ļ�����ƥ�����,����**yuep*.txt
     * @return
     *        �����������ļ�
     */
    public List<String> search(String filePath,String fileNameMatchPattern);
    
}
