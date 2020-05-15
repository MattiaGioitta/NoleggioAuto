package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	//Coda degli eventi
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	//PARAMETRI DI SIMULAZIONE
	private int NC = 10;//number of cars
	private Duration T_NC = Duration.of(10, ChronoUnit.MINUTES);//intervallo tra i clienti
	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(17, 00);
	
	
	
	//MODELLO DEL MONDO
	private int nAuto;//Auto disponibili nel deposito compreso tra 0 e NC
	
	
	//VALORI DA CALCOLARE
	private int clientiArrivati;
	private int clientiInsoddisfatti;
	

	//Metodi per impostare i parametri
	
	public void setNumCars(int N) {
		this.NC = N;
		}
	public void setClientFrequency(Duration of) {
		this.T_NC = of;		
	}
	
	//Metodi per restituire i risultati
	public int getClientiArrivati() {
		return clientiArrivati;
	}
	public int getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}
	
	//Simulazione
	
	public void run() {
		//preparazione iniziale (mondo + coda degli eventi)
		this.nAuto = this.NC;
		this.clientiArrivati = this.clientiInsoddisfatti = 0;
		
		this.queue.clear();
		
		LocalTime oraArrivoCliente = this.oraApertura;
		do {
			Event e = new Event(oraArrivoCliente, EventType.NEW_CLIENT);
			this.queue.add(e);
			oraArrivoCliente = oraArrivoCliente.plus(this.T_NC);
			
		}while(oraArrivoCliente.isBefore(oraChiusura));
		
		
		//esecuzione del ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			System.out.println(e);
			processEvent(e);
			
			
		}
	}
	private void processEvent(Event e) {
		switch(e.getType()) {
		case NEW_CLIENT:
			if(this.nAuto>0) {
				//cliente servito-->auto noleggiata
				//1.aggiorna modello del mondo
				this.nAuto--;
				//2.aggiorna i risultati
				this.clientiArrivati++;
				//3.genera nuovi eventi
				//restituire l'auto
				double num = Math.random();//[0,1)
				Duration travel;
				if(num<1.0/3.0)
					travel = Duration.of(1, ChronoUnit.HOURS);
				else if(num<2.0/3.0)
					travel = Duration.of(2, ChronoUnit.HOURS);
				else
					travel = Duration.of(3, ChronoUnit.HOURS);
				Event nuovo = new Event(e.getTime().plus(travel),EventType.CAR_RETURNED);
				this.queue.add(nuovo);
			}else {
				//cliente insoddisfatto
				this.clientiArrivati++;
				this.clientiInsoddisfatti++;
			}
			break;
		case CAR_RETURNED:
			this.nAuto++;
			
			break;
		}
	}
	
	
	
	}
