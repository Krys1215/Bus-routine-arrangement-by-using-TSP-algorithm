package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSP {
    // 小区数量
    //the number of destinations
    private int cityNum;
    // 距离矩阵
    //graph
    private int[][] distance;
    // 最优路径
    //best path
    private List<Integer> bestPath;
    // 最优距离
    //shortest distance
    private int bestDistance;

    public TSP(int cityNum, int[][] distance) {
        this.cityNum = cityNum;
        this.distance = distance;
        this.bestPath = new ArrayList<>();
        this.bestDistance = Integer.MAX_VALUE;
    }

    public void tsp() {
        // 初始化最优路径
        //initialize the best path
        bestPath.add(0);
        // 从小区0开始旅行
        //start from the first residence or stop
        travel(0, 1, 0, bestPath);
    }

    private void travel(int city, int n, int dis, List<Integer> path) {
        // 所有小区都已经访问过
        //check if all the stop is visited
        if (n == cityNum) {
            // 加上最后一次旅行的距离
            //plu the last distance
            dis += distance[city][0];
            // 更新最优解
            //update the best solution
            if (dis < bestDistance) {
                bestDistance = dis;
                bestPath = new ArrayList<>(path);
                bestPath.add(0);
            }
            return;
        }
// 遍历其他所有小区
        //traversal all the other destinations
        for (int i = 0; i < cityNum; i++) {
// 当前小区已经访问过，跳过
            //if visited, skip
            if (path.contains(i)) {
                continue;
            }
// 加上当前城市到下一个小区的距离
            //add on the distance till the next stop
            int newDis = dis + distance[city][i];
// 剪枝：如果当前距离已经大于已知的最优距离，则无需继续搜索
            //pruning
            if (newDis >= bestDistance) {
                continue;
            }
// 将当前城市加入路径
            //add the current destination to the path
            path.add(i);
// 继续旅行
            //keep travelling
            travel(i, n + 1, newDis, path);
// 回溯
            //recall
            path.remove(path.size() - 1);
        }
    }

    public List<Integer> getBestPath() {
        return bestPath;
    }

    public int getBestDistance() {
        return bestDistance;
    }

    public static void main(String[] args) {

        int[] selectedStation = passengerCall();
        ArrayList<Station> stations = stationCreation();
        ArrayList<Station> selectedStations = dynamicStationSelection(selectedStation,stations);
        //创建graph
        //Create the graph
        int[][]distance = new int[selectedStations.size()][selectedStations.size()];

        for (int i = 0; i < selectedStations.size(); i++) {
            distance[i] = selectedStations.get(i).distance;
        }

        int cityNum = selectedStations.size();
        TSP tsp = new TSP(cityNum, distance);

        tsp.tsp();
        //traverse the path and print the visited nodes.
        List<Integer> pathList = tsp.getBestPath();
        //System.out.print("Bus Parking Square -> ");
        for (int i = 0; i < pathList.size(); i++) {
            System.out.print(selectedStations.get(pathList.get(i)).name + " -> ");
        }
        //System.out.println("Bus Parking Square");
        System.out.print("END");
        System.out.println("\nThe shortest distance: " + tsp.getBestDistance() + "m");



    }
    //这个是一开始实验用的生成的矩阵 涵盖了每个点到每个点的距离
    //for test purpose
    public static int[][] mapCreating(){
        return new int[][]{
 //       FSKTM     MUTIARA 360     SC      EL      SKY     KTM     SPE600
 //	 	    0  		1 		2 		3  		4 		5 		6 		7
/*0*/	{	0, 		4400,	3800,	5100,	5600,	6500,	5900,	650}, //FSKTM
/*1*/	{	4000,	0,		1000,	2100,	3000,	3900,	6000,	4200},//MUTIARA
/*2*/	{	4300,	1100,	0,		3100,	3500,	4400,	6500,	3100},//360
/*3*/	{	5100,	1600,	1800,	0,		1500,	1500,	4300,	4600},//SC
/*4*/	{	5600,	2900,	3500,	1400,	0,		1000,	4400,	5100},//EL
/*5*/	{	6500,	3800,	4100,	1300,	1000,	0,		2600,	8100},//OS/SKY
/*6*/	{	4100,	2700,	3000,	2200,	2700,	2600,	0,		6000},//KTM
/*7*/	{	6500,	2600,	3100,	4000,	5100,	2600,	5300,	0   },//SPE
        };
    }
    //为了能动态的存储每个站的信息，还是创建了对象
    //station objects creating
    public static ArrayList<Station> stationCreation(){

        Station fsktm = new Station("Bus Parking Square",0, new int[]{0, 4400, 3800, 5100, 5600, 6500, 5900, 650});//start point
        Station mutiara = new Station("Mutiara Residence",1, new int[]{4000, 0, 1000, 2100, 3000, 3900, 6000, 4200});
        Station univ360 = new Station("Univ 360 Residence",2, new int[]{4300, 1100, 0, 3100, 3500, 4400, 6500, 3100});
        Station southCity = new Station("South City Plaza",3, new int[]{5100, 1600, 1800, 0, 1500, 1500, 4300, 4600});
        Station eastLake = new Station("East Lake Residence",4, new int[]{5600, 2900, 3500, 1400, 0, 1000, 4400, 5100});
        Station skyVilla = new Station("Skyvilla Residence",5, new int[]{6500, 3800, 4100, 1300, 1000, 0, 2600, 8100});
        Station ktm = new Station("KTM Serdang", 6, new int[]{4100, 2700, 3000, 2200, 2700, 2600, 0, 6000});
        Station spe = new Station("SPE",7, new int[]{6500, 2600, 3100, 4000, 5100, 2600, 5300, 0});
        ArrayList<Station> arrayList = new ArrayList<>();
        arrayList.add(fsktm);
        arrayList.add(mutiara);
        arrayList.add(univ360);
        arrayList.add(southCity);
        arrayList.add(eastLake);
        arrayList.add(skyVilla);
        arrayList.add(ktm);
        arrayList.add(spe);
        return arrayList;
    }
    //来读取用户的call，有就是1，没有就是0. 这个返回的数列就是可以摘选出“完整的数列中” 谁被挑选了。用1和0来区别
    //use 1s and 0s to know which stations is selected.
    public static int[] passengerCall(){

        int[]array = new int[8];
        Scanner scan = new Scanner(System.in);
        System.out.println("------Request pick-up:1, no:0------" +
                "\n   ---Default Start Point: FSKTM---");
        //System.out.print("FSKTM: ");
        //array[0] = scan.nextInt();
        array[0] = 1;
        System.out.print("Mutiara Residence: ");
        array[1] = scan.nextInt();
        System.out.print("Univ 360 Residence: ");
        array[2] = scan.nextInt();
        System.out.print("South City Plaza: ");
        array[3] = scan.nextInt();
        System.out.print("East Lake Residence: ");
        array[4] = scan.nextInt();
        System.out.print("Skyvilla Residence: ");
        array[5] = scan.nextInt();
        System.out.print("KTM Serdang: ");
        array[6] = scan.nextInt();
        System.out.print("SPE: ");
        array[7] = scan.nextInt();
        //TEST: System.out.println(Arrays.toString(array));
        return array;
    }
    //Using English to explain this method:
    //I found that if i wanted to let user to choose the station, i need to delete some of the stations inside the original whole arraylist
    //that contains all the stations. if i do so, the index will not be matched. e.g. for the row 0, column 2 means that the distance between
    //node0 to node2 is some value. but, if i delete one row, the distance will not be matched anymore.
    public static ArrayList<Station> dynamicStationSelection(int[]array, ArrayList<Station>arrayList){//array是选定的站
        //to store the selected station based on the "array" with 1s and 0s.
        ArrayList<Station> firstSelectedStation= new ArrayList<>();

        ArrayList<Integer>secondSelectedDistance = new ArrayList<>();
        //to select the previous selected stations
        for (int i = 0; i < array.length; i++) {
            if(array[i] == 1){
                firstSelectedStation.add(arrayList.get(i));
            }
        }
        //The indexes of selected elements inside the arrays of selected Stations' indexes is matched
        //so i just need to extract the selected indexes' array to initialize the graph
        for (int i = 0; i < firstSelectedStation.size(); i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[j] == 1){
                    secondSelectedDistance.add(firstSelectedStation.get(i).distance[j]);
                }
            }
            int[] arrayTemp = secondSelectedDistance.stream().mapToInt(k -> k).toArray();
            //to change the "distance" attribute to selected "distance" array.
            firstSelectedStation.get(i).setDistance(arrayTemp);
        }
        //TEST System.out.println(firstSelectedStation.get(1).name);
        return firstSelectedStation;
    }
}
class Station{
    String name;
    int index;
    int[] distance;

    public Station(String name, int index, int[] distance) {
        this.name = name;
        this.index = index;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public int[] getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setDistance(int[] distance) {
        this.distance = distance;
    }
}




