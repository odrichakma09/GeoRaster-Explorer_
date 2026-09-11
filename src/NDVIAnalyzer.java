public class NDVIAnalyzer {

    public Raster calculate(Raster nirRaster, Raster redRaster) {

        int rows = nirRaster.getRows();
        int columns = nirRaster.getColumns();

        double[][] ndviData = new double[rows][columns];

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) {

                double nir = nirRaster.getValue(row, column);
                double red = redRaster.getValue(row, column);

                if (nir == nirRaster.getNoDataValue()
                        || red == redRaster.getNoDataValue()) {

                    ndviData[row][column] = nirRaster.getNoDataValue();

                } else {

                    double denominator = nir + red;

                    if (denominator == 0) {
                        ndviData[row][column] = nirRaster.getNoDataValue();
                    } else {

                        double ndvi = (nir - red) / denominator;

                        ndviData[row][column] = ndvi;
                    }
                }
            }
        }

        return new Raster(
            ndviData,
            nirRaster.getCellSize(),
            nirRaster.getNoDataValue()
        );
    }
}