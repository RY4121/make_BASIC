package newlang4;

import java.util.regex.Pattern;

public class ValueImpl extends Value{
	private String sValue;
	private int iValue;
	private double dValue;
	private boolean bValue;
	private ValueType type;

	public ValueImpl(String s) {
		super(s);
		this.type = ValueType.STRING;
		this.sValue = s;
	}
	public ValueImpl(int i) {
		super(i);
		this.type = ValueType.INTEGER;
		this.iValue = i;
		this.sValue = String.valueOf(i);
	}
	public ValueImpl(double d) {
		super(d);
		this.type = ValueType.DOUBLE;
		this.dValue = d;
		this.sValue = String.valueOf(d);
	}
	public ValueImpl(boolean b) {
		super(b);
		this.type = ValueType.BOOL;
		this.bValue = b;
		this.sValue = String.valueOf(b);
	}
	public ValueImpl(String s, ValueType t) {
		super(s, t);
		this.sValue = s;
		this.type = t;
	}

	@Override
	public String getSValue() {
		return this.sValue;
	}

	@Override
	public int getIValue() {
		if (type == ValueType.DOUBLE) return parseIntToDouble();
		else if (type == ValueType.BOOL) return parseBoolToDigit();
		else if (type == ValueType.STRING) {
			if (this.sValue.equals("True") || this.sValue.equals("False")) return parseStrToBool();
			else return parseIntToDouble();
		}
		return this.iValue;
	}

	@Override
	public double getDValue() {
		if (type == ValueType.INTEGER) return Double.parseDouble(this.sValue);
		else if (type == ValueType.BOOL) return parseBoolToDigit();
		else if (type == ValueType.STRING) {
			if (this.sValue.equals("True") || this.sValue.equals("False")) return parseStrToBool();
			else return Double.parseDouble(this.sValue);
		}
		return this.dValue;
	}

	@Override
	public boolean getBValue() {
		if (type == ValueType.INTEGER ) return this.iValue == 0 ?  false :  true;
		else if (type == ValueType.DOUBLE) return this.dValue == 0.0 ? false : true;
		else if (type == ValueType.STRING) {
			int bool = parseStrToBool();
			return bool == 0 ? false : true;
		}
		return this.bValue;
	}

	@Override
	public ValueType getType() {
		return this.type;
	}

	public int parseIntToDouble() {
		try {
			return Integer.parseInt(this.sValue);
		} catch (NumberFormatException e) {
			if (!Pattern.compile("^[-+.0-9]+$").matcher(this.sValue).matches()) throw new NumberFormatException();
			int idx = this.sValue.indexOf(".");
			return Integer.parseInt(this.sValue.substring(0, idx));
		}
	}

	public int parseBoolToDigit() {
		return this.bValue ? 1 : 0;
	}

	public int parseStrToBool() {
		if (this.sValue.equals("True")) return 1;
		else return 0;
	}

	public int parseStrToDigit() {
		if (Pattern.compile("[-+.0-9]+$").matcher(this.sValue).matches()) {
			double tmpD = Double.parseDouble(this.sValue);
			return tmpD == 0.0 ? 0 : 1;
		}else {
			int tmpI = Integer.parseInt(this.sValue);
			return tmpI == 0 ? 0 : 1;
		}
	}

}
