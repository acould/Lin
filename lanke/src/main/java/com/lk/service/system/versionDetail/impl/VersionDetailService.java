package com.lk.service.system.versionDetail.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.service.system.versionDetail.VersionDetailManager;

/**
 * 版本详情
 * @author lzh
 */
@Service("versionDetailService")
public class VersionDetailService implements VersionDetailManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
}
