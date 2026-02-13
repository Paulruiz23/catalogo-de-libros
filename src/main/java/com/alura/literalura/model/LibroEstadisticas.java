package com.alura.literalura.model;

import java.util.IntSummaryStatistics;

public class LibroEstadisticas {

    private final IntSummaryStatistics stats;

    public LibroEstadisticas(IntSummaryStatistics stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return String.format("""
                Estadísticas de descargas de libros:
                Total de libros: %d
                Descargas totales: %d
                Descargas promedio: %.2f
                Descargas mínimas: %d
                Descargas máximas: %d
                """,
                stats.getCount(),
                stats.getSum(),
                stats.getAverage(),
                stats.getMin(),
                stats.getMax()
        );
    }
}

