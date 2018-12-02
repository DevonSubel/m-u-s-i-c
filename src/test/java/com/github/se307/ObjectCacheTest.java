package com.github.se307;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ObjectCacheTest {

	ObjectCache<String, Long> cache;

	@Before
	public void setup() {
		cache = new ObjectCache<String, Long>(10);
	}

	@Test
	public void createObjectCache() {
		assertEquals(10, cache.capacity());
		assertEquals(0, cache.size());
	}

	@Test
	public void addItems() {
		cache.insertItem("a", 1L);
		cache.insertItem("b", 2L);

		assertEquals(2, cache.size());

		cache.insertItem("b", 3L);

		assertEquals(2, cache.size());
	}

	@Test
	public void retrieveItems() {
		cache.insertItem("a", 1L);
		cache.insertItem("b", 2L);

		assertEquals(2L, (long) cache.getItem("b"));
		assertEquals(1L, (long) cache.getItem("a"));
	}

	@Test
	public void addRetrieveManyItems() {
		String[] names = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l" };
		long i = 0;

		for (String name : names) {
			cache.insertItem(name, (long) i);
			i++;
		}

		assertEquals(10, cache.size());

		assertEquals(null, cache.getItem("a"));
		assertEquals(null, cache.getItem("b"));
		assertEquals(2L, (long) cache.getItem("c"));
	}

}
