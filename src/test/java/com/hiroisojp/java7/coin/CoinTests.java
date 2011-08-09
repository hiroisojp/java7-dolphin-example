package com.hiroisojp.java7.coin;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hiroisojp.java7.coin.diamond.DiamondTest;
import com.hiroisojp.java7.coin.literals.LiteralsTest;
import com.hiroisojp.java7.coin.multicatch.MultiCatchTest;
import com.hiroisojp.java7.coin.stringsinswitch.StringsInSwitchTest;
import com.hiroisojp.java7.coin.trywithresources.TryWithResourcesTest;
import com.hiroisojp.java7.coin.varargs.VarargsTest;

/**
 * Java7 Project Coin Tests.
 * 
 * <p>
 * see more infomation. <a
 * href="http://jcp.org/en/jsr/detail?id=334">JSR-334</a>
 * 
 * @author hiroisojp
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ DiamondTest.class, LiteralsTest.class, MultiCatchTest.class,
		StringsInSwitchTest.class, TryWithResourcesTest.class,
		VarargsTest.class })
public class CoinTests {

}
