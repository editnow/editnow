package pl.pl.mgr.editnow.service.queue;

public interface Sender<T> {

  void send(T data);

}
