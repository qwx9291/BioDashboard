package com.bio.oss.service.impl;

import com.bio.common.base.CoreServiceImpl;
import com.bio.common.config.BiodashboardConfig;
import com.bio.oss.dao.FileDao;
import com.bio.oss.domain.FileDO;
import com.bio.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class FileServiceImpl extends CoreServiceImpl<FileDao, FileDO> implements FileService {

    @Autowired
    private BiodashboardConfig ifastConfig;

    @Override
    public List<FileDO> getFilesByFId(String id) {
        return null;
    }

    @Override
    public void saveAllFiles(Map<String, Object> param) {

    }

    @Override
    public void removeByFId(String id) {

    }

    @Override
    public void removeByFIds(List<String> ids) {

    }
}
