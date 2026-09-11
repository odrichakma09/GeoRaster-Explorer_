public class RasterAnalyzer {

    public double minimum(Raster raster) {

        double min = Double.POSITIVE_INFINITY;

        for (int row = 0; row < raster.getRows(); row++) {

            for (int column = 0; column < raster.getColumns(); column++) {

                double value = raster.getValue(row, column);

                if (value != raster.getNoDataValue() && value < min) {
                    min = value;
                }
            }
        }

        return min;
    }


    public double maximum(Raster raster) {

        double max = Double.NEGATIVE_INFINITY;

        for (int row = 0; row < raster.getRows(); row++) {

            for (int column = 0; column < raster.getColumns(); column++) {

                double value = raster.getValue(row, column);

                if (value != raster.getNoDataValue() && value > max) {
                    max = value;
                }
            }
        }

        return max;
    }


    public double mean(Raster raster) {

        double sum = 0.0;
        int count = 0;

        for (int row = 0; row < raster.getRows(); row++) {

            for (int column = 0; column < raster.getColumns(); column++) {

                double value = raster.getValue(row, column);

                if (value != raster.getNoDataValue()) {
                    sum += value;
                    count++;
                }
            }
        }

        return sum / count;
    }


    public int countValidCells(Raster raster) {

        int count = 0;

        for (int row = 0; row < raster.getRows(); row++) {

            for (int column = 0; column < raster.getColumns(); column++) {

                double value = raster.getValue(row, column);

                if (value != raster.getNoDataValue()) {
                    count++;
                }
            }
        }

        return count;
    }


    public int countNoDataCells(Raster raster) {

        int count = 0;

        for (int row = 0; row < raster.getRows(); row++) {

            for (int column = 0; column < raster.getColumns(); column++) {

                double value = raster.getValue(row, column);

                if (value == raster.getNoDataValue()) {
                    count++;
                }
            }
        }

        return count;
    }


    public double dataCoverage(Raster raster) {

        int totalCells = raster.getRows() * raster.getColumns();

        int validCells = countValidCells(raster);

        return ((double) validCells / totalCells) * 100;
    }


    public void printRaster(Raster raster) {

        for (int row = 0; row < raster.getRows(); row++) {

            for (int column = 0; column < raster.getColumns(); column++) {

                double value = raster.getValue(row, column);

                if (value == raster.getNoDataValue()) {
                    System.out.print("NoData\t");
                } else {
                    System.out.printf("%.3f\t", value);
                }
            }

            System.out.println();
        }
    }
}