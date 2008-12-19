
public interface Sendable {
	//This must return a string which can be passed to the constructor of the object
	//to recreate the object
	public String toSendString();
}
