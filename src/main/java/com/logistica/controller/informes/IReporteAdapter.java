package com.logistica.controller.informes;

import com.logistica.model.Compra;
import java.util.List;

public interface IReporteAdapter {
    void generarReporteCompras(List<Compra> compras, String filePath);
}
