package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje nasljeđuje sučelje Collection, 
 * daje ostatak popisa metoda potrebnih za potpunu implementaciju budućih kolekcija.
 * @author teakr
 *
 */
public interface List<T> extends Collection<T>{
	
	/**
	 * Metoda prima index te vraća element kolekcije na tom mjestu.
	 * @param index index s kojeg tražimo element.
	 * @return Object u kolekciji koji se nalazi na predanom indeksu.
	 */
	T get(int index);
	
	/**
	 * Metoda prima objekt i poziciju na koju dodaje predani objekt u kolekciju.
	 * @param value vrijednost objekta koji će dodati u kolekciju.
	 * @param position pozicija na koju želimo da doda predani objekt.
	 */
	void insert(T value, int position);
	
	/**
	 * Metoda prima vrijednost objekta i vraća prvu poziciju na kojoj se takav objekt nalazi u kolekciji.
	 * @param value objekt čiji nas indeks u kolekciji zanima.
	 * @return index na kojem se nalazi predani objekt.
	 */
	int indexOf(Object value);
	
	/**
	 * Metoda uklanja element koji se nalazi na mjestu primljenog indexa.
	 * @param index index s kojeg želimo ukloniti element.
	 */
	void remove(int index);


}
