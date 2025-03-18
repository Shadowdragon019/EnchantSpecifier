package lol.roxxane.enchant_specifier.utils;

import java.util.Map;
import java.util.Objects;

public class Pair<F, S> implements Map.Entry<F, S> {
	public F first;
	public S second;

	Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public static <F, S> Pair<F, S> of(F first, S second) {
		return new Pair<>(first, second);
	}

	@Override
	public F getKey() {
		return first;
	}

	@Override
	public S getValue() {
		return second;
	}

	@Override
	public S setValue(S value) {
		S old_value = second;
		second = value;
		return old_value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		Pair<?, ?> pair = (Pair<?, ?>) object;
		return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}
}