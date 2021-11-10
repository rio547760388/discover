package com.ronhan.iso8583.discover;

import com.solab.iso8583.TraceNumberGenerator;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/19
 **/
public class CustomTraceNumberGenerator implements TraceNumberGenerator {
    private volatile int value = 0;

    private int pre;

    public CustomTraceNumberGenerator(int pre, int initialValue) {
        if (pre < 0 || pre > 9) {
            throw new IllegalArgumentException("pre value must be between 0 and 9");
        }
        this.pre = pre;

        if (initialValue >= 1 && initialValue <= 99999) {
            this.value = initialValue - 1;
        } else {
            throw new IllegalArgumentException("Initial value must be between 1 and 999999");
        }
    }

    @Override
    public synchronized int nextTrace() {
        ++this.value;
        if (this.value > 99999) {
            this.value = 1;
        }

        return this.value + (pre * 100000);
    }

    @Override
    public int getLastTrace() {
        return this.value;
    }
}
