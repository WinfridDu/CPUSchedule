public class Partition implements Comparable<Partition>{
    private int start;
    private int size;

    public Partition(int start, int size) {
        this.start = start;
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int compareTo(Partition o) {
        return this.start - o.getStart();
    }
}
