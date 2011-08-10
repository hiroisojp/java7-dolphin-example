package com.hiroisojp.java7;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hiroisojp.java7.coin.CoinTests;
import com.hiroisojp.java7.forkjoin.ForkJoinTests;
import com.hiroisojp.java7.nio.NioTests;

/**
 * Java7 features Test Suite.
 * 
 * @author hiroisojp
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ CoinTests.class, ForkJoinTests.class, NioTests.class })
public class Java7TestSuite {

}
