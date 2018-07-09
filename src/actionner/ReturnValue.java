package actionner;

public class ReturnValue {
	public enum Type {
		VALUE, VARIABLE, POINTER
	}
	
	public final Type type;
	public final int value;
	public final int borneMax;
	
	
	public ReturnValue(Type type, int value) {
		this.type = type;
		this.value = value;
		
		this.borneMax = value;
	}
	
	public ReturnValue(int valueMin, int valueMax) {
		this.type = Type.VALUE;
		
		this.value = valueMin;
		this.borneMax = valueMax;
	}
	
}
