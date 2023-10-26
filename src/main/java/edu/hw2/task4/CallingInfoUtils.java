package edu.hw2.task4;

public final class CallingInfoUtils {
    private final static int CALLING_METHOD_INDEX = 2;

    private CallingInfoUtils() {
    }

    /**
     * Provide info about who is called it.
     * @return record with caller class and method name
     */
    public static CallingInfo callingInfo() {
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        final StackTraceElement callerElement = stackTraceElements[CALLING_METHOD_INDEX];

        final String className = callerElement.getClassName();
        final String methodName = callerElement.getMethodName();

        return new CallingInfo(className, methodName);
    }
}
