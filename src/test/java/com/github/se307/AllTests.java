package com.github.se307;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DatabaseDriverTest.class, DynamicSongSetTest.class, LocalResourcesTest.class, ObjectCacheTest.class,
		SongTest.class, StaticSongSetTest.class, UserSettingsManagerTest.class })

public class AllTests {

}
