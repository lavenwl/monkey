package com.stang.game.dao.impl;

import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.stang.game.common.GameConstants;
import com.stang.game.common.PageProperty;
import com.stang.game.dao.IEntityDao;
import com.stang.game.entity.detail.ServerDetail;

/**
 * @author fei_wei
 * @company 上海三唐信息科技有限公司
 * @description 实体数据库处理实现
 */
public abstract class EntityDao<T> implements IEntityDao<T> {

	protected static SqlMapClient sqlMapper;

	private Class<T> poClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
	private String poClassName = poClass.getSimpleName();

	static {
		if (sqlMapper == null) {
			try {
				Reader reader = Resources
						.getResourceAsReader(GameConstants.DB_IBATIS_CONFIG);
				sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
				reader.close();
			} catch (Exception e) {
				GameConstants.log.warn("", e);
			}
		}
	}

	/**
	 * @method insert
	 * @param model
	 *            实现该类时提供的实体类对象，装有插入信息
	 * @return {int}
	 * @description 插入数据
	 */
	public int insert(T model) {
		int code = GameConstants.CODE_DAO_FAILURE;
		try {
			sqlMapper.startTransaction();
			sqlMapper.insert("insert" + poClassName, model);
			sqlMapper.commitTransaction();
			code = GameConstants.CODE_DAO_SUCCESS;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			GameConstants.log.warn("", e);
		} finally {
			try {
				sqlMapper.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				GameConstants.log.warn("", e);
			}
		}
		return code;
	}

	/**
	 * @method update
	 * @param model
	 *            实现该类时提供的实体类对象，装有更新信息
	 * @return {int}
	 * @description 更新数据
	 */
	public int update(T model) {
		int code = GameConstants.CODE_DAO_FAILURE;
		try {
			sqlMapper.startTransaction();
			sqlMapper.insert("update" + poClassName, model);
			sqlMapper.commitTransaction();
			code = GameConstants.CODE_DAO_SUCCESS;
		} catch (SQLException e) {
			GameConstants.log.warn("", e);
		} finally {
			try {
				sqlMapper.endTransaction();
			} catch (SQLException e) {
				GameConstants.log.warn("", e);
			}
		}

		return code;

	}

	/**
	 * @method getModel
	 * @param param
	 *            分装了查询条件
	 * @return {T}
	 * @description 读取类型为实现该类时提供的实体类型数据
	 */
	public T getModel(Map param) {
		T model = null;
		try {
			model = (T) sqlMapper.queryForObject("get" + poClassName, param);
		} catch (Exception e) {
			GameConstants.log.warn("", e);
		}
		return model;
	}

	/**
	 * @method delete
	 * @param id
	 *            {Serializable} 提供的唯一标识
	 * @return {int}
	 * @description 删除记录
	 */
	public int delete(Serializable id) {
		int code = GameConstants.CODE_DAO_FAILURE;

		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			param.put("flag", 1);
			sqlMapper.startTransaction();
			sqlMapper.update("delete" + poClassName, param);
			sqlMapper.commitTransaction();
			param = null;
		} catch (Exception e) {

		} finally {
			try {
				sqlMapper.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				GameConstants.log.warn("", e);
			}
		}
		return code;
	}

	/**
	 * @method findCount
	 * @param pp
	 *            {PageProperty} 相关的提供分页的参数
	 * @return {int}
	 * @description 查找记录条数
	 */
	public int findCount(PageProperty pp) {
		Integer count = 0;

		try {
			count = (Integer) sqlMapper.queryForObject("get" + poClassName
					+ "Count", pp.getParamMap());
		} catch (Exception e) {
			GameConstants.log.warn("", e);
		}
		return count.intValue();
	}

	/**
	 * @method findPageList
	 * @param pp
	 *            {PageProperty} 相关的提供分页的参数
	 * @return {List<T>}
	 * @description 获取满足分页信息的数据
	 */
	public List<T> findPageList(PageProperty pp) {

		List<T> _list = null;

		try {
			_list = (List<T>) sqlMapper.queryForList("listSplit" + poClassName,
					pp.getParamMap());
		} catch (Exception e) {
			GameConstants.log.warn("", e);
		}

		return _list;

	}

	/**
	 * @method list
	 * @param param
	 *            {Map} 参数
	 * @return {List<T>}
	 * @description 获取数据列表
	 */
	public List<T> list(Map param) {
		List<T> _list = null;

		try {
			_list = (List<T>) sqlMapper.queryForList("list" + poClassName,
					param);
		} catch (Exception e) {
			GameConstants.log.warn("", e);
		}

		return _list;
	}

	/**
	 * @method deleteList
	 * @param ids
	 *            需要删除的对应ID组
	 * @return {int}
	 * @description 批量删除
	 */
	public int deleteList(String ids) {
		int code = GameConstants.CODE_DAO_FAILURE;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", ids);
			param.put("flag", -1);

			sqlMapper.startTransaction();
			sqlMapper.update("deleteList" + poClassName, param);
			sqlMapper.commitTransaction();
			code = GameConstants.CODE_DAO_SUCCESS;
			param = null;
		} catch (Exception e) {
			GameConstants.log.warn("", e);
		} finally {
			try {
				sqlMapper.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				GameConstants.log.warn("", e);
			}
		}
		return code;
	}

	public boolean updateServer(ServerDetail model) {
		boolean isSuccess = false;
		try {
			sqlMapper.startTransaction();
			System.out.println("SErverDaoImpl.updateServer:serverid:" + model.getId());
			sqlMapper.update("updateServer", model);
			sqlMapper.commitTransaction();
			isSuccess = true;
		} catch (Exception e) {
			GameConstants.log.warn("", e);
		} finally {
			try {
				sqlMapper.endTransaction();
			} catch (SQLException e) {
				GameConstants.log.warn("", e);
			}
		}
		return isSuccess;
	}

}
