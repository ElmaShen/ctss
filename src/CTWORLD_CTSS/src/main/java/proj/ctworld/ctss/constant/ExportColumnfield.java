package proj.ctworld.ctss.constant;

public enum ExportColumnfield {
	UNIT_CODE("精舍代碼"),
	PLACE("場所"),
	FIRE_REFUGE("防火避難設施"),
	FIRE_PROTECTION("消防安全設備"),
	ELETRIC_DEVICE("電器安全"),
	FIRE_SOURCE("日常火源"),
	CT_NOTE("本山備註"),
	MANAGER("負責人"),
	EXAMINER1("檢查者1"),
	EXAMINER2("檢查者2");
	
	private String text;
	
	private ExportColumnfield(String text) {
		setText(text);
	}
	
	private void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
