package edu.hw10.task1;

@SuppressWarnings("ALL") public sealed interface TestClass {
    String s();
    final class Good implements TestClass {
        public String s;

        @Override
        public String s() {
            return s;
        }

        public Good() {
            s = "not widest public";
        }

        public Good(int i) {
            s = "widest public";
        }

        private Good(int i, int i2) {
            s = "widest but private";
        }

        public static TestClass create() {
            var t = new TestClass.Good();
            t.s += " factory";
            return t;
        }

        public static TestClass create(int i) {
            var t = new TestClass.Good(i);
            t.s += " factory";
            return t;
        }

        private static TestClass create(int i, int i2) {
            var t = new TestClass.Good(i, i2);
            t.s += " factory";
            return t;
        }
    }

    final class Bad implements TestClass {
        public String s;

        @Override
        public String s() {
            return s;
        }

        private Bad() {
        }

        private static TestClass create(int i, int i2) {
            return new Bad();
        }

        public static TestClass notCreate(int i, int i2) {
            return null;
        }
    }

    final class SubObject implements TestClass {
        public String s;

        @Override
        public String s() {
            return s;
        }

        public SubObject(Object o, @MyNotNull Object o2) {

        }
    }
}
