package com.card.operation.utils;

/**
 * 身份证性别、民族枚举
 * @author taochuang
 *@since 17.11.15
 */
public enum SexAndGender {
	unkonw("0", "未知"),
	male("1", "男"),
	female("2", "女"),
	unstated("9", "未说明"),
	han("01", "汉"),
	menggu("02", "蒙古"),
	hui("03", "回"),
	zang("04", "藏"),
	weiwuer("05", "维吾尔"),
	miao("06", "苗"),
	yi("07", "彝"),
	zhuang("08", "壮"),
	buyi("09", "布依"),
	chaoxian("10", "朝鲜"),
	man("11", "满"),
	dong("12", "侗"),
	yao("13", "瑶"),
	bai("14", "白"),
	tujia("15", "土家"),
	hani("16", "哈尼"),
	hasake("17", "哈萨克"),
	dai("18", "傣"),
	li("19", "黎"),
	susu("20", "傈傈"),
	wa("21", "佤"),
	she("22", "畲"),
	gaoshan("23", "高山"),
	lahu("24", "拉祜"),
	shui("25", "水"),
	dongxiang("26", "东乡"),
	naxi("27", "纳西"),
	jingpo("28", "景颇"),
	keerkemu("29", "柯尔克牧"),
	tu("30", "土"),
	dawoer("31", "达斡尔"),
	mulao("32", "仫佬"),
	qiang("33", "羌"),
	bulang("34", "布朗"),
	sala("35", "撒拉"),
	maonan("36", "毛南"),
	gelao("37", "仡佬"),
	xibo("38", "锡伯"),
	achang("39", "阿昌"),
	pumi("40", "普米"),
	tajike("41", "塔吉克"),
	nu("42", "怒"),
	wumubieke("43", "乌木别克"),
	eluos("44", "俄罗斯"),
	ewenke("45", "鄂温克"),
	deang("46", "德昂"),
	baoan("47", "保安"),
	yugu("48", "裕固"),
	jing("49", "京"),
	tataer("50", "塔塔尔"),
	dulong("51", "独龙"),
	elunchun("52", "鄂伦春"),
	hezhe("53", "赫哲"),
	menba("54", "门巴"),
	luoba("55", "珞巴"),
	jinuo("56", "基诺");

	private final String code;
	private final String info;
	

	private SexAndGender(String code, String info) {
		this.code = code;
		this.info = info;
	}
	
	public static String stateOf(String code) {
		for(SexAndGender sexAndGender : values()) {
			if(sexAndGender.code.equals(code)) {
				return sexAndGender.info;
			}
		}
		return null;
	}
	
}
