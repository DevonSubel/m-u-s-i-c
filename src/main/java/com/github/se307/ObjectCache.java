package com.github.se307;

import java.util.HashMap;

/**
 * @author Maxence Weyrich
 *
 * This can serve as a cache to store the most recently accessed Objects.
 * This is useful when database access is expensive and the same Object is being 
 * frequently accessed.
 * 
 * This works by using the combination of a linked-list and a HashMap to keep
 * track of both the object location in memory and to keep track of the most
 * recently used objects.
 */
public class ObjectCache<K, E> {

	private HashMap<K, Node> tableRef;
	private Node head;
	private Node tail;
	
	private int itemCount;
	private int maxItems;
	
	
	public ObjectCache(int itemsToCache) {
		this.maxItems = itemsToCache;
		tableRef = new HashMap<K, Node>(itemsToCache);
		this.maxItems = itemsToCache;
		this.itemCount = 0;
		
		this.head = new Node(null, null);
		this.tail = new Node(null, null);
		
		this.head.next = this.tail;
		this.head.prev = this.head;
		this.tail.prev = this.head;
		this.tail.next = this.tail;
	}
	
	
	/**
	 * Puts the Node at the front of the Linked List
	 * @param n the Node to move
	 */
	private void moveNodeToFront(Node n) {
		n.prev = this.head;
		n.next = this.head.next;
		this.head.next.prev = n;
		this.head.next = n;
	}
	
	/**
	 * Remove the items in cache that have not been used most recently
	 */
	private void trimCache() {
		while(this.itemCount > this.maxItems) {
			Node n = this.tail.prev;
			
			n.prev.next = this.tail;
			this.tail.prev = n.prev;
			
			this.tableRef.remove(n.key);
			
			this.itemCount--;
		}
	}
	
	/**
	 * Insert a new item into the cache
	 * @param key the key of the item to insert
	 * @param item the value to insert 
	 */
	public void insertItem(K key, E item) {
		if(!this.tableRef.containsKey(key)) {
			Node n = new Node(key, item);
			this.tableRef.put(key, n);
			moveNodeToFront(n);
			this.itemCount++;
			
			trimCache();
		}
	}
	
	/**
	 * Get an item from the cache, if present.
	 * @param key the key of the item
	 * @return the value, or null if it was not present
	 */
	public E getItem(K key) {
		if(!this.tableRef.containsKey(key)) {
			return null;
		}
		Node n = tableRef.get(key);
		
		// Update linked list  
		n.next.prev = n.prev;
		n.prev.next = n.next;

		moveNodeToFront(n);
		return n.item;
	}
	
	
	class Node {
		public E item;
		public K key;
		public Node next;
		public Node prev;

		public Node(K key, E item) {
			this.key = key;
			this.item = item;
		}
	}
}
