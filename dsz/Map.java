package dsz;

import java.util.ArrayList;

public class Map {
	int XSize, YSize, totalSize;
	ArrayList<Integer> textureMap = new ArrayList<Integer>();
	ArrayList<Integer> colMap = new ArrayList<Integer>();
	
	void loadMap(String data){
		String[] dataArray = data.split("\\s+");
		XSize = Integer.parseInt(dataArray[0]);
		YSize = Integer.parseInt(dataArray[1]);
		totalSize = XSize * YSize;
		int size = totalSize+2;
		
		for(int i=0;i<size;i++){
			textureMap.add(Integer.parseInt(dataArray[i]));
			colMap.add(Integer.parseInt(dataArray[size+i]));
		}
	}

}
