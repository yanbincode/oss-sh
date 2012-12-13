package service.impl;

import java.util.Date;
import java.util.List;

import model.YearRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import service.OssUserService;
import service.YearRecordService;
import core.exception.OssErrors;
import core.exception.OssRollbackCheckedException;
import core.support.AbstractServiceSupport;
import core.utils.BeanUtils;
import core.utils.MessageUtils;
import core.validation.EntityAnnotationValidator;
import dao.YearRecordDao;

/**
 * 年报service实现类
 * 
 * @author yanbin
 * 
 */
@Service
@Transactional
public class YearRecordServiceImpl extends AbstractServiceSupport implements YearRecordService {

	@Autowired
	private YearRecordDao<YearRecord> yearRecordDao;
	@Autowired
	private OssUserService ossUserService;

	@Override
	@Transactional(readOnly = true)
	public YearRecord get(Long recordId) {
		if (null == recordId) {
			throw new IllegalArgumentException(MessageUtils.getExceptionValue("ID_NOT_EXISTS"));
		}
		YearRecord yearRecord = yearRecordDao.select(recordId);
		if (null == yearRecord) {
			throw new IllegalArgumentException(MessageUtils.getExceptionValue("ID_NOT_EXISTS"));
		}
		return yearRecord;
	}

	@Override
	@Transactional(readOnly = true)
	public List<YearRecord> getShowList(Long pageIndex) {
		initPage(pageIndex);
		return yearRecordDao.selectYearRecords(startIndex, endIndex);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCount() {
		return yearRecordDao.selectCount();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getLastPage(Long count) {
		return dealLastPage(count);
	}

	@Override
	@Transactional(readOnly = true)
	public YearRecord getByYearTime(Date dateTime) {
		if (null == dateTime) {
			throw new IllegalArgumentException("arguments is null");
		}
		return yearRecordDao.selectByYearTime(dateTime);
	}

	@Override
	public void add(Date dateTime) throws OssRollbackCheckedException {
		Assert.notNull(dateTime);
		yearRecordDao.statisticsYear(dateTime);
	}

	@Override
	public void reStatistics(Date dateTime) throws OssRollbackCheckedException {
		Assert.notNull(dateTime);
		yearRecordDao.reStatisticsYear(dateTime);
	}

	@Override
	public void modify(YearRecord yearRecord) throws OssRollbackCheckedException {
		Assert.notNull(yearRecord);
		YearRecord dbYearRecord = get(yearRecord.getRecordId());
		BeanUtils.copyAllProperties(dbYearRecord, yearRecord);
		initModify(dbYearRecord);
		yearRecordDao.insertOrUpdate(dbYearRecord);
	}

	@Override
	public void delete(YearRecord yearRecord) throws OssRollbackCheckedException {
		Assert.notNull(yearRecord);
		yearRecordDao.delete(yearRecord);
	}

	@Override
	public void validateNotNull(YearRecord yearRecord) throws OssRollbackCheckedException {
		Assert.notNull(yearRecord);
		OssErrors errors = EntityAnnotationValidator.validate(yearRecord);
		if (null != errors && errors.hasErrors()) {
			throw new OssRollbackCheckedException(errors);
		}
	}

	/**
	 * 初始化修改操作
	 * 
	 * @param yearRecord
	 */
	private void initModify(YearRecord yearRecord) {
		Assert.notNull(yearRecord);
		yearRecord.setModifier(ossUserService.get(1l));
		yearRecord.setModifiedTime(new Date());
	}

}
