package com.frandorado.springmicrometerundertow;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.TimeGauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.undertow.server.handlers.MetricsHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

@Component
public class UndertowMeterBinder implements ApplicationListener<ApplicationReadyEvent> {

    private final UndertowMetricsHandlerWrapper undertowMetricsHandlerWrapper;

    public UndertowMeterBinder(UndertowMetricsHandlerWrapper undertowMetricsHandlerWrapper) {
        this.undertowMetricsHandlerWrapper = undertowMetricsHandlerWrapper;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        bindTo(applicationReadyEvent.getApplicationContext().getBean(MeterRegistry.class));
    }

    public void bindTo(MeterRegistry meterRegistry) {
        bind(meterRegistry, undertowMetricsHandlerWrapper.getMetricsHandler());
    }

    public void bind(MeterRegistry registry, MetricsHandler metricsHandler) {
        bindTimer(registry, "undertow.requests", "Number of requests", metricsHandler,
                m -> m.getMetrics().getTotalRequests(), m2 -> m2.getMetrics().getMinRequestTime());
        bindTimeGauge(registry, "undertow.request.time.max", "The longest request duration in time", metricsHandler,
                m -> m.getMetrics().getMaxRequestTime());
        bindTimeGauge(registry, "undertow.request.time.min", "The shortest request duration in time", metricsHandler,
                m -> m.getMetrics().getMinRequestTime());
        bindCounter(registry, "undertow.request.errors", "Total number of error requests ", metricsHandler,
                m -> m.getMetrics().getTotalErrors());

    }

    private void bindTimer(MeterRegistry registry, String name, String desc, MetricsHandler metricsHandler,
                           ToLongFunction<MetricsHandler> countFunc, ToDoubleFunction<MetricsHandler> consumer) {
        FunctionTimer.builder(name, metricsHandler, countFunc, consumer, TimeUnit.MILLISECONDS)
                .description(desc).register(registry);
    }

    private void bindTimeGauge(MeterRegistry registry, String name, String desc, MetricsHandler metricResult,
                               ToDoubleFunction<MetricsHandler> consumer) {
        TimeGauge.builder(name, metricResult, TimeUnit.MILLISECONDS, consumer).description(desc)
                .register(registry);
    }

    private void bindCounter(MeterRegistry registry, String name, String desc, MetricsHandler metricsHandler,
                             ToDoubleFunction<MetricsHandler> consumer) {
        FunctionCounter.builder(name, metricsHandler, consumer).description(desc)
                .register(registry);
    }
}