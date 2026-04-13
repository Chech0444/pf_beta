package com.logistica.model.observer;
import com.logistica.model.Evento;

public interface IObserver {
    void updateEventoCancelado(Evento evento);
}
