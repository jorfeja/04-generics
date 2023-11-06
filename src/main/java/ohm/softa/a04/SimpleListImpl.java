package ohm.softa.a04;

import java.util.Iterator;

/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
public class SimpleListImpl<T> implements SimpleList<T> {

	private ListElement head;
	private int size;

	public SimpleListImpl() {
		head = null;
	}

	/**
	 * Add an object to the end of the list
	 * @param item item to add
	 */
	public void add(T item) {
		/* special case empty list */
		if(head == null){
			head = new ListElement<>(item);
		}else {
			/* any other list length */
			ListElement<T> current = head;
			while (current.getNext() != null){
				current = current.getNext();
			}
			current.setNext(new ListElement<>(item));
		}
		size++;
	}

	@Override
	public void addDefault(Class<T> cl) throws InstantiationException, IllegalAccessException{
		add(cl.newInstance());
	}

	/**
	 * @return size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Get a new SimpleList instance with all items of this list which match the given filter
	 * @param filter SimpleFilter instance
	 * @return new SimpleList instance
	 */
	public SimpleList<T> filter(SimpleFilter<T> filter){
		SimpleList<T> result = new SimpleListImpl<>();
		for(T item : this){
			if(filter.include(item)){
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Iterator<T> iterator() {
		return new SimpleIterator();
	}

	/**
	 * Helper class which implements the Iterator interface
	 * Has to be non static because otherwise it could not access the head of the list
	 */
	private class SimpleIterator implements Iterator<T> {

		private ListElement<T> current = head;

		/**
		 * @inheritDoc
		 */
		@Override
		public boolean hasNext() {
			return current != null;
		}

		/**
		 * @inheritDoc
		 */
		@Override
		public T next() {
			T tmp = current.getItem();
			current = current.getNext();
			return tmp;
		}
	}

	/**
	 * Helper class for the linked list
	 * can be static because the ListElement does not need to access the SimpleList instance
	 * Since it is static, it introduces a new generic type Z.
	 */
	private static class ListElement<Z> {
		private Z item;
		private ListElement<Z> next;

		ListElement(Z item) {
			this.item = item;
			this.next = null;
		}

		/**
		 * @return get object in the element
		 */
		public Z getItem() {
			return item;
		}

		/**
		 * @return successor of the ListElement - may be NULL
		 */
		public ListElement<Z> getNext() {
			return next;
		}

		/**
		 * Sets the successor of the ListElement
		 * @param next ListElement
		 */
		public void setNext(ListElement<Z> next) {
			this.next = next;
		}
	}

}
