package movelableObjects;

public enum Direction {

	UP(0), DOWN(1), LEFT(2), RIGHT(3);
     int id;
	 Direction (int id){
		this.id = id;
	}
   public int getID() {
	   return id;
   }
}
