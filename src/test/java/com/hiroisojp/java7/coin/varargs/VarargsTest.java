package com.hiroisojp.java7.coin.varargs;

import static junit.framework.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test for Simplified Varargs Method Invocation.
 * 
 * @author hiroisojp
 * 
 */
public class VarargsTest {

	/**
	 * 型パラメータの可変長引数における警告の挙動を確認するためのテストです。
	 * <p>
	 * 可変長引数にジェネリクスが使用されている場合、警告が発生しますが、
	 * 安全を示すためのアノテーション、SafeVarargsが提供されるようになりました。
	 */
	@Test
	public void testVarargs() {

		// SafeVarargsを注釈していている場合、警告が出なくなった。
		// Type safety : A generic array of List<String> is created for a
		// varargs parameter
		List<List<String>> list = safeList(safeList("aaa", "bbb"));
		for (List<String> stringList : list) {
			for (String s : stringList) {
				System.out.println(s);
			}
		}

		try {
			List<String> list1 = safeList("aaa", "bbb");
			List<String> list2 = safeList("ccc", "ddd");
			List<String> unsafeList = unSafeList(list1, list2);

			// この例では、SafeVarargsを注釈していても、ヒープ汚染されているので、コンパイルは通るが、取得時にClassCastExceptionが発生する。
			// String s = lists[0].get(0);
			for (String s : unsafeList) {
				System.out.println(s);
			}
			fail();
		}
		catch (ClassCastException ex) {
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * 
	 * <p>
	 * ヒープ汚染が発生する可能性があるため、以下の警告がでる。
	 * <p>
	 * 「Type safety: Potential heap pollution via varargs parameter args」
	 * <p>
	 * 警告を出さないようにするには、SafeVarargsを注釈する。 SafeVarargsは、static or finalなメソッドに注釈できる。
	 * 
	 * @param args
	 * @return
	 */
	public <T> List<T> list(T... args) {
		return Arrays.asList(args);
	}

	@SafeVarargs
	public static <T> List<T> safeList(T... args) {
		return Arrays.asList(args);
	}

	// finalにも注釈できる
	@SafeVarargs
	public final <T> List<T> finalSafeList(T... args) {
		return Arrays.asList(args);
	}

	@SafeVarargs
	public static <T> List<T> unSafeList(List<String>... lists) {

		Object[] array = lists;

		// ヒープ汚染を発生させる。（ジェネリクスで保証している型と別の型が紛れ込む）
		array[0] = Arrays.asList(new Integer(10));
		return (List<T>) Arrays.asList(array);
	}

}
