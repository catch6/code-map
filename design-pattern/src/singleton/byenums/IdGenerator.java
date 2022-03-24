package singleton.byenums;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Catch
 * @since 2022-03-24
 */
public enum IdGenerator {
    INSTANCE;

    private final AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.incrementAndGet();
    }
}
