public class Raster {

    private double[][] data;

    private int rows;
    private int columns;

    private double cellSize;
    private double noDataValue;

    private double originX;
    private double originY;

    private String coordinateReferenceSystem;


    // --------------------------------
    // Original Constructor
    // --------------------------------

    public Raster(
            double[][] data,
            double cellSize,
            double noDataValue) {

        this.data = data;

        this.rows = data.length;
        this.columns = data[0].length;

        this.cellSize = cellSize;
        this.noDataValue = noDataValue;

        // Default spatial information
        this.originX = 0.0;
        this.originY = 0.0;
        this.coordinateReferenceSystem = "Unknown";

        
    }


    // --------------------------------
    // Expanded Constructor
    // --------------------------------

    public Raster(
            double[][] data,
            double cellSize,
            double noDataValue,
            double originX,
            double originY,
            String coordinateReferenceSystem) {

        this.data = data;

        this.rows = data.length;
        this.columns = data[0].length;

        this.cellSize = cellSize;
        this.noDataValue = noDataValue;

        this.originX = originX;
        this.originY = originY;

        this.coordinateReferenceSystem =
            coordinateReferenceSystem;
    }


    // --------------------------------
    // Getters
    // --------------------------------

    public int getRows() {
        return rows;
    }


    public int getColumns() {
        return columns;
    }


    public double getCellSize() {
        return cellSize;
    }


    public double getNoDataValue() {
        return noDataValue;
    }


    public double getValue(int row, int column) {
        return data[row][column];
    }


    public double getOriginX() {
        return originX;
    }


    public double getOriginY() {
        return originY;
    }


    public String getCoordinateReferenceSystem() {
        return coordinateReferenceSystem;
    }
}