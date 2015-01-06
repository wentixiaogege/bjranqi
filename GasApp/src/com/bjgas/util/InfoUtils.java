package com.bjgas.util;

public class InfoUtils {
	public static final String TAG_THREAD = "Thread_Tag";
	public static final String TAG_LIFECYCLE = "Life_Cycle";

	// public static final String DISPLAY_ZONGXIAOLV = "总效率";
	//
	// public static final String DISPLAY_HAOQi = "耗气";
	// public static final String DISPLAY_ZHIRE = "制热";
	//
	// public static final String DISPLAY_HAODIAN = "耗电";
	// public static final String DISPLAY_ZHILENG = "制冷";

	// public static final String DISPLAY_FADIAN = "发电";
	// public static final String DISPLAY_HAOQI = "耗气";
	// public static final String DISPLAY_YURE = "余热";
	public static final String ZONGXITONG_XIAOLV = "总系统效率";
	public static final String FADIANJI_XIAOLV = "发电机系统效率";
	public static final String DIANZHILENG_XIAOLV = "电制冷系统效率";
	public static final String GUOLU_XIAOLV = "锅炉系统效率";
	public static final String ZHIRANJI_XIAOLV = "直燃机效率";
	public static final String LENGQUETA_XIAOLV = "冷却塔效率";
	public static final String SHUIBENG_XIAOLV = "水泵效率";
	public static final String YICINENGYUAN_XIAOLV = "一次能源利用率";
	public static final String YURE_XIAOLV = "余热系统利用率";

	public static final String INPUT_ELEC = "总耗电（℃）";
	public static final String INPUT_ELEC_SIMPLE = "耗电";
	public static final String INPUT_AIR = "总耗天然气（m³）";
	public static final String INPUT_AIR_SIMPLE = "耗气";
	public static final String INPUT_WATER = "总耗水（t）";
	public static final String INPUT_WATER_SIMPLE = "耗水";

	public static final String OUTPUT_ELEC = "供电总量（℃）";
	public static final String OUTPUT_ELEC_SIMPLE = "供电";
	public static final String OUTPUT_COLD = "总供冷量（RT）";
	public static final String OUTPUT_COLD_SIMPLE = "供冷";
	public static final String OUTPUT_HOT = "总供热量（J）";
	public static final String OUTPUT_HOT_SIMPLE = "供热";

	public static final String FADIANJI_FADIAN = "供电量";
	public static final String FADIANJI_YURE = "总供热量";
	public static final String FADIANJI_HAOQI = "耗天然气";

	public static final String ZHILENG_HAODIAN = "耗电量";
	public static final String ZHILENG_ZHILENG = "供冷";

	public static final String GUOLU_HAOQI = "耗天然气";
	public static final String GUOLU_ZHIRE = "供热";

	public static final String SHENGCHANYONGDIAN_YONGDIAN = "生产用电（℃）";

	public static final String JINGYING_SHOURU_ELEC = "电总收入";
	public static final String JINGYING_SHOURU_ELEC_SIMPLE = "发电";
	public static final String JINGYING_SHOURU_HOT = "热量收入";
	public static final String JINGYING_SHOURU_HOT_SIMPLE = "产热";
	public static final String JINGYING_SHOURU_COLD = "冷量收入";
	public static final String JINGYING_SHOURU_COLD_SIMPLE = "产冷";
	public static final String JINGYING_TOURU_ELEC = "电总投入";
	public static final String JINGYING_TOURU_ELEC_SIMPLE = "耗电";
	public static final String JINGYING_TOURU_WATER = "水总投入";
	public static final String JINGYING_TOURU_WATER_SIMPLE = "耗水";
	public static final String JINGYING_TOURU_GAS = "天然气总投入";
	public static final String JINGYING_TOURU_GAS_SIMPLE = "耗气";

	// 以下是各种html请求的key
	public static final String ZONGJIEGOU_KEY = "zongjiegou";
	public static final String FADIANJI_KEY = "fadianji";
	public static final String ZHILENG_KEY = "zhileng";
	public static final String GUOLU_KEY = "guolu";
	public static final String SHENGCHANYONGDIAN_KEY = "shengchanyongdian";
	public static final String ZONGXIAOLV = "zongxiaolv";
	public static final String FADIANJIXIAOLV = "fadianjixiaolv";
	public static final String DIANZHILENGXIAOLV = "dianzhilengxiaolv";
	public static final String GUOLUXIAOLV = "guoluxiaolv";
	public static final String ZHIRANJIXIAOLV = "zhiranjixiaolv";
	public static final String LENGQUETAXIAOLV = "lengquetaxiaolv";
	public static final String LENGDONGTAXIAOLV = "lengdongtaxiaolv";
	public static final String NENGYUANLIYONGXIAOLV = "nengyuanliyongxiaolv";
	public static final String YUREXIAOLV = "yurexiaolv";
	// jingying
	public static final String ZONGXITONG = "zongxitong";
	public static final String TOURU = "touru";
	public static final String CHANCHU = "chanchu";

	// 地图坐标信息
	public static final int STANDARD_WIDTH = 1280;
	public static final int STANDARD_HEIGHT = 800;
	// 清河医院
	public static final int X_QINGHE = 335;
	public static final int Y_QINGHE = 358;
	// 金雁饭店
	public static final int JINGYANFANDIAN_X = 670;
	public static final int JINGYANFANDIAN_Y = 163;
	// 北七家园
	public static final int BEIQIJIAYUAN_X = 405;
	public static final int BEIQIJIAYUAN_Y = 273;
	// 中石油创新基地
	public static final int ZHONGSHIYOU_X = 601;
	public static final int ZHONGSHIYOU_Y = 271;
	// 中关村一号
	public static final int ZHONGGUANCUNYIHAO_X = 514;
	public static final int ZHONGGUANCUNYIHAO_Y = 360;
	// 中关村软件园
	public static final int ZHONGGUANCUNRUANJIANYUAN_X = 388;
	public static final int ZHONGGUANCUNRUANJIANYUAN_Y = 428;
	// 海淀医院
	public static final int HAIDIANYIYUAN_X = 562;
	public static final int HAIDIANYIYUAN_Y = 432;
	// 焦化厂
	public static final int JIAOHUACHANG_X = 426;
	public static final int JIAOHUACHANG_Y = 499;
	// 通州中医院
	public static final int TONGZHOUZHONGYIYUAN_X = 635;
	public static final int TONGZHOUZHONGYIYUAN_Y = 502;
	// 国润新通酒店
	public static final int GUORUNXINTONG_X = 726;
	public static final int GUORUNXINTONG_Y = 569;

	public static final String ADDRESS = "address";
	public static final String GUORUNXINTONG = "国润新通酒店";
	public static final String ZHONGSHIYOU = "中石油创新基地";
	public static final String JINYAN = "金雁饭店";
	public static final String ZHONGGUANCUNRUANJIANYUAN = "中关村软件园";
	public static final String TONGZHOUZHONGYIYUAN = "通州中医院";
	public static final String HAIDIANYIYUAN = "海淀医院";
	public static final String JIAOHUACHANG = "焦化厂";
	public static final String BEIQI = "北七家园区";
	public static final String ZHONGGUANCUNYIHAO = "中关村壹号";
	public static final String QINGHEYIYUAN = "清河医院";

	// 大标题
	public static final String TIT_ZONGJIEGOU = "总结构";
	public static final String TIT_FADIANJIXITONG = "发电机系统";
	public static final String TIT_ZHILENGXITONG = "制冷系统";
	public static final String TIT_GUOLUXITONG = "锅炉系统";
	public static final String TIT_SHENGCHANYONGDIAN = "生产用电";
	public static final String TIT_ZONGXITONGXIAOLV = "总系统效率";
	public static final String TIT_ZONGXITONGXIAOLV_SIMPLE = "总效率";

	public static final String TIT_FADIANJIXITONGXIAOLV = "发电机系统效率";
	public static final String TIT_DIANZHILENGXITONGXIAOLV = "电制冷系统效率";
	public static final String TIT_GUOLUXITONGXIAOLV = "锅炉系统效率";
	public static final String TIT_ZHIRANJIXIAOLV = "直燃机效率";
	public static final String TIT_LENGQUETAXIAOLV = "冷却塔效率";
	public static final String TIT_SHUIBENGXIAOLV = "水泵效率";
	public static final String TIT_YICINENGYUANLIYONGXIAOLV = "一次能源利用率";
	public static final String TIT_YICINENGYUANLIYONGXIAOLV_SIMPLE = "利用率";

	public static final String TIT_YUREXITONGLIYONGLV = "余热系统利用率";
	public static final String TIT_ZONGXITONGJINGYING = "总系统经营分析";
	public static final String TIT_TOURUFENXI = "投入分析";
	public static final String TIT_CHANCHUFENXI = "产出分析";

}
