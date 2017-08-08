package com.tool;

import java.util.concurrent.TimeUnit;

/**
 * redis key前缀
 * @Title: RedisPrefix.java
 * @Package mall.common.utils
 * @Description: redis key前缀
 * @author tianzy
 * @date 2017年7月14日下午4:12:25
 */
public class RedisPrefix {

	// 通用缓存时间，1天
	public static final int COMMON_CACHE_TIME = 1;
	public static final TimeUnit COMMON_TIMEUNIT = TimeUnit.DAYS;

	/**
	 * 用户
	 */
	public static final String USER = "user_";
	/**
	 * 用户收货地址
	 */
	public static final String ADDR = "addr_";
	/**
	 * 商品
	 */
	public static final String GOODS_INFO = "goods_info_";
	/**
	 * 用户收货地址列表
	 */
	public static final String LISTADDR = "listaddr_";
	/**
	 * 店铺关键信息（数据库 缓存 搜索）
	 */
	public static final String SHOPUNIQUE = "shopunique_";

	/**
	 * 商品SKU信息
	 */
	public static final String GOODS_SKU = "goods_sku_";

	/**
	 * 商品规格信息
	 */
	public static final String GOODS_SPEC = "goods_spec_";

	/**
	 * 商品属性信息
	 */
	public static final String GOODS_ATTR = "goods_attr_";

	/**
	 * 商品图片
	 */
	public static final String GOODS_PICS = "goods_pics_";

	/**
	 * 商品评论
	 */
	public static final String GOODS_EVALUATES = "goods_evaluates_";

	/**
	 * 商品品牌
	 */
	public static final String BRAND = "brand_";

	/**
	 * 商品分类品牌信息
	 */
	public static final String BRAND_CATE = "brand_cate_";

	/**
	 * 商品分类
	 */
	public static final String CATEGORY = "category_";

	/**
	 * 商品子分类缓存
	 */
	public static final String CATEGORY_CHILD = "category_child_";

	/**
	 * 属性
	 */
	public static final String ATTR = "attribute_";

	/**
	 * 分类属性
	 */
	public static final String ATTR_CATE = "attribute_cate_";

	/**
	 * 规格
	 */
	public static final String SPEC = "specification_";

	/**
	 * 分类规格
	 */
	public static final String SPEC_CATE = "specification_cate_";

	/**
	 * 活动详情
	 */
	public static final String ACTIVITY = "activity_";

	/**
	 * 商家信息
	 */
	public static final String SHOP_INFO = "shop_info_";

	/**
	 * 商家分类
	 */
	public static final String SHOP_CATES = "shop_cates_";

	/**
	 * 商家对应的host
	 */
	public static final String SHOP_HOST = "shop_host_";

	/**
	 * 商家对应的权限资源
	 */
	public static final String SHOP_AUTH_RESOURCE = "shop_auth_";

	/**
	 * 商家对应的权限资源
	 */
	public static final String TEMPLATE_RESOURCE = "template_";
}
