package utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class PairList<A, B> implements List<Pair<A, B>> {
	
	private ArrayList<Pair<A, B>> list;
	
	public PairList() {
		list = new ArrayList<>();
	}
	
	
	public B get(A key) {
		for (Pair<A, B> element : list) {
			if (element.l().equals(key))
				return element.r();
		}
		
		return null;
	}
	
	public void put(A key, B value) {
		list.add(new Pair<>(key, value));
	}
	
	
	/*
	 * IMPLEMENTATION DE LIST
	 */

	public void trimToSize() {
		list.trimToSize();
	}

	public void ensureCapacity(int minCapacity) {
		list.ensureCapacity(minCapacity);
	}

	public int size() {
		return list.size();
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public Object clone() {
		return list.clone();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public String toString() {
		return list.toString();
	}

	public Pair<A, B> get(int index) {
		return list.get(index);
	}

	public Pair<A, B> set(int index, Pair<A, B> element) {
		return list.set(index, element);
	}

	public boolean add(Pair<A, B> e) {
		return list.add(e);
	}

	public boolean equals(Object o) {
		return list.equals(o);
	}

	public void add(int index, Pair<A, B> element) {
		list.add(index, element);
	}

	public Pair<A, B> remove(int index) {
		return list.remove(index);
	}

	public boolean remove(Object o) {
		return list.remove(o);
	}

	public int hashCode() {
		return list.hashCode();
	}

	public void clear() {
		list.clear();
	}

	public boolean addAll(Collection<? extends Pair<A, B>> c) {
		return list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Pair<A, B>> c) {
		return list.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public Stream<Pair<A, B>> stream(){return list.stream();}

	public Stream<Pair<A, B>> parallelStream(){return list.parallelStream();}

	public ListIterator<Pair<A, B>> listIterator(int index) {
		return list.listIterator(index);
	}

	public ListIterator<Pair<A, B>> listIterator() {
		return list.listIterator();
	}

	public Iterator<Pair<A, B>> iterator() {
		return list.iterator();
	}

	public List<Pair<A, B>> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public void forEach(Consumer<? super Pair<A, B>> action) {
		list.forEach(action);
	}

	public Spliterator<Pair<A, B>> spliterator() {
		return list.spliterator();
	}

	public boolean removeIf(Predicate<? super Pair<A, B>> filter) {
		return list.removeIf(filter);
	}

	public void replaceAll(UnaryOperator<Pair<A, B>> operator) {
		list.replaceAll(operator);
	}

	public void sort(Comparator<? super Pair<A, B>> c) {
		list.sort(c);
	}

	
}
