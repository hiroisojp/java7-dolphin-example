package com.hiroisojp.java7.coin.literals;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for Binary integral literals and underscores in numeric literals.
 * 
 * @author hiroisojp
 * 
 */
public class LiteralsTest {

	/**
	 * リテラル表記を確認するテストです。
	 * <p>
	 * java7から、以下の表記が可能になりました。
	 * <ul>
	 * <li>2進数リテラル</li>
	 * <li>数値リテラルのアンダースコアによる区切り</li>
	 * </ul>
	 * <p>
	 * 2進数リテラル
	 * <p>
	 * "0b"もしくは、"0B"を先頭に付与して表記することで、2進数として扱われます。 <code>int i = 0b1111011;</code>
	 * と、<code>int i = 123;</code>は同値となります。
	 * <p>
	 * 数値リテラルのアンダースコアによる区切り
	 * <p>
	 * 数値リテラルについて、可読性向上のため、アンダースコアによる区切りを利用できるようになりました。
	 * <code>int i = 123_456_789;</code>のように、任意の場所で区切り文字を利用し、可読性を向上させることができます。
	 */
	@Test
	public void testLiterals() {
		binaryLiterals();
		numericLiterals();
	}

	protected void numericLiterals() {
		int a = 123;

		// 数値リテラルの区切り文字としてアンダースコアが可能になった。
		int b = 1_2_3;

		// 2進数リテラルは、先頭に"0b"を付けて表記。
		int c = 0b1111011;

		// 16進数は、先頭に"0x"を付けて表記。（従来から存在）
		int d = 0x7b;
		assertEquals(a, b);
		assertEquals(a, c);
		assertEquals(a, d);

		// for文にも普通に利用できる。
		for (int i = 0; i < 1_0___0; i++) {
			System.out.println(i);
		}

		// 小数点以下にも可能。区切りは複数あってもよい。
		double e = 1.234_456_7_8;
		System.out.println(e);

		// 2進数リテラル、16進数リテラルにも区切り文字を利用できる。
		int f = 0b1_1_1_1;
		int g = 0x000f_0001_01;
		System.out.println(f);
		System.out.println(g);

		// 区切り文字は連続してもコンパイルエラーにならない。（意味があるかは別として）
		int h = 1___2___3__4_5;
		int i = 12345;
		assertEquals(h, i);

		// 区切り文字の位置が悪いとコンパイルエラーになる。
		// int ng = _1234; // 変数名として宣言できるためNG
		// int ng = 0x_1234;
	}

	protected void binaryLiterals() {
		byte a = (byte) 0b1111011;
		byte b = (byte) 0x7b;
		assertEquals(a, b);
	}

}
