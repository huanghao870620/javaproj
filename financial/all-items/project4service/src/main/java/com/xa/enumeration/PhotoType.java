package com.xa.enumeration;
/**
 * 照片类型
 * @author burgess
 *
 */
public enum PhotoType {
	SID_PHOTO_TYPE(1L), /* 学生证照片 */
	ADM_NOTICE_FILE_TYPE(2L), /* 录取通知书 */
	PASSPORT_FILE_TYPE(3L), /* 护照 */
	LIFE_PHOTO_FILE_TYPE(4L), /* 生活照 */
	GOODS_ACCORDING_TO_POSITIVE_TYPE(5L), /* 商品正面视图 */
	LEFT_PHOTO(6L), /* 商品左侧视图*/
	RIGHT_PHOTO(7L), /* 商品右侧视图 */
	GOODS_INVOICE_TYPE(8L), /* 商品發票 */
	EXPRESS_SINGLE_TYPE(9L), /* 快遞單 */
	HEAD_PORTRAIT(10L), /* 客户头像 */
	CLASSIFIACTION_LOG(11L), /*分类logo*/
	COMMODITY_THUMBNAIL(12L), /*商品缩略图*/
	GOOD_BIG_PHOTO(13L),/*商品大图*/
	GOOD_CUT_PHOTO(14L), /*商品切图*/
	BRAND_PHOTO(15L), /*品牌图片*/
	COUNTRY_PHOTO(16L), /*国家图片*/
	GOOD_ADV_PHOTO(17L), /*商品广告图*/
	VENUE_ADV_PHOTO(18L), /*会场广告图*/
	CERTIFICATION_FRONT_PHOTO(19L),/*认证身份证正面照*/
	CERTIFICATION_BACK_PHOTO(20L) /*认证身份证背面照*/
	;
	private Long value;

	PhotoType(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
