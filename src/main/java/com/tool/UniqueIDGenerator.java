package com.tool;

/**
 * 全局唯一ID生成器
 * Created by Chenjie on 2017/7/12.
 */
public class UniqueIDGenerator {
	
	private final long workerId;
    private final long epoch = 1403854494756L;
    private final long workerIdBits = 10L;
    private final long maxWorkerId;
    private long sequence;
    private final long sequenceBits;
    private final long workerIdShift;
    private final long timestampLeftShift;
    private final long sequenceMask;
    private long lastTimestamp;
    private static UniqueIDGenerator flowIdWorker = new UniqueIDGenerator(1L);

    private  UniqueIDGenerator(long workerId) {
        this.getClass();
        this.maxWorkerId = ~(-1L << 10);
        this.sequence = 0L;
        this.sequenceBits = 12L;
        this.getClass();
        this.workerIdShift = 12L;
        this.getClass();
        this.getClass();
        this.timestampLeftShift = 12L + 10L;
        this.getClass();
        this.sequenceMask = ~(-1L << 12);
        this.lastTimestamp = -1L;
        if(workerId <= this.maxWorkerId && workerId >= 0L) {
            this.workerId = workerId;
        } else {
            throw new IllegalArgumentException(String.format("worker Id can\'t be greater than %d or less than 0", new Object[]{Long.valueOf(this.maxWorkerId)}));
        }
    }

    /**
     * 生成唯一ID
     *
     * @return 唯一ID
     */
    public static Long generateSysNo(){
        return UniqueIDGenerator.instance().nextId();
    }

    private synchronized long nextId() {
        long timestamp = timeGen();
        if(this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & this.sequenceMask;
            if(this.sequence == 0L) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }

        if(timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("clock moved backwards.Refusing to generate id for %d milliseconds", new Object[]{Long.valueOf(this.lastTimestamp - timestamp)}));
        } else {
            this.lastTimestamp = timestamp;
            this.getClass();
            return timestamp - 1403854494756L << (int)this.timestampLeftShift | this.workerId << (int)this.workerIdShift | this.sequence;
        }
    }

    private static UniqueIDGenerator instance() {
        return flowIdWorker;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = timeGen(); timestamp <= lastTimestamp; timestamp = timeGen()) {
        }

        return timestamp;
    }
    private static long timeGen() {
        return System.currentTimeMillis();
    }
}