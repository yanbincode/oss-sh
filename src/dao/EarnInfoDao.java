package dao;

import java.util.List;

import core.support.BaseHibernateDao;

import model.EarnInfo;

/**
 * 收入信息dao接口
 * 
 * @author yanbin
 * 
 * @param <T>
 */
public interface EarnInfoDao<T> extends BaseHibernateDao<T> {

	/**
	 * 获取所有的支出信息
	 * 
	 * @return
	 */
	public List<EarnInfo> selectAllPayInfos();

	/**
	 * 分页查找
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<EarnInfo> selectPayInfos(Long startIndex, Long endIndex);

	/**
	 * 获取所有的条数
	 * 
	 * @return
	 */
	public Long selectCount();

}
