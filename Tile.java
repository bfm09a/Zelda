class Tile{
	private Entity layer1;
	private Entity layer2;//top layer
	int x;
	int y;

	Tile(String type, int startX, int startY, int h){
		layer1 = l1;
		layer2 = l2;
		x = startX;
		y = startY;
	}

	Tile(int startX, int startY){
		layer1 = l1;
		layer2 = l2;
		x = startX;
		y = startY;
	}

	public void setLayer1(Entity setter){
		layer1 = setter;
	}

	public void setLayer2(Entity setter){
		layer2 = setter;
	}

	public Entity getLayer1(){
		return layer1;
	}

	public Entity getLayer2(){
		return layer2;
	}
	public void draw(Graphics page){

}