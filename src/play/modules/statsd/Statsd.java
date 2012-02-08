package play.modules.statsd;

import play.Logger;
import play.Play;
import play.PlayPlugin;

/**
 * This is a simple API wrapper for <a
 * href="https://github.com/etsy/statsd">StatsD</a>.  StatsD is a simple daemon
 * for stats aggregation (counters and timers).
 * 
 * Each "key" provided to a method of this class will define the <a
 * href="http://graphite.wikidot.com/">Graphite</a> bucket.
 * 
 * Statsd uses UDP to send information to the StatsD daemon.
 * 
 * @author Rich Kroll
 * 
 */
public class Statsd extends PlayPlugin {
	private static StatsdClient client;

	@Override
	public void onApplicationReady() {
		init();
	}

	private synchronized void init() {
		if (client == null) {
			String host = Play.configuration.getProperty("statsd.host",
					"localhost");
			String port = Play.configuration.getProperty("statsd.port", "8125");

			try {
				client = new StatsdClient(host, Integer.valueOf(port));
				Logger.info(
						"statsd client started successfully publishing stats to %s:%s",
						host, port);
			} catch (Exception e) {
				Logger.error(e, "Error starting statsd client");
			}
		}
	}

	/**
	 * Add a new timing statistic
	 * 
	 * @param key
	 *            Graphite bucket
	 * @param value
	 *            Time in ms
	 * @return
	 */
	public static boolean timing(String key, int value) {
		return client.timing(key, value);
	}

	/**
	 * Add a new timing statistic with a specific sample rate
	 * 
	 * @param key
	 *            Graphite bucket
	 * @param value
	 *            Time in ms
	 * @param sampleRate
	 *            For a sample rate of 0.1, it tells StatsD that this counter is
	 *            being sent sampled every 1/10th of the time.
	 * 
	 * @return
	 */
	public static boolean timing(String key, int value, double sampleRate) {
		return client.timing(key, value, sampleRate);
	}

	/**
	 * Decrement the specified counter by one.
	 * 
	 * @param key
	 *            Graphite bucket
	 * @return
	 */
	public static boolean decrement(String key) {
		return client.decrement(key);
	}

	/**
	 * Decrement the specified counter by the specified value.
	 * 
	 * @param key
	 *            Graphite key
	 * @param value
	 *            the amount to reduce
	 * @return
	 */
	public static boolean decrement(String key, int value) {
		return client.decrement(key, value);
	}

	/**
	 * Decrement the specified counter using the provided value and sample rate.
	 * 
	 * @param key
	 *            Graphite key
	 * @param value
	 *            the amount to reduce
	 * @param sampleRate
	 *            For a sample rate of 0.1, it tells StatsD that this counter is
	 *            being sent sampled every 1/10th of the time.
	 * @return
	 */
	public static boolean decrement(String key, int value, double sampleRate) {
		return client.decrement(key, value, sampleRate);
	}

	/**
	 * Decrement each of the provdied keys by one.
	 * 
	 * @param keys
	 *            Graphite buckets
	 * @return
	 */
	public static boolean decrement(String... keys) {
		return client.decrement(keys);
	}

	/**
	 * Decrement each of the keys by the supplied value.
	 * 
	 * @param value
	 *            the amound to reduce
	 * @param keys
	 *            Graphite buckets
	 * @return
	 */
	public static boolean decrement(int value, String... keys) {
		return client.decrement(value, keys);
	}

	/**
	 * Decrement each of the keys by the supplied value specifing a sample rate.
	 * 
	 * @param value
	 *            The value to reduce
	 * @param sampleRate
	 *            For a sample rate of 0.1, it tells StatsD that this counter is
	 *            being sent sampled every 1/10th of the time.
	 * @param keys
	 *            Graphite buckets
	 * @return
	 */
	public static boolean decrement(int value, double sampleRate,
			String... keys) {
		return client.increment(value, sampleRate, keys);
	}

	/**
	 * Increase the specified counter by one.
	 * 
	 * @param key
	 *            Graphite bucket
	 * @return
	 */
	public static boolean increment(String key) {
		return client.increment(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean increment(String key, int value) {
		return client.increment(key, value);
	}

	/**
	 * Increase the specified counter by the value provided, specifying a sample
	 * rate.
	 * 
	 * @param key
	 *            Graphite bucket
	 * @param value
	 *            amount to increase
	 * @param sampleRate
	 *            For a sample rate of 0.1, it tells StatsD that this counter is
	 *            being sent sampled every 1/10th of the time.
	 * @return
	 */
	public static boolean increment(String key, int value, double sampleRate) {
		return client.increment(key, value, sampleRate);
	}

	/**
	 * Increase a list of keys by the specified amount, specifying a sample
	 * rate.
	 * 
	 * @param value
	 *            The amount to increase
	 * @param sampleRate
	 *            For a sample rate of 0.1, it tells StatsD that this counter is
	 *            being sent sampled every 1/10th of the time.
	 * @param keys
	 *            Graphite Keys
	 * @return
	 */
	public static boolean increment(int value, double sampleRate,
			String... keys) {
		return client.increment(value, sampleRate, keys);
	}
}
