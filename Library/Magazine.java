package Library;

public class Magazine extends LibraryItem {
    private int issueNumber;
    private boolean isMonthly;

    public Magazine(int id, String title, int year,int issueNumber, boolean isMonthly) {
        super(id,title,year);
        this.issueNumber = issueNumber;
        this.isMonthly = isMonthly;
    }


    @Override
    public String getDescription() {
        return "";
    }

}
