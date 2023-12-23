package edu.hw10.task1;

import java.lang.reflect.Parameter;
import java.util.Random;
import java.util.UUID;

public sealed interface ParameterValueGenerator {
    Object gen(Parameter parameter, Random random);

    record PrimIntegerGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            int min = Integer.MIN_VALUE;
            if (parameter.isAnnotationPresent(MinValue.class)) {
                min = (int) parameter.getAnnotation(MinValue.class).value();
            }
            int max = Integer.MAX_VALUE;
            if (parameter.isAnnotationPresent(MaxValue.class)) {
                max = (int) parameter.getAnnotation(MaxValue.class).value();
            }
            return random.nextInt(min, max);
        }
    }

    record IntegerGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            int min = Integer.MIN_VALUE;
            if (parameter.isAnnotationPresent(MinValue.class)) {
                min = (int) parameter.getAnnotation(MinValue.class).value();
            }
            int max = Integer.MAX_VALUE;
            if (parameter.isAnnotationPresent(MaxValue.class)) {
                max = (int) parameter.getAnnotation(MaxValue.class).value();
            }
            boolean canBeNull = !parameter.isAnnotationPresent(MyNotNull.class);
            if (canBeNull && random.nextBoolean()) {
                return null;
            }
            return random.nextInt(min, max);
        }
    }

    record PrimLongGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            long min = Long.MIN_VALUE;
            if (parameter.isAnnotationPresent(MinValue.class)) {
                min = parameter.getAnnotation(MinValue.class).value();
            }
            long max = Long.MAX_VALUE;
            if (parameter.isAnnotationPresent(MaxValue.class)) {
                max = parameter.getAnnotation(MaxValue.class).value();
            }
            return random.nextLong(min, max);
        }
    }

    record LongGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            long min = Long.MIN_VALUE;
            if (parameter.isAnnotationPresent(MinValue.class)) {
                min = parameter.getAnnotation(MinValue.class).value();
            }
            long max = Long.MAX_VALUE;
            if (parameter.isAnnotationPresent(MaxValue.class)) {
                max = parameter.getAnnotation(MaxValue.class).value();
            }
            boolean canBeNull = !parameter.isAnnotationPresent(MyNotNull.class);
            if (canBeNull && random.nextBoolean()) {
                return null;
            }
            return random.nextLong(min, max);
        }
    }

    record PrimFloatGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            float min = Float.MIN_VALUE;
            if (parameter.isAnnotationPresent(MinValue.class)) {
                min = parameter.getAnnotation(MinValue.class).value();
            }
            float max = Float.MAX_VALUE;
            if (parameter.isAnnotationPresent(MaxValue.class)) {
                max = parameter.getAnnotation(MaxValue.class).value();
            }
            return random.nextFloat(min, max);
        }
    }

    record FloatGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            float min = Float.MIN_VALUE;
            if (parameter.isAnnotationPresent(MinValue.class)) {
                min = parameter.getAnnotation(MinValue.class).value();
            }
            float max = Float.MAX_VALUE;
            if (parameter.isAnnotationPresent(MaxValue.class)) {
                max = parameter.getAnnotation(MaxValue.class).value();
            }
            boolean canBeNull = !parameter.isAnnotationPresent(MyNotNull.class);
            if (canBeNull && random.nextBoolean()) {
                return null;
            }
            return random.nextFloat(min, max);
        }
    }

    record PrimDoubleGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            double min = Double.MIN_VALUE;
            if (parameter.isAnnotationPresent(MinValue.class)) {
                min = parameter.getAnnotation(MinValue.class).value();
            }
            double max = Double.MAX_VALUE;
            if (parameter.isAnnotationPresent(MaxValue.class)) {
                max = parameter.getAnnotation(MaxValue.class).value();
            }
            return random.nextDouble(min, max);
        }
    }

    record DoubleGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            double min = Double.MIN_VALUE;
            if (parameter.isAnnotationPresent(MinValue.class)) {
                min = parameter.getAnnotation(MinValue.class).value();
            }
            double max = Double.MAX_VALUE;
            if (parameter.isAnnotationPresent(MaxValue.class)) {
                max = parameter.getAnnotation(MaxValue.class).value();
            }
            boolean canBeNull = !parameter.isAnnotationPresent(MyNotNull.class);
            if (canBeNull && random.nextBoolean()) {
                return null;
            }
            return random.nextDouble(min, max);
        }
    }

    record PrimBooleanGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            return random.nextBoolean();
        }
    }

    record BooleanGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            boolean canBeNull = !parameter.isAnnotationPresent(MyNotNull.class);
            if (canBeNull && random.nextBoolean()) {
                return null;
            }
            return random.nextBoolean();
        }
    }

    record StringGenerator() implements ParameterValueGenerator {
        @Override
        public Object gen(Parameter parameter, Random random) {
            boolean canBeNull = !parameter.isAnnotationPresent(MyNotNull.class);
            if (canBeNull && random.nextBoolean()) {
                return null;
            }
            return UUID.randomUUID().toString();
        }
    }
}
