package lol.roxxane.enchant_specifier.utils;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class EsUtils {
	@SuppressWarnings("unchecked")
	public static <K, V> HashMap<K, V> new_hash_map(Object... objects) {
		var hashmap = new HashMap<K, V>();
		Object key = null;
		var is_key = true;
		for (var object : objects) {
			if (is_key) {key = object; is_key = false;}
			else { hashmap.put((K) key, (V) object); is_key = true; }
		}
		return hashmap;
	}

	public static CommentedConfig new_config(HashMap<String, Object> map) {
		return TomlFormat.newConfig(() -> map);
	}

	public static CommentedConfig new_config(Object... objects) {
		return TomlFormat.newConfig(() -> new_hash_map(objects));
	}

	public static <InKey, InValue, OutKey, OutValue> HashMap<OutKey, OutValue> map(Map<InKey, InValue> in_map,
		BiFunction<InKey, InValue, Pair<OutKey, OutValue>> function
	) {
		var out_map = new HashMap<OutKey, OutValue>();
		for (var entry : in_map.entrySet()) {
			var pair = function.apply(entry.getKey(), entry.getValue());
			out_map.put(pair.first, pair.second);
		}
		return out_map;
	}
}
