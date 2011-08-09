package com.hiroisojp.java7.nio.path;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class PathTest {

	String base = "src/test/resources/nio/sample_nio_path.txt";

	@Test
	public void testPath() throws IOException {

		Path path = Paths.get(base);

		testIsAbsolute(path);
		testGetFileName(path);
		testRoot(path);
		testStartWith(path);
		testEndWith(path);
		testSubpath(path);

		testIterable(path);
		testSplitPath(path);
		testDotPath(path);

		testURIPath(path);

	}

	protected void testIsAbsolute(Path path) throws IOException {
		assertFalse(path.isAbsolute());

		Path uriPath = Paths.get(path.toUri());
		assertTrue(uriPath.isAbsolute());
	}

	protected void testGetFileName(Path path) throws IOException {

		assertEquals("sample_nio_path.txt", path.getFileName().toString());
		assertEquals("nio", path.getParent().getFileName().toString());

	}

	protected void testRoot(Path path) throws IOException {
		// 相対パスの場合、nullが返される。
		Path root = path.getRoot();
		assertNull(root);

		// 絶対パスの場合、(windowsの場合)実行ディレクトリのドライブが返される。
		Path uriPath = Paths.get(path.toUri());
		root = uriPath.getRoot();
		assertNotNull(root);
		System.out.println(root.toString());
	}

	protected void testStartWith(Path path) throws IOException {

		// 文字列ではなく、ファイル名部分のPathオブジェクトの表現と一致しないので、falseとなる。
		assertFalse(path.startsWith("sr"));

		assertTrue(path.startsWith("src"));

		// 絶対パスの場合、(windowsの場合)実行ディレクトリのドライブから比較されるので結果は異なる。
		Path uriPath = Paths.get(path.toUri());
		assertFalse(uriPath.startsWith("src"));

	}

	protected void testEndWith(Path path) throws IOException {

		// 文字列ではなく、ファイル名部分のPathオブジェクトの表現と一致しないので、falseとなる。
		assertFalse(path.endsWith("path.txt"));

		assertTrue(path.endsWith("sample_nio_path.txt"));
	}

	protected void testSubpath(Path path) throws IOException {

		assertEquals(5, path.getNameCount());
		assertEquals("src", path.getName(0).toString());
		assertEquals("test", path.getName(1).toString());

		Path subpath = path.subpath(1, 4);

		assertTrue(subpath.endsWith("test/resources/nio"));

		try {
			// path.subpathで取得したものは、実行位置からみると存在しないのでエラーになる。
			subpath.toRealPath();
			fail();
		}
		catch (NoSuchFileException ex) {
			// ok
		}

		Path src = Paths.get("src");
		// 実行位置からみて存在するsrcを起点としてresolveによって取得すると存在するので正しく取得できる。
		Path nio = src.resolve(subpath);
		nio.toRealPath();

		Path dir1 = Paths.get("src/test/resources/nio/dir1/");

		// dir1からみて、子要素を取得。
		Path dir2file1 = dir1.resolve("dir2/dir2file1.txt");

		// dir2にある兄弟要素を取得。
		Path dir2file2 = dir2file1.resolveSibling("dir2file2.txt");
		dir2file2.toRealPath();

		// 「src/test/resources/nio」からみて、「src/test/resources/nio/sample_nio_path.txt」の位置関係を示す
		Path txt = nio.relativize(path);
		assertEquals("sample_nio_path.txt", txt.toString());
		System.out.println(txt.toAbsolutePath());

		// 存在しないファイルでもオブジェクトは取得可能
		Path dummydir = src.resolve("dummydir");
		Path dummydir2 = dummydir.resolve("dummydir2");

		assertNotNull(dummydir);
		assertNotNull(dummydir2);

	}

	protected void testSplitPath(Path path) throws IOException {

		String[] ss = new String[] { "test", "resources", "nio/",
				"sample_nio_path.txt" };
		Path path2 = Paths.get("src", ss);

		assertEquals(path.normalize(), path2.normalize());
		assertEquals(path.toRealPath(), path2.toRealPath());

		assertEquals(path.toAbsolutePath(), path2.toAbsolutePath());
		assertEquals(path.toString(), path2.toString());

		System.out.println(String.format(
				"path.toAbsolutePath()=[%s], path2.toAbsolutePath()=[%s]",
				path.toAbsolutePath(), path2.toAbsolutePath()));
		System.out.println(String.format(
				"path.toString()=[%s], path2.toString()=[%s]", path.toString(),
				path2.toString()));

	}

	protected void testDotPath(Path path) throws IOException {

		String[] s = base.split("/");
		Path path2 = Paths.get("./", s);

		// 「.」、「..」などの表現を取り除くと一致。
		assertEquals(path.normalize(), path2.normalize());

		assertEquals(path.toRealPath(), path2.toRealPath());

		// normalize以外の表現では、「.」が含まれている分一致しない。
		assertFalse(path.toAbsolutePath().equals(path2.toAbsolutePath()));
		assertFalse(path.toString().equals(path2.toString()));

		System.out.println(String.format(
				"path.toAbsolutePath()=[%s], path2.toAbsolutePath()=[%s]",
				path.toAbsolutePath(), path2.toAbsolutePath()));
		System.out.println(String.format(
				"path.toString()=[%s], path2.toString()=[%s]", path.toString(),
				path2.toString()));

	}

	protected void testURIPath(Path path) throws IOException {

		File file = path.toFile();

		assertTrue(file.exists());
		URI uri = file.toURI();

		Path path2 = Paths.get(uri);

		assertEquals(path.toAbsolutePath(), path2.toAbsolutePath());
		assertEquals(path.toRealPath(), path2.toRealPath());

		// pathは、相対パスから取得しているので、相対パスで返される。
		// pathFromURIは、URIから取得しているので絶対パスが返される。
		assertFalse(path.toString().equals(path2.toString()));

		assertFalse(path.compareTo(path2) == 0);

		System.out
				.println(String
						.format("path.toAbsolutePath()=[%s], path2.toAbsolutePath()=[%s], file.getAbsolutePath()=[%s]",
								path.toAbsolutePath(), path2.toAbsolutePath(),
								file.getAbsolutePath()));
		System.out
				.println(String
						.format("path.toString()=[%s], path2.toString()=[%s], file.toString()=[%s]",
								path.toString(), path2.toString(),
								file.toString()));

	}

	protected void testIterable(Path path) throws IOException {
		// ディレクトリ、ファイル単位でPathオブジェクト化されている。
		for (Path p : path) {
			System.out.println(p.toString());
		}
	}

}
