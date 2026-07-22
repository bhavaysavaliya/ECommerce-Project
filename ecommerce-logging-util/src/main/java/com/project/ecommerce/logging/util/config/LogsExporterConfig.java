package com.project.ecommerce.logging.util.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for wiring the OpenTelemetry log appender
 * into the application's logging framework (Logback), conditional on
 * whether OTel export is enabled.
 *
 * <p>The {@link OpenTelemetryAppender} bridges log events emitted through
 * SLF4J/Logback to the OpenTelemetry logging pipeline, so application logs
 * are exported alongside traces and metrics to the configured OTLP endpoint
 * (see {@code management.opentelemetry.logging.export.otlp.endpoint} in
 * {@code application.yml}).</p>
 *
 * @see OtelProperties
 * @see OpenTelemetryAppender
 */
@Configuration
public class LogsExporterConfig {

    /**
     * Registers an {@link InitializingBean} that installs the OpenTelemetry
     * Logback appender once the application context has finished wiring
     * dependencies, but only when OTel is enabled via configuration.
     *
     * <p>This installation step is deferred to {@link InitializingBean}
     * (rather than performed at bean construction time) because
     * {@link OpenTelemetryAppender#install(OpenTelemetry)} attaches to the
     * Logback context statically, and should only run after the
     * {@link OpenTelemetry} SDK bean is fully constructed and available.</p>
     *
     * <p>When {@link OtelProperties#isEnabled()} returns {@code false},
     * installation is skipped entirely, so no OTel appender is attached and
     * no attempt is made to reach an OTLP collector — useful for local
     * development or environments where no collector is running.</p>
     *
     * @param openTelemetry  the configured {@link OpenTelemetry} SDK instance,
     *                       auto-configured by Spring Boot's OTel starter
     * @param otelProperties the bound {@link OtelProperties}, used to check
     *                       whether OTel export is enabled before installing
     *                       the appender
     * @return an {@link InitializingBean} callback that performs the
     *         conditional appender installation during context startup
     */
    @Bean
    public InitializingBean otelLogAppenderInstaller(OpenTelemetry openTelemetry, OtelProperties otelProperties) {
        return () -> {
            if (otelProperties.isEnabled()) {
                OpenTelemetryAppender.install(openTelemetry);
            }
        };
    }
}