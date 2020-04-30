package com.bio.sys.service.impl;

import com.bio.common.base.CoreServiceImpl;
import com.bio.sys.dao.AnnireportDao;
import com.bio.sys.domain.AnnireportDO;
import com.bio.sys.service.AnnireportService;
import org.springframework.stereotype.Service;

/**
 * 
 * <pre>
 * 年报
 * </pre>
 * <small> 2019-12-31 10:19:10 | chenx</small>
 */
@Service
public class AnnireportServiceImpl extends CoreServiceImpl<AnnireportDao, AnnireportDO> implements AnnireportService {

	@Override
	public Integer getReportCountByTitle(String title) {
		return baseMapper.getReportCountByTitle(title);

	}

}
