package com.project.ecommerce.logging.util.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Type-safe configuration properties binding for OpenTelemetry (OTel) toggles.
 *
 * <p>Binds all properties under the {@code otel} prefix in the application's
 * configuration (e.g. {@code application.yml}) to this class, allowing OTel
 * behavior to be enabled or disabled per environment without code changes.</p>
 *
 * <p>Example configuration:</p>
 * <pre>{@code
 * otel:
 *   enabled: false
 * }</pre>
 *
 * <p>This class is registered as a Spring bean via {@link Component} and
 * activated for property binding via {@link ConfigurationProperties}, so it
 * is available for injection wherever OTel-related conditional logic is
 * needed (e.g. {@link LogsExporterConfig}).</p>
 *
 * <p>Lombok's {@link Getter} generates the {@code isEnabled()} accessor
 * for the boolean {@link #enabled} field at compile time.</p>
 *
 * @see LogsExporterConfig
 */
@Component
@ConfigurationProperties(prefix = "otel")
@Getter
public class OtelProperties {

    /**
     * Whether OpenTelemetry export (logs, metrics, tracing) is enabled.
     *
     * <p>Bound from the {@code otel.enabled} property. When {@code false},
     * OTel exporters and appenders configured elsewhere in the application
     * should remain inactive, allowing local/dev environments to run without
     * a reachable OTel collector.</p>
     */
    private boolean enabled;
}