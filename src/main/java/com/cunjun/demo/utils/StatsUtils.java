package com.cunjun.demo.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/8
 */
public class StatsUtils {

    /**
     * 整数列表中的最大值
     */
    public static long maxOfLongList(List<Long> longList) {
        if (CollectionUtils.isEmpty(longList)) {
            throw new IllegalArgumentException("longList cannot be empty");
        }
        return longList.stream().max(Long::compare).get();
    }

    /**
     * 整数列表中的最小值
     */
    public static long minOfLongList(List<Long> longList) {
        if (CollectionUtils.isEmpty(longList)) {
            throw new IllegalArgumentException("longList cannot be empty");
        }
        return longList.stream().min(Long::compare).get();
    }

    /**
     * 小数列表中的最大值
     */
    public static double maxOfDoubleList(List<Double> doubleList) {
        if (CollectionUtils.isEmpty(doubleList)) {
            throw new IllegalArgumentException("doubleList cannot be empty");
        }
        return doubleList.stream().max(Double::compare).get();
    }

    /**
     * 小数列表中的最小值
     */
    public static double minOfDoubleList(List<Double> doubleList) {
        if (CollectionUtils.isEmpty(doubleList)) {
            throw new IllegalArgumentException("doubleList cannot be empty");
        }
        return doubleList.stream().min(Double::compare).get();
    }

    /**
     * 整数平均值，两位小数
     */
    public static double meanOfLongList(List<Long> longList) {
        if (CollectionUtils.isEmpty(longList)) {
            throw new IllegalArgumentException("longList cannot be empty");
        }
        double mean = longList.stream().mapToDouble(Long::doubleValue).average().orElse(0.0);
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(mean));
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 小数平均值，两位小数
     */
    public static double meanOfDoubleList(List<Double> doubleList) {
        if (CollectionUtils.isEmpty(doubleList)) {
            throw new IllegalArgumentException("doubleList cannot be empty");
        }
        double mean = doubleList.stream().mapToDouble(x -> x).average().orElse(0.0);
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(mean));
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 整数中位数
     */
    public static double medianOfLongList(List<Long> longList) {
        if (CollectionUtils.isEmpty(longList)) {
            throw new IllegalArgumentException("longList cannot be empty");
        }
        DoubleStream sorted = longList.stream().mapToDouble(Long::doubleValue).sorted();
        OptionalDouble optionalDouble = longList.size() % 2 == 0 ?
            sorted.skip(longList.size() / 2 - 1).limit(2).average() :
            sorted.skip(longList.size() / 2).findFirst();
        if (optionalDouble.isPresent()) {
            return optionalDouble.getAsDouble();
        }
        throw new IllegalArgumentException("不存在中位数");
    }

    /**
     * 小数中位数
     */
    public static double medianOfDoubleList(List<Double> doubleList) {
        if (CollectionUtils.isEmpty(doubleList)) {
            throw new IllegalArgumentException("doubleList cannot be empty");
        }
        DoubleStream sorted = doubleList.stream().mapToDouble(x -> x).sorted();
        OptionalDouble optionalDouble = doubleList.size() % 2 == 0 ?
            sorted.skip(doubleList.size() / 2 - 1).limit(2).average() :
            sorted.skip(doubleList.size() / 2).findFirst();
        if (optionalDouble.isPresent()) {
            return optionalDouble.getAsDouble();
        }
        throw new IllegalArgumentException("不存在中位数");
    }

    /**
     * 整数标准差
     */
    public static double standardDeviationOfLongList(List<Long> longList) {
        if (CollectionUtils.isEmpty(longList)) {
            throw new IllegalArgumentException("longList cannot be empty");
        }
        BigDecimal mean = new BigDecimal(String.valueOf(meanOfLongList(longList)));
        BigDecimal sumOfDiffWithMeanSquared = BigDecimal.ZERO;
        for (Long aLong : longList) {
            BigDecimal diffWithMean = new BigDecimal(String.valueOf(aLong)).subtract(mean);
            BigDecimal diffWithMeanSquared = new BigDecimal(String.valueOf(Math.pow(diffWithMean.doubleValue(), 2)));
            sumOfDiffWithMeanSquared = sumOfDiffWithMeanSquared.add(diffWithMeanSquared);
        }
        BigDecimal subOfDiffWithMeanDivideBySize = sumOfDiffWithMeanSquared.divide(new BigDecimal(String.valueOf(longList.size())));
        BigDecimal resultBigDecimal = new BigDecimal(String.valueOf(Math.sqrt(subOfDiffWithMeanDivideBySize.doubleValue())));
        return resultBigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 小数标准差
     */
    public static double standardDeviationOfDoubleList(List<Double> doubleList) {
        if (CollectionUtils.isEmpty(doubleList)) {
            throw new IllegalArgumentException("doubleList cannot be empty");
        }
        BigDecimal mean = new BigDecimal(String.valueOf(meanOfDoubleList(doubleList)));
        BigDecimal sumOfDiffWithMeanSquared = BigDecimal.ZERO;
        for (Double aDouble : doubleList) {
            BigDecimal diffWithMean = new BigDecimal(String.valueOf(aDouble)).subtract(mean);
            BigDecimal diffWithMeanSquared = new BigDecimal(String.valueOf(Math.pow(diffWithMean.doubleValue(), 2)));
            sumOfDiffWithMeanSquared = sumOfDiffWithMeanSquared.add(diffWithMeanSquared);
        }
        BigDecimal subOfDiffWithMeanDivideBySize = sumOfDiffWithMeanSquared.divide(new BigDecimal(String.valueOf(doubleList.size())));
        BigDecimal resultBigDecimal = new BigDecimal(String.valueOf(Math.sqrt(subOfDiffWithMeanDivideBySize.doubleValue())));
        return resultBigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
