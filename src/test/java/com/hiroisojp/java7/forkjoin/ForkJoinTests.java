package com.hiroisojp.java7.forkjoin;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hiroisojp.java7.forkjoin.fibonacci.FibonacciTest;
import com.hiroisojp.java7.forkjoin.mapreduce.MapReduceTest;

/**
 * Java7 Fork/Join Framework Tests.
 * 
 * <p>
 * see more infomation. <a
 * href="http://itpro.nikkeibp.co.jp/article/COLUMN/20110527/360769/?k2">Java SE
 * 7徹底理解　第2回 細粒度の並行処理 - Fork/Join Framework</a>
 * 
 * @author hiroisojp
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ FibonacciTest.class, MapReduceTest.class })
public class ForkJoinTests {

}
