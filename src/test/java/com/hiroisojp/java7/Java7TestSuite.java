package com.hiroisojp.java7;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hiroisojp.java7.coin.CoinTests;
import com.hiroisojp.java7.forkjoin.ForkJoinTests;

/**
 * Java7 features Test Suite.
 * 
 * @author hiroisojp
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ CoinTests.class, ForkJoinTests.class })
public class Java7TestSuite {

}
