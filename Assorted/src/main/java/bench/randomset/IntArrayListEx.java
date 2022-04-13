/*
 * Copyright (c) 2010-2022 SEEK.  All rights reserved.
 */
package bench.randomset;


import java.io.Serial;
import java.util.Arrays;

import it.unimi.dsi.fastutil.ints.IntArrayList;

/**
 * FastUtil array list with extensions.
 */
public class IntArrayListEx extends IntArrayList {
    @Serial
    private static final long serialVersionUID = 1L;

    private IntArrayListEx(final int[] a) {
        super(a, false);
        size = a.length;
    }

    /**
     * Constructor with expected capacity.
     */
    public IntArrayListEx(final int capacity) {
        super(capacity);
    }

    /**
     * Assumes the index is always less than the size (i.e. the invariant is checked elsewhere, and saves another conditional check).
     */
    public int getIntUnchecked(final int index) {
        return this.a[index];
    }

    /**
     * Primitive int based sort.
     */
    public void sort() {
        Arrays.sort(this.a, 0, this.size);
    }

    /**
     * Wraps the provided array.
     */
    public static IntArrayListEx wrap(final int[] array) {
        return new IntArrayListEx(array);
    }
}

