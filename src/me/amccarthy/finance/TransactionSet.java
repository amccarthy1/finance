package me.amccarthy.finance;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class TransactionSet extends TreeSet<Transaction> {
    private int cachedTotal;
    private int cachedRange;

    private boolean recalculateTotal = true;
    private boolean recalculateRange = true;

    public TransactionSet() {
        super((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
    }

    private void clearCache() {
        recalculateTotal = true;
        recalculateRange = true;
    }

    public synchronized int total() {
        if (recalculateTotal) {
            cachedTotal = stream().mapToInt(Transaction::getAmount).sum();
            recalculateTotal = false;
        }
        return cachedTotal;
    }

    public synchronized int range() {
        if (recalculateRange) {
            Date minDate = null;
            Date maxDate = null;
            for (Transaction t : this) {
                if (minDate == null || minDate.compareTo(t.getDate()) > 0) {
                    minDate = t.getDate();
                }
                if (maxDate == null || maxDate.compareTo(t.getDate()) < 0) {
                    maxDate = t.getDate();
                }
            }
            if (minDate == maxDate) { // same date or null
                cachedRange = 0;
                return 0;
            }
            long longRange = (maxDate.getTime() - minDate.getTime());
            longRange = longRange / 1000;   // msec -> sec
            longRange = longRange / 60;     // sec  -> min
            longRange = longRange / 60;     // min  -> hour
            longRange = longRange / 24;     // hour -> day
            longRange = longRange / 30;     // day  -> month (approx)
            cachedRange = (int) longRange;
        }
        return cachedRange;
    }

    /**
     * Get the total per month of this group
     * @return
     *      total cost divided by the range in months of the group
     */
    // TODO Rethink range calculation. Should this be more precise? Should it
    //      be the total range of the transaction history instead?
    public synchronized int perMonth() {
        if (range() == 0) {
            return total(); // ~1 month
        }
        return total() / range();
    }

    // to cache total, override all mutating methods to clear the cache.
    @Override
    public synchronized boolean add(Transaction t) {
        clearCache();
        return super.add(t);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends Transaction> ts) {
        clearCache();
        return super.addAll(ts);
    }

    @Override
    public void clear() {
        clearCache();
        super.clear();
    }

    @Override
    public boolean remove(Object o) {
        clearCache();
        return super.remove(o);
    }
}
