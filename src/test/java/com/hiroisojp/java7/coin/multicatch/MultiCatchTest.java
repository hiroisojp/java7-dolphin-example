package com.hiroisojp.java7.coin.multicatch;

import static junit.framework.Assert.fail;

import org.junit.Test;

import com.hiroisojp.java7.exception.MyAChildException;
import com.hiroisojp.java7.exception.MyAException;
import com.hiroisojp.java7.exception.MyBException;
import com.hiroisojp.java7.exception.MyExceptionEnum;

/**
 * Test for Multi-catch and more precise rethrow.
 * 
 * @author hiroisojp
 * 
 */
public class MultiCatchTest {

	/**
	 * MultiCatchの挙動を確認するテストです。
	 * <p>
	 * java7から、複数の例外を"|"区切りで一つのcatch節で扱うことが可能になりました。
	 * <p>
	 * <code>
	 * try{
	 *    ...something
	 * }
	 * catch(MyExceptionA | MyExceptionB){
	 *    ...exception handling
	 * }
	 * </code>
	 */
	@Test
	public void testMultiCatch() {

		throwException(MyExceptionEnum.EXCEPTION_A);
		throwException(MyExceptionEnum.EXCEPTION_B);
		throwExceptionChild(MyExceptionEnum.EXCEPTION_A);
		throwExceptionChild(MyExceptionEnum.EXCEPTION_A_CHILD);
	}

	/**
	 * Precise Rethrowの挙動を確認するテストです。
	 * <p>
	 * java7から、再スロー時にも型推論が機能するようになりました。 throws宣言に適切な型を記載することが可能になります。
	 * 
	 * @see MultiCatchTest#rethrow()
	 * @see MultiCatchTest#oldRethrow()
	 */
	@Test
	public void testPreciseRethrow() {
		try {
			rethrow();
			fail();
		}
		catch (MyAException ex) {
		}
	}

	protected void throwException(MyExceptionEnum type) {
		try {
			if (type.equals(MyExceptionEnum.EXCEPTION_A)) {
				throw new MyAException();
			}
			else if (type.equals(MyExceptionEnum.EXCEPTION_B)) {
				throw new MyBException();
			}
			// "|"区切りで複数の例外をまとめることが可能になった。
		}
		catch (MyAException | MyBException ex) {
			System.out.println(ex.getClass() + " がスローされました。");
			// throw ex;
		}
	}

	protected void throwExceptionChild(MyExceptionEnum type) {
		try {
			if (type.equals(MyExceptionEnum.EXCEPTION_A)) {
				throw new MyAException();
			}
			else if (type.equals(MyExceptionEnum.EXCEPTION_A_CHILD)) {
				throw new MyAChildException();
			}
			// 継承関係にある場合は親クラス指定のみ対応。
			// どちらもコンパイルエラーになる。
			// } catch (MyExceptionA | MyExceptionAChild ex) {
			// } catch (MyExceptionAChild | MyExceptionA ex) {
		}
		catch (MyAException ex) {
			System.out.println(ex.getClass() + " がスローされました。");
			// throw ex;
		}
	}

	protected void rethrow() throws MyAException { // 再スロー時、throwsは、catch節で宣言したスコープのExceptionでないといけなかったが、指定ができるようになった。
		try {
			throw new MyAException();
			// java6では、型推論が働かないのでコンパイルエラーとなる。
		}
		catch (final Exception ex) {
			System.out.println(ex.getClass() + " がスローされました。再スローします。");
			throw ex;
		}
	}

	protected void oldRethrow() throws MyAException {
		try {
			throw new MyAException();
			// MyExceptionAを明示している場合は、java6でもOK。
		}
		catch (MyAException ex) {
			throw ex;
		}
	}

}
