package com.hiroisojp.java7.coin.diamond;

import static junit.framework.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Test for Improved Type Inference for Generic Instance Creation (diamond).
 * 
 * @author hiroisojp
 * 
 */
public class DiamondTest {

	/**
	 * diamondの挙動を確認するためのテストです。
	 * <p>
	 * diamondでは、型推論により、コンストラクタ呼び出し時の記述が簡易化されました。
	 * <p>
	 * java6では、<code>List<String> list = new ArrayList<String>();</code>
	 * のように、インスタンス作成時にも型情報を記述しなければいけませんでしたが、java6からは、次のように記述することが可能です。
	 * <code>List<String> list = new ArrayList<>();</code>
	 */
	@Test
	public void testDiamond() {
		// 型推論により、記載が簡易になった。
		List<String> list = new ArrayList<>();
		list.add("aaa");
		list.add("bbb");

		List<String> oldStyleList = new ArrayList<String>();
		oldStyleList.add("aaa");
		oldStyleList.add("bbb");

		Map<String, Object> map = new HashMap<>();
		map.put("aaa", "111");
		map.put("bbb", "222");

		Map<String, Object> oldStyleMap = new HashMap<String, Object>();
		oldStyleMap.put("aaa", "111");
		oldStyleMap.put("bbb", "222");

		// 複雑なネストしたものもOK。
		Map<String, List<Map<String, Object>>> complexMap = new HashMap<>();
		List<Map<String, Object>> listMap = new ArrayList<>();
		listMap.add(map);
		listMap.add(oldStyleMap);

		complexMap.put("aaa", listMap);

		// 型情報を与える必要があるものはあまり変わっていない。
		// addAllなどはコンパイルエラー。
		// list.addAll(new ArrayList<>());

		// toArrayは相変わらずできない。
		// String[] ss = list.toArray();
		String[] ss = list.toArray(new String[] {});
		assertNotNull(ss);

	}

}
