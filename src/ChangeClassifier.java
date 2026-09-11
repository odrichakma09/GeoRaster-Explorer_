public class ChangeClassifier {

    public Raster classify(Raster changeRaster) {

        int rows = changeRaster.getRows();
        int columns = changeRaster.getColumns();

        double[][] classifiedData = new double[rows][columns];

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) { 

                double change = changeRaster.getValue(row, column);

                if (change == changeRaster.getNoDataValue()) {

                    classifiedData[row][column] =
                        changeRaster.getNoDataValue();

                } else if (change > 0.05) {

                    classifiedData[row][column] = 1;

                } else if (change < -0.05) {

                    classifiedData[row][column] = -1;

                } else {

                    classifiedData[row][column] = 0;
                }
            }
        }

        return new Raster(
            classifiedData,
            changeRaster.getCellSize(),
            changeRaster.getNoDataValue() 
        );
    }
}
