package com.hiroisojp.java7.coin.trywithresources;

import static junit.framework.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * Test for try-with-resources statement.
 * 
 * @author hiroisojp
 * 
 */
public class TryWithResourcesTest {

	String path = "src/test/resources/sample_try_with_resources.txt";

	String CHARSET = "UTF-8";

	/**
	 * try-with-resources Statement（リソース管理用構文）の挙動を確認するテストです。
	 * <p>
	 * java7では、入出力用の新たな構文が追加されました。
	 * <p>
	 * 従来では、try-finallyを利用して、リソースのクローズ処理が必要でしたが、新たな構文を利用することにより、
	 * 自動的にクローズ処理を行ってくれます。
	 * <p>
	 * この構文を利用する場合は、java7から追加された{@link AutoCloseable} インターフェースを実装する必要がありますが、
	 * {@link Cloneable} インタフェースのスーパーインタフェースであるため、標準で{@link Cloneable}
	 * を実装しているクラスはこの構文を利用することができます。
	 */
	@Test
	public void testTryWithResources() {

		tryWithResourcesSingle();
		tryWithResourcesMulti();

	}

	protected void tryWithResourcesSingle() {

		File file = new File(path);

		// close処理を記載しなくても自動的にクローズされる。
		try (InputStream in = new FileInputStream(file)) {
			byte[] buf = new byte[8192];
			while ((in.read(buf)) >= 0) {
				System.out.println(new String(buf, CHARSET));
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			fail();
		}

	}

	protected void tryWithResourcesMulti() {

		File file = new File(path);

		// 複数処理する場合は、カンマ区切りで処理できる。
		try (InputStream in = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			byte[] buf = new byte[8192];
			int n;
			while ((n = in.read(buf)) >= 0) {
				out.write(buf, 0, n);
			}
			String result = new String(out.toByteArray(), CHARSET);
			System.out.println(result);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			fail();
		}

	}

}
