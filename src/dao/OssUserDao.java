package dao;

import java.util.List;

import core.support.BaseHibernateDao;

import model.OssUser;

/**
 * 支出dao接口
 * 
 * @author yanbin
 * 
 */
public interface OssUserDao<T> extends BaseHibernateDao<T> {

	/**
	 * 获取所有
	 * 
	 * @return
	 */
	public List<OssUser> selectAll();

	/**
	 * 根据用户名和密码获取用户
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public OssUser selectByUserNameAndPassWord(String userName, String passWord);

}
