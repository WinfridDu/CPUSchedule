public class PCB implements Comparable<PCB>{

    private String name;
    private int time;
    private int rank;
    private int start;
    private int size;

    public PCB(String name, int time, int rank, int size) {
        this.name = name;
        this.time = time;
        this.rank = rank;
        this.size = size;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return start;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(PCB PCB) {
        return this.rank != PCB.rank ? PCB.rank - this.rank : this.time - PCB.time;
    }
}
