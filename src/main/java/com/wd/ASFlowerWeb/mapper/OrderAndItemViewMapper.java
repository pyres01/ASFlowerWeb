package com.wd.ASFlowerWeb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wd.ASFlowerWeb.entity.OrderAndItemView;

/**
 * @author 若尘
 *
 * 2019年3月9日
 *
 */
public interface OrderAndItemViewMapper {

	@Select("SELECT * from order_and_item WHERE order_uid = ${uid}")
	List<OrderAndItemView> selectAllByUid(@Param("uid")Integer uid);
}
