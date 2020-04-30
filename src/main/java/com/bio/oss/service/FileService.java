package com.bio.oss.service;

import com.bio.common.base.CoreService;
import com.bio.oss.domain.FileDO;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 文件上传
 * </pre>
 * <small> 2018年4月6日 | Aron</small>
 */
public interface FileService extends CoreService<FileDO> {

    List<FileDO> getFilesByFId(String id);

    void saveAllFiles(Map<String,Object> param);
    void removeByFId(String id);

    void removeByFIds(List<String> ids);
}
