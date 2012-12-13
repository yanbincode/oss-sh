package service.impl;

import java.util.Date;
import java.util.List;

import model.PayInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import service.OssUserService;
import service.PayInfoService;
import core.exception.OssErrors;
import core.exception.OssRollbackCheckedException;
import core.support.AbstractServiceSupport;
import core.utils.Assert;
import core.utils.BeanUtils;
import core.utils.MessageUtils;
import core.validation.EntityAnnotationValidator;
import dao.PayInfoDao;

/**
 * 支出信息业务实现类
 * 
 * @author yanbin
 * 
 */
@Transactional
@Service
public class PayInfoServiceImpl extends AbstractServiceSupport implements PayInfoService {

	@Autowired
	private PayInfoDao<PayInfo> payInfoDao;
	@Autowired
	private OssUserService ossUserService;

	@Override
	@Transactional(readOnly = true)
	public PayInfo get(Long recordId) {
		if (null == recordId) {
			throw new IllegalArgumentException(MessageUtils.getExceptionValue("ID_NOT_EXISTS"));
		}
		PayInfo payInfo = payInfoDao.select(recordId);
		if (null == payInfo) {
			throw new IllegalArgumentException(MessageUtils.getExceptionValue("ID_NOT_EXISTS"));
		}
		return payInfo;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PayInfo> getAllPayInfos() {
		return payInfoDao.selectAllPayInfos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<PayInfo> getShowList(Long index) {
		// 初始化分页
		initPage(index == null ? 0 : index);
		return payInfoDao.selectPayInfos(startIndex, endIndex);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCount() {
		return payInfoDao.selectCount();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getLastPage(Long count) {
		return dealLastPage(count);
	}

	@Override
	public void add(PayInfo payInfo) throws OssRollbackCheckedException {
		Assert.notNull(payInfo);
		initAdd(payInfo);
		initModify(payInfo);
		payInfoDao.insert(payInfo);
	}

	@Override
	public void modify(PayInfo payInfo) throws OssRollbackCheckedException {
		Assert.notNull(payInfo);
		PayInfo dbPayInfo = get(payInfo.getRecordId());
		BeanUtils.copyAllProperties(dbPayInfo, payInfo);
		initModify(dbPayInfo);
		payInfoDao.insertOrUpdate(dbPayInfo);
	}

	@Override
	public void delete(PayInfo payInfo) throws OssRollbackCheckedException {
		Assert.notNull(payInfo);
		payInfoDao.delete(payInfo);
	}

	@Override
	@Transactional(readOnly = true)
	public void validateNotNull(PayInfo payInfo) throws OssRollbackCheckedException {
		Assert.notNull(payInfo);
		OssErrors errors = EntityAnnotationValidator.validate(payInfo);
		if (null != errors && errors.hasErrors()) {
			throw new OssRollbackCheckedException(errors);
		}
	}

	/**
	 * 初始化添加人添加时间
	 * 
	 * @param payInfo
	 */
	private void initAdd(PayInfo payInfo) {
		Assert.notNull(payInfo);
		payInfo.setCreator(ossUserService.get(1l)); // 要完善
		payInfo.setCreatedTime(new Date());
	}

	/**
	 * 初始化修改人，修改时间
	 * 
	 * @param payInfo
	 */
	private void initModify(PayInfo payInfo) {
		Assert.notNull(payInfo);
		payInfo.setModifier(ossUserService.get(1l));
		payInfo.setModifiedTime(new Date());
	}

}
