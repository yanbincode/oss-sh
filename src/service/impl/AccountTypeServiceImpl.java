package service.impl;

import java.util.Date;
import java.util.List;

import model.AccountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import service.AccountTypeService;
import service.OssUserService;
import core.exception.OssErrors;
import core.exception.OssRollbackCheckedException;
import core.support.AbstractServiceSupport;
import core.utils.Assert;
import core.utils.BeanUtils;
import core.utils.MessageUtils;
import core.validation.EntityAnnotationValidator;
import dao.AccountTypeDao;

@Service
@Transactional
public class AccountTypeServiceImpl extends AbstractServiceSupport implements AccountTypeService {

	@Autowired
	private AccountTypeDao<AccountType> accountTypeDao;
	@Autowired
	private OssUserService ossUserService;

	@Override
	public AccountType get(Long recordId) {
		if (null == recordId) {
			throw new IllegalArgumentException(MessageUtils.getExceptionValue("ID_NOT_EXISTS"));
		}
		AccountType accountType = accountTypeDao.select(recordId);
		if (null == accountType) {
			throw new IllegalArgumentException(MessageUtils.getExceptionValue("ID_NOT_EXISTS"));
		}
		return accountType;
	}

	@Override
	public List<AccountType> getAccountTypesByUse(String usePlace) {
		return accountTypeDao.selectAccountTypesByUse(usePlace);
	}

	@Override
	public List<AccountType> getShowList(Long pageIndex) {
		initPage(pageIndex);
		List<AccountType> accountTypes = accountTypeDao.selectPayInfos(startIndex, endIndex);
		return accountTypes;
	}

	@Override
	public Long getCount(Long pageIndex) {
		return accountTypeDao.selectCount();
	}

	@Override
	public Long getLastPage(Long count) {
		return dealLastPage(count);
	}

	@Override
	public void validateNotNull(AccountType accountType) throws OssRollbackCheckedException {
		Assert.notNull(accountType);
		OssErrors errors = EntityAnnotationValidator.validate(accountType);
		if (null != errors && errors.hasErrors()) {
			throw new OssRollbackCheckedException(errors);
		}
	}

	@Override
	public void add(AccountType accountType) throws OssRollbackCheckedException {
		Assert.notNull(accountType);
		initAdd(accountType);
		initModify(accountType);
		accountTypeDao.insert(accountType);
	}

	@Override
	public void modify(AccountType accountType) throws OssRollbackCheckedException {
		Assert.notNull(accountType);
		AccountType dbAccountType = get(accountType.getRecordId());
		BeanUtils.copyAllProperties(dbAccountType, accountType);
		initModify(dbAccountType);
		accountTypeDao.insertOrUpdate(dbAccountType);
	}

	@Override
	public void delete(AccountType accountType) throws OssRollbackCheckedException {
		Assert.notNull(accountType);
		accountTypeDao.delete(accountType);
	}

	/**
	 * 初始化添加
	 * 
	 * @param accountType
	 */
	private void initAdd(AccountType accountType) {
		Assert.notNull(accountType);
		accountType.setCreator(ossUserService.get(1l));
		accountType.setCreatedTime(new Date());
	}

	/**
	 * 初始化修改
	 * 
	 * @param accountType
	 */
	private void initModify(AccountType accountType) {
		Assert.notNull(accountType);
		accountType.setModifier(ossUserService.get(1l));
		accountType.setModifiedTime(new Date());
	}

}
