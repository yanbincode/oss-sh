package service.impl;

import java.util.Date;
import java.util.List;

import model.MonthRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import service.MonthRecordService;
import service.OssUserService;
import core.exception.OssErrors;
import core.exception.OssRollbackCheckedException;
import core.support.AbstractServiceSupport;
import core.utils.BeanUtils;
import core.utils.MessageUtils;
import core.validation.EntityAnnotationValidator;
import dao.MonthRecordDao;

/**
 * 月报service接口实现类
 * 
 * @author yanbin
 * 
 */
@Service
@Transactional
public class MonthRecordServiceImpl extends AbstractServiceSupport implements MonthRecordService {

	@Autowired
	private MonthRecordDao<MonthRecord> monthRecordDao;
	@Autowired
	private OssUserService ossUserService;

	@Override
	@Transactional(readOnly = true)
	public List<MonthRecord> getShowList(Long pageIndex) {
		initPage(pageIndex);
		return monthRecordDao.selectMonthRecords(startIndex, endIndex);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCount() {
		return monthRecordDao.selectCount();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getLastPage(Long count) {
		return dealLastPage(count);
	}

	@Override
	public void add(Date dateTime) throws OssRollbackCheckedException {
		Assert.notNull(dateTime);
		monthRecordDao.statisticsMonth(dateTime);
	}

	@Override
	public void reStatistics(Date dateTime) throws OssRollbackCheckedException {
		Assert.notNull(dateTime);
		monthRecordDao.reStatisticsMonth(dateTime);
	}

	@Override
	@Transactional(readOnly = true)
	public MonthRecord get(Long recordId) {
		if (null == recordId) {
			throw new IllegalArgumentException(MessageUtils.getExceptionValue("ID_NOT_EXISTS"));
		}
		MonthRecord monthRecord = monthRecordDao.select(recordId);
		if (null == monthRecord) {
			throw new IllegalArgumentException(MessageUtils.getExceptionValue("ID_NOT_EXISTS"));
		}
		return monthRecord;
	}

	@Override
	@Transactional(readOnly = true)
	public MonthRecord getByMonthTime(Date dateTime) {
		if (null == dateTime) {
			throw new IllegalArgumentException("arguments is null");
		}
		return monthRecordDao.selectByMonthTime(dateTime);
	}

	@Override
	public void modify(MonthRecord monthRecord) throws OssRollbackCheckedException {
		Assert.notNull(monthRecord);
		MonthRecord dbMonthRecord = get(monthRecord.getRecordId());
		BeanUtils.copyAllProperties(dbMonthRecord, monthRecord);
		initModify(dbMonthRecord);
		monthRecordDao.insertOrUpdate(dbMonthRecord);
	}

	@Override
	public void delete(MonthRecord monthRecord) throws OssRollbackCheckedException {
		Assert.notNull(monthRecord);
		monthRecordDao.delete(monthRecord);
	}

	@Override
	@Transactional(readOnly = true)
	public void validateNotNull(MonthRecord monthRecord) throws OssRollbackCheckedException {
		Assert.notNull(monthRecord);
		OssErrors errors = EntityAnnotationValidator.validate(monthRecord);
		if (null != errors && errors.hasErrors()) {
			throw new OssRollbackCheckedException(errors);
		}
	}

	/**
	 * 初始化修改操作
	 * 
	 * @param monthRecord
	 */
	private void initModify(MonthRecord monthRecord) {
		Assert.notNull(monthRecord);
		monthRecord.setModifier(ossUserService.get(1l));
		monthRecord.setModifiedTime(new Date());
	}

}
