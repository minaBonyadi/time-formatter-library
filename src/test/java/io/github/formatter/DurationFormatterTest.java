package io.github.formatter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DurationFormatterTest {

    @Test
    void shouldFormatMillisecondsAsSeconds() {
        assertThat(DurationFormatter.format(10_145))
                .isEqualTo("10 seconds");
    }

    @Test
    void shouldTruncateRemainingMilliseconds() {
        assertThat(DurationFormatter.format(10_999))
                .isEqualTo("10 seconds");
    }

    @Test
    void shouldFormatZeroMilliseconds() {
        assertThat(DurationFormatter.format(0))
                .isEqualTo("0 seconds");
    }

    @Test
    void shouldFormatMinutesAndSeconds() {
        assertThat(DurationFormatter.format(214_000))
                .isEqualTo("3 minutes 34 seconds");
    }

    @Test
    void shouldIncludeZeroSecondsForWholeMinutes() {
        assertThat(DurationFormatter.format(300_000))
                .isEqualTo("5 minutes");
    }

    @Test
    void shouldFormatSecondsWithoutMinutes() {
        assertThat(DurationFormatter.format(59_000))
                .isEqualTo("59 seconds");
    }

    @Test
    void shouldHideZeroHours() {
        assertThat(DurationFormatter.format(3_214_000))
                .isEqualTo("53 minutes 34 seconds");
    }

    @Test
    void shouldFormatOneHourAndFiveMinutes() {
        assertThat(DurationFormatter.format(3_900_000))
                .isEqualTo("1 hours 5 minutes");
    }

    @Test
    void shouldSuppressZeroValuedHours() {
        assertThat(DurationFormatter.format(214_000))
                .isEqualTo("3 minutes 34 seconds");
    }

    @Test
    void shouldSuppressZeroValuedMinutes() {
        assertThat(DurationFormatter.format(3_659_000))
                .isEqualTo("1 hours 59 seconds");
    }

    @Test
    void shouldSuppressZeroValuedSeconds() {
        assertThat(DurationFormatter.format(3_600_000))
                .isEqualTo("1 hours");
    }

    @Test
    void shouldFormatHoursMinutesAndSeconds() {
        assertThat(DurationFormatter.format(7_439_000))
                .isEqualTo("2 hours 3 minutes 59 seconds");
    }

    @Test
    void shouldFormatLongFormatExplicitly() {
        assertThat(DurationFormatter.format(
                7_414_000,
                DurationFormat.LONG))
                .isEqualTo("2 hours 3 minutes 34 seconds");
    }

    @Test
    void shouldFormatShortFormat() {
        assertThat(DurationFormatter.format(
                7_414_000,
                DurationFormat.SHORT))
                .isEqualTo("2h 3m 34s");
    }

    @Test
    void shouldSuppressZeroUnitsInShortFormat() {
        assertThat(DurationFormatter.format(
                3_659_000,
                DurationFormat.SHORT))
                .isEqualTo("1h 59s");
    }

    @Test
    void shouldFormatZeroDurationInShortFormat() {
        assertThat(DurationFormatter.format(
                0,
                DurationFormat.SHORT))
                .isEqualTo("0s");
    }

    @Test
    void shouldRejectNegativeMilliseconds() {
        assertThatThrownBy(() -> DurationFormatter.format(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Duration cannot be negative: -1");
    }

    @Test
    void shouldRejectNullFormat() {
        assertThatThrownBy(() ->
                DurationFormatter.format(1_000, null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("format must not be null");
    }
}
