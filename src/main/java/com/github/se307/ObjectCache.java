package com.github.se307;

import java.util.HashMap;

/**
 * @author Maxence Weyrich
 *
 *         This can serve as a cache to store the most recently accessed
 *         Objects. This is useful when database access is expensive and the
 *         same Object is being frequently accessed.
 * 
 *         This works by using the combination of a linked-list and a HashMap to
 *         keep track of both the object location in memory and to keep track of
 *         the most recently used objects.
 */
public class ObjectCache<K, E> {

	private HashMap<K, Node> tableRef;
	private Node head;
	private Node tail;

	private int itemCount;
	private int maxItems;

	public ObjectCache(int itemsToCache) {
		this.maxItems = itemsToCache;
		tableRef = new HashMap<>(itemsToCache);
		this.itemCount = 0;

		this.head = new Node(null, null);
		this.tail = new Node(null, null);

		this.head.setNext(this.tail);
		this.head.setPrev(this.head);
		this.tail.setPrev(this.head);
		this.tail.setNext(this.tail);
	}

	/**
	 * Puts the Node at the front of the Linked List
	 * 
	 * @param n the Node to move
	 */
	private void moveNodeToFront(Node n) {
		n.setPrev(this.head);
		n.setNext(this.head.next());
		this.head.next().setPrev(n);
		this.head.setNext(n);
	}

	/**
	 * Remove the items in cache that have not been used most recently
	 */
	private void trimCache() {
		while (this.itemCount > this.maxItems) {
			Node n = this.tail.prev();

			n.prev().setNext(this.tail);
			this.tail.setPrev(n.prev());

			this.tableRef.remove(n.key);

			this.itemCount--;
		}
	}

	/**
	 * Insert a new item into the cache
	 * 
	 * @param key  the key of the item to insert
	 * @param item the value to insert
	 */
	public void insertItem(K key, E item) {
		if (!this.tableRef.containsKey(key)) {
			Node n = new Node(key, item);
			this.tableRef.put(key, n);
			moveNodeToFront(n);
			this.itemCount++;

			trimCache();
		}
	}

	/**
	 * Get an item from the cache, if present.
	 * 
	 * @param key the key of the item
	 * @return the value, or null if it was not present
	 */
	public E getItem(K key) {
		if (!this.tableRef.containsKey(key)) {
			return null;
		}
		Node n = tableRef.get(key);

		// Update linked list
		n.next().setPrev(n.prev());
		n.prev().setNext(n.next());

		moveNodeToFront(n);
		return n.item();
	}
	
	public int capacity() {
		return this.maxItems;
	}
	
	public int size() {
		return this.itemCount;
	}

	private class Node {
		private E item;
		private K key;
		private Node next;
		private Node prev;

		public Node(K key, E item) {
			this.key = key;
			this.item = item;
		}
		
		public E item() {
			return item;
		}
		
		public K key() {
			return key;
		}
		
		public Node next() {
			return next;
		}
		
		public Node prev() {
			return prev;
		}
		
		public void setNext(Node o) {
			next = o;
		}
		
		public void setPrev(Node o) {
			prev = o;
		}
	}
}
