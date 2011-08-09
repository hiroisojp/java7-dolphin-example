package com.hiroisojp.java7.coin.stringsinswitch;

import org.junit.Test;

/**
 * Test for Strings in switch.
 * 
 * @author hiroisojp
 * 
 */
public class StringsInSwitchTest {

	/**
	 * switch文の文字列利用を確認するテストです。
	 * <p>
	 * java7では、switch文のcaseラベルに文字列を指定できるようになりました。
	 */
	@Test
	public void testStringsInSwitch() {
		stringsInSwitch("aaa");
		stringsInSwitch("bbb");
		stringsInSwitch("ccc");
		stringsInSwitch("ddd");
		stringsInSwitch("eee");
	}

	protected void stringsInSwitch(String s) {
		switch (s) {
		case "aaa":
			System.out.println("case aaa");
			break;
		case "bbb":
			System.out.println("case bbb");
			break;
		case "ccc":
			System.out.println("case ccc");
		case "ddd":
			System.out.println("case ddd");
			break;
		default:
			System.out.println("unknown case " + s);
			break;
		}
	}

}
