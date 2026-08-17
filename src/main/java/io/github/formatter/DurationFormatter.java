package io.github.formatter;

import java.util.Objects;

/**
 * Utility class for formatting durations expressed in milliseconds.
 *
 * <p>The formatter supports long and short output formats and omits
 * zero-valued time units from the result.
 *
 * <p>Examples:
 * <pre>{@code
 * DurationFormatter.format(10_145);
 * // "10 seconds"
 *
 * DurationFormatter.format(3_214_000);
 * // "53 minutes 34 seconds"
 *
 * DurationFormatter.format(7_439_000, DurationFormat.SHORT);
 * // "2h 3m 59s"
 * }</pre>
 *
 * <p>Milliseconds that do not form a complete second are truncated.
 *
 * <p>This class is stateless and thread-safe.
 */
public final class DurationFormatter {

    private static final long MILLISECONDS_PER_SECOND = 1_000;
    private static final long SECONDS_PER_MINUTE = 60;
    private static final long MINUTES_PER_HOUR = 60;

    private DurationFormatter() {
        // Utility class
    }

    /**
     * Formats a duration using {@link DurationFormat#LONG}.
     *
     * @param milliseconds duration in milliseconds; must not be negative
     * @return the formatted duration
     * @throws IllegalArgumentException if the duration is negative
     */
    public static String format(long milliseconds) {
        return format(milliseconds, DurationFormat.LONG);
    }

    /**
     * Formats a duration using the specified output format.
     *
     * <p>Milliseconds are truncated to complete seconds and zero-valued
     * units are omitted.
     *
     * @param milliseconds duration in milliseconds; must not be negative
     * @param format output format; must not be {@code null}
     * @return the formatted duration
     * @throws IllegalArgumentException if the duration is negative
     * @throws NullPointerException if {@code format} is {@code null}
     */
    public static String format(
            long milliseconds,
            DurationFormat format) {

        validateMilliseconds(milliseconds);
        Objects.requireNonNull(format, "format must not be null");

        DurationComponents duration =
                DurationComponents.fromMilliseconds(milliseconds);

        return switch (format) {
            case LONG -> formatLong(duration);
            case SHORT -> formatShort(duration);
        };
    }

    private static void validateMilliseconds(long milliseconds) {
        if (milliseconds < 0) {
            throw new IllegalArgumentException(
                    "Duration cannot be negative: " + milliseconds
            );
        }
    }

    private static String formatLong(DurationComponents duration) {
        return buildResult(
                duration,
                "hours",
                "minutes",
                "seconds",
                "0 seconds"
        );
    }

    private static String formatShort(DurationComponents duration) {
        return buildResult(
                duration,
                "h",
                "m",
                "s",
                "0s"
        );
    }

    private static String buildResult(
            DurationComponents duration,
            String hourUnit,
            String minuteUnit,
            String secondUnit,
            String zeroValue) {

        StringBuilder result = new StringBuilder();

        appendUnit(result, duration.hours(), hourUnit);
        appendUnit(result, duration.minutes(), minuteUnit);
        appendUnit(result, duration.seconds(), secondUnit);

        return result.isEmpty()
                ? zeroValue
                : result.toString();
    }

    private static void appendUnit(
            StringBuilder result,
            long value,
            String unit) {

        if (value == 0) {
            return;
        }

        if (!result.isEmpty()) {
            result.append(' ');
        }

        result.append(value);

        if (isLongUnit(unit)) {
            result.append(' ');
        }

        result.append(unit);
    }

    private static boolean isLongUnit(String unit) {
        return unit.length() > 1;
    }

    private record DurationComponents(
            long hours,
            long minutes,
            long seconds) {

        private static DurationComponents fromMilliseconds(
                long milliseconds) {

            long totalSeconds =
                    milliseconds / MILLISECONDS_PER_SECOND;

            long seconds =
                    totalSeconds % SECONDS_PER_MINUTE;

            long totalMinutes =
                    totalSeconds / SECONDS_PER_MINUTE;

            long minutes =
                    totalMinutes % MINUTES_PER_HOUR;

            long hours =
                    totalMinutes / MINUTES_PER_HOUR;

            return new DurationComponents(
                    hours,
                    minutes,
                    seconds
            );
        }
    }
}