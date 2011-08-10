package com.hiroisojp.java7.nio;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hiroisojp.java7.nio.files.FilesTest;
import com.hiroisojp.java7.nio.path.PathTest;

@RunWith(Suite.class)
@SuiteClasses({ PathTest.class, FilesTest.class })
public class NioTests {

}
