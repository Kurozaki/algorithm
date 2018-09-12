package core;

/**
 * Created by YotWei on 2018/9/8.
 */
public interface Metric<T> {

    int getMetric(T t1, T t2);
}
