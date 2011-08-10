package com.hiroisojp.java7.nio.files;

import static junit.framework.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class FilesTest {

	String resource = "src/test/resources/nio/";

	@Test
	public void testCopy() throws IOException {
		Path source = Paths.get(resource, "sample_nio_files.txt");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Files.copy(source, out);

		String result = new String(out.toByteArray(), "UTF-8");
		System.out.println(result);
	}

	@Test
	public void testCreateDirectories() throws IOException {
		Path dir = Paths.get(resource, "filesdir/dir1");

		File f = dir.toFile();

		if (f.exists()) {
			fail();
		}

		Files.createDirectories(dir);

		if (!f.exists()) {
			fail();
		}

		Files.delete(dir);

	}

	@Test
	public void testCreateDirectory() throws IOException {

		Path dir = Paths.get(resource, "filesdir/dir2");

		File f = dir.toFile();

		if (f.exists()) {
			fail();
		}

		try {
			Files.createDirectory(dir);
		}
		catch (NoSuchFileException e) {
			// サブディレクトリもいっしょには作成できない。
			// サブディレクトリもまとめて作成したい場合は、Files.createDirectoriesを利用する。
		}

		Path parent = Paths.get(resource, "filesdir");
		Files.createDirectory(parent);

		Path child = Paths.get(resource, "filesdir/dir2");
		Files.createDirectory(child);

		if (!f.exists()) {
			fail();
		}

		Files.delete(child);
		Files.delete(parent);

	}

	@Test
	public void testCreateFile() throws IOException {
		Path txt = Paths.get(resource, "files.txt");

		File f = txt.toFile();

		if (f.exists()) {
			fail();
		}

		Files.createFile(txt);

		if (!f.exists()) {
			fail();
		}

		Files.delete(txt);

	}

	@Test
	public void testDelete() throws IOException {

		Path dir = Paths.get(resource, "filesdir");
		try {
			Files.delete(dir);
			fail();
		}
		catch (NoSuchFileException e) {
			// 存在しない場合はエラー。
		}

		Files.createDirectory(dir);
		Files.delete(dir);

	}

	@Test
	public void testDeleteIfExists() throws IOException {

		Path dir = Paths.get(resource, "filesdir");

		// deleteと異なり、ファイルがない場合でもエラーにならない。
		Files.deleteIfExists(dir);

		Files.createDirectory(dir);
		Files.deleteIfExists(dir);

	}

}
