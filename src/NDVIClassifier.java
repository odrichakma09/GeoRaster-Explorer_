public class NDVIClassifier {

    public Raster classify(Raster ndviRaster) {

        int rows = ndviRaster.getRows();
        int columns = ndviRaster.getColumns();

        double[][] classifiedData = new double[rows][columns];

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) {

                double ndvi = ndviRaster.getValue(row, column);

                if (ndvi == ndviRaster.getNoDataValue()) {

                    classifiedData[row][column] =
                        ndviRaster.getNoDataValue();

                } else if (ndvi < 0.0) {

                    classifiedData[row][column] = 1;

                } else if (ndvi < 0.2) {

                    classifiedData[row][column] = 2;

                } else if (ndvi < 0.5) {

                    classifiedData[row][column] = 3;

                } else {

                    classifiedData[row][column] = 4;
                }
            }
        }

        return new Raster(
            classifiedData,
            ndviRaster.getCellSize(),
            ndviRaster.getNoDataValue()
        );
    }
}
